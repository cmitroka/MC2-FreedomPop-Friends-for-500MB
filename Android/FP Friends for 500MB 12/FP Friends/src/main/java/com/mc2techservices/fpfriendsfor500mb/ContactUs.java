package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ContactUs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }

    public void onClickEmailUs(View arg0)
    {
        SendEmail();
    }

    public void SendEmail()
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"service@mc2techservices.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "FP 500 Msg " + AppSpecific.gloUUID);
        email.putExtra(Intent.EXTRA_TEXT, "");

        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose an email client from..."));

        } catch (android.content.ActivityNotFoundException ex)
        {
        }

    }
}
