package com.example.shaowenlong201901202.retrofit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApis {
    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String,String> map);
}
