package com.coco.appmarket.protocol;

import com.coco.appmarket.base.BaseProtocol;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.utils.HttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * 主页界面的网络请求
 */

public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    private ArrayList<String> pictures;
    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseJson(String result) {
        //解析json数据
        try {
            JSONObject jo = new JSONObject(result);
            // 解析应用列表数据
            JSONArray ja = jo.getJSONArray("list");
            ArrayList<AppInfo> appInfoList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo info = new AppInfo();
                info.des = jo1.getString("des");
                info.downloadUrl = jo1.getString("downloadUrl");
                info.iconUrl = jo1.getString("iconUrl");
                info.id = jo1.getString("id");
                info.name = jo1.getString("name");
                info.packageName = jo1.getString("packageName");
                info.size = jo1.getLong("size");
                info.stars = (float) jo1.getDouble("stars");
                appInfoList.add(info);
            }

            // 初始化轮播条的数据
            JSONArray ja1 = jo.getJSONArray("picture");
            pictures = new ArrayList<>();
            for (int i = 0; i < ja1.length(); i++) {
                String pic = HttpHelper.URL + "image?name=" + ja1.getString(i);
                pictures.add(pic);
            }
            return appInfoList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getPictureList() {
        return pictures;
    }
}
