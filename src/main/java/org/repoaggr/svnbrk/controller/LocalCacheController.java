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

    public static void cachingObject(String id, String filename, Object record)
            throws IOException {
        Path dir = dirPath(id);
        if(!localExists(id)) {
            Files.createDirectories(dir);
        }
        FileOutputStream fos = new FileOutputStream(
                dir.toString() + separator + filename
        );
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(record);
        oos.flush();
        oos.close();
    }
    public static Object uncachingObject(String id, String filename)
            throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(
                dirPath(id) + separator + filename
        );
        ObjectInputStream oin = new ObjectInputStream(fis);
        return oin.readObject();
    }
}