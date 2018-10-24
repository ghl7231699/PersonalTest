package com.example.annotationlib.annotation;


public class Person {
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;

    private int temp;

    @WrappedMethod()
    public String getInfo() {
        return id + ":\t" + first_name + "\t" + last_name;
    }

    private String method() {
        return "default Nothing";
    }
}
