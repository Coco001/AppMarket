package com.coco.appmarket.protocol;


import com.coco.appmarket.base.BaseProtocol;
import com.coco.appmarket.bean.AppInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * 应用界面的网络请求
 */

public class AppProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    private ArrayList<AppInfo> mAppList;// 应用列表集合
    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseJson(String result) {
        //解析json数据
        try {
            JSONArray ja = new JSONArray(result);
            mAppList = new ArrayList<AppInfo>();
            for (int i = 0; i < ja.length(); i++) {
                AppInfo info = new AppInfo();

                JSONObject jo1 = (JSONObject) ja.get(i);
                info.des = jo1.getString("des");
                info.downloadUrl = jo1.getString("downloadUrl");
                info.iconUrl = jo1.getString("iconUrl");
                info.id = jo1.getString("id");
                info.name = jo1.getString("name");
                info.packageName = jo1.getString("packageName");
                info.size = jo1.getLong("size");
                info.stars = jo1.getDouble("stars");

                mAppList.add(info);
            }

            return mAppList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
