package com.coco.appmarket.fragment;

import android.graphics.Color;
import android.media.MediaRecorder;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.protocol.RecoProtocol;
import com.coco.appmarket.utils.UIUtils;
import com.coco.appmarket.utils.fly.ShakeListener;
import com.coco.appmarket.utils.fly.StellarMap;

import java.util.ArrayList;
import java.util.Random;


/**
 * 推荐
 */
public class RecommendFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {
        final StellarMap view = (StellarMap) View.inflate(UIUtils.getContext(), R.layout.fragment_recommend, null);
        RecAdapter adapter = new RecAdapter();
        view.setAdapter(adapter);
        view.setRegularity(6, 9);//9行6列的格子
        int padding = UIUtils.dip2px(18);
        view.setInnerPadding(padding, padding, padding, padding);//设置内边界
        view.setGroup(0, true);//设置第一页显示
        //设置摇晃的监听事件
        ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                view.zoomIn();//跳转到下一页
            }
        });
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        RecoProtocol protocol = new RecoProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class RecAdapter implements StellarMap.Adapter {

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            int count = mData.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                count += mData.size() % getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            position += group * getCount(group - 1);
            String text = mData.get(position);
            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(text);
            Random random = new Random();
            int size = 18 + random.nextInt(10);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);//设置字体大小
            //设置颜色
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(222);
            int b = 30 + random.nextInt(200);
            textView.setTextColor(Color.rgb(r, g, b));
            return textView;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn) {
                if (group > 0) {
                    group--;
                } else {
                    group = getGroupCount() - 1;
                }
            } else {
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    group = 0;
                }
            }
            return group;
        }
    }

}
