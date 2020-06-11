package com.all.design23.n19_memento;

import lombok.Getter;
import lombok.Setter;

/**
 * 仓库 保存备忘录 以便回复
 */
@Getter
@Setter
public class Storage {
    private Memento memento;

    public Storage(Memento memento) {
        this.memento = memento;
    }
}
