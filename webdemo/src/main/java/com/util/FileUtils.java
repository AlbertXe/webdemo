package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author AlbertXe
 * @date 2019-11-21 21:20
 */
public class FileUtils {
    static Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void main(String[] args) {
        getCD();
    }

    /**
     * 可以创建多层目录文件
     * @param path
     * @return
     * @throws IOException
     */
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

    /**
     * 获得系统盘符
     */
    public static void getCD() {
        FileSystemView view = FileSystemView.getFileSystemView();

        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++) {
            System.out.println("盘符："+view.getSystemDisplayName(roots[i]));
            System.out.println("总大小："+formatSize(roots[i].getTotalSpace()));
            System.out.println("剩余大小："+formatSize(roots[i].getFreeSpace()));
        }
    }

    /**
     * 格式化盘符大小
     * @param size
     * @return
     */
    public static String formatSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (size < 1024) {
            return df.format(size) + "B";
        } else if (size < 1024 * 1024) {
            return df.format((double)size / 1024) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            return df.format((double)size / (1024 * 1024)) + "M";
        }else {
            return df.format((double)size / (1024 * 1024*1024)) + "G";
        }
    }
}
