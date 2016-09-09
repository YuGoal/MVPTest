package com.wjhl.mvptest.meizi.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjhl.mvptest.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/9/9.
 */

public class MeiZiAdapter extends RecyclerView.Adapter<MeiZiAdapter.NormalTextViewHolder> {
    private static final String TAG = "MeiZiAdapter";
    private  LayoutInflater mLayoutInflater;
    private  Context mContext;
    private List<String> mTitles;


    public MeiZiAdapter(Context mContext, List<String> mTitles) {
        this.mContext = mContext;
        this.mTitles = mTitles;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_meizi,parent,false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mTitles.get(position))
                .crossFade()
                .into(holder.mImageView);
        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        NormalTextViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_meizi);
        }
    }

}
