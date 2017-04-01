package com.coco.appmarket.protocol;


import com.alibaba.fastjson.JSON;
import com.coco.appmarket.base.BaseProtocol;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.bean.SubjectInfo;

import java.util.ArrayList;

/**
 * 专题界面的网络请求
 */

public class SubProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<SubjectInfo> parseJson(String result) {
        //解析json数据
        ArrayList<SubjectInfo> list = (ArrayList<SubjectInfo>) JSON.parseArray(result, SubjectInfo.class);

        return list;
    }

}
