package com.all.design23.n15_observer;

import java.util.Vector;

public abstract class AbstractSubject implements Subject {
    private Vector<ObServer> vector = new Vector<>();

    @Override
    public void add(ObServer obServer) {
        vector.add(obServer);
    }

    @Override
    public void del(ObServer obServer) {
        vector.remove(obServer);
    }

    @Override
    public void notifyObservers() {
        while (vector.iterator().hasNext()) {
            vector.iterator().next().update();
        }

    }
}
