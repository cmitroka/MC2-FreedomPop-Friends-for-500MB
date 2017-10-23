package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.ads.MobileAds;
import com.mc2techservices.ads.AdsMain;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

public class Splashscreen extends Activity {
    private static Timer t;
    int pCounter;
    ProgressBar progressBarSS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        t=new Timer();
        //PreInit();
        Init();
        ConfigUser();
        LogAppLaunch();
        SetupScreen();
        ShowWaiting();
    }

    private void PreInit()
    {
        boolean Conn=GeneralFunctions.Comm.isInternetConnectionThere(this,this);
        boolean C2=Conn;
        String pUUID=GeneralFunctions.Cfg.ReadSharedPreference("UUID");
        String pKey1=GeneralFunctions.Text.GetRandomString("ANF",15);
        String pKey2=Decode.PMConvertIDtoValue(pKey1);
        Log.d("APP", "pKey2: "+pKey2);
    }

    private void Init()
    {
        AppSpecific.gloPackageName = getApplicationContext().getPackageName();
        AppSpecific.gloLD="XZQX";
        AppSpecific.gloPD="~_~";
        AppSpecific.gloxmlns= "xmlns=\"fpfriender.mc2techservices.com\">";
        AppSpecific.gloWebURL="http://fp.mc2techservices.com/";
        AppSpecific.gloWebURL="http://192.168.199.1/FPF/";  //test
        AppSpecific.gloWebServiceURL=AppSpecific.gloWebURL + "FPFriender.asmx";
        MobileAds.initialize(this,"ca-app-pub-2250341510214691~6445840426");  //FP Friends for 500MB App ID
        //GeneralFunctions.Cfg.WriteSharedPreference(GeneralFunctions.Dte.GetCurrentDate(), "");  //test
    }
    private void ConfigUser()
    {
        //GeneralFunctions.Cfg.WriteSharedPreference("UUID", "");
        if (GeneralFunctions.Cfg.ReadSharedPreference("UUID").equals(""))
        {
            //GeneralFunctions.Cfg.WriteSharedPreference("UUID", GeneralFunctions.Text.GetRandomString("ANF", 8));  //Old way
            String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
            GeneralFunctions.Cfg.WriteSharedPreference("UUID",android_id);
        }
        AppSpecific.gloUUID=GeneralFunctions.Cfg.ReadSharedPreference("UUID");
        //AppSpecific.gloUUID="00000000000001"; //test
        String pKey=Decode.PMConvertIDtoValue(AppSpecific.gloUUID);
        AppSpecific.gloKey=pKey;
        if (GeneralFunctions.Cfg.ReadSharedPreference("Bonus1").equals(""))
        {
            String r1 = GeneralFunctions.Text.GetRandomInt(1,3);
            String r2 = GeneralFunctions.Text.GetRandomInt(4,5);
            GeneralFunctions.Cfg.WriteSharedPreference("Bonus1", r1);
            GeneralFunctions.Cfg.WriteSharedPreference("Bonus2", r2);
        }
    }

    private void SetupScreen()
    {
        TextView txtAppName=(TextView)findViewById(R.id.txtTitle);
        Typeface face=Typeface.createFromAsset(getAssets(),
                "fonts/AsimovProUltObl.otf");
        txtAppName.setTypeface(face);
        TextView txtAppVersion=(TextView)findViewById(R.id.txtAppVersion);
        progressBarSS=(ProgressBar) findViewById(R.id.progressBarSS);
        progressBarSS.setAlpha(1);
        txtAppVersion.setText(GeneralFunctions.Sys.GetVersion());
    }
    private void LogAppLaunch()
    {
        String pParams = "pUUID=" + AppSpecific.gloUUID + "&pChannel=Android";
        String pURL=AppSpecific.gloWebServiceURL + "/LogUsage";
        //GeneralFunctions.Comm.NonAsyncWebCall(pURL, pParams);
        new GeneralFunctions.AsyncWebCall().execute(pURL,pParams);
    }
    private void ShowWaiting()
    {
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        pCounter++;
                        if (pCounter>3)
                        {
                            t.cancel();
                            progressBarSS.setAlpha(0);
                            Log.d("APP", "Switch Screens");
                            SwitchScreens();
                        }
                    }
                });
            }
        }, 0, 1000); // 1000 means start delay (1 sec), and the second is the loop delay.
    }

    private void SwitchScreens()
    {
        Intent intent = new Intent(this, WhatIsFPF500.class);
        startActivity(intent);
    }

    @Override
    public void onResume () {
        super.onResume();
        SetupScreen();
    }

}
