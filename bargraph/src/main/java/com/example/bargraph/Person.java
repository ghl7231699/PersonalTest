package com.example.bargraph;

import java.util.Observable;

/**
 * Created by guhongliang on 2017/9/20.
 */

public class Person extends Observable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        setChanged();
        notifyObservers();
    }
}
