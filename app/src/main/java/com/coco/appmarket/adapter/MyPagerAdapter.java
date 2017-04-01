package com.coco.appmarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coco.appmarket.R;
import com.coco.appmarket.fragment.FragmentFactory;
import com.coco.appmarket.utils.UIUtils;

/**
 * pageradapter
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private String[] mTabNames;// 页签名称集合

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        mTabNames = UIUtils.getStringArray(R.array.tab_names);
    }

    // 加载每个页签标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }

    // 返回Fragment对象,将onCreateView方法的返回view填充给ViewPager
    // 此方法类似instantiateItem
    @Override
    public Fragment getItem(int position) {
        // 从工厂类中生产Fragment并返回
        return FragmentFactory.createFragment(position);
    }

    // 返回item个数
    @Override
    public int getCount() {
        return mTabNames.length;
    }
}
