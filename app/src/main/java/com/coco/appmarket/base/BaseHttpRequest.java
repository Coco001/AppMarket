package com.coco.appmarket.base;

import com.coco.appmarket.utils.HttpUtils;
import com.coco.appmarket.utils.IOUtils;
import com.coco.appmarket.utils.UIUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 请求网络的基类
 */

public abstract class BaseHttpRequest<T> {
    private static String URL = "http://127.0.0.1:8090/";
    private boolean flag = false;
    private String resultFromNet;
    //分页从请求数据，先检查本地是否存在缓存
    public T getData(int index) {
        String result = getCache(index);
        if (result != null) {
            return parseJson(result);
        } else {
            getDataFromNet(index);
            while (!flag) {

            }
            flag = false;
            result = resultFromNet;
        }
        return parseJson(result);
    }

    private void getDataFromNet(final int index) {
        HttpUtils.getOkHttpRequest(URL + getKey() + "?index" + index + getParams(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                //将数据缓存到本地
                setCache(s, index);
            }
        });
    }

    private void setCache(String s, int index) {
        resultFromNet = s;
        flag = true;
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, getKey() + index + getParams());
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            //设置缓存的有效时间
            long deadLine = System.currentTimeMillis() + 30 * 60 * 1000;
            writer.write(deadLine + "\n");

            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }

    private String getCache(int index) {
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, getKey() + index + getParams());

        if (cacheFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadLine = reader.readLine();
                long deadTime = Long.parseLong(deadLine);
                StringBuilder sb = new StringBuilder();
                String line;
                if (System.currentTimeMillis() < deadTime) {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    //获取网络接口的具体参数
    protected abstract String getParams();

    //获取网络接口的具体地址
    protected abstract String getKey();

    //子类实现，由于每个页面请求的数据都不一样
    protected abstract T parseJson(String result);

}
