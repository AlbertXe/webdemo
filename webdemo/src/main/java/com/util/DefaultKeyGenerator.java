package com.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.Calendar;

/**
 * @description: 雪花算法 分布式key生存
 * @author: AlbertXe
 * @create: 2020-09-14 11:32
 */
public class DefaultKeyGenerator {
    private static long epoch;
    private static final long workId_bit = 10L;
    private static final long seq_bit = 12L;
    private static final long seq_mask = (1 << seq_bit) - 1;
    /**
     * 工作线程序号 左移位数
     */
    private static final long workIdLeft = seq_bit;
    /**
     * 时间左移
     */
    private static final long timeStampLeft = workId_bit + workIdLeft;
    private static final long workIdMax = 1 << workId_bit;

    private long seq;
    private long lastTime;

    @Getter
    @Setter
    private static long workId;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 0, 0, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        epoch = calendar.getTimeInMillis();
        initWokrId();
    }

    /**
     * 自己定义取workId得方法
     */
    private static void initWokrId() {

    }

    public static void setWorkId(long workId) {
        if (workId >= 0 && workId < workIdMax) {
            DefaultKeyGenerator.workId = workId;
        }
    }

    public synchronized Number generateId() {
        long time = System.currentTimeMillis();
        Assert.state(time >= lastTime, "时间回退");
        if (time == lastTime) {
            if (0L == (seq = ++seq & seq_mask)) {
                time = waitUtilNextTime();
            }
        } else {
            seq = 0L;
        }
        lastTime = time;
        Long n1 = (time - epoch) << timeStampLeft;
        System.out.println("n1 ===" + n1);
        Long n2 = workId << workIdLeft;
        Long n3 = seq;
        return n1 | n2 | n3;
    }

    /**
     * 在一毫米所有id分配完毕 1024
     * 等待下一毫秒
     *
     * @return
     */
    private long waitUtilNextTime() {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }

    public static void main(String[] args) {
        DefaultKeyGenerator generator = new DefaultKeyGenerator();

        for (int i = 0; i < 50; i++) {
            generator.setWorkId(i);
            for (int j = 0; j < 1000; j++) {
                System.out.println("workId=" + getWorkId() + "            id=" + generator.generateId());
            }
        }
    }


}
