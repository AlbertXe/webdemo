package com.all.design23.n15_observer;

public interface Subject {
    void add(ObServer obServer);

    void del(ObServer obServer);

    void notifyObservers();

    void operation();

}
