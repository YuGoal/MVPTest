package com.wjhl.mvptest.http;

import android.os.Build;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2016/7/25.
 */
public class MyInterceptors implements Interceptor {
    private static final String TAG = "MyInterceptors";
    @Override
    public Response intercept(Chain chain) throws IOException {

        //封装headers
        Request request = chain.request().newBuilder()
                .addHeader("showapi_appid","24037") //手机型号
                .addHeader("showapi_sign", "c2b9739c26de4c83ad8eb853da9f755b") //设备的系统版本
                .build();
        Headers headers = request.headers();
        //打印
        System.out.println("phoneModel is : " + headers.get("phoneModel"));
        String requestUrl = request.url().toString(); //获取请求url地址
        String methodStr = request.method(); //获取请求方式
        RequestBody body = request.body(); //获取请求body
        String bodyStr = (body==null?"":body.toString());
        //打印Request数据
        Log.d(TAG, "intercept: Request Url is :" + requestUrl + "\nMethod is : " + methodStr + "\nRequest Body is :" + bodyStr + "\n");

        Response response = chain.proceed(request);

        if (response != null) {
            //byte[] str = Base64.decode(response.body().string(), Base64.DEFAULT);
//            s1 = Base64Decoder.decode(response.body().string());
            //s1 = new String(str);
        } else {
            System.out.println("Respong is null");
        }
        Log.d(TAG, "intercept: response  "+response.body().toString());
        return response;
    }
}
