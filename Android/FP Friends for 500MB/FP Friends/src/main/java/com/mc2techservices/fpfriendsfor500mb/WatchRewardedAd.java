package com.mc2techservices.fpfriendsfor500mb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Timer;
import java.util.TimerTask;


public class WatchRewardedAd extends Activity implements RewardedVideoAdListener {
    private RewardedVideoAd mAd;
    boolean pRewarded;
    int pCounter;
    private static Timer t;
    TextView tvLoadingAd;
    TextView tvGotCredits;
    ProgressBar progressBar2;
    Button cmdGetMoreCredits;
    Button cmdUseCredits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_rewarded_ad);
        pRewarded=false;
        tvLoadingAd= (TextView)findViewById(R.id.tvLoadingAd);
        tvGotCredits=(TextView)findViewById(R.id.tvGotCredits);
        progressBar2 =(ProgressBar)findViewById(R.id.progressBar2);
        cmdUseCredits=(Button)findViewById(R.id.cmdUseCredits);
        cmdGetMoreCredits=(Button)findViewById(R.id.cmdGetMoreCredits);
        cmdGetMoreCredits.setVisibility(View.GONE);
        cmdUseCredits.setVisibility(View.GONE);
        tvGotCredits.setVisibility(View.GONE);

        t=new Timer();
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        LoadRewardedVideoAd();
    }
    private void LoadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-2250341510214691/3116915607", new AdRequest.Builder().build());
        if (!mAd.isLoaded())
        {
        }
        ViewVideoAd();
        //ca-app-pub-3940256099942544/5224354917 is Google Example and Safe
        //ca-app-pub-2250341510214691/4262113967 is Barcode Budler
        //ca-app-pub-2250341510214691/3116915607 is FPF
    }

    private void LogReward(String pAmnt)
    {
        //string pUUID, string pDetails
        if (pRewarded==false)
        {
            String pDate=GeneralFunctions.Dte.GetCurrentDate();
            String pAmntSeen=GeneralFunctions.Cfg.ReadSharedPreference(pDate);
            int iAmntSeen=GeneralFunctions.Conv.StringToInt(pAmntSeen);
            iAmntSeen++;
            GeneralFunctions.Cfg.WriteSharedPreference(pDate,GeneralFunctions.Conv.IntToString(iAmntSeen));
        }



        GeneralFunctions.Cfg.WriteSharedPreference("WatchedAd", "1");
        String pUUID=AppSpecific.gloUUID;
        String pKey1=GeneralFunctions.Text.GetRandomString("ANF",15);
        String pKey2=Decode.PMConvertIDtoValue(pKey1);
        String pParams = "pUUID=" + pUUID + "&pKey1=" + pKey1 + "&pKey2=" + pKey2 + "&pDetails=Ad" + pAmnt;
        String pURL=AppSpecific.gloWebServiceURL + "/LogCredits";
        new GeneralFunctions.AsyncWebCall().execute(pURL,pParams);
    }
    private void SwitchScreensForwards()
    {
        Intent intent = new Intent(this, SubmitEmailForFriends.class);
        startActivity(intent);
        finish();
    }
    private void SwitchScreensBack()
    {
        //Intent intent = new Intent(this, WhatsTheCatch.class);
        //startActivity(intent);
        finish();
    }
    private void SwitchScreens()
    {
        cmdGetMoreCredits.setVisibility(View.VISIBLE);
        cmdUseCredits.setVisibility(View.VISIBLE);
        tvGotCredits.setVisibility(View.VISIBLE);
    }


    private String DisplayDots(int pDots)
    {
        String retVal="";
        if (pDots==1||pDots==4||pDots==7||pDots==10||pDots==13||pDots==16||pDots==19||pDots==22||pDots==25||pDots==28||pDots==31||pDots==34||pDots==37||pDots==40||pDots==43||pDots==46||pDots==49||pDots==52||pDots==55||pDots==58||pDots==61||pDots==64||pDots==67||pDots==70||pDots==73||pDots==76||pDots==79||pDots==82||pDots==85||pDots==88||pDots==91||pDots==94||pDots==97)
        {
            retVal=".";
        }
        else if (pDots==2||pDots==5||pDots==8||pDots==11||pDots==14||pDots==17||pDots==20||pDots==23||pDots==26||pDots==29||pDots==32||pDots==35||pDots==38||pDots==41||pDots==44||pDots==47||pDots==50||pDots==53||pDots==56||pDots==59||pDots==62||pDots==65||pDots==68||pDots==71||pDots==74||pDots==77||pDots==80||pDots==83||pDots==86||pDots==89||pDots==92||pDots==95||pDots==98)
        {
            retVal="..";
        }
        else if (pDots==3||pDots==6||pDots==9||pDots==12||pDots==15||pDots==18||pDots==21||pDots==24||pDots==27||pDots==30||pDots==33||pDots==36||pDots==39||pDots==42||pDots==45||pDots==48||pDots==51||pDots==54||pDots==57||pDots==60||pDots==63||pDots==66||pDots==69||pDots==72||pDots==75||pDots==78||pDots==81||pDots==84||pDots==87||pDots==90||pDots==93||pDots==96||pDots==99)
        {
            retVal="...";
        }
        return retVal;
    }
    public void onUseClicked(View arg0) {
        SwitchScreensForwards();
    }
    public void onGetMoreClicked(View arg0) {
        SwitchScreensBack();
    }

    private void ViewVideoAd()
    {
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        pCounter++;
                        String pDots=DisplayDots(pCounter);
                        tvLoadingAd.setText("Loading Ad"+pDots);
                        Log.d("APP", String.valueOf(pCounter));
                        if (pCounter>20)
                        {
                            t.cancel();
                            Log.d("APP", "Timed Out");

                        }
                        else if (mAd.isLoaded())
                        {
                            t.cancel();
                            Log.d("APP", "Ads ready; show it.");
                            tvLoadingAd.setVisibility(View.GONE);
                            progressBar2.setVisibility(View.GONE);
                            mAd.show();
                        }

                    }
                });
            }
        }, 0, 1000); // 1000 means start delay (1 sec), and the second is the loop delay.
    }

    // Required to reward the user.
    @Override
    public void onRewarded(RewardItem reward) {
        Log.d("APP", "onRewarded");
        LogReward("1");
        SwitchScreens();
        pRewarded=true;
        // Reward the user.
    }

    // The following listener methods are optional.
    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d("APP", "onRewardedVideoAdLeftApplication");
        LogReward("4");
        pRewarded=true;
        //finish();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d("APP", "onRewardedVideoAdClosed");
        if (pRewarded==false)
        {
            SwitchScreensBack();
        }
        else
        {
            SwitchScreens();
        }
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Log.d("APP", "onRewardedVideoAdFailedToLoad");
        finish();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d("APP", "onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d("APP", "onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d("APP", "onRewardedVideoStarted");
    }

}
