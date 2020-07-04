package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
     * NIO方式copy
     *
     * @param source
     * @param target
     * @throws IOException
     */
    public static void copyNIO(String source, String target) throws IOException {
        try (FileInputStream is = new FileInputStream(source)) {
            try (FileOutputStream os = new FileOutputStream(target)) {
                FileChannel outChannel = os.getChannel();
                FileChannel inChannel = is.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(4086);
                while (inChannel.read(buffer) != -1) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            }
        }
    }


    /**
     * 可以创建多层目录文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File getFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return file;
        } else {
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
            System.out.println("盘符：" + view.getSystemDisplayName(roots[i]));
            System.out.println("总大小：" + formatSize(roots[i].getTotalSpace()));
            System.out.println("剩余大小：" + formatSize(roots[i].getFreeSpace()));
        }
    }

    /**
     * 格式化盘符大小
     *
     * @param size
     * @return
     */
    public static String formatSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (size < 1024) {
            return df.format(size) + "B";
        } else if (size < 1024 * 1024) {
            return df.format((double) size / 1024) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            return df.format((double) size / (1024 * 1024)) + "M";
        } else {
            return df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
    }
//    全局规则glob
//    使用类似于正则表达式但语法更简单的模式，匹配路径的字符串。
//
//    glob:*.java 匹配以java结尾的文件
//    glob:*.* 匹配包含'.'的文件
//    glob:*.{java,class} 匹配以java或class结尾的文件
//    glob:foo.? 匹配以foo开头且一个字符扩展名的文件
//    glob:/home/*/* 在unix平台上匹配，例如/home/gus/data等
//    glob:/home/** 在unix平台上匹配，例如/home/gus，/home/gus/data
//    glob:c:\\\\* 在windows平台上匹配，例如c:foo，c:bar，注意字符串转义
//    规则说明
//    * 匹配零个或多个字符与名称组件，不跨越目录
//    ** 匹配零个或多个字符与名称组件，跨越目录（含子目录）
//    ? 匹配一个字符的字符与名称组件
//    \ 转义字符，例如\{表示匹配左花括号
//    [] 匹配方括号表达式中的范围，连字符(-)可指定范围。例如[ABC]匹配"A"、"B"和"C"；[a-z]匹配从"a"到"z"；[abce-g]匹配"a"、"b"、"c"、"e"、"f"、"g"；
//    [!...]匹配范围之外的字符与名称组件，例如[!a-c]匹配除"a"、"b"、"c"之外的任意字符
//    {}匹配组中的任意子模式，多个子模式用","分隔，不能嵌套。
//    正则规则regex
//    使用java.util.regex.Pattern支持的正则表达式。

    /**
     * 遍历目录 查找特定文件
     */
    public static List<String> listFiles(String dir, String glob) throws IOException {
        List<String> files = new ArrayList<>();
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);

        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {

            //            Path path = Paths.get("/usr/web/bbf.jar"); //endsWith 必须是路径中一段完整的
//            path.endsWith("bbf.jar");  // true
//            path.endsWith(".jar");     // false
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (pathMatcher.matches(file)) {
                    files.add(file.toString());
                    System.out.println(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return files;
    }
}
