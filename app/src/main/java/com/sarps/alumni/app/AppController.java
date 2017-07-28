package com.sarps.alumni.app;

/**
 * Created by Sarps on 7/11/2016.
 */

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.adapter.FontsOverride;


public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FontsOverride.setDefaultFont(this, "MONOSPACE", "Raleway-Regular.ttf");
    }

    public static synchronized AppController getInstance() {
        if(mInstance == null)
        {
            mInstance = new AppController();
        }
        return mInstance;
    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}