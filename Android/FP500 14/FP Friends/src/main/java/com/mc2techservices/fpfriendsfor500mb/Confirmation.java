package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Confirmation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }

    public void onExitClicked(View arg0) {
        System.exit(0);
    }

    public void onClickEmailUs(View arg0)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"service@mc2techservices.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "FP Friends for Free Data " + AppSpecific.gloUUID);
        email.putExtra(Intent.EXTRA_TEXT, "");

        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose an email client from..."));

        } catch (android.content.ActivityNotFoundException ex)
        {
        }
    }

}
