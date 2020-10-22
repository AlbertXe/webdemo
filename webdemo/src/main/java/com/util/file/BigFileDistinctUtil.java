package com.util.file;

import com.util.FileUtils;
import lombok.Builder;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 大文件去重
 * @author: AlbertXe
 * @create: 2020-10-21 10:23
 */
@Builder(toBuilder = true)
public class BigFileDistinctUtil {
    @Builder.Default
    private int size = 1 << 20;
    @Builder.Default
    private String charset = "gbk";
    @Builder.Default
    private List<File> tempFiles = new CopyOnWriteArrayList<>();
    private Function<String, String> function;


    public void uniq(File file) {
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        // 拆分小文件  小文件去重  合并  删除临时文件
        division(file);

        tempFiles.parallelStream().forEach(f -> uniqLittleFile(f));

        FileUtils.combineFiles(tempFiles, file);

        tempFiles.parallelStream().forEach(f -> f.delete());
    }

    private void uniqLittleFile(File file) {
        String path = file.getParent() + "/" + file.getName() + ".tmp";
        List<String> lines = FileUtils.lines(file, charset);
        ArrayList<String> resultList = lines.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(line -> function.apply(line)))), ArrayList::new));
        String result = resultList.stream().collect(Collectors.joining(System.lineSeparator()));
        if (StringUtils.isNotBlank(result)) {
            result += System.lineSeparator();
        }
        File tempFile = FileUtils.getFile(path);
        FileUtils.write(tempFile, result, charset);
        FileUtils.copyFile(tempFile, file);
        tempFile.delete();
    }

    @SneakyThrows
    private void division(File file) {
        long length = file.length();
        int fileSum = (int) (length / size + 1);
        List<BufferedWriter> writers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))) {
            for (int i = 0; i < fileSum; i++) {
                String tempFile = file.getParent() + "/" + file.getName() + "_" + i + ".temp";
                tempFiles.add(FileUtils.getFile(tempFile));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), charset));
                writers.add(writer);
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String key = function.apply(line);
                int hash = key.hashCode() & Integer.MAX_VALUE;
                int i = hash % fileSum;
                writers.get(i).write(line + System.lineSeparator());
            }
        } finally {
            writers.parallelStream().forEach(writer -> {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
