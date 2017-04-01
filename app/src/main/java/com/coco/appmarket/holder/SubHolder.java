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
import com.coco.appmarket.bean.SubjectInfo;
import com.coco.appmarket.utils.HttpHelper;
import com.coco.appmarket.utils.UIUtils;

/**
 * 专题界面的holder.
 */

public class SubHolder extends BaseHolder<SubjectInfo> {

    private ImageView card_iv;
    private TextView card_des;

    @Override
    public View InitView() {
        View view = UIUtils.inflate(R.layout.item_cardview);
        card_iv = (ImageView) view.findViewById(R.id.card_iv);
        card_des = (TextView) view.findViewById(R.id.card_des);
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        card_des.setText(data.des);
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" +data.url).placeholder(R.mipmap.subject_default).into(card_iv);
    }
}
