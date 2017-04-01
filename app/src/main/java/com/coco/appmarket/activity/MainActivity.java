package com.coco.appmarket.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.coco.appmarket.R;
import com.coco.appmarket.adapter.MyPagerAdapter;
import com.coco.appmarket.base.BaseActivity;
import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.fragment.FragmentFactory;

public class MainActivity extends BaseActivity {

    private TabLayout inditor_title;
    private ViewPager inditor_viewpager;

    @Override
    protected void initEvent() {
        inditor_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inditor_viewpager.removeOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
