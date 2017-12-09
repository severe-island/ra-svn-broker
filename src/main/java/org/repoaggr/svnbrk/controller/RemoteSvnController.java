package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.RegistrationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

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
}
