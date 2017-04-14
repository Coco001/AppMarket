package com.coco.appmarket.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.bean.DownloadInfo;
import com.coco.appmarket.utils.DownloadManager;
import com.coco.appmarket.utils.HttpHelper;
import com.coco.appmarket.utils.ProgressArc;
import com.coco.appmarket.utils.UIUtils;

/**
 * 应用界面的holder.
 */

public class AppHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObserver,View.OnClickListener{

    private ImageView iv_icon;
    private RatingBar rb_star;
    private TextView tv_name;
    private TextView tv_size;
    private TextView tv_des;
    private FrameLayout fl_download;
    private TextView tv_download;

    private ProgressArc pbProgress;
    private DownloadManager mDownloadManager;
    private float mProgress;// 当前下载进度
    private int mCurrentState;// 当前下载状态
    private AppInfo mAppInfo;

    @Override
    public View InitView() {
        View view = UIUtils.inflate(R.layout.item_listview);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_des = (TextView) view.findViewById(R.id.tv_des);

        //下载按钮
        fl_download = (FrameLayout) view.findViewById(R.id.fl_download);
        tv_download = (TextView) view.findViewById(R.id.tv_download);

        //初始化圆形进度条
        pbProgress = new ProgressArc(UIUtils.getContext());
        //设置圆形进度条直径
        pbProgress.setArcDiameter(UIUtils.dip2px(26));
        //设置进度条颜色
        pbProgress.setProgressColor(UIUtils.getColor(R.color.progress));
        //设置进度条宽高布局参数
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                UIUtils.dip2px(27), UIUtils.dip2px(27));
        fl_download.addView(pbProgress, params);

        fl_download.setOnClickListener(this);

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        rb_star.setNumStars((int) data.stars);
        tv_name.setText(data.name);
        tv_des.setText(data.des);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" +data.iconUrl).into(iv_icon);

        mAppInfo = data;

        mDownloadManager = DownloadManager.getInstance();
        // 监听下载进度
        mDownloadManager.registerObserver(this);

        DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(data);
        if (downloadInfo == null) {
            // 没有下载过
            mCurrentState = DownloadManager.STATE_NONE;
            mProgress = 0;
        } else {
            // 之前下载过, 以内存中的对象的状态为准
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        }

        refreshUI(mProgress, mCurrentState, mAppInfo.id);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_download:
                // 根据当前状态来决定相关操作
                if (mCurrentState == DownloadManager.STATE_NONE
                        || mCurrentState == DownloadManager.STATE_PAUSE
                        || mCurrentState == DownloadManager.STATE_ERROR) {
                    // 开始下载
                    mDownloadManager.download(mAppInfo);
                } else if (mCurrentState == DownloadManager.STATE_DOWNLOAD
                        || mCurrentState == DownloadManager.STATE_WAITING) {
                    // 暂停下载
                    mDownloadManager.pause(mAppInfo);
                } else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
                    // 开始安装
                    mDownloadManager.install(mAppInfo);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshOnMainThread(info);
    }

    @Override
    public void onDownloadProgressChanged(DownloadInfo info) {
        refreshOnMainThread(info);
    }

    // 主线程刷新ui
    private void refreshOnMainThread(final DownloadInfo info) {
        // 判断要刷新的下载对象是否是当前的应用
        if (info.id.equals(mAppInfo.id)) {
            UIUtils.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refreshUI(info.getProgress(), info.currentState, info.id);
                }
            });
        }
    }

    /**
     * 刷新界面
     *
     * @param progress
     * @param state
     */
    private void refreshUI(float progress, int state, String id) {

        if (!mAppInfo.id.equals(id)) {
            return;
        }
        mCurrentState = state;
        mProgress = progress;
        switch (state) {
            case DownloadManager.STATE_NONE:
                pbProgress.setBackgroundResource(R.mipmap.ic_download);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tv_download.setText(UIUtils.getString(R.string.app_state_download));
                break;
            case DownloadManager.STATE_WAITING:
                pbProgress.setBackgroundResource(R.mipmap.ic_download);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tv_download.setText(UIUtils.getString(R.string.app_state_waiting));
                break;
            case DownloadManager.STATE_DOWNLOAD:
                pbProgress.setBackgroundResource(R.mipmap.ic_pause);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                pbProgress.setProgress(progress, true);
                tv_download.setText((int) (progress * 100) + "%");
                break;
            case DownloadManager.STATE_PAUSE:
                pbProgress.setBackgroundResource(R.mipmap.ic_resume);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                fl_download.setVisibility(View.VISIBLE);
                break;
            case DownloadManager.STATE_ERROR:
                pbProgress.setBackgroundResource(R.mipmap.ic_redownload);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tv_download.setText(UIUtils.getString(R.string.app_state_error));
                break;
            case DownloadManager.STATE_SUCCESS:
                pbProgress.setBackgroundResource(R.mipmap.ic_install);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tv_download.setText(UIUtils.getString(R.string.app_state_downloaded));
                break;

            default:
                break;
        }
    }

}
