package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mc2techservices.ads.AdsMain;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HowAdsWork extends Activity {
    TextView txtAdsRemaining;
    TextView txtCreditsAvailable;
    ProgressBar pbarLoading;
    Button cmdContinue;
    String pDateTimeBeforeReset;
    String pAdsToShow;
    String pIsBonusAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_ads_work);
        Setup();
    }
    protected void Setup() {
        txtAdsRemaining=(TextView)findViewById(R.id.txtAdsRemaining);
        txtCreditsAvailable=(TextView)findViewById(R.id.txtCreditsAvailable);
        cmdContinue=(Button)findViewById(R.id.cmdContinue);
        pbarLoading=(ProgressBar)findViewById(R.id.progressBarLoading);
        cmdContinue.setEnabled(false);
        pIsBonusAd="0";
        GetServerInfo();
    }

    public void onContinueClicked(View arg0) {
        Continue();
    }
    private void Continue()
    {
        if (pIsBonusAd.equals("1"))
        {
            ShowBonus();
        }
        else
        {
            ShowAd("0");
        }
    }
    protected void ShowAd(String BonusAd) {
        if (pAdsToShow.equals("Admob"))
        {
            Intent intent = new Intent(this, WatchRewardedAd.class);
            intent.putExtra("Bonus", BonusAd);
            startActivity(intent);
        }
        else if (pAdsToShow.equals("Internal"))
        {
            Intent intent = new Intent(this, WatchInternalAd.class);
            intent.putExtra("Bonus", BonusAd);
            startActivity(intent);
        }
        else
        {
            //We shouldn't be showing adds...
        }
        //finish();
    }

    private void ShowBonus()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Bonus Ad!");
        alert.setMessage("If you check out (click on, push 'Install' etc...) this ad you'll be given 4 credits.  If not, you'll be given 1 after the ad.");
        //final EditText input = new EditText(this);
        //input.setText(AppSpecific.gloUUID);
        //alert.setView(input);
        alert.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ShowAd("1");
            }
        });
        alert.show();
    }

    public void onCancelClicked(View arg0) {
        Cancel();
    }
    private void Cancel()
    {
        finish();
    }


    private void GetServerInfo()
    {
        String pParams = "pUniqueID=" + AppSpecific.gloUUID;
        String pURL=AppSpecific.gloWebServiceURL + "/GetServerInfo";
        AsyncWebCallRunner runner = new AsyncWebCallRunner();
        runner.execute(pURL, pParams);
    }
    private void ShowIssue()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Oops");
        alert.setMessage("We couldn't get a response from the server.  You may be offline.  Or we're offline.  Either way, try again in a bit and hopefully this will work.");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        alert.show();
    }
    public void ProcessServerInfo(String pServerInfo) {
        String[] separated = pServerInfo.split(",");
        if (separated.length<2)
        {
            ShowIssue();
            return;
        }
        int iAdmobAdsWatched=GeneralFunctions.Conv.StringToInt(separated[0]);
        int iAdmobAdsClicked=GeneralFunctions.Conv.StringToInt(separated[1]);
        int pInternalAdWatched=GeneralFunctions.Conv.StringToInt(separated[2]);
        String pCredits=separated[4];
        txtCreditsAvailable.setText("  " + pCredits + "  ");
        pDateTimeBeforeReset=separated[3];
        int iAmntRem=5-(iAdmobAdsWatched+pInternalAdWatched);
        int iAmntWatched=iAdmobAdsWatched+pInternalAdWatched;
        iAmntWatched=iAmntWatched+1;
        String pAmntRem=GeneralFunctions.Conv.IntToString(iAmntRem);
        String pAmntWatched=GeneralFunctions.Conv.IntToString(iAmntWatched);
        String pBonus1=GeneralFunctions.Cfg.ReadSharedPreference("Bonus1");
        String pBonus2=GeneralFunctions.Cfg.ReadSharedPreference("Bonus2");
        if (pAmntWatched.equals(pBonus1)) pIsBonusAd="1";
        if (pAmntWatched.equals(pBonus2)) pIsBonusAd="1";
        int pCombinedAdsWatched=iAdmobAdsWatched+pInternalAdWatched;
        if (pCombinedAdsWatched>=5)
        {
            //Dont Show Ads
            pAdsToShow="None";
        }
        else if (iAdmobAdsClicked>=2)
        {
            //Internal Ads Only Now
            pAdsToShow="Internal";
        }
        else
        {
            //Show Admob Ads
            pAdsToShow="Admob";
        }
        txtAdsRemaining.setText("  " + pAmntRem + "  ");
        if (pAdsToShow.equals("None"))
        {
            cmdContinue.setEnabled(false);
            cmdContinue.setText("Come back in " + pDateTimeBeforeReset + " to get more credits!  Or use a purchase option.");
        }
        else
        {
            cmdContinue.setEnabled(true);
        }
    }

    private void UseAdsInfo() {
    }
    @Override
    public void onResume () {
        super.onResume();
        Setup();
        Log.i("App","onResume");
        //InitSceen();
    }

    public class AsyncWebCallRunner extends AsyncTask<String, String, String> {

        private String resp;
        //private String USER_AGENT = "Mozilla/5.0";

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                String url = params[0];
                String urlParameters = params[1];
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj
                        .openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                try {
                    DataOutputStream wr = new DataOutputStream(
                            con.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();
                } catch (Exception e) {
                    resp = "CallWebService Exception Occured 1";
                    return resp;
                }

                int responseCode = con.getResponseCode();
                if (responseCode!=200)
                {
                    resp = "responseCode!=200";
                    return resp;
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                resp = response.toString();
            } catch (Exception e) {
                Log.d("YMCA_Check_In", "Error 002");
                Log.d("YMCA_Check_In", e.getMessage());
                resp = "CallWebService Exception Occured 2";
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("YMCA_Check_In", "onPostExecute");
            Log.d("YMCA_Check_In", result);
            String Response = GeneralFunctions.Comm.GetResonseData(result); // "http://YMCA_Check_In.mc2techservices.com/Barcodes/9876543321.jpg";																		// //
            Log.d("YMCA_Check_In", "Response: "+Response);
            pbarLoading.setVisibility(View.INVISIBLE);
            ProcessServerInfo(Response);
        }

        @Override
        protected void onPreExecute() {
            Log.d("YMCA_Check_In", "onPreExecute");
            pbarLoading.setVisibility(View.VISIBLE);
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            //0 is for VISIBLE
            //4 is for INVISIBLE
        }

        @Override
        protected void onProgressUpdate(String... text) {
            Log.d("YMCA_Check_In", "onProgressUpdate");
            Log.d("YMCA_Check_In", "onProgressUpdate");
            // finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
