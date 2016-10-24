package com.example.android.codepathdagger;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

// TODO see drawable/modules_and_components.PNG

public class MainActivity extends AppCompatActivity {
    public static final String TAG= MainActivity.class.getSimpleName();

    @Inject @Named("cached")
    OkHttpClient cachedClient;
    @Inject @Named("non_cached") OkHttpClient client;
    @Inject
    SharedPreferences sharedPreferences;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_main);

        // assign annotated singleton instances to fields
        // InjectorClass.inject(this);
        // getComponent().inject(this);
        // We need to cast to `MyApp` in order to get the right method
        ((MyApp) getApplication()).getNetComponent().inject(this);
        // analogy - ButterKnife.bind(this);

        // you can also inject single instances
//        cachedClient=((MyApp) getApplication()).getNetComponent().provideCachedOkHttpClient();

        sharedPreferences.edit().putString("key","1234").commit();
        Log.d(TAG,sharedPreferences.getString("key",null));
        Log.d(TAG,sharedPreferences.toString());
    }
}
