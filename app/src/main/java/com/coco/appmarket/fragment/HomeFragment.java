package com.coco.appmarket.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.bumptech.glide.Glide;
import com.coco.appmarket.R;
import com.coco.appmarket.activity.HomeDetailActivity;
import com.coco.appmarket.base.BaseApplication;
import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.base.MyBaseAdapter;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.holder.HomeHolder;
import com.coco.appmarket.protocol.HomeProtocol;
import com.coco.appmarket.utils.UIUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import java.util.ArrayList;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

	private ArrayList<AppInfo> mData;
	private Context mContext;
	private Banner mBanner;
	private ArrayList<String> mImagesUrl;

	@Override
	public View onCreateSuccessView() {
		mContext = BaseApplication.getContext();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_home, null);

		ListView listView = (ListView) view.findViewById(R.id.home_listview);
		mBanner = (Banner) UIUtils.inflate(R.layout.banner_item);
		setBanner();
		DisplayMetrics size = UIUtils.getScreenSize(UIUtils.getContext());
		int widthPixels = size.widthPixels;
		int heightPixels = size.heightPixels;
		mBanner.setLayoutParams(new AbsListView.LayoutParams(widthPixels, heightPixels/3));
		listView.addHeaderView(mBanner);
		HomeAdapter adapter = new HomeAdapter(mData);
		listView.setAdapter(adapter);
		//设置条目监听
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(UIUtils.getContext(), HomeDetailActivity.class);
                //获取点击的对象
                AppInfo info = mData.get(position - 1);
                if (info != null) {
                    intent.putExtra("packageName", info.packageName);
                }
                startActivity(intent);
			}
		});
		return view;
	}

	private void setBanner() {
		//设置banner样式
		mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
		//设置图片加载器
		mBanner.setImageLoader(new GlideImageLoader());

		mBanner.setImages(mImagesUrl);
		//设置banner动画效果
		mBanner.setBannerAnimation(Transformer.Accordion);
		//设置标题集合（当banner样式有显示title时）
		//String[] titles = new String[]{"砍价我最行", "人脉总动员", "人脉总动员","想不到你是这样的app", "疯狂购物节","砍价我最行", "人脉总动员", "想不到你是这样的app", "疯狂购物节","疯狂购物节"};
		//mBanner.setBannerTitles(Arrays.asList(titles));
		//设置自动轮播，默认为true
		mBanner.isAutoPlay(true);
		//设置轮播时间
		mBanner.setDelayTime(1500);
		//设置指示器位置（当banner模式中有指示器时）
		mBanner.setIndicatorGravity(BannerConfig.RIGHT);
		//banner设置方法全部调用完毕时最后调用
		mBanner.start();
	}

	public class GlideImageLoader extends ImageLoader {
		@Override
		public void displayImage(Context context, Object path, ImageView imageView) {
			//Picasso 加载图片简单用法
			Glide.with(context).load((String) path).into(imageView);
		}

	}

	@Override
	public LoadingPage.ResultState onLoad() {
		HomeProtocol protocol = new HomeProtocol();
		mData = protocol.getData(0);
		mImagesUrl = protocol.getPictureList();
		return check(mData);
	}

	class HomeAdapter extends MyBaseAdapter<AppInfo> {
		public HomeAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		protected ArrayList<AppInfo> onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			ArrayList<AppInfo> moreData = protocol.getData(getListSize());
			return moreData;
		}

		@Override
		protected BaseHolder getHolder(int position) {
			return new HomeHolder();
		}

	}

}
