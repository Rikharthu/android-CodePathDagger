package com.example.android.codepathdagger.module;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module // Module class provides dependencies (creates them)
// signal to Dagger to search within the available methods for possible instance providers.
public class NetModule {
    public static final String TAG=NetModule.class.getSimpleName();

    String mBaseUrl;

    // method naming doesn't matter. return type and @Provides annotation is used to associating.

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    // Dagger will only look for methods annotated with @Provides
    @Provides // provides dependency
    @Singleton// single instance,created once ( also #Thread-Safe)
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        Log.d(TAG,"providesSharedPreferences()");
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    Cache provideOkHttpCache(Application application) {
        Log.d(TAG,"provideOkHttpCache()");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        Log.d(TAG,"provideGson()");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    // If we need two different objects of the same return type, we can use the @Named qualifier annotation
    @Provides @Named("cached")
    @Singleton
    OkHttpClient provideOkHttpClientCached(Cache cache) {
        Log.d(TAG,"provideOkHttpClient() - cached");
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        return client;
    }

    @Provides @Named("non_cached") @Singleton
    OkHttpClient provideOkHttpClient() {
        Log.d(TAG,"provideOkHttpClient()");
        OkHttpClient client = new OkHttpClient();
        return client;
    }

    // Retrofit instance depends both on Gson and OkHttpClient.
    // @Provides annotation will recognize that there is a dependency on Gson and OkHttpClient
    // to build a Retrofit instance.
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Log.d(TAG,"provideRetrofit()");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
