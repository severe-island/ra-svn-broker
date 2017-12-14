package org.repoaggr.svnbrk.controller;

import jdk.vm.ci.meta.MemoryAccessProvider;
import org.repoaggr.svnbrk.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.joda.time.DateTime;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.*;

public final class RemoteSvnController {
    private RemoteSvnController() { }

    // Служебное --------------------------------------------------------------
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
    private static long getSize(SVNRepository repository) throws SVNException {
        // СДЕЛАТЬ РАСЧЁТ РАЗМЕРА АСИНХРОННЫМ!
        return getSize(repository, "/");
    }

    private static long getSize(SVNRepository repository, String path)
            throws SVNException {
        long size = 0;
        Collection entries = repository.getDir(path, -1, null, (Collection)null);
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

    // Заполнение объекта CommitData размерами файлов
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
                entries = repository.getDir(parent, revision, null, (Collection)null);
            }
            for (Iterator iterator1 = entries.iterator(); iterator1.hasNext();) {
                SVNDirEntry entry = ((SVNDirEntry) iterator1.next());
                //System.out.println(entry.getName());
                if((parent + entry.getName()).equals(file.getPath())){
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
    public static Overview getOverview(Meta meta)
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
                new BigDecimal(getSize(repository))
        );
        Overview overview = new Overview("success", "success");
        overview.setData(data);
        return overview;
    }

    // Список коммитов --------------------------------------------------------
    public static ResponseEntity<List> getCommitsList(String id) {
        // ЗАГЛУШКА!
        ArrayList list = new ArrayList<String>();
        list.add("r111");
        list.add("r112");
        list.add("r113");
        list.add("r114");
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    // Данные по коммиту ------------------------------------------------------
    public static ResponseEntity<Commit> getCommit(String repoId, String commitId) {
        // ЗАГЛУШКА!
        ArrayList<CommitDataFiles> files = new ArrayList<CommitDataFiles>();
        /*files.add(new CommitDataFiles(
                1,
                "A",
                1,
                0,
                "x.cpp"
        ));
        files.add(new CommitDataFiles(
                1,
                "A",
                1,
                0,
                "y.cpp"
        ));*/
        /*
        CommitData data = new CommitData(
                new BigDecimal(Clock.systemDefaultZone().millis()),
                "example",
                "Mario",
                2,
                0,
                "master",
                files
        );*/
        Commit commit = new Commit(
                "success",
                "success"
        );
        return new ResponseEntity<Commit>(commit, HttpStatus.OK);
    }

    // Список веток -----------------------------------------------------------
    public static ResponseEntity<List> getBranchesList(String id) {
        // ЗАГЛУШКА!
        ArrayList list = new ArrayList<String>();
        list.add("master");
        list.add("slave");
        list.add("omega");
        list.add("SPARTAAAAA!");
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    // Данные по ветке --------------------------------------------------------
    public static ResponseEntity<Branch> getBranch(String repoId, String branchId) {
        // ЗАГЛУШКА!
        BranchData data = new BranchData(
                "master",
                "unlocked",
                new BigDecimal(Clock.systemDefaultZone().millis()),
                "r1",
                "mario"
        );
        Branch branch = new Branch("success", "success", data);
        return new ResponseEntity<Branch>(branch, HttpStatus.OK);
    }

    // Вытягивание данных по коммиту ------------------------------------------
    public static Commit getCommitInfo(Meta meta, String commitId, CommitData data)
        throws SVNException
    {
        long revision = Long.valueOf(commitId);
        SVNRepository repository = getRepository(meta);
        List<String> l_filenames = new ArrayList<>();
        data.getFiles().forEach(item -> l_filenames.add(item.getPath()));
        String[] a_filenames = new String[l_filenames.size()];

        //SVNLogEntry logEntry = (SVNLogEntry)
        Iterator iter = repository.log(
                l_filenames.toArray(a_filenames),
                null,
                revision,
                revision,
                true,
                true
        ).iterator();//.next();
        SVNLogEntry logEntry = (SVNLogEntry) iter.next();
        data.setCommitter(logEntry.getAuthor());
        data.setMessage(logEntry.getMessage());
        data.setCommittedAt(new BigDecimal(logEntry.getDate().getTime()));
        fillFileSizes(repository, data, revision);

        /*for (int i = 0; i < data.getFiles().size(); i++) {
            String p = "/" + data.getFiles().get(i).getPath();
            SVNLogEntryPath path = logEntry
                    .getChangedPaths()
                    .get(p);
            data.getFiles().get(i).setFlag("" + path.getType());
        }*/

        /*
        logEntry.getChangedPaths().keySet().forEach(x -> System.out.println(x));
        System.out.println("-----");
        for (String name : logEntry.getChangedPaths().keySet()) {
            SVNLogEntryPath file = logEntry.getChangedPaths().get(name);
            System.out.println(file.getPath());
            if(file.getCopyPath() != null)
                System.out.println(file.getCopyPath());
            //System.out.println(file.getPath());
            //System.out.println(path.getPath());
            //System.out.println("/" + file.getPath());
        }*/
        Commit commit = new Commit("success", "success");
        commit.setData(data);
        return commit;
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
}
