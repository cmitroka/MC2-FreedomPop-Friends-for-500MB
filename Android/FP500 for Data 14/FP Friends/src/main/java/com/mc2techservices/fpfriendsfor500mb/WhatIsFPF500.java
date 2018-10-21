package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WhatIsFPF500 extends Activity {
    Spinner spinner;
    AdView pBannerAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_fpf500);
        UpdateUseSplashscreen();
        SetupDropdown();
        SetupBannerAd();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, ContactUs.class);
        startActivity(intent);
        SetupBannerAd();
        return true;
    }
    private void UpdateUseSplashscreen()
    {
        try {
            if (AppSpecific.gloUseSplashscreen.equals("1"))
            {
                GeneralFunctions.Cfg.WriteSharedPreference("UseSplashscreen", "1");
            }
        }
        catch (Exception ex)
        {

        }
    }

    private void SetupBannerAd()
    {
        pBannerAd=(AdView)findViewById(R.id.pBannerAdView);
        AdRequest pAdRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("44F0D84065AAB1963737AF06A4054953")
                .build();
        pBannerAd.loadAd(pAdRequest);
        pBannerAd.setAdListener(new AdListener() {
            @Override
            public void onAdLeftApplication() {
                Log.d("APP", "Ad left application!");
                LogWatchAd();
            }
        });
    }
    private void LogWatchAd()
    {
        //string pUUID, string pDetails
        GeneralFunctions.Cfg.WriteSharedPreference("WatchedAd", "1");
        String pUUID=AppSpecific.gloUUID;
        String pKey1=GeneralFunctions.Text.GetRandomString("ANF",15);
        String pKey2=Decode.PMConvertIDtoValue(pKey1);
        String pParams = "pUUID=" + pUUID + "&pKey1=" + pKey1 + "&pKey2=" + pKey2 + "&pDetails=Ad0";
        String pURL=AppSpecific.gloWebServiceURL + "/LogCredits";
        new GeneralFunctions.AsyncWebCall().execute(pURL,pParams);
    }

    private void SetupDropdown()
    {
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10 or more");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    public void onDeterimeUsefulnessClicked(View arg0) {
        DeterimeUsefulness();
    }
    private void DeterimeUsefulness()
    {
        String sTemp=String.valueOf(spinner.getSelectedItem());
        String msg="";
        int iTemp=GeneralFunctions.Conv.StringToInt(sTemp);
        if (sTemp.length()>1) iTemp=10;
        if (iTemp==0)
        {
            msg="Your monthly data amount will increase by 100MB - maximizing the usefulness of this app.";
        }
        else if (iTemp>0 & iTemp<10)
        {
            int iMBIncrease=iTemp*10;
            int iMBIncreaseAmnt=100-iMBIncrease;
            msg="Your monthly data amount will increase " + iMBIncreaseAmnt + "MBs.  This app will still be useful to you, but you won’t get a full 100MB from it.";
        }
        else
        {
            msg="Since you already have 10 friends, this app will not increase your monthly data amount at all.";
        }
        GeneralFunctions.Oth.Alert(this,msg);
    }
    public void onContinueClicked(View arg0) {
        Continue();
    }
    private void Continue()
    {

        Toast.makeText(getApplicationContext(), "Just a quick ad before continuing..." , Toast.LENGTH_SHORT).show();
        PrepToShowAd();
        //finish();
    }
    private void SwitchScreen()
    {
        Intent intent = new Intent(this, WatchRewardedAd.class);
        intent.putExtra("Bonus", "None");
        startActivity(intent);
    }
    private void PrepToShowAd()
    {
        Log.d("APP", "ActivateBeginTypeValues");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                SwitchScreen();
            }
        }, 2000);
        //This is going to perform once after the 30 Second Time Period Interval is over. A kind of timeoput-action.
    }

    public void onUUIDClicked(View arg0)
    {
        //ShowUUID();
    }
    private void ShowUUID()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert.setTitle("Title");
        //alert.setMessage("Message");

        final EditText input = new EditText(this);
        input.setText(AppSpecific.gloUUID);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }
    public void onCancelClicked(View arg0) {
        Cancel();
    }
    private void Cancel()
    {
        System.exit(0);
    }
}
