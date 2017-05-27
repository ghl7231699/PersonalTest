package com.example.liangge.rxjavatest.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by guhongliang on 2017/5/27.
 */
@Entity
public class Student {
    @Id
    private long id;
    private String name;
    private int score;
    @Generated(hash = 1228956245)
    public Student(long id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
