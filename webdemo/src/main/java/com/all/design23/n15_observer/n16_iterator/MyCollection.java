package com.all.design23.n15_observer.n16_iterator;

import org.junit.Test;

public class MyCollection implements Collection {

    private String[] ss = {"A", "B", "C", "D"};

    @Override
    public Iterator iterator() {
        return new MyIterator(this);
    }

    @Override
    public Object get(int index) {
        return ss[index];
    }

    @Override
    public int size() {
        return ss.length;
    }


    @Test
    public void test() {
        MyCollection collection = new MyCollection();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
