package com.all.design23.n22_mediator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {
    /**
     * 只要维护 user和mediator的关系
     */
    private Mediator mediator;

    public User(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void work();
}
