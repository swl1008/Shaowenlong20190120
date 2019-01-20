package com.example.shaowenlong201901202.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
/**
 * date 2019/01/20
 * user 邵文龙
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
