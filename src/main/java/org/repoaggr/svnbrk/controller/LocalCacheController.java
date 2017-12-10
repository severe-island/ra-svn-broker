package org.repoaggr.svnbrk.controller;

import com.sun.deploy.util.SystemUtils;
import org.repoaggr.svnbrk.model.Overview;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;

public final class LocalCacheController {
    private LocalCacheController() {
    }

    private static String separator;

    private static Path dirPath(String id) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            separator = "\\";
        else
            separator = "/";
        return Paths.get("cache" + separator + id);
    }

    private static String overviewPath(Path dir) {
        return dir.toString() + separator + "overview";
    }

    public static boolean localExists(String id) {
        return Files.exists(dirPath(id));
    }

    public static void cachingOverview(String id, Overview overview) throws IOException {
        Path dir = dirPath(id);
        if(!localExists(id)) {
            Files.createDirectories(dir);
        }
        FileOutputStream fos = new FileOutputStream(overviewPath(dir));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(overview);
        oos.flush();
        oos.close();
    }
    public static Overview uncachingOverview(String id)
            throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(overviewPath(dirPath(id)));
        ObjectInputStream oin = new ObjectInputStream(fis);
        return (Overview) oin.readObject();
    }
}