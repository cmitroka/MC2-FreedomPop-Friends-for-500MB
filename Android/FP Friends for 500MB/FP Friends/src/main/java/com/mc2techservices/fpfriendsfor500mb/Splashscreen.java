package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

public class Splashscreen extends Activity {
    private static Timer t=new Timer();
    int pCounter;
    ProgressBar progressBarSS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
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
        //AppSpecific.gloWebURL="http://192.168.199.1/FPF/";  //test
        AppSpecific.gloWebServiceURL=AppSpecific.gloWebURL + "FPFriender.asmx";
        MobileAds.initialize(this,"ca-app-pub-2250341510214691~6445840426");  //FP Friends for 500MB App ID
        GeneralFunctions.Cfg.WriteSharedPreference("WatchedAd", "");
    }
    private void ConfigUser()
    {
        //GeneralFunctions.Cfg.WriteSharedPreference("UUID", "");
        if (GeneralFunctions.Cfg.ReadSharedPreference("UUID").equals(""))
        {
            GeneralFunctions.Cfg.WriteSharedPreference("UUID", GeneralFunctions.Text.GetRandomString("ANF", 8));
        }
        AppSpecific.gloUUID=GeneralFunctions.Cfg.ReadSharedPreference("UUID");
        //AppSpecific.gloUUID="00000000000001"; //test
        String pKey=Decode.PMConvertIDtoValue(AppSpecific.gloUUID);
        AppSpecific.gloKey=pKey;
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
        finish();
    }

    @Override
    public void onResume () {
        super.onResume();
        SetupScreen();
    }

}
