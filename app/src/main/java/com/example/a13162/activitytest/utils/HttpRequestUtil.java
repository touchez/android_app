package com.example.a13162.activitytest.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestUtil {

    private static final String openLockUrl = "https://touchez.cn:8090/pyapi/openlock";

    public static void callOpenLock(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(openLockUrl)
                        .build();

                try {
                    try (Response response = okHttpClient.newCall(request).execute()) {
//                        return response.body().string();
                        Log.i("touchez", "call OpenLock api, response is: " + response);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
