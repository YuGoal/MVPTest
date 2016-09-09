package com.wjhl.mvptest.meizi.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
    private boolean isLoadingMore = false;
    StaggeredGridLayoutManager layoutManager;

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

//        recycleview.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
//        recycleview.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
        layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        recycleview.setLayoutManager(layoutManager);//这里用线性宫格显示 类似于瀑布流

        recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = new LinearLayoutManager(MeiziActivity.this).findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    if(isLoadingMore){
                        Log.d(TAG,"ignore manually update!");
                    } else{
                        getMeizi(String.valueOf(++page));//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = false;
                    }
                }
            }
        });

    }


    public void getMeizi(String page) {
        Map<String, String> map = new HashMap<>();
        map.put("showapi_appid", "24037");
        map.put("showapi_sign", "76e3b44c58e841458ad832387acf37f9");
        map.put("type", "34");
        map.put("num", "20");
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
                    for(int i=0;i<10;i++){
                        url.add(i,mJson.getJSONObject("showapi_res_body").getJSONObject(String.valueOf(i)).getString("thumb"));
                    }
                    mAdapter = new MeiZiAdapter(MeiziActivity.this,url);
                    recycleview.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    swipeLayout.setRefreshing(false);
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
