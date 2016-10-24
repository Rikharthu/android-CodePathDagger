package com.example.android.codepathdagger;


import android.app.Application;

import com.example.android.codepathdagger.component.DaggerNetComponent;
import com.example.android.codepathdagger.component.NetComponent;
import com.example.android.codepathdagger.module.AppModule;
import com.example.android.codepathdagger.module.NetModule;

public class MyApp extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Instantiate the component within an Application class since these instances
        // should be declared only once throughout the entire lifespan of the application:

        // Rebuild the project for generated Dagger Component (injector class) to appear
        // Dagger%COMPONENT_NAME%
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule("https://api.github.com"))
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mNetComponent = com.codepath.dagger.components.DaggerNetComponent.create();

        // Rename Application in the manifest, so Android launchis this Class instead
        /* <application
                android:allowBackup="true"
                android:name=".MyApp"> */
    }

    /** Returns DaggerNetComponent NetModule with Application context*/
    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
