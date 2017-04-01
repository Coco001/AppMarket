package com.coco.appmarket.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.utils.HttpHelper;
import com.coco.appmarket.utils.UIUtils;

/**
 * 应用界面的holder.
 */

public class AppHolder extends BaseHolder<AppInfo> {

    private ImageView iv_icon;
    private RatingBar rb_star;
    private TextView tv_name;
    private TextView tv_size;
    private TextView tv_des;

    @Override
    public View InitView() {
        View view = UIUtils.inflate(R.layout.item_listview);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        rb_star.setNumStars((int) data.stars);
        tv_name.setText(data.name);
        tv_des.setText(data.des);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" +data.iconUrl).into(iv_icon);
    }
}
