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

import java.util.ArrayList;
import java.util.List;

public class WhatIsFPF500 extends Activity {
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_fpf500);
        SetupDropdown();
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
            msg="Your monthly data amount will increase by 500MB - maximizing the usefulness of this app.";
        }
        else if (iTemp>0 & iTemp<10)
        {
            int iMBIncrease=iTemp*50;
            int iMBIncreaseAmnt=500-iMBIncrease;
            msg="Your monthly data amount will increase " + iMBIncreaseAmnt + "MBs.  This app will still be useful to you, but you won’t get a full 500MB from it.";
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
        Intent intent = new Intent(this, WhatsTheCatch.class);
        startActivity(intent);
        //finish();
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
