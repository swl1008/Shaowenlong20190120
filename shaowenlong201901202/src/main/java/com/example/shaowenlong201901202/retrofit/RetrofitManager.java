package com.example.shaowenlong201901202.retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * date 2019/01/20
 * user 邵文龙
 */

public class RetrofitManager {
        private final String BASE_URL="http://www.zhaoapi.cn/product/";
        private static RetrofitManager instance;
        private BaseApis baseApis;

    public static RetrofitManager getInstance() {
        if (instance == null){
            synchronized (RetrofitManager.class){
                instance = new RetrofitManager();
            }
        }
        return instance;
    }

    private RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(15,TimeUnit.SECONDS)
                .connectTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true);

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();

        baseApis = retrofit.create(BaseApis.class);
    }
    //post请求
    public void post(String url, Map<String,String> map,HttpListener listener){
        if (map == null){
            map = new HashMap<>();
        }
        baseApis.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    public Observer getObserver(final HttpListener listener){
        Observer observer = new Observer<ResponseBody>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (listener != null){
                    listener.onFailed(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (listener != null){
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null){
                        listener.onFailed(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

    public interface HttpListener{
        void onSuccess(String data);
        void onFailed(String error);
    }
}
