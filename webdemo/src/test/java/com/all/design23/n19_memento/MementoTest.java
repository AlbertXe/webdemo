package com.all.design23.n19_memento;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MementoTest {

    @Test
    public void test1() {
        Original original = new Original("init");
        Memento memento = original.createMemento();
        Storage storage = new Storage(memento);

        log.info("初始化值{}", original.getValue());
        original.setValue("second");
        log.info("更新值{}", original.getValue());
        original.restore(storage.getMemento());
        log.info("恢复备份值{}", original.getValue());

    }

}