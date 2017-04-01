package com.coco.appmarket.protocol;


import com.coco.appmarket.base.BaseProtocol;
import org.json.JSONArray;
import java.util.ArrayList;

/**
 * 应用界面的网络请求
 */

public class RecoProtocol extends BaseProtocol<ArrayList<String>> {

    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseJson(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                list.add(ja.getString(i));
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
