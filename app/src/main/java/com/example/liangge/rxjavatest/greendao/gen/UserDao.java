package com.example.liangge.rxjavatest.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.liangge.rxjavatest.bean.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property AgentCode = new Property(1, String.class, "agentCode", false, "AGENTCODE");
        public final static Property Sex = new Property(2, String.class, "sex", false, "SEX");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Birthday = new Property(4, String.class, "birthday", false, "BIRTHDAY");
        public final static Property MobilePhone = new Property(5, String.class, "mobilePhone", false, "MOBILEPHONE");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"AGENTCODE\" TEXT," + // 1: agentCode
                "\"SEX\" TEXT," + // 2: sex
                "\"NAME\" TEXT," + // 3: name
                "\"BIRTHDAY\" TEXT," + // 4: birthday
                "\"MOBILEPHONE\" TEXT);"); // 5: mobilePhone
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String agentCode = entity.getAgentCode();
        if (agentCode != null) {
            stmt.bindString(2, agentCode);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(5, birthday);
        }
 
        String mobilePhone = entity.getMobilePhone();
        if (mobilePhone != null) {
            stmt.bindString(6, mobilePhone);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String agentCode = entity.getAgentCode();
        if (agentCode != null) {
            stmt.bindString(2, agentCode);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(3, sex);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(5, birthday);
        }
 
        String mobilePhone = entity.getMobilePhone();
        if (mobilePhone != null) {
            stmt.bindString(6, mobilePhone);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // agentCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sex
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // birthday
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // mobilePhone
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setAgentCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSex(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBirthday(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMobilePhone(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
