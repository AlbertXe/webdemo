package com.util;

import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 * @date 2019-11-21 21:20
 */
public class FileUtils {

    public static File getFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }else {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return file;
        }
    }
}
