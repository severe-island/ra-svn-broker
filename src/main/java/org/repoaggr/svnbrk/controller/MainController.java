package org.repoaggr.svnbrk.controller;

import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.repoaggr.svnbrk.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.List;

import static org.repoaggr.svnbrk.configuration.Constants.CACHE_META;
import static org.repoaggr.svnbrk.configuration.Constants.CACHE_OVERVIEW;
import static org.repoaggr.svnbrk.configuration.Constants.CACHE_TEMP;

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
            throws SVNException, IOException
    {
        // Если репозиторий уже зарегистрирован - вернуть ошибку.
        if(LocalCacheController.localExists(id))
            throw new UnsupportedOperationException(
                    "Repository " + id + " is already registered.");

        // Получение обзора удалённого репозитория
        Meta meta;
        if(login != null && password != null) {
            meta = new Meta(url, login, password);
        }
        else {
            meta = new Meta(url,"", "");
        }
        Overview overview = RemoteSvnController
                .getOverview(id, meta);

        // Создание локальной записи
        LocalCacheController
                .cachingObject(id, CACHE_OVERVIEW, overview);
        LocalCacheController
                .cachingObject(id, CACHE_META, meta);

        return new ResponseEntity<RegistrationStatus>(
                new RegistrationStatus("success", "success"),
                HttpStatus.CREATED
        );
    }

    // Обзор репозитория ------------------------------------------------------
    public static ResponseEntity<Overview> getOverview(String id)
            throws SVNException, IOException, ClassNotFoundException
    {
        // Вытягивается локальная копия обзора
        Overview cachedOverview = (Overview) LocalCacheController
                .uncachingObject(id, CACHE_OVERVIEW);
        Meta meta = (Meta) LocalCacheController
                .uncachingObject(id, CACHE_META);

        // Возвращается BigDecimal.ZERO, если не удалось соединиться.
        BigDecimal lastRevisionDate = RemoteSvnController
                .getLastSyncDate(meta);
        // Если нет соединения, то используется локальная копия.
        if(lastRevisionDate.equals(BigDecimal.ZERO)) {
            cachedOverview.setStatus("warning");
            cachedOverview.setReason("Cannot connect to remote repository. Cached data is used.");
            return new ResponseEntity<Overview>(cachedOverview, HttpStatus.OK);
        }
        // Если даты совпадают, то используется локальная копия
        if (cachedOverview.getData().getLastSychDate()
                .equals(lastRevisionDate)) {
            updateMetadata(id, meta);
            return new ResponseEntity<Overview>(cachedOverview, HttpStatus.OK);
        }

        // Иначе - вытягивается с репозитория
        updateMetadata(id, meta);
        Overview overview = RemoteSvnController.getOverview(id, meta);

        return new ResponseEntity<Overview>(overview, HttpStatus.OK);
    }

    // Данные по коммиту ------------------------------------------------------
    public static ResponseEntity<Commit> getCommit(String repoId, String commitId)
        throws SVNException, IOException, ClassNotFoundException
    {
        Meta meta = (Meta) LocalCacheController.uncachingObject(repoId, CACHE_META);
        CommitData data = RemoteSvnController.getCommitMetaData(meta, commitId);
        RemoteSvnController.downloadCommit(meta, commitId,
                LocalCacheController.dirTemp(repoId, CACHE_TEMP + commitId));
        data = LocalCacheController.parseCommitFile(repoId, CACHE_TEMP + commitId, data);
        Files.delete(Paths.get(LocalCacheController.dirTemp(repoId, CACHE_TEMP + commitId)));
        Commit commit = new Commit("success", "success");
        commit.setData(data);
        return new ResponseEntity<>(
                commit,
                HttpStatus.OK
        );
    }

    // Данные по ветке --------------------------------------------------------
    public static ResponseEntity<Branch> getBranch(String repoId, String branchId)
        throws SVNException, IOException, ClassNotFoundException
    {
        Meta meta = (Meta) LocalCacheController.uncachingObject(repoId, CACHE_META);
        return new ResponseEntity<Branch>(
                RemoteSvnController.getBranch(meta, branchId),
                HttpStatus.OK
        );
    }

    // Список веток -----------------------------------------------------------
    public static ResponseEntity<BrokerList> getBranchesList(String id)
        throws SVNException, IOException, ClassNotFoundException
    {
        Meta meta = (Meta) LocalCacheController.uncachingObject(id, CACHE_META);
        return new ResponseEntity<BrokerList>(
                RemoteSvnController.getBranchesList(meta),
                HttpStatus.OK
        );
    }

    // Список коммитов --------------------------------------------------------
    public static ResponseEntity<BrokerList> getCommitsList(String id)
            throws SVNException, IOException, ClassNotFoundException
    {
        Meta meta = (Meta) LocalCacheController.uncachingObject(id, CACHE_META);
        return new ResponseEntity<BrokerList>(
                RemoteSvnController.getCommitsList(meta),
                HttpStatus.OK
        );
    }
}
