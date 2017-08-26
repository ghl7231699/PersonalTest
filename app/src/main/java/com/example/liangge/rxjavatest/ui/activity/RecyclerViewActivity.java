package com.example.liangge.rxjavatest.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.User;
import com.example.liangge.rxjavatest.common.config.SslContextFactory;
import com.example.liangge.rxjavatest.common.httpurl.HttpUrls;
import com.example.liangge.rxjavatest.common.utils.CommonUtils;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.greendao.gen.DaoMaster;
import com.example.liangge.rxjavatest.greendao.gen.DaoSession;
import com.example.liangge.rxjavatest.greendao.gen.UserDao;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by guhongliang on 2017/8/23.
 * 使用RecyclerView实现复杂的列表布局
 */

public class RecyclerViewActivity extends BaseActivity {

    private static final String TAG = "RecyclerViewActivity";
    @BindView(R.id.recycler_view_tb)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.recycler_view_content_tv)
    TextView mTextView;

    private UserDao mUserDao;
    private OkHttpClient okHttpClient;

    private String[] sex = new String[]{"男", "女"};

    @Override
    public int getLayoutId() {
        return R.layout.recycler_view_activity_layout;
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        initDb();
        okHttpClient = SslContextFactory.getOkHttpClient();
    }

    private void initDb() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster
                .DevOpenHelper(getApplicationContext(), "rxjava.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        //获取到UerDao
        mUserDao = daoSession.getUserDao();
    }

    @Override
    public void initData() {
        setTitle("首页");
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.recycler_view_insert, R.id.recycler_view_insert_list, R.id.recycler_view_modification,
            R.id.recycler_view_delete, R.id.recycler_view_query, R.id.recycler_view_query_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recycler_view_insert:
                insertData();
                break;
            case R.id.recycler_view_insert_list:
                insertDataList();
                break;
            case R.id.recycler_view_modification:
                break;
            case R.id.recycler_view_delete:
                break;
            case R.id.recycler_view_query:
                queryData();
                break;
            case R.id.recycler_view_query_all:
                queryDataList();
                break;
        }
    }

    /**
     * 查询全部数据
     */
    private void queryDataList() {
        List<User> users = mUserDao.queryBuilder().list();
        Log.e(TAG, "queryDataList: " + users.size());
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Log.e(TAG, "queryDataList: " + user.toString());
            mTextView.append(user.getName() + "\n"
                    + user.getAgentCode() + "\n"
                    + user.getSex());
        }
    }

    /**
     * 查询一条数据
     */
    private void queryData() {
        User unique = mUserDao.queryBuilder().where(UserDao.Properties.Sex.eq("女")).build().unique();
        if (unique == null) {
            mTextView.setText("未查询到数据！");
            return;
        }
        mTextView.setText(unique.getName() + "\n"
                + unique.getAgentCode() + "\n" + unique.getMobilePhone());
    }

    /**
     * 插入数据
     */

    private void insertData() {
        MediaType type = MediaType.parse("application/json;charset=utf-8");
        RequestBody body1 = RequestBody.create(type, new Gson().toJson(CommonUtils.getUserParam()));
        final Request request = new Request.Builder()
                .post(body1)
                .url(HttpUrls.URL_LOGIN_IP + "ju321/isb/isb-ucm-adapter-in/agentDetail")
                .build();
        //3 将request封装成call
        Call call = okHttpClient.newCall(request);
        //4 执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String s = response.body().string();
//                    User user = new Gson().fromJson(s, User.class);
                    User user = new User();
                    user.setAgentCode("123456");
                    user.setBirthday("1990.10.10");
                    user.setMobilePhone("151123112353");
                    user.setSex("女");
                    user.setName("荔枝");
                    try {
                        mUserDao.insertOrReplace(user);
                        Log.e(TAG, "onResponse: 插入数据库successful");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: 插入数据库fail");
                    }
                }
            }
        });
    }

    /**
     * 批量添加数据
     */
    private void insertDataList() {
        try {
            List<User> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                User user = new User();
                Random r = new Random();
                user.setSex(sex[r.nextInt(1)]);
                user.setName("荔枝" + i);
                user.setAgentCode("" + i);
                list.add(user);
            }
            for (User u : list
                    ) {
                Log.e(TAG, "insertDataList: " + u.getSex());
                mUserDao.insertOrReplace(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
