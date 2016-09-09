package com.wjhl.mvptest.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lenovo on 2016/7/27.
 */
public interface APIService {

    @POST("819-1")
    Call<ResponseBody> getWeather(@QueryMap Map<String,String> cityId);

}