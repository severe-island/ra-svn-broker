package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.Overview;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;

import static org.repoaggr.svnbrk.configuration.Constants.CACHE_OVERVIEW;

public class AsyncLocalRemoteController {

    // Подсчёт размеров репозитория -------------------------------------------
    public static void asyncGetRepositorySize(String id, SVNRepository repository)
            throws SVNException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
                long size = RemoteSvnController.getSize(repository);
                Overview overview = (Overview) LocalCacheController
                        .uncachingObject(id, CACHE_OVERVIEW);
                overview.setStatus("success");
                overview.setReason("success");
                overview.getData().setSize(new BigDecimal(size));
                LocalCacheController.cachingObject(id, CACHE_OVERVIEW, overview);
            }
            catch (SVNException | IOException
                    | ClassNotFoundException | InterruptedException e)
            {
                try {
                    Overview overview = (Overview) LocalCacheController
                            .uncachingObject(id, CACHE_OVERVIEW);
                    overview.setStatus("warning");
                    overview.setReason("Cannot calculate the size of repository."
                            + " Remote repository connection failure.");
                    overview.getData().setSize(BigDecimal.ZERO);
                    LocalCacheController.cachingObject(id, CACHE_OVERVIEW, overview);
                }
                catch (IOException | ClassNotFoundException e1)
                {
                    System.out.println(e1.getMessage());
                }
            }
        });
        t.start();
    }

    // Сбор коммитов ----------------------------------------------------------
    private static void asyncGetCommitsList() {

    }
}
