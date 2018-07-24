package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PreLaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pre_launch);
        //GeneralFunctions.Cfg.WriteSharedPreference("UseSplashscreen", "");  //test for splashscreen
        AppSpecific.gloUseSplashscreen = GeneralFunctions.Cfg.ReadSharedPreference("UseSplashscreen");
        if (AppSpecific.gloUseSplashscreen.equals("")) {
            GeneralFunctions.Cfg.WriteSharedPreference("UseSplashscreen", "0");
            AppSpecific.gloUseSplashscreen = "1";
        }
        Launch();
    }
    private void Launch()
    {
        Intent intent = new Intent(this, Splashscreen.class);
        startActivity(intent);
        finish();
    }

}
