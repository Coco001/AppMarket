package com.coco.appmarket.protocol;

import com.coco.appmarket.base.BaseProtocol;
import com.coco.appmarket.bean.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * 分类界面的网络请求
 */

public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {
    private ArrayList<CategoryInfo> mCategoryList;// 应用列表集合
    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategoryInfo> parseJson(String result) {
        //解析json数据
        try {
            JSONArray ja = new JSONArray(result);
            mCategoryList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject object = ja.getJSONObject(i);
                CategoryInfo titleInfo = new CategoryInfo();
                if (object.has("title")) {
                    String title = object.getString("title");
                    titleInfo.title = title;
                    titleInfo.isTitle = true;
                    mCategoryList.add(titleInfo);
                }
                if (object.has("infos")) {
                    JSONArray infos = object.getJSONArray("infos");
                    for (int j = 0; j < infos.length(); j++) {
                        CategoryInfo info = new CategoryInfo();
                        JSONObject obj = infos.getJSONObject(j);
                        info.name1 = obj.getString("name1");
                        info.name2 = obj.getString("name2");
                        info.name3 = obj.getString("name3");
                        info.url1 = obj.getString("url1");
                        info.url2 = obj.getString("url2");
                        info.url3 = obj.getString("url3");
                        info.isTitle = false;
                        mCategoryList.add(info);
                    }
                }
            }

            return mCategoryList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
