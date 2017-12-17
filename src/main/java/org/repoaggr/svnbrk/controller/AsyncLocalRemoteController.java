package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.BrokerList;
import org.repoaggr.svnbrk.model.Meta;
import org.repoaggr.svnbrk.model.Overview;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;

import static org.repoaggr.svnbrk.configuration.Constants.*;

public class AsyncLocalRemoteController {

    // Подсчёт размеров репозитория -------------------------------------------
    public static void asyncGetRepositorySize(String id, SVNRepository repository)
            throws SVNException {
        Thread t = new Thread(() -> {
            try {
                // Задержка, чтобы минимизировать конфликты с записью файла.
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
                    overview.setStatus(STATUS_WARN);
                    overview.setReason(W_SIZE_PROCESSING_FAIL + " "
                            + e.getMessage());
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
    public static void asyncGetCommitsList(String id, Meta meta) {
        Thread t = new Thread(() -> {
            try {
                // Задержка, чтобы минимизировать конфликты с записью файла.
                Thread.sleep(2000);
                BrokerList list = RemoteSvnController.getCommitsList(meta);
                LocalCacheController.cachingObject(id, CACHE_COMMITS, list);
            }
            catch (SVNException | IOException | InterruptedException e) {
                try {
                    BrokerList list = (BrokerList) LocalCacheController
                            .uncachingObject(id, CACHE_COMMITS);
                    list.setStatus(STATUS_WARN);
                    list.setReason(W_COMMITS_PROCESSING_FAIL + " "
                            + e.getMessage());
                }
                catch (IOException | ClassNotFoundException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        });
        t.start();
    }
}
