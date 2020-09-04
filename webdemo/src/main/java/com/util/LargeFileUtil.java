package com.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class LargeFileUtil {

    private byte key = "\n".getBytes()[0];

    public static void main(String[] args) throws Exception {
        LargeFileUtil util = new LargeFileUtil();
        File file = new File("D:\\a.txt");
        util.getStartNum(file, 10);
    }

    public long getStartNum(File file, long position) throws Exception {
        // 开始位置
        long startNum = position;
        FileChannel fcin = new RandomAccessFile(file, "r").getChannel();
        fcin.position(position);
        try {
            int cache = 1024;
            // 大小1024
            ByteBuffer rBuffer = ByteBuffer.allocate(cache);
            // 每次读取的内容
            byte[] bs = new byte[cache];
            // 缓存
            byte[] tempBs = new byte[0];
            String line = "";
            while (fcin.read(rBuffer) != -1) {
                // buff位置
                int rSize = rBuffer.position();
//                rBuffer.rewind();
                rBuffer.get(bs);
                rBuffer.clear();
                byte[] newStrByte = bs;
                // 如果发现有上次未读完的缓存,则将它加到当前读取的内容前面
                if (null != tempBs) {
                    int tL = tempBs.length;
                    newStrByte = new byte[rSize + tL];
                    System.arraycopy(tempBs, 0, newStrByte, 0, tL);
                    System.arraycopy(bs, 0, newStrByte, tL, rSize);
                }
                // 获取开始位置之后的第一个换行符
                int endIndex = indexOf(newStrByte, 0);
                if (endIndex != -1) {
                    return startNum + endIndex;
                }
                tempBs = substring(newStrByte, 0, newStrByte.length);
                startNum += 1024;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fcin.close();
        }
        return position;
    }

    private int indexOf(byte[] src, int fromIndex) throws Exception {

        for (int i = fromIndex; i < src.length; i++) {
            if (src[i] == key) {
                return i;
            }
        }
        return -1;
    }

    private byte[] substring(byte[] src, int fromIndex, int endIndex) throws Exception {
        int size = endIndex - fromIndex;
        byte[] ret = new byte[size];
        System.arraycopy(src, fromIndex, ret, 0, size);
        return ret;
    }

}

