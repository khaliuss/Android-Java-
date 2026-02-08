package com.example.dogtest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static ApiService apiFactory;
    private static String BASE_URL = "https://dog.ceo/api/breeds/";


    public static ApiService getApiFactory() {
        if (apiFactory == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            apiFactory = retrofit.create(ApiService.class);
        }
        return apiFactory;
    }
}
