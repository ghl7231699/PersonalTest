package com.example.annotationlib.annotation;


import com.sun.tools.classfile.Dependency;

public class CustomKnife {
    public static void bind(Object host, Object source, Dependency.Finder finder) {
        String className = host.getClass().getName();
    }
}
