package com.wjhl.mvptest.meizi.presenter;

import com.wjhl.mvptest.meizi.model.IMeiziModel;
import com.wjhl.mvptest.meizi.model.MeiziModel;
import com.wjhl.mvptest.meizi.view.IMeiziView;

/**
 * Created by lenovo on 2016/8/31.
 */

public class MeiziPresenter {
    private static final String TAG = "MeiziPresenter";
    private IMeiziModel meiziModel;
    private IMeiziView meiziView;

    public MeiziPresenter(IMeiziView meiziView) {
        this.meiziView = meiziView;
        meiziModel = new MeiziModel();
    }

    public void loadMeizi( int id ) {

    }
}
