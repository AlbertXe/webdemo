package com.all.design23.n19_memento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Original {
    private String value;

    public Original(String value) {
        this.value = value;
    }

    public Memento createMemento() {
        return new Memento(value);
    }

    /**
     * 恢复
     *
     * @param memento
     */
    public void restore(Memento memento) {
        this.value = memento.getValue();
    }
}
