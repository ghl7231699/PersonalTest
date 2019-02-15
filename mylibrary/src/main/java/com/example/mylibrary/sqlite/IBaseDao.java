package com.example.mylibrary.sqlite;


public interface IBaseDao<T> {
    Long insert(T entity);
}
