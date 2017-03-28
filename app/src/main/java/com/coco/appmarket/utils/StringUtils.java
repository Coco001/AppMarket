package com.coco.appmarket.utils;

import android.text.TextUtils;

public class StringUtils {
	public static boolean isEmpty(String value) {
		if (TextUtils.isEmpty(value)) {
			return false;
		} else {
			return true;
		}
	}
}
