package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HowAdsWork extends Activity {
    TextView txtAdsRemaining;
    TextView txtCreditsAvailable;
    Button cmdContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_ads_work);
        //Setup();
    }
    protected void Setup() {
        txtAdsRemaining=(TextView)findViewById(R.id.txtAdsRemaining);
        txtCreditsAvailable=(TextView)findViewById(R.id.txtCreditsAvailable);
        cmdContinue=(Button)findViewById(R.id.cmdContinue);
        GetCreditAvailable();
        GetAdsRemaining();
    }

    private void GetCreditAvailable()
    {
        String pParams = "pUUID=" + AppSpecific.gloUUID;
        String pURL = AppSpecific.gloWebServiceURL + "/GetCreditAmount";
        String temp="";
        /*
        AsyncWebCallRunner runner = new AsyncWebCallRunner();
        runner.execute(pURL, pParams);
        */
        temp=GeneralFunctions.Comm.NonAsyncWebCall(pURL,pParams);
        txtCreditsAvailable.setText("  " + temp + "  ");
    }
    public void onContinueClicked(View arg0) {
        Continue();
    }
    private void Continue()
    {
        Intent intent = new Intent(this, WatchRewardedAd.class);
        startActivity(intent);
        //finish();
    }
    public void onCancelClicked(View arg0) {
        Cancel();
    }
    private void Cancel()
    {
        finish();
    }
    private void GetAdsRemaining()
    {
        String pDate=GeneralFunctions.Dte.GetCurrentDate();
        String pAmntSeen=GeneralFunctions.Cfg.ReadSharedPreference(pDate);
        int iAmntSeen=GeneralFunctions.Conv.StringToInt(pAmntSeen);
        int iAmntRem=3-iAmntSeen;
        if (iAmntRem<=0)
        {
            cmdContinue.setEnabled(false);
            cmdContinue.setText("Come back tomorrow to get more credits!  Or use a purchase option.");
        }
        String  pAmntRem=GeneralFunctions.Conv.IntToString(iAmntRem);
        txtAdsRemaining.setText("  " + pAmntRem + "  ");

    }
    @Override
    public void onResume () {
        super.onResume();
        Setup();
        Log.i("App","onResume");
        //InitSceen();
    }

}
