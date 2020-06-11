package com.all.design23.n19_memento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Memento {
    private String value;

    public Memento(String value) {
        this.value = value;
    }
}
