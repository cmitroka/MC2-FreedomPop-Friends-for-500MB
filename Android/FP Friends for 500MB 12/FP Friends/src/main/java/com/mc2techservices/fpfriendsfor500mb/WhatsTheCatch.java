package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WhatsTheCatch extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_the_catch);
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
        return true;
    }



    public void onCancelClicked(View arg0) {
        Cancel();
    }
    public void onPurchaseRequestsClicked(View arg0) {
        PurchaseRequests();
    }
    public void onWatchAdClicked(View arg0) {
        ShowHowAdsWork();
        //WatchAdDisclaimer();
    }

    public void onOverrideClicked(View arg0) {
        AttempOverride();
    }


    private void Cancel()
    {
        System.exit(0);
    }
    public void onContinueClicked(View arg0) {
        Continue();
    }
    private void Continue()
    {
        Intent intent = new Intent(this, SubmitEmailForFriends.class);
        startActivity(intent);
        //finish();
    }

    private void PurchaseRequests()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_purchase);
        dialog.setTitle("Purchase Type");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.tvTitle);
        text.setText("What kind of purchase would you like to do?");

        Button cmdFive = (Button) dialog.findViewById(R.id.cmdFive);
        // if button is clicked, close the custom dialog
        cmdFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Purchase("5_friend_requests_purchase");
                dialog.dismiss();
            }
        });
        Button cmdTen = (Button) dialog.findViewById(R.id.cmdTen);
        // if button is clicked, close the custom dialog
        cmdTen .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Purchase("10_friend_requests_purchase");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void AttempOverride()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Override Code");
        alert.setMessage("Please enter " + AppSpecific.gloUUID + " override code:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText("");
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String inputValue = input.getText().toString();
                if (inputValue.equals(AppSpecific.gloKey))
                {
                    Purchase("override");
                }
                finish();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    private void Purchase(String pPurchType)
    {
        Intent intent = new Intent(this, InAppPurchase.class);
        intent.putExtra("PurchType", pPurchType);
        startActivity(intent);
        finish();
    }
    private void WatchAd()
    {
        Intent intent = new Intent(this, WatchRewardedAd.class);
        startActivity(intent);
        //finish();
    }
    private void WatchAdDisclaimer() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Log.d("APP", "Watch an ad.");
                        WatchAd();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.d("APP", "Dont watch an ad");
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How this works...");
        builder.setMessage("You'll now be shown a rewards ad.\n\n-If you close the ad before it completes, you get 0 credits.\n-If you watch the entire ad, you get 1 credit.\n-If you check out the ad/offer (so it opens after the video), you get 5 credits.\n\nContinue?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    private void ShowHowAdsWork()
    {
        Intent intent = new Intent(this, HowAdsWork.class);
        startActivity(intent);
        //finish();
    }
    @Override
    public void onResume () {
        super.onResume();
        Log.i("App","onResume");
        //InitSceen();
    }

}
