package com.all.design23.n22_mediator;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class MyMediator implements Mediator {
    private User user1;
    private User user2;

    /**
     * 创建中介 实际要工作的对象 类似代理
     */
    @Override
    public void createMediator() {
        user1 = new User1(this);
        user2 = new User2(this);

    }

    /**
     * 中介 编排工作
     */
    @Override
    public void workAll() {
        user1.work();
        user2.work();
    }
}
