package com.wjhl.mvptest.meizi.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.wjhl.mvptest.R;
import com.wjhl.mvptest.http.APIService;
import com.wjhl.mvptest.meizi.presenter.MeiziPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MeiziActivity extends AppCompatActivity{
    private static final String TAG = "MeiziActivity";

    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    private MeiziPresenter meiziPresenter;
    private int page = 1;
    private List<String> url = new ArrayList<>();
    private MeiZiAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);
        ButterKnife.bind(this);

        getMeizi(String.valueOf(page));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMeizi(String.valueOf(page));
            }
        });

        recycleview.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

    }


    public void getMeizi(String page) {
        Map<String, String> map = new HashMap<>();
        map.put("showapi_appid", "24037");
        map.put("showapi_sign", "76e3b44c58e841458ad832387acf37f9");
        map.put("type", "34");
        map.put("num", "10");
        map.put("page", page);
        Retrofit retrofit = new Retrofit.Builder()
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl("http://route.showapi.com/")
                .build();
        APIService apiStores = retrofit.create(APIService.class);
        Call<ResponseBody> call = apiStores.getWeather(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    JSONObject mJson = new JSONObject(res);
                    Log.d(TAG, "onResponse: "+res);

                    url.add(0,mJson.getJSONObject("showapi_res_body").getJSONObject("3").getString("thumb"));
                    Log.d(TAG, "onResponse: "+url.get(0).toString());
                    for(int i=0;i<10;i++){

                    }
                    mAdapter = new MeiZiAdapter(MeiziActivity.this,url);
                    recycleview.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
