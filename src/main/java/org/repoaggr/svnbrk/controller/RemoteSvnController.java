package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.*;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.repoaggr.svnbrk.configuration.Constants.*;

public final class RemoteSvnController {
    private RemoteSvnController() { }

    // Подготовка репозитория
    private static SVNRepository getRepository(Meta meta)
            throws SVNException
    {
        SVNRepository repository = SVNRepositoryFactory
                .create(SVNURL.parseURIEncoded(meta.getUrl()));

        // Аутентификация
        if(meta.authNeeds()) {
            repository.setAuthenticationManager(
                    SVNWCUtil.createDefaultAuthenticationManager(
                            meta.getLogin(),
                            meta.getPassword()
                    )
            );
        }
        return repository;
    }

    // Получение размера директории репозитория
    public static long getSize(SVNRepository repository) throws SVNException {
        return getSize(repository, "/");
    }

    private static long getSize(SVNRepository repository, String path)
            throws SVNException {
        long size = 0;
        Collection entries = repository
                .getDir(path, -1, null, (Collection)null);
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            if ( entry.getKind() == SVNNodeKind.DIR) {
                size += getSize(repository,
                        (path.equals(""))
                                ? entry.getName()
                                : path + "/" + entry.getName());
            } else {
                size += entry.getSize();
            }
        }
        return size;
    }

    private static String getParent(String path) {
        String[] buffer = path.split("/");
        String parent = "";
        for(int i = 0; i < buffer.length - 1; i++) {
            parent += buffer[i] + "/";
        }
        return parent;
    }

    private static boolean isParent(String path, String parent) {
        return parent.equals(getParent(path));
    }
    private static boolean branchesFolderExist(SVNRepository repository) throws SVNException {
        Iterator iterator = repository.getDir(
                "/",
                repository.getLatestRevision(),
                null,
                (Collection)null
        ).iterator();
        for (iterator = iterator; iterator.hasNext();) {
            if (((SVNDirEntry)iterator.next()).getName().equals("branches"))
                return true;
        }
        return false;
    }

    // Заполнение объекта CommitData размерами файлов -------------------------
    private static void fillFileSizes(
            SVNRepository repository, CommitData data, long revision)
            throws SVNException
    {
        String parent = "\t";
        Collection entries = null;
        // Поскольку API не располагает средствами получения размеров файлов
        // напрямую, возникает необходимость делать это следующим методом:
        for (Iterator iterator = data.getFiles().iterator(); iterator.hasNext();){
            CommitDataFiles file = (CommitDataFiles) iterator.next();
            if(!isParent(file.getPath(), parent)) {
                parent = getParent(file.getPath());
                entries = repository
                        .getDir(parent, revision, null, (Collection)null);
            }
            for (Iterator iterator1 = entries.iterator(); iterator1.hasNext();) {
                SVNDirEntry entry = ((SVNDirEntry) iterator1.next());
                if((parent + entry.getName()).equals(file.getPath())){
                    if(!file.getFlag().equals("D"))
                        file.setSize(entry.getSize());
                    break;
                }
            }
        }
    }

    // Дата последнего коммита ------------------------------------------------
    public static BigDecimal getLastSyncDate(Meta meta) {
        try {
            SVNRepository repository = getRepository(meta);
            return new BigDecimal(
                    repository.info("/", repository.getLatestRevision())
                            .getDate()
                            .getTime()
            );
        }
        catch (SVNException e) {
            return BigDecimal.ZERO;
        }
    }

    // Обзор репозитория ------------------------------------------------------
    public static Overview getOverview(String id, Meta meta)
            throws SVNException
    {
        SVNRepository repository = getRepository(meta);
        OverviewData data = new OverviewData(
                new BigDecimal(repository.info("/", repository.getLatestRevision())
                        .getDate()
                        .getTime()
                ),
                "svn",
                meta.getUrl(),
                BigDecimal.ZERO
        );
        Overview overview = new Overview(STATUS_WARN, W_SIZE_PROCESSING);
        overview.setData(data);
        AsyncLocalRemoteController.asyncGetRepositorySize(id, repository);
        return overview;
    }

    // Получение данных по коммиту (без дельт) --------------------------------
    public static CommitData getCommitMetaData(Meta meta, String commitId)
        throws SVNException
    {
        long revision = Long.valueOf(commitId);
        SVNRepository repository = getRepository(meta);
        SVNLogEntry logEntry =(SVNLogEntry)repository.log(
                new String[]{""},
                null,
                revision,
                revision,
                true,
                true
        ).iterator().next();
        List<CommitDataFiles> files = new ArrayList<>();
        logEntry.getChangedPaths().keySet().forEach(
                path -> {
                    if(logEntry.getChangedPaths().get(path).getKind()
                            == SVNNodeKind.FILE) {
                        files.add(new CommitDataFiles(
                                path, "" + logEntry
                                .getChangedPaths().get(path).getType()
                        ));
                    }
                }
        );
        CommitData data = new CommitData(
                logEntry.getAuthor(),
                logEntry.getMessage(),
                new BigDecimal(logEntry.getDate().getTime()),
                files
        );
        fillFileSizes(repository, data, revision);
        return data;
    }

    // Вытягивание данных по дельтам коммита ----------------------------------
    public static void downloadCommit(Meta meta, String commitId, String tempPath)
            throws SVNException, NumberFormatException, IOException
    {
        long revision = Long.valueOf(commitId);
        long prerevision = revision - 1;
        FileOutputStream temp = new FileOutputStream(tempPath);
        SVNDiffClient diffClient = new SVNDiffClient(
                SVNWCUtil.createDefaultAuthenticationManager(
                        meta.getLogin(),
                        meta.getPassword()
                ), null
        );
        diffClient.doDiff(
                SVNURL.parseURIEncoded(meta.getUrl()),
                SVNRevision.create(prerevision),
                SVNURL.parseURIEncoded(meta.getUrl()),
                SVNRevision.create(revision),
                SVNDepth.INFINITY,
                true, temp
        );
        temp.close();
    }

    // Вытягивание данных по конкретной ветке ---------------------------------
    public static Branch getBranch(Meta meta, String branchId) throws SVNException {
        SVNRepository repository = getRepository(meta);
        // Если не ветка trunk
        if (!branchId.equals("trunk")) {
            String path = "/branches/" + branchId;
            SVNLogEntry logEntry = (SVNLogEntry) repository.log(
                    new String[]{path},
                    null,
                    0,
                    repository.getLatestRevision(),
                    false,
                    true
            ).iterator().next();
            String lock = ((SVNDirEntry) repository.getDir(
                    path,
                    logEntry.getRevision(),
                    null,
                    (Collection) null
            ).iterator().next()).getLock() == null ? "unlocked" : "locked";
            BranchData data = new BranchData(
                    branchId,
                    lock,
                    new BigDecimal(logEntry.getDate().getTime()),
                    String.valueOf(logEntry.getRevision()),
                    logEntry.getAuthor()
            );
            return new Branch(STATUS_SUCCESS, S_SUCCESS, data);
        }
        else {
            SVNLogEntry logEntry = (SVNLogEntry) repository.log(
                    new String[]{ "/" },
                    null,
                    1,
                    1,
                    false,
                    true
            ).iterator().next();
            BranchData data = new BranchData(
                    branchId,
                    "unlocked",
                    new BigDecimal(logEntry.getDate().getTime()),
                    "1",
                    logEntry.getAuthor()
            );
            return new Branch(STATUS_SUCCESS, S_SUCCESS, data);
        }
    }

    // Вытягивание списка веток -----------------------------------------------
    public static BrokerList getBranchesList(Meta meta) throws SVNException {
        SVNRepository repository = getRepository(meta);
        List<String> list = new ArrayList<>();
        list.add("trunk");
        if(branchesFolderExist(repository)) {
            repository.getDir(
                    "/branches",
                    repository.getLatestRevision(),
                    null,
                    (Collection) null
            ).forEach(item -> list.add(((SVNDirEntry) item).getName()));
        }
        return new BrokerList(STATUS_SUCCESS, S_SUCCESS, list);
    }

    // Вытягивание списка коммитов --------------------------------------------
    public static BrokerList getCommitsList(Meta meta) throws SVNException {
        SVNRepository repository = getRepository(meta);
        List<String> list = new ArrayList<>();
        for(long i = 1; i <= repository.getLatestRevision(); i++) {
            System.out.println(i);
            list.add(String.valueOf(i));
        }
        return new BrokerList(STATUS_SUCCESS, S_SUCCESS, list);
    }
}
