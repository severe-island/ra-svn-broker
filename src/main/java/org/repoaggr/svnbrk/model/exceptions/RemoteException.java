package org.repoaggr.svnbrk.model.exceptions;

import static org.repoaggr.svnbrk.configuration.Constants.REMOTE_EXCEPTION;

public class RemoteException extends RuntimeException {
    public RemoteException() {
        super(REMOTE_EXCEPTION);
    }

    public RemoteException(String message) {
        super(message);
    }
}
