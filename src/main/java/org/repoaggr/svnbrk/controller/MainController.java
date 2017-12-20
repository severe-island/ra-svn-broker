package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.*;
import org.repoaggr.svnbrk.model.exceptions.CacheException;
import org.repoaggr.svnbrk.model.exceptions.RemoteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tmatesoft.svn.core.SVNException;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;

import static org.repoaggr.svnbrk.configuration.Constants.*;

public final class MainController {
    private MainController() {}

    // Обновление даты синхронизации ------------------------------------------
    private static void updateMetadata(String id, Meta meta) throws IOException {
        meta.setLast_sync_date(new BigDecimal(Clock.systemDefaultZone().millis()));
        LocalCacheController.cachingObject(id, CACHE_META, meta);
    }

    // Регистрация репозитория в сервисе --------------------------------------
    public static ResponseEntity<RegistrationStatus> postRegistrationStatus(
            String url, String login, String password, String id)
    {
        try {
            // Если репозиторий уже зарегистрирован - вернуть ошибку.
            if (LocalCacheController.localExists(id))
                throw new CacheException(
                        "Repository '" + id + "' is already registered.");

            // Получение обзора удалённого репозитория
            Meta meta;
            if (login != null && password != null) {
                meta = new Meta(url, login, password);
            } else {
                meta = new Meta(url, "", "");
            }
            Overview overview = RemoteSvnController
                    .getOverview(id, meta);

            // Создание кэша списка коммитов и запуск их асинхронного подсчёта.
            BrokerList list = new BrokerList(STATUS_WARN,
                    W_COMMITS_PROCESSING);
            LocalCacheController.cachingObject(id, CACHE_COMMITS, list);
            AsyncLocalRemoteController.asyncGetCommitsList(id, meta);

            // Создание локальной записи
            LocalCacheController
                    .cachingObject(id, CACHE_OVERVIEW, overview);
            LocalCacheController
                    .cachingObject(id, CACHE_META, meta);

            return new ResponseEntity<>(
                    new RegistrationStatus(STATUS_SUCCESS, S_SUCCESS),
                    HttpStatus.CREATED
            );
        }
        catch (SVNException e) {
            throw new RemoteException(e.getMessage());
        }
        catch (IOException e) {
            throw new CacheException(e.getMessage());
        }
    }

    // Обзор репозитория ------------------------------------------------------
    public static ResponseEntity<Overview> getOverview(String id)
    {
        try {
            // Вытягивается локальная копия обзора
            Overview cachedOverview = (Overview) LocalCacheController
                    .uncachingObject(id, CACHE_OVERVIEW);
            Meta meta = (Meta) LocalCacheController
                    .uncachingObject(id, CACHE_META);

            // Возвращается BigDecimal.ZERO, если не удалось соединиться.
            BigDecimal lastRevisionDate = RemoteSvnController
                    .getLastSyncDate(meta);
            // Если нет соединения, то используется локальная копия.
            if (lastRevisionDate.equals(BigDecimal.ZERO)) {
                cachedOverview.setStatus(STATUS_WARN);
                cachedOverview.setReason(W_CANNOT_CONNECT);
                return new ResponseEntity<>(cachedOverview, HttpStatus.OK);
            }
            // Если даты совпадают, то используется локальная копия
            if (cachedOverview.getData().getLastSychDate()
                    .equals(lastRevisionDate)) {
                updateMetadata(id, meta);
                return new ResponseEntity<>(cachedOverview, HttpStatus.OK);
            }

            // Иначе - вытягивается с репозитория
            updateMetadata(id, meta);
            Overview overview = RemoteSvnController.getOverview(id, meta);

            return new ResponseEntity<>(overview, HttpStatus.OK);
        }
        catch (SVNException e) {
            throw new RemoteException(e.getMessage());
        }
        catch (IOException | ClassNotFoundException e) {
            throw new CacheException(e.getMessage());
        }
    }

    // Данные по коммиту ------------------------------------------------------
    public static ResponseEntity<Commit> getCommit(String repoId, String commitId)
    {
        try {
            Meta meta = (Meta) LocalCacheController
                    .uncachingObject(repoId, CACHE_META);
            CommitData data = RemoteSvnController
                    .getCommitMetaData(meta, commitId);
            RemoteSvnController.downloadCommit(meta, commitId, LocalCacheController
                    .dirTemp(repoId, CACHE_TEMP + commitId));
            data = LocalCacheController.parseCommitFile(
                    repoId, CACHE_TEMP + commitId, data);
            Files.delete(Paths.get(LocalCacheController
                    .dirTemp(repoId, CACHE_TEMP + commitId)));
            Commit commit = new Commit(STATUS_SUCCESS, S_SUCCESS);
            commit.setData(data);
            return new ResponseEntity<>(
                    commit,
                    HttpStatus.OK
            );
        }
        catch (SVNException e) {
            throw new RemoteException(e.getMessage());
        }
        catch (IOException | ClassNotFoundException e) {
            throw new CacheException(e.getMessage());
        }
    }

    // Данные по ветке --------------------------------------------------------
    public static ResponseEntity<Branch> getBranch(String repoId, String branchId)
    {
        try {
            Meta meta = (Meta) LocalCacheController.uncachingObject(repoId, CACHE_META);
            return new ResponseEntity<>(
                    RemoteSvnController.getBranch(meta, branchId),
                    HttpStatus.OK
            );
        }
        catch (SVNException e) {
            throw new RemoteException(e.getMessage());
        }
        catch (IOException | ClassNotFoundException e) {
            throw new CacheException(e.getMessage());
        }
    }

    // Список веток -----------------------------------------------------------
    public static ResponseEntity<BrokerList> getBranchesList(String id)
    {
        try {
            Meta meta = (Meta) LocalCacheController.uncachingObject(id, CACHE_META);
            return new ResponseEntity<>(
                    RemoteSvnController.getBranchesList(meta),
                    HttpStatus.OK
            );
        }
        catch (SVNException e) {
            throw new RemoteException(e.getMessage());
        }
        catch (IOException | ClassNotFoundException e) {
            throw new CacheException(e.getMessage());
        }
    }

    // Список коммитов --------------------------------------------------------
    public static ResponseEntity<BrokerList> getCommitsList(String id)
    {
        try {
            // Вытягивается локальная копия обзора
            Overview cachedOverview = (Overview) LocalCacheController
                    .uncachingObject(id, CACHE_OVERVIEW);
            Meta meta = (Meta) LocalCacheController
                    .uncachingObject(id, CACHE_META);
            BrokerList list = (BrokerList) LocalCacheController
                    .uncachingObject(id, CACHE_COMMITS);
            BigDecimal lastRevisionDate = RemoteSvnController
                    .getLastSyncDate(meta);
            // Если нет соединения, то используется локальная копия.
            if (lastRevisionDate.equals(BigDecimal.ZERO)) {
                cachedOverview.setStatus(STATUS_WARN);
                cachedOverview.setReason(W_CANNOT_CONNECT);
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            // Если даты совпадают, то используется локальная копия
            if (cachedOverview.getData().getLastSychDate()
                    .equals(lastRevisionDate)) {
                updateMetadata(id, meta);
                return new ResponseEntity<>(list, HttpStatus.OK);
            }

            AsyncLocalRemoteController.asyncGetCommitsList(id, meta);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (IOException | ClassNotFoundException e) {
            throw new CacheException(e.getMessage());
        }
    }
}
