package com.example.shaowenlong201901202.presenter;

import com.example.shaowenlong201901202.callback.MyCallBack;
import com.example.shaowenlong201901202.model.ModelImpl;
import com.example.shaowenlong201901202.view.Iview;

import java.util.Map;

public class PresenterImpl implements Ipresenter{

    private Iview iview;
    private ModelImpl model;

    public PresenterImpl(Iview iview) {
        this.iview = iview;
        model = new ModelImpl();
    }
    @Override
    public void startRequestPost(String url, Map<String, String> map, Class clazz) {
        model.requestData(url, map, clazz, new MyCallBack() {
            @Override
            public void getData(Object data) {
                iview.showData(data);
            }
        });
    }
    public void onDetach(){
        if (iview != null){
            iview = null;
        }
        if (model != null){
            model = null;
        }
    }
}
