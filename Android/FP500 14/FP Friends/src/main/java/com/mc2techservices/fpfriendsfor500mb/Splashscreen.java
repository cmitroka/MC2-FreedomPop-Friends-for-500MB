package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

    WebComm wc1;
    Timer wc1Tmr;
    int wc1Cnt;

    ProgressBar progressBarSS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppSpecific.gloUseSplashscreen.equals("1"))
        {
            setContentView(R.layout.activity_splashscreen);
            //int x=1/0;//test for splashscreen
        }
        else
        {
            setContentView(R.layout.activity_splashscreen_no_background);
        }
        PreInit();
        Init();
        ConfigUser();
        LogAppLaunch();
        SetupScreen();
        GetFPStatus();
        //ShowWaiting();
    }

    private void PreInit()
    {
        /*
        boolean Conn=GeneralFunctions.Comm.isInternetConnectionThere(this,this);
        boolean C2=Conn;
        String pUUID=GeneralFunctions.Cfg.ReadSharedPreference("UUID");
        String pKey1=GeneralFunctions.Text.GetRandomString("ANF",15);
        String pKey2=Decode.PMConvertIDtoValue(pKey1);
        Log.d("APP", "pKey2: "+pKey2);
        */

        /*
        f = new Foo();
        String pParams = "pSetPassword=&pSet1or0=";
        String pURL="http://fp.mc2techservices.com/FPFriender.asmx" + "/FPStatus";
        f.execFoo(pURL,pParams);
        String z=f.pWebResponse;
        */



        //String pParams = "pSetPassword=&pSet1or0=";
        //String pURL=AppSpecific.gloWebServiceURL + "/FPStatus";
        //GeneralFunctions.Comm.NonAsyncWebCall(pURL, pParams);
        //AsyncTask<String, String, String> awc=new GeneralFunctions.AsyncWebCall().execute(pURL,pParams);
    }

    private void Init()
    {
        AppSpecific.gloPackageName = getApplicationContext().getPackageName();
        AppSpecific.gloBlockFRs="0";
        AppSpecific.gloLD="XZQX";
        AppSpecific.gloPD="~_~";
        AppSpecific.gloxmlns= "xmlns=\"fpfriender.mc2techservices.com\">";
        AppSpecific.gloWebURL="http://fp.mc2techservices.com/";
        //AppSpecific.gloWebURL="http://192.168.199.1/FPF/";  //test
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
            if (android_id==null) android_id="";
            if (android_id.length()<5) android_id=GeneralFunctions.Text.GetRandomString("ANF", 15);
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
    private void GetFPStatus()
    {
        wc1 = new WebComm();
        String pWCParams = "pSetPassword=&pSet1or0=";
        String pWCURL=AppSpecific.gloWebServiceURL + "/FPStatus";
        wc1.ExecuteWebRequest(pWCURL,pWCParams);
        GetWC1();
    }
    private void LogAppLaunch()
    {
        WebComm wc = new WebComm();
        String pParams = "pUUID=" + AppSpecific.gloUUID + "&pChannel=Android";
        String pURL=AppSpecific.gloWebServiceURL + "/LogUsage";
        wc.ExecuteWebRequest(pURL,pParams);
        //GeneralFunctions.Comm.NonAsyncWebCall(pURL, pParams);
        //new GeneralFunctions.AsyncWebCall().execute(pURL,pParams);
    }
    private void GetWC1()
    {
        wc1Cnt=0;
        wc1Tmr=new Timer();
        wc1Tmr.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        wc1Cnt++;
                        Log.d("APP", String.valueOf(wc1Tmr));
                        if (wc1Cnt>10)
                        {
                            //timed out
                            wc1Tmr.cancel();
                            Log.d("APP", "Timed Out");
                            ShowIssue("We couldn't talk to our server for 10 seconds, so you may be offline or we're rebooting.  You can still use the app, but it may be limited.");
                        }
                        else
                        {
                            Log.d("APP", "equals");
                            if (!wc1.wcWebResponse.equals("..."))
                            {
                                wc1Tmr.cancel();
                                Log.d("APP", "Theres a Response");
                                if (wc1.wcWebResponse.equals("1"))
                                {
                                    SwitchScreens();
                                }
                                else
                                {
                                    AppSpecific.gloBlockFRs="1";
                                    ShowIssue("We couldn't talk to our server for some reason, so you may be offline or we're rebooting.  You can still use the app, but it may be limited.");
                                }
                            }
                        }
                    }
                });
            }
        }, 0, 1000); // 1000 means start delay (1 sec), and the second is the loop delay.
    }

    private void ShowIssue(String pMsg)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Oops");
        alert.setMessage(pMsg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SwitchScreens();
            }
        });
        alert.show();
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
