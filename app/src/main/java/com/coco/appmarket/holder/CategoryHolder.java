package com.coco.appmarket.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.bean.CategoryInfo;
import com.coco.appmarket.utils.HttpHelper;
import com.coco.appmarket.utils.UIUtils;

/**
 * 分类holder
 */
public class CategoryHolder extends BaseHolder<CategoryInfo> implements OnClickListener {

    private ImageView ivIcon1, ivIcon2, ivIcon3;
    private TextView tvName1, tvName2, tvName3;
    private LinearLayout llGrid1, llGrid2, llGrid3;

    @Override
    public View InitView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.list_item_category, null);

        ivIcon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        ivIcon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        ivIcon3 = (ImageView) view.findViewById(R.id.iv_icon3);

        tvName1 = (TextView) view.findViewById(R.id.tv_name1);
        tvName2 = (TextView) view.findViewById(R.id.tv_name2);
        tvName3 = (TextView) view.findViewById(R.id.tv_name3);

        llGrid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        llGrid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        llGrid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);
        llGrid1.setOnClickListener(this);
        llGrid2.setOnClickListener(this);
        llGrid3.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        if (data != null) {
            tvName1.setText(data.name1);
            tvName2.setText(data.name2);
            tvName3.setText(data.name3);
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.url1)
                    .placeholder(R.mipmap.ic_default)
                    .into(ivIcon1);
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.url2)
                    .placeholder(R.mipmap.ic_default)
                    .into(ivIcon2);
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.url3)
                    .placeholder(R.mipmap.ic_default)
                    .into(ivIcon3);
        }
    }

    @Override
    public void onClick(View v) {
        CategoryInfo data = getData();

        switch (v.getId()) {
            case R.id.ll_grid1:
                Toast.makeText(UIUtils.getContext(), data.name1, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid2:
                Toast.makeText(UIUtils.getContext(), data.name2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid3:
                Toast.makeText(UIUtils.getContext(), data.name3, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

}
