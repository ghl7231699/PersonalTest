package com.example.mylibrary.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mylibrary.DLog;
import com.example.mylibrary.sqlite.annotation.TableName;
import com.example.mylibrary.sqlite.annotation.TableField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseDao<T> implements IBaseDao<T> {

    /**
     * 持有数据库操作类的引用
     */
    private SQLiteDatabase mDatabase;
    private Class<T> entityClass;
    private String tabName;
    private boolean init;
    private HashMap<String, Field> cacheMap;

    public synchronized boolean init(Class<T> entity, SQLiteDatabase database) {
        if (!init) {
            entityClass = entity;
            mDatabase = database;
            tabName = entityClass.getAnnotation(TableName.class).value();
            if (!database.isOpen()) {
                return false;
            }
            if (!autoCreateTable()) {
                return false;
            }
        }
        initCacheMap();
        return init;
    }

    private void initCacheMap() {
        cacheMap = new HashMap<>();
        //查表获取到实际的字段名（查询空表）
        String sql = "select * from " + tabName + " limit 1,0";
        Cursor cursor = mDatabase.rawQuery(sql, null);
        //得到字段组名
        String[] columnNames = cursor.getColumnNames();
        Field[] fields = entityClass.getDeclaredFields();
        for (String columnName : columnNames) {
            Field result = null;
            for (Field field : fields) {
                if (field.getAnnotation(TableField.class).value().equals(columnName)) {
                    result = field;
                    break;
                }
            }
            if (result != null) {
                cacheMap.put(columnName, result);
            }
        }
        cursor.close();
    }

    /**
     * 建表操作
     *
     * @return 是否建表成功
     */

    private boolean autoCreateTable() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS ").append(tabName).append(" ( ");
        Field[] fields = entityClass.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                Class type = field.getType();
                if (type == String.class) {
                    sb.append(field.getAnnotation(TableField.class).value()).append(" TEXT,");
                } else if (type == Integer.class || type == int.class) {
                    sb.append(field.getAnnotation(TableField.class).value()).append(" INTEGER,");
                } else if (type == Double.class || type == double.class) {
                    sb.append(field.getAnnotation(TableField.class).value()).append(" TEXT,");
                } else if (type == Long.class || type == long.class) {
                    sb.append(field.getAnnotation(TableField.class).value()).append(" TEXT,");
                } else if (type == byte[].class) {
                    sb.append(field.getAnnotation(TableField.class).value()).append(" BLOB,");
                }
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(" )");
            DLog.d("BaseDao\t", sb.toString());
            try {
                mDatabase.execSQL(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            init = true;
            return true;
        }
        return false;
    }

    @Override
    public Long insert(T entity) {
        ContentValues contentValues = getValues(entity);
        mDatabase.insert(tabName, null, contentValues);
        return null;
    }

    /**
     * 插入数据库操作
     *
     * @param entity 实体
     * @return
     */
    private ContentValues getValues(T entity) {
        ContentValues contentValues = new ContentValues();

        for (Map.Entry<String, Field> next : cacheMap.entrySet()) {
            //成员变量
            Field field = next.getValue();
            //列名
            String key = next.getKey();
            field.setAccessible(true);
            putValues(key, entity, field, contentValues);
        }
        return contentValues;
    }

    private void putValues(String key, T entity, Field field, ContentValues contentValues) {
        try {
            Object o = field.get(entity);
            Class type = field.getType();
            if (type == String.class) {
                String value = (String) o;
                contentValues.put(key, value);
            } else if (type == Integer.class || type == int.class) {
                int value = (int) o;
                contentValues.put(key, value);
            } else if (type == Double.class || type == double.class) {
                double value = (double) o;
                contentValues.put(key, value);
            } else if (type == Long.class || type == long.class) {
                long value = (long) o;
                contentValues.put(key, value);
            } else if (type == byte[].class) {
                byte[] value = (byte[]) o;
                contentValues.put(key, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
