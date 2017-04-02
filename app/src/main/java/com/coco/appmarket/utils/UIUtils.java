package com.coco.appmarket.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.coco.appmarket.base.BaseApplication;

public class UIUtils {

	public static Context getContext() {
		return BaseApplication.getContext();
	}

	public static int getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	public static Handler getHandler() {
		return BaseApplication.getHandler();
	}

	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}

	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);
	}

	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	public static int dip2px(float dp) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (density * dp + 0.5);
	}

	public static float px2dip(float px) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return px / density;
	}

	public static View inflate(int layoutId) {
		return View.inflate(getContext(), layoutId, null);
	}

	public static boolean isRunOnUiThread() {
		return getMainThreadId() == android.os.Process.myTid();
	}

	public static void runOnUiThread(Runnable runnable) {
		if (isRunOnUiThread()) {
			runnable.run();
		} else {
			getHandler().post(runnable);
		}
	}

	/**
	 * 获取屏幕相关参数
	 * @param context 上下文
	 * @return 屏幕的宽高
	 */
	public static DisplayMetrics getScreenSize(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		display.getMetrics(metrics);
		return metrics;
	}
}
