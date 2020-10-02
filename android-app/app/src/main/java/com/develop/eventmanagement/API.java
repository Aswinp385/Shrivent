package com.develop.eventmanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class API {
    public static String BaseUrl = "http://cassiel.in/event/api/";
    public static String ImageUrl = "http://68.183.95.156/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        //if (retrofit == null) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        // }
        return retrofit;
    }

    public static Retrofit getSimpleClient() {
        // if (retrofit == null) {
        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        // }
        return retrofit;

    }


    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(50, TimeUnit.SECONDS)
            .connectTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(new BasicAuthInterceptor("admin", "1234"))
            .build();


    private static class BasicAuthInterceptor implements Interceptor {
        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }
}
