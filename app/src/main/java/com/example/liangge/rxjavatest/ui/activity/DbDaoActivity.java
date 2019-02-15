package com.example.liangge.rxjavatest.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.mylibrary.sqlite.BaseDao;
import com.example.mylibrary.sqlite.Person;


public class DbDaoActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_dbdao;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    public void inert(View v) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhaozhongshuo" +
                ".db";
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(path, null);
        BaseDao<Person> baseDao = new BaseDao<>();
        baseDao.init(Person.class, database);
    }
}
