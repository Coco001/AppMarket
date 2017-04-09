package com.coco.appmarket.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import com.coco.appmarket.R;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.holder.DetailAppDesHolder;
import com.coco.appmarket.holder.DetailAppPicsHolder;
import com.coco.appmarket.holder.DetailAppSafeHolder;
import com.coco.appmarket.holder.DetailAppinforHolder;
import com.coco.appmarket.holder.DetailDownloadHolder;
import com.coco.appmarket.protocol.HomeDetailProtocol;
import com.coco.appmarket.utils.UIUtils;

/**
 * 应用详情的页面
 */

public class HomeDetailActivity extends AppCompatActivity {

    private LoadingPage mLoadingPage;
    private FrameLayout fl_detail_appinfo;
    private FrameLayout fl_detail_safeinfo;
    private HorizontalScrollView hsv_detail_pics;
    private FrameLayout fl_detail_des;
    private FrameLayout fl_detail_downlod;
    private AppInfo mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        mLoadingPage = new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };
        setContentView(mLoadingPage);//将view设置给activity
        // 开始加载数据
        mLoadingPage.loadData();
    }

    public View onCreateSuccessView() {
        View view = UIUtils.inflate(R.layout.layout_home_detail);
        fl_detail_appinfo = (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
        fl_detail_safeinfo = (FrameLayout) view.findViewById(R.id.fl_detail_safeinfo);
        hsv_detail_pics = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pics);
        fl_detail_des = (FrameLayout) view.findViewById(R.id.fl_detail_des);
        fl_detail_downlod = (FrameLayout) view.findViewById(R.id.fl_detail_downlod);

        DetailAppinforHolder appinforHolder = new DetailAppinforHolder();//详情
        fl_detail_appinfo.addView(appinforHolder.getRootView());
        appinforHolder.setData(mData);

        DetailAppSafeHolder appSafeHolder = new DetailAppSafeHolder();//安全
        fl_detail_safeinfo.addView(appSafeHolder.getRootView());
        appSafeHolder.setData(mData);

        DetailAppPicsHolder appPicsHolder = new DetailAppPicsHolder();//图片
        hsv_detail_pics.addView(appPicsHolder.getRootView());
        appPicsHolder.setData(mData);

        DetailAppDesHolder appDesHolder = new DetailAppDesHolder();//描述
        fl_detail_des.addView(appDesHolder.getRootView());
        appDesHolder.setData(mData);

        DetailDownloadHolder downloadHolder = new DetailDownloadHolder();//下载
        fl_detail_downlod.addView(downloadHolder.getRootView());
        downloadHolder.setData(mData);

        appinforHolder.setData(mData);
        return view;
    }

    public LoadingPage.ResultState onLoad() {
        //请求网络加载数据
        HomeDetailProtocol protocol = new HomeDetailProtocol(getIntent().getStringExtra("packageName"));
        mData = protocol.getData(0);
        if (mData != null) {
            return LoadingPage.ResultState.STATE_SUCCESS;//跳转到onCreateSuccessView()方法
        } else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }

}
