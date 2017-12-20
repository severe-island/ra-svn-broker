package org.repoaggr.svnbrk.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MainControllerTest {
    private static final String REPO_ID = "__test__";
    private static final String COMMIT_ID = "962";
    private static final String BRANCH_ID = "trunk";
    private static final String URL_ID = "https://svn.apache.org/repos/asf";
    private static final String LOGIN = "";
    private static final String PASSWORD = "";

    @AfterAll
    static void removeTestRepo() {
        LocalCacheController.deleteDirectory(REPO_ID);
    }

    @BeforeAll
    static void postRegistrationStatus() {
        MainController.postRegistrationStatus(URL_ID, LOGIN, PASSWORD, REPO_ID);
    }

    @Test
    void getOverview() {
        MainController.getOverview(REPO_ID);
    }

    @Test
    void getCommit() {
        MainController.getCommit(REPO_ID, COMMIT_ID);
    }

    @Test
    void getBranch() {
        MainController.getBranch(REPO_ID, BRANCH_ID);
    }

    @Test
    void getBranchesList() {
        MainController.getBranchesList(REPO_ID);
    }

    @Test
    void getCommitsList() {
        MainController.getCommitsList(REPO_ID);
    }

}