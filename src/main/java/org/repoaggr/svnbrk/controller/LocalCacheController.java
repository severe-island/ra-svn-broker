package org.repoaggr.svnbrk.controller;

import org.repoaggr.svnbrk.model.CommitData;
import org.repoaggr.svnbrk.model.CommitDataFiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String dirTemp(String id, String tempPath) {
        return dirPath(id) + separator + tempPath;
    }

    public static CommitData parseCommitFile(String id, String tempPath, CommitData data)
            throws IOException
    {
        int positiveDelta = 0;
        int negativeDelta = 0;
        Pattern p_titles = Pattern
                .compile("\\+\\+\\+.*\\(.*\\)"); // Поиск названий файлов
        Pattern p_deltas = Pattern
                .compile("@@.*@@"); // Поиск строки дельт
        Pattern p_positiveDelta = Pattern
                .compile("\\+\\d+(,\\d+)*"); // Поиск положительной дельты
        Pattern p_negativeDelta = Pattern
                .compile("-\\d+(,\\d+)*"); // Поиск отрицательной дельты
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(dirPath(id) + separator + tempPath),
                        StandardCharsets.UTF_8)
        );
        String line;
        boolean fileIsChanged = false;
        int lastIndex = 0;
        while ((line = reader.readLine()) != null){
            if(p_titles.matcher(line).matches()) {
                String name = "/" + line.substring(4).split("\t")[0];
                for (CommitDataFiles file : data.getFiles()) {
                    if(file.getPath().equals(name)) {
                        fileIsChanged = true;
                        lastIndex = data.getFiles().indexOf(file);
                    }
                }
            }
            else if(p_deltas.matcher(line).matches() && fileIsChanged) {
                Matcher m_positiveDelta = p_positiveDelta.matcher(line);
                Matcher m_negativeDelta = p_negativeDelta.matcher(line);
                if (m_positiveDelta.find()) {
                    String[] temp = m_positiveDelta.group().split("\\+|,");
                    int localPositiveDelta =
                            temp.length == 2 ? 1 : Integer.valueOf(temp[2]);
                    data.getFiles().get(lastIndex)
                            .setPositiveDelta(localPositiveDelta);
                    positiveDelta += localPositiveDelta;
                }
                if (m_negativeDelta.find()) {
                    String[] temp = m_negativeDelta.group().split("-|,");
                    int localNegativeDelta =
                            temp.length == 2 ? 1 : Integer.valueOf(temp[2]);
                    data.getFiles().get(lastIndex)
                            .setNegativeDelta(localNegativeDelta);
                    negativeDelta += localNegativeDelta;
                }
                fileIsChanged = false;
            }
        }
        reader.close();
        data.setPositiveDelta(positiveDelta);
        data.setNegativeDelta(negativeDelta);
        return data;
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