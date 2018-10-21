package com.mc2techservices.fpfriendsfor500mb;

import com.android.vending.billing.util.IabHelper;
import com.android.vending.billing.util.IabResult;
import com.android.vending.billing.util.Inventory;
import com.android.vending.billing.util.Purchase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;


public class InAppPurchase extends Activity {
    private static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhyaNrPDo+WoF4BT07X8Y9LI4azs5Ok7Kvf5y/DARlZuJulO6Br7RFb2x/7VELvFj9dZb9t+Vuow6hvLhkZ4i00fb7hRKHjiFU4reZJm4ydkcDHJSLtOSUZVAYJjr7pgoOuHys6Dd1D3ftiOsBwNYF8YaNnM45+JP73wEg4v+2dRiOHPtKPqMG1LhTGETEZN/5i/Tnp9ZyY2z0L8T4RzkggNBkzSq1LdwmTJvBN4Z/JH9qgQ0LFyxUpbqKicoDmo/tBis+GHl1wJWduP55m+yIHntE11yWLyh6pEV24OdLchY8tOHocaz14yP+Gjqwxcukb5r/OBKpoJqiXrjSE8tuwIDAQAB";
    static String ITEM_SKU;
    static final int RC_REQUEST = 10001;
    String pOrderDetails;
    String pPurchaseType;
    String pPayload;
    IabHelper mHelper;


    private void LogPurchase()
    {
        String Key1=GeneralFunctions.Text.GetRandomString("ANF", 20);
        String Key2=Decode.PMConvertIDtoValue(Key1);
        String Details="";
        if (pPurchaseType.contains("5"))
        {
            Details="P5";
        }
        else if (pPurchaseType.contains("10"))
        {
            Details="P10";
        }
        else if (pPurchaseType.contains("Override"))
        {
            Details="Override";
        }
        String pParams = "pUUID=" + AppSpecific.gloUUID + "&pKey1=" +Key1 + "&pKey2=" +Key2 +  "&pDetails=" + Details;
        String pURL = AppSpecific.gloWebServiceURL + "/LogCredits";
        new GeneralFunctions.AsyncWebCall().execute(pURL,pParams);
        updateStatus("finish();");
        SwitchScreensForwards();
    }
    private void SwitchScreensForwards()
    {
        Intent intent = new Intent(this, SubmitEmailForFriends.class);
        startActivity(intent);
        finish();

    }
    private void updateStatus(String newTextIn)
    {
        TextView textViewToUse=(TextView)findViewById(R.id.txtDefaultPurchaseStatus);
        String temp0=textViewToUse.getText().toString();
        int amnt=temp0.length();
        if (amnt>500)
        {
            amnt=500;
        }
        String finalText=temp0.substring(0,amnt);
        finalText=newTextIn + System.getProperty("line.separator") + finalText;
        textViewToUse.setText(finalText);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_in_app_purchase);
        pOrderDetails="N/A";
        pPurchaseType=(getIntent().getStringExtra("PurchType"));  //10_friend_requests_purchase
        if (Build.FINGERPRINT.startsWith("generic")) pPurchaseType="Override";
        //pPurchaseType="Override";  //test
        ITEM_SKU = pPurchaseType; //unlimited_use
        if (pPurchaseType.equals("Override"))
        {
            pOrderDetails="Override";
            LogPurchase();
            return;
        }
        SetupIAP();
    }
    private void SetupIAP()
    {
        updateStatus("SetupIAP()");
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    updateStatus("Problem setting up in-app billing: " + result);
                    //return;
                }
                if (mHelper == null)
                {
                    updateStatus("mHelper == null");
                    //return;
                }
                updateStatus("Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(final IabResult result, final Inventory inventory) {
            if (mHelper == null) {
                return;
            }
            if (result.isFailure()) {
                updateStatus("Failed to query inventory: " + result);
                return;
            }
            // They've bought the  Consumable
            Purchase ConsumablePurchase = inventory.getPurchase(ITEM_SKU);
            if (ConsumablePurchase == null) {
                updateStatus("We've got to buy it");
                LaunchPurchase();
            }
            else
            {
                //Already own it, should consume it to get rid of it???
                updateStatus("Consuming ConsumablePurchase.");
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };

    private void LaunchPurchase()
    {
        updateStatus("LaunchPurchase() Started");
        pPayload=GeneralFunctions.Cfg.ReadSharedPreference("UUID");
        mHelper.launchPurchaseFlow(this, ITEM_SKU, RC_REQUEST,
                mPurchaseFinishedListener, pPayload);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateStatus("onActivityResult eval");
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            updateStatus("onActivityResult 1");

            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            updateStatus("onActivityResult 2");
        }
        updateStatus("onActivityResult Done");
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        if (payload.equals(pPayload))
        {
            return true;
        }
        else
        {
            return false;
        }
        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            updateStatus("Purchase eval: " + result + ", purchase: " + purchase);
            String pResult=result.toString();
            if (result.isFailure())
            {
                finish();
                return;
            }
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                updateStatus("Purchase fail 1");
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                updateStatus("Purchase fail 2");
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            updateStatus("Purchase successful");
            if (purchase.getSku().equals(ITEM_SKU)) {
                updateStatus("Now we have to consume it");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            updateStatus("Beging Consumption");
            if (mHelper == null) return;
            if (result.isSuccess()) {
                updateStatus("Consumption OK");
                pOrderDetails=purchase.getOrderId();
                LogPurchase();
            }
            else {
                updateStatus("Consumption Failed");
                complain("Error while consuming: " + result);
            }
        }
    };

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        updateStatus("Destory");
        super.onDestroy();

        // very important:
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    void complain(String message) {
        //finish();
        if (1==1) return;
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }
}
