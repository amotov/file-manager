package kz.kcell.file.manager.util;

import java.text.DecimalFormat;

public class FileUtils {

    private static final String[] UNITS = new String[] { "B", "kB", "MB", "GB", "TB" };
    private static final String ZERO_FILE_SIZE = "0 B";

    public static String readableFileSize(long size) {
        if(size <= 0) return ZERO_FILE_SIZE;
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + UNITS[digitGroups];
    }

}
