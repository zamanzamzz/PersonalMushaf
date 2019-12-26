package com.android.personalmushaf.util;

import android.os.Environment;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static final String ASSETSDIRECTORY = Environment.getExternalStorageDirectory() + "/personal_mushaf";

    public static boolean checkRootDataDirectory() {
        File dataDirectory = new File(ASSETSDIRECTORY);
        return dataDirectory.exists() && dataDirectory.isDirectory();
    }

    public static void unzip(File zipFile, File targetDirectory, ProgressBar progressBar) throws IOException {
        progressBar.setMax(new ZipFile(zipFile).size());
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
                        progressBar.incrementProgressBy(count);
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            }
        } finally {
            zis.close();
        }
    }

    public static void validateDirectory(File directory) {
        if (!directory.exists())
            directory.mkdir();
        else if (!directory.isDirectory()){
            directory.delete();
            directory.mkdir();
        }
    }
}
