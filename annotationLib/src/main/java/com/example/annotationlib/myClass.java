package com.example.annotationlib;

import com.example.annotationlib.annotation.Column;
import com.example.annotationlib.annotation.Person;
import com.example.annotationlib.annotation.WrappedMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class myClass {
    public static void main(String[] args) {
        Class<?> clazz = Person.class;
        Method[] methods = clazz.getMethods();
        for (Method m :
                methods) {
            if (m.getAnnotation(WrappedMethod.class) != null) {
                System.out.print("method is\t" + m.getName() + "\n");
            }
        }
        System.out.println();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field :
                fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                System.out.print("column is\t" + column.name() + "-" + field.getName() + ",");
            }
        }

    }
}
