package org.repoaggr.svnbrk.controller;

import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
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

public final class MainController {
    private MainController() {}

    private static final String CACHE_TEMP = "temp";
    private static final String CACHE_OVERVIEW = "overview";
    private static final String CACHE_META = "_meta";

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
                    .getOverview(meta);

            // Создание локальной записи
            LocalCacheController
                    .cachingObject(id, CACHE_OVERVIEW, overview);
            LocalCacheController
                    .cachingObject(id, CACHE_META, meta);

            return new ResponseEntity<RegistrationStatus>(
                    new RegistrationStatus("success", "success"),
                    HttpStatus.CREATED
            );
        } catch(SVNException e) {
            return new ResponseEntity<RegistrationStatus>(
                    new RegistrationStatus(
                            "failure",
                            "Registration not completed: " + e.getMessage()),
                    HttpStatus.FORBIDDEN
            );
        } catch(IOException e) {
            return new ResponseEntity<RegistrationStatus>(
                    new RegistrationStatus(
                            "failure",
                            "Registration not completed: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
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

            // Возвращается 0, если не удалось соединиться.
            BigDecimal lastRevisionDate = RemoteSvnController
                    .getLastSyncDate(meta);
            // Если нет соединения, то используется локальная копия
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
            Overview overview = RemoteSvnController.getOverview(meta);

            return new ResponseEntity<Overview>(overview, HttpStatus.OK);
        }
        catch (SVNException e) {
            return new ResponseEntity<Overview>(
                    new Overview(
                            "failure",
                            "Remote repository failure: " + e.getMessage()),
                    HttpStatus.FORBIDDEN
            );
        }
        catch (IOException e) {
            return new ResponseEntity<Overview>(
                    new Overview(
                            "failure",
                            "Cache failure: " + e.getMessage()),
                    HttpStatus.FORBIDDEN
            );
        }
        catch (ClassNotFoundException e) {
            return new ResponseEntity<Overview>(
                    new Overview("failure", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // Данные по коммиту ------------------------------------------------------
    public static ResponseEntity<Commit> getCommit(String repoId, String commitId) {
        try {
            Meta meta = (Meta) LocalCacheController.uncachingObject(repoId, CACHE_META);
            RemoteSvnController.downloadCommit(meta, commitId,
                    LocalCacheController.dirTemp(repoId, CACHE_TEMP));
            CommitData data = LocalCacheController.parseCommitFile(repoId, CACHE_TEMP);
            //Files.delete(Paths.get(LocalCacheController.dirTemp(repoId, CACHE_TEMP)));
            return new ResponseEntity<Commit>(
                    RemoteSvnController.getCommitInfo(meta, commitId, data),
                    HttpStatus.OK
            );
        }
        catch (SVNException e) {
            e.printStackTrace();
            return new ResponseEntity<Commit>(
                    new Commit("failure", e.getMessage()),
                    HttpStatus.FORBIDDEN
            );
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<Commit>(
                    new Commit("failure", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
