package com.example.liangge.rxjavatest.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.View;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.example.mylibrary.sqlite.BaseDao;
import com.example.mylibrary.sqlite.Person;

/**
 * 类描述：约束布局activity
 * 创建人：ghl
 * 创建时间：2019/2/15
 *
 * @version v1.0
 */

public class ConstraintActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_constraint;
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
