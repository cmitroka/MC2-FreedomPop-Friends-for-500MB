package com.mc2techservices.fpfriendsfor500mb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class SubmitEmailForFriends extends Activity  {
    String[] iRequestType;
    Spinner ddRequestType;
    int iRequestsAvailable;
    TextView tvCreditsAvailable;
    EditText etEmailRequestsTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("App","onCreate");
        setContentView(R.layout.activity_submit_email_for_friends);
        InitSceen();
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

    @Override
    public void onResume () {
        super.onResume();
        Log.i("App","onResume");
        //InitSceen();
    }
    private void InitSceen()
    {
        tvCreditsAvailable= (TextView) findViewById(R.id.tvCreditsAvailable);
        etEmailRequestsTo=(EditText) findViewById(R.id.etEmailRequestsTo);
        GetCreditAvailable();
        ddRequestType = (Spinner)findViewById(R.id.ddRequestType);
        if (iRequestsAvailable==0)
        {
            iRequestType = new String[] { "Need Credits"};
            etEmailRequestsTo.setEnabled(false);

        }
        else if (iRequestsAvailable==5)
        {
            iRequestType = new String[] { "5 Requests"};
        }
        else if (iRequestsAvailable>5)
        {
            iRequestType = new String[] { "10 Requests","5 Requests" };
        }
        ConfigDropdown(ddRequestType,iRequestType,"");
    }
    private void ConfigDropdown(Spinner pWhichDD, String[] pValArray, String pSetToText)
    {
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, pValArray);
        pWhichDD.setAdapter(adapterType);
        int setPosT=adapterType.getPosition(pSetToText);
        pWhichDD.setSelection(setPosT);
    }
    private void ShowConfirmationMessage()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SubmitRequest();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hang On!");
        builder.setMessage("You're 100% sure this email is the FreedomPop account you want to receive the friend requests?").setPositiveButton("Yes - I'm Sure", dialogClickListener)
                .setNegativeButton("Better double check...", dialogClickListener).show();
    }
    private void ShowNoCreditsMessage()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SwitchScreensBack();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need credits to get friend requests; would you like to acquire some now?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    private void SwitchScreensBack()
    {
        Intent intent = new Intent(this, WhatsTheCatch.class);
        startActivity(intent);
        finish();
    }

    public void onSubmitClicked(View arg0) {
        RequestPreProcess();
    }
    private void RequestPreProcess()
    {
        String pTypeSelected=ddRequestType.getSelectedItem().toString();
        if (pTypeSelected.equals("Need Credits"))
        {
            ShowNoCreditsMessage();
            return;
        }
        else
        {
            ShowConfirmationMessage();
            return;
        }
    }
    private void SubmitRequest()
    {
        String pTypeSelected=ddRequestType.getSelectedItem().toString();
        String pParams="";
        if (pTypeSelected.equals("Need Credits"))
        {
            ShowNoCreditsMessage();
            return;
        }
        else if (pTypeSelected.equals("5 Requests"))
        {
            pParams = "pUUID=" + AppSpecific.gloUUID + "&pRequestType=5&pEmail=" + etEmailRequestsTo.getText() ;
        }
        else if (pTypeSelected.equals("10 Requests"))
        {
            pParams = "pUUID=" + AppSpecific.gloUUID + "&pRequestType=10&pEmail=" + etEmailRequestsTo.getText() ;
        }
        String pURL = AppSpecific.gloWebServiceURL + "/MakeRequest";
        String temp=GeneralFunctions.Comm.NonAsyncWebCall(pURL,pParams);
        Log.d("APP", "temp: "+temp);
        SwitchScreensForward();
    }
    private void SwitchScreensForward()
    {
        Intent intent = new Intent(this, Confirmation.class);
        startActivity(intent);
        finish();
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
        iRequestsAvailable=GeneralFunctions.Conv.StringToInt(temp);
        tvCreditsAvailable.setText(temp);
    }
}
