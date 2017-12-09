package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public final class RemoteSvnController {
    private RemoteSvnController() { }

    // Регистрация репозитория в сервисе --------------------------------------
    public static ResponseEntity<RegistrationStatus> postRegistrationStatus(
            String url, String login, String password, String id) {
        try {
            SVNRepository repository = SVNRepositoryFactory
                    .create(SVNURL.parseURIEncoded(url));

            // Аутентификация
            if(!login.isEmpty() || !password.isEmpty()) {
                ISVNAuthenticationManager authenticationManager =
                        SVNWCUtil.createDefaultAuthenticationManager(
                                login, password
                        );
                repository.setAuthenticationManager(authenticationManager);
            }
            repository.testConnection();
            // сюда докинуть логику кэширования
            return new ResponseEntity<RegistrationStatus>(
                    new RegistrationStatus("success", "success"),
                    HttpStatus.CREATED
            );
        } catch(SVNException e) {
            return new ResponseEntity<RegistrationStatus>(
                    new RegistrationStatus("failure", e.getMessage()),
                    HttpStatus.FORBIDDEN
            );
        }
    }

    // Обзор репозитория ------------------------------------------------------
    public static ResponseEntity<Overview> getOverview(String id) {
        // ЗАГЛУШКА!
        Overview overview = new Overview("success", "success");
        OverviewData data = new OverviewData(
                new BigDecimal(Clock.systemDefaultZone().millis()),
                "svn",
                "xep",
                new BigDecimal(0)
        );
        overview.setData(data);
        return new ResponseEntity<Overview>(overview, HttpStatus.OK);
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
