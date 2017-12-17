package org.repoaggr.svnbrk.model.exceptions;

import static org.repoaggr.svnbrk.configuration.Constants.CACHE_EXCEPTION;

public class CacheException extends RuntimeException {
    public CacheException() {
        super(CACHE_EXCEPTION);
    }

    public CacheException(String message) {
        super(message);
    }
}
