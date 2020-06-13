package com.all.design23.n09_facade;

import lombok.extern.slf4j.Slf4j;

/**
 * 外观模式
 * 外观模式是为了解决类与类之家的依赖关系的，像spring一样，可以将类和类之间的关系配置到配置文件中，
 * 而外观模式就是将他们的关系放在一个Facade类中，降低了类类之间的耦合度
 */
@Slf4j
public class Computer {
    private CPU cpu;
    private Memory memory;
    private Disk disk;

    public Computer() {
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void start() {
        log.info("begin start computer");
        cpu.start();
        memory.start();
        disk.start();
        log.info("start computer success");
    }

    public void shutdown() {
        log.info("begin shutdown computer");
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
        log.info("shutdown computer success");
    }
}
