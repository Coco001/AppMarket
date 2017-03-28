package com.coco.appmarket.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.coco.appmarket.R;
import com.coco.appmarket.adapter.MyPagerAdapter;
import com.coco.appmarket.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private TabLayout inditor_title;
    private ViewPager inditor_viewpager;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        inditor_viewpager.setAdapter(adapter);
        inditor_title.setupWithViewPager(inditor_viewpager);
    }

    @Override
    protected void initView() {
        inditor_title = (TabLayout) findViewById(R.id.inditor_title);
        inditor_viewpager = (ViewPager) findViewById(R.id.inditor_viewpager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
