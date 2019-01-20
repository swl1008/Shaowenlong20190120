package com.example.shaowenlong201901202.model;

import com.example.shaowenlong201901202.callback.MyCallBack;
import com.example.shaowenlong201901202.retrofit.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

/**
 * date 2019/01/20
 * user 邵文龙
 */
public class ModelImpl implements Imodel{
    @Override
    public void requestData(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null){
                    myCallBack.getData(o);
                }
            }

            @Override
            public void onFailed(String error) {
            }
        });
    }
}
