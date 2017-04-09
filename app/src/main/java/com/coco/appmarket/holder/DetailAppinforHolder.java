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
 * 应用详情页面的holder.
 */

public class DetailAppinforHolder extends BaseHolder<AppInfo> {
    private ImageView iv_icon;
    private TextView tv_name;
    private RatingBar rb_star;
    private TextView tv_download_num;
    private TextView tv_version;
    private TextView tv_date;
    private TextView tv_size;

    @Override
    public View InitView() {
        View view = UIUtils.inflate(R.layout.layout_detail_appinfo);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_download_num = (TextView) view.findViewById(R.id.tv_download_num);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_size = (TextView) view.findViewById(R.id.tv_size);

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.iconUrl).into(iv_icon);
        tv_name.setText(data.name);
        rb_star.setNumStars((int) data.stars);
        tv_download_num.setText("下载量：" + data.downloadNum);
        tv_version.setText("版本号：" + data.version);
        tv_date.setText(data.date);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
    }
}
