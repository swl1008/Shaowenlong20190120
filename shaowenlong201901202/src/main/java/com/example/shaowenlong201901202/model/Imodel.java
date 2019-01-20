package com.example.shaowenlong201901202.model;

import com.example.shaowenlong201901202.callback.MyCallBack;

import java.util.Map;

public interface Imodel {
    void requestData(String url, Map<String,String> map, Class clazz, MyCallBack myCallBack);
}
