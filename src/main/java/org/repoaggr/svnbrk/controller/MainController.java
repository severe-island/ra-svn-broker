package org.repoaggr.svnbrk.controller;

import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import org.repoaggr.svnbrk.model.Overview;
import org.repoaggr.svnbrk.model.OverviewData;
import org.repoaggr.svnbrk.model.RegistrationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;

public final class MainController {
    private MainController() {}

    // Регистрация репозитория в сервисе --------------------------------------
    public static ResponseEntity<RegistrationStatus> postRegistrationStatus(
            String url, String login, String password, String id)
    {
        try {
            Overview overview = RemoteSvnController.getOverview(url, login, password);
            overview.getData().setLogin(login);
            overview.getData().setPassword(password);

            if(LocalCacheController.localExists(id))
                throw new UnsupportedOperationException(
                        "Repository " + id + " is already registered.");
            LocalCacheController.cachingOverview(id, overview);

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
            Overview cachedOverview = LocalCacheController.uncachingOverview(id);

            // Если даты совпадают - используется локальная копия
            if (cachedOverview.getData().getLastSychDate()
                    .equals(RemoteSvnController.getLastSyncDate()))
                return new ResponseEntity<Overview>(cachedOverview, HttpStatus.OK);

            // Иначе - вытягивается с репозитория
            Overview overview = RemoteSvnController.getOverview(
                    cachedOverview.getData().getUrl(),
                    cachedOverview.getData().getLogin(),
                    cachedOverview.getData().getPassword()
            );
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
}
