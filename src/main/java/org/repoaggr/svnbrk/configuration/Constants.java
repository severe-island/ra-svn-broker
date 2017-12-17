package org.repoaggr.svnbrk.configuration;

public final class Constants {
    // Пути
    public static final String CACHE_TEMP = "temp";
    public static final String CACHE_OVERVIEW = "overview";
    public static final String CACHE_META = "_meta";
    public static final String CACHE_COMMITS = "commits";
    // Сообщения
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_WARN = "warning";
    public static final String STATUS_FAIL = "failure";
    public static final String S_SUCCESS = "success";
    public static final String W_CANNOT_CONNECT = "Cannot connect to remote SVN-repository - cached data is using";
    public static final String W_SIZE_PROCESSING = "Size is calculating - please, wait";
    public static final String W_COMMITS_PROCESSING = "List is collecting - please, wait";
    public static final String W_SIZE_PROCESSING_FAIL = "Calculation of repo's size wasn't successfully complete";
    public static final String W_COMMITS_PROCESSING_FAIL = "Collection of commits' list wasn't successfully complete";
    // Исключения
    public static final String REMOTE_EXCEPTION = "Remote resource not found or unavailable";
    public static final String CACHE_EXCEPTION = "Cache record not found or unavailable";
}
