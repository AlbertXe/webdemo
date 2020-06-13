package com.all.design23.n05_prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 原型模式
 */
public class Prototype implements Cloneable, Serializable {


    private static final long serialVersionUID = -530735593289579256L;

    /**
     * 浅克隆
     *
     * @return
     * @throws CloneNotSupportedException
     */
    public Prototype clone() throws CloneNotSupportedException {
        return (Prototype) super.clone();
    }

    /**
     * 深度克隆  流的方式
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(this);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(os.toByteArray()));
        return ois.readObject();
    }

}
