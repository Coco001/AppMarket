package com.coco.appmarket.utils;

import java.io.File;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 网络请求的工具类
 */

public class HttpUtils {

    public static void getOkHttpRequest(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);//enqueue内部实现了开启子线程
    }

    public static void postOkHttpRequest(String address, String[] titles, String[] contens, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        for (int i = 0; i < titles.length; i++) {
            builder.add(titles[i], contens[i]);
        }

        FormBody build = builder.build();

        Request request = new Request.Builder()
                .url(address)
                .post(build)
                .build();

        client.newCall(request).enqueue(callback);//enqueue内部实现了开启子线程
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    //上传文件
    public static void postOkHttpRequest(String address, String path, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        File file = new File(path);
        Request request = new Request.Builder()
                .url(address)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        client.newCall(request).enqueue(callback);
    }
}
