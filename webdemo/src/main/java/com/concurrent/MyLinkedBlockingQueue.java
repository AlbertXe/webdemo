package com.concurrent;

import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java 实现阻塞队列
 */
public class MyLinkedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, Serializable {
    private List<E> items;
    private int takeIndex;
    private int putIndex;
    private AtomicInteger count = new AtomicInteger();


    private ReentrantLock putLock = new ReentrantLock();
    private ReentrantLock takeLock = new ReentrantLock();
    private Condition notEmpty = takeLock.newCondition();
    private Condition notFull = putLock.newCondition();

    private final int capicity;


    public MyLinkedBlockingQueue(int size, boolean fair) {
        this.capicity = size;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(E o) throws InterruptedException {
        int c = -1;
        putLock.lockInterruptibly();

        try {
            while (count.get() == capicity) {
                notFull.await();
            }
            items.add(o);
            c = count.getAndIncrement();
            if (c + 1 < capicity) {
                notFull.signal();
            }

        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            takeLock.lock();
            try {
                notEmpty.signal();
            } finally {
                takeLock.unlock();

            }
        }


    }

    @Override
    public boolean offer(Object o, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public E take() throws InterruptedException {
        int c = -1;
        E e;
        takeLock.lockInterruptibly();

        try {
            while (count.get() == 0) {
                notEmpty.await();
            }
            e = items.get(0);
            c = count.getAndDecrement();
            if (c > 1) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capicity) {
            putLock.lock();
            try {
                notFull.signal();
            } finally {
                putLock.unlock();
            }
        }

        return e;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection c) {
        return 0;
    }

    @Override
    public int drainTo(Collection c, int maxElements) {
        return 0;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}
