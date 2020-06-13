package com.all.design23.n10_bridge;

import com.all.design23.n06_adapter.inter.Sourceable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Bridge {
    private Sourceable sourceable;

    public void method() {
        sourceable.method1();
    }


}
