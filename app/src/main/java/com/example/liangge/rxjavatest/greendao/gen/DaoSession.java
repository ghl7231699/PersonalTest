package com.example.liangge.rxjavatest.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.liangge.rxjavatest.bean.User;
import com.example.liangge.rxjavatest.entity.Student;

import com.example.liangge.rxjavatest.greendao.gen.UserDao;
import com.example.liangge.rxjavatest.greendao.gen.StudentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig studentDaoConfig;

    private final UserDao userDao;
    private final StudentDao studentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        studentDao = new StudentDao(studentDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Student.class, studentDao);
    }
    
    public void clear() {
        userDaoConfig.clearIdentityScope();
        studentDaoConfig.clearIdentityScope();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}
