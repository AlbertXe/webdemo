package com.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 86150
 * Atree
 * 2021/3/31 21:19
 */
@Data
public class Atree {
    public Atree(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name="";
    private List<Atree> childes = new ArrayList<>();
    private Atree parent;
    private boolean flag = false;

    public void addChild(Atree child) {
        childes.add(child);
        child.setParent(this);
    }

    public void removeChild(Atree chile) {
        childes.remove(chile);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Atree> getChildes() {
        return childes;
    }

    public Atree getParent() {
        return parent;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Atree{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", flag=" + flag +
                ", childes=" + childes +
                '}';
    }
}
