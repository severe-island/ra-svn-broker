package org.repoaggr.svnbrk.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.repoaggr.svnbrk.configuration.Constants.TEST_REPO_ID;

class MainControllerTest {
    private static final String COMMIT_ID = "962";
    private static final String BRANCH_ID = "trunk";
    private static final String URL_ID = "https://svn.apache.org/repos/asf";
    private static final String LOGIN = "";
    private static final String PASSWORD = "";

    @AfterAll
    static void removeTestRepo() {
        LocalCacheController.deleteDirectory(TEST_REPO_ID);
    }

    @BeforeAll
    static void postRegistrationStatus() {
        LocalCacheController.deleteDirectory(TEST_REPO_ID);
        MainController.postRegistrationStatus(URL_ID, LOGIN, PASSWORD, TEST_REPO_ID);
    }

    @Test
    void getOverview() {
        MainController.getOverview(TEST_REPO_ID);
    }

    @Test
    void getCommit() {
        MainController.getCommit(TEST_REPO_ID, COMMIT_ID);
    }

    @Test
    void getBranch() {
        MainController.getBranch(TEST_REPO_ID, BRANCH_ID);
    }

    @Test
    void getBranchesList() {
        MainController.getBranchesList(TEST_REPO_ID);
    }

    @Test
    void getCommitsList() {
        MainController.getCommitsList(TEST_REPO_ID);
    }

}