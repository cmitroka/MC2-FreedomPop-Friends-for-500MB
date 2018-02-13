package com.mc2techservices.fpfriendsfor500mb;
import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDex;

public class fpfriendsfor500mb extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        fpfriendsfor500mb.context = getApplicationContext();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }
    public static Context getAppContext() {
        return fpfriendsfor500mb.context;
    }
}