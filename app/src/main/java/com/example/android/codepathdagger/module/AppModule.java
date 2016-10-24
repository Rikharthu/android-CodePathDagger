package com.example.android.codepathdagger.module;


import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
/** Provides Application Context */
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton // also #ThreadSafe
    // denotes to Dagger that this method is the constructor for the Application return type
    Application providesApplication() {
        return mApplication;
    }
}
