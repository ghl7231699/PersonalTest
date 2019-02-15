package com.example.mylibrary.sqlite;

import com.example.mylibrary.sqlite.annotation.TableName;
import com.example.mylibrary.sqlite.annotation.TableField;

@TableName("tb_person")
public class Person {

    @TableField("name")
    public String name;
    @TableField("age")
    public int age;
    @TableField("height")
    public double height;
    @TableField("sex")
    public int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
