package com.example.liangge.rxjavatest;

import com.example.liangge.rxjavatest.constant.User;
import com.example.liangge.rxjavatest.constant.UserParam;
import com.example.liangge.rxjavatest.utils.BeanTest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by liangge on 2017/3/26.
 */

public interface Api {
    @GET("user/{id}")
    Call<User> getUserInfoWithPath(@Path("id") int user_id);

//    @POST("ju321/isb/isb-ucm-adapter-in/agentDetail")
//    Call<BaseResult> login(@Body UserParam param);
    @POST("ju321/isb/isb-ucm-adapter-in/agentDetail")
    Call<BeanTest> login(@Body UserParam param);
}
