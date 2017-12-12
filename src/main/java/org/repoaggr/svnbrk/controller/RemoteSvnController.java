package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.joda.time.DateTime;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class RemoteSvnController {
    private RemoteSvnController() { }

    // Служебное --------------------------------------------------------------
    // Подготовка репозитория
    private static SVNRepository getRepository(String url)
        throws SVNException
    {
        return SVNRepositoryFactory
                .create(SVNURL.parseURIEncoded(url));
    }

    private static SVNRepository getRepository(
            String url, Meta meta)
            throws SVNException
    {
        SVNRepository repository = SVNRepositoryFactory
                .create(SVNURL.parseURIEncoded(url));

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

    // ЗАКОНЧИТЬ!!!
    public static BigDecimal getLastSyncDate() {
        return new BigDecimal(0);
    }

    // Тест соединения с удалённым репозиторием -------------------------------
    public static boolean isRemoteConnection(String url) {
        try {
            SVNRepository repository = getRepository(url);
            repository.testConnection();
            return true;
        } catch (SVNException e) {
            return false;
        }
    }

    public static boolean isRemoteConnection(
            String url, Meta meta) {
        try {
            SVNRepository repository = getRepository(url, meta);
            repository.testConnection();
            return true;
        } catch (SVNException e) {
            return false;
        }
    }

    // Обзор репозитория ------------------------------------------------------
    public static Overview getOverview(String url)
        throws SVNException
    {
        SVNRepository repository = getRepository(url);
        OverviewData data = new OverviewData(
                new BigDecimal(Clock.systemDefaultZone().millis()),
                "svn",
                url,
                new BigDecimal(getSize(repository))
        );
        Overview overview = new Overview("success", "success");
        overview.setData(data);
        return overview;
    }

    public static Overview getOverview(
            String url, Meta meta)
            throws SVNException
    {
        SVNRepository repository = getRepository(url, meta);
        OverviewData data = new OverviewData(
                new BigDecimal(Clock.systemDefaultZone().millis()),
                "svn",
                url,
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
        files.add(new CommitDataFiles(
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
        ));
        CommitData data = new CommitData(
                new BigDecimal(Clock.systemDefaultZone().millis()),
                "example",
                "Mario",
                2,
                0,
                "master",
                files
        );
        Commit commit = new Commit(
                "success",
                "success",
                data
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
}
