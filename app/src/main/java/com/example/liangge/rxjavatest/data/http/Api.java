package com.example.liangge.rxjavatest.data.http;

import com.example.liangge.rxjavatest.bean.BaseBean;
import com.example.liangge.rxjavatest.bean.BeanTest;
import com.example.liangge.rxjavatest.bean.UserInfo;
import com.example.liangge.rxjavatest.common.constant.User;
import com.example.liangge.rxjavatest.common.constant.UserParam;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

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

    @POST("ju321/isb/isb-ucm-adapter-in/agentDetail")
    Observable<BeanTest> UserLogin(@Body UserParam param);

    @POST("ju321/isb/isb-ucm-adapter-in/agentDetail")
    Observable<BaseBean<UserInfo>> userLogin(@Body UserParam param);

    @GET("ju321/isb/isb-ucm-adapter-in/agentDetail")
    Observable<BaseBean<UserInfo>> checkIsUpdate();

    //上传文件
    @POST("ju321/isb/isb-nci-adapter-in/ImageToFillIn")
    Call<ResponseBody> upLoading(@Body UserParam param);

    /**
     * 下载文件
     *
     * @param body @Part(“description”) 就是RequestBody实例中包裹的字符串值
     * @param file @Part MultipartBody.Part file 我们使用MultipartBody.Part类，使我们能够发送实际文件 file就是你要往服务器上传的文件
     * @return
     */
    @GET("xhrs/EBT2/NCI_EBT2.0_ZY_5.0.apk")
    Call<ResponseBody> downLoad(@Part("description") RequestBody body, @Part MultipartBody.Part file);

    //无参数下载
    @GET
    Call<ResponseBody> downLoad(@Url String fileUrl);
}
