package org.repoaggr.svnbrk;

public class SvnOverview {
    private final long last_sync_date;
    private final String type = "svn";
    private final String url;
    private final int size;

    public SvnOverview(
        long last_sync_date,
        String url,
        int size
    ) {
        this.last_sync_date = last_sync_date;
        this.url = url;
        this.size = size;
    }

    // Getters:
    public long getLastSyncDate() {
        return last_sync_date;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getSize() {
        return size;
    }
}