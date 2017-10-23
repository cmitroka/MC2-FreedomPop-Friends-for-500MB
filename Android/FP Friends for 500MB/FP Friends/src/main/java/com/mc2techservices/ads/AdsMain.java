package com.mc2techservices.ads;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import com.mc2techservices.fpfriendsfor500mb.AppSpecific;
import com.mc2techservices.fpfriendsfor500mb.GeneralFunctions;

import java.util.UUID;

/**
 * Created by Administrator on 10/21/2017.
 */

public class AdsMain {
    public static String gloxmlns;
    public static String gloWebServiceURL;
    public static String gloWebURL;
    public static void Setup()
    {
        gloxmlns= "xmlns=\"fpfriender.mc2techservices.com\">";
        gloWebURL="http://fp.mc2techservices.com/";
        gloWebURL="http://192.168.199.1/FPF/";  //test
        gloWebServiceURL=AppSpecific.gloWebURL + "FPFriender.asmx";
    }
    public static String DetermineAdState()
    {
        String retVal="";
        String pURL=AppSpecific.gloWebServiceURL + "/GetAdInfo";
        String pParams = "pUniqueID=" + AppSpecific.gloUUID;
        String pAdInfo= GeneralFunctions.Comm.NonAsyncWebCall(pURL,pParams);
        String pAdsToShow="";
        String[] separated = pAdInfo.split(",");
        int iAdmobAdsWatched=GeneralFunctions.Conv.StringToInt(separated[0]);
        int iAdmobAdsClicked=GeneralFunctions.Conv.StringToInt(separated[1]);
        int pInternalAdWatched=GeneralFunctions.Conv.StringToInt(separated[2]);
        String pDateTimeBeforeReset=separated[3];
        int pCombinedAdsWatched=iAdmobAdsWatched+pInternalAdWatched;
        if (pCombinedAdsWatched>=5)
        {
            //Dont Show Ads
            pAdsToShow="None";
        }
        else if (iAdmobAdsWatched>=2)
        {
            //Internal Ads Only Now
            pAdsToShow="Internal";
        }
        else
        {
            //Show Admob Ads
            pAdsToShow="Admob";
        }
        retVal=pAdInfo+","+pAdsToShow;
        return retVal;
    }

}
