package com.android.personalmushaf.util;

import com.android.personalmushaf.navigation.navigationdata.QuranConstants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static boolean checkRootDataDirectory() {
        File dataDirectory = new File(QuranConstants.ASSETSDIRECTORY);
        return dataDirectory.exists() && dataDirectory.isDirectory();
    }

    public static List<String> getExistingMushafDirectories() {
        List<String> expectedMushafDirectories = Arrays.asList("naskh_13_line", "naskh_15_line", "madani_15_line");
        List<String> existingMushafDirectories = new ArrayList<>();
        File currentFile;
        for (String expectedMushafDirectory: expectedMushafDirectories) {
            currentFile = new File(QuranConstants.ASSETSDIRECTORY + "/" + expectedMushafDirectory);
            if (currentFile.exists() && currentFile.isDirectory())
                existingMushafDirectories.add(currentFile.getName());
        }
        return existingMushafDirectories;
    }

    public static List<String> getAvailableMushafs(List<String> existingMushafDirectories) {
        List<String> availableMushafs = new ArrayList<>();
        File imagesDirectory;
        for (String existingMushafDirectory: existingMushafDirectories) {
            imagesDirectory = new File(QuranConstants.ASSETSDIRECTORY + "/" + existingMushafDirectory + "/images");
            if ((imagesDirectory.exists() && imagesDirectory.isDirectory()) && imagesDirectory.list().length > 500)
                availableMushafs.add(existingMushafDirectory);
        }

        return availableMushafs;
    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            }
        } finally {
            zis.close();
        }
    }
}
