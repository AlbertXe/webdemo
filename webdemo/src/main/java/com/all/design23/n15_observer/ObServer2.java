package com.all.design23.n15_observer;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

@Slf4j
public class ObServer2 implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        log.info("ObServer2......");
    }
}
