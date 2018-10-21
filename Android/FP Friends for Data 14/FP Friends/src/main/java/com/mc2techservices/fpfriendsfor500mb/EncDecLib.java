package com.mc2techservices.fpfriendsfor500mb;

import android.content.Context;

/**
 * Created by Administrator on 9/11/2017.
 */

public class EncDecLib
{
    public static String AppUUID;
    private static String encrypt(String passPhrase, int saltValLessThan500, String WhatToEnc)
    {
        char mychartoenc;
        char mychartofrompassphrase;
        int val1=0;
        int val2=0;
        int multiplier=0;
        int valTotal=0;
        int posToTry=0;
        int amntToSub=0;
        String encd="";
        for (int j = 0; j < WhatToEnc.length(); j++) {
            mychartoenc = WhatToEnc.charAt(j);
            val1 = (int)mychartoenc;
            amntToSub=(passPhrase.length()*multiplier);
            posToTry=(j-amntToSub);
            if (posToTry==passPhrase.length())
            {
                multiplier++;
                posToTry=0;
            }

            mychartofrompassphrase = passPhrase.charAt(posToTry);
            val2 = (int)mychartofrompassphrase;
            valTotal=val1+val2+saltValLessThan500;
            String a=String.format("%03d", valTotal);
            encd=encd+a;
        }
        return encd;

    }
    private static String decrypt(String passPhrase, int saltValLessThan500, String WhatToDec)
{
        char mychartofrompassphrase;
        int val1=0;
        int val2=0;
        int valTotal=0;
        int posToTry=0;
        String encedVal="";
        String retVal="";

        for (int i = 0; i < WhatToDec.length(); i=i+3) {
            encedVal=WhatToDec.substring(i,i+3);
            try {
                valTotal = Integer.parseInt(encedVal);
            } catch (Exception e) {
                //They did something wierd with the Launches key
            }


            if (posToTry==passPhrase.length())
            {
                posToTry=0;
            }
            mychartofrompassphrase = passPhrase.charAt(posToTry);
            val2 = (int)mychartofrompassphrase;
            posToTry++;
            val1=valTotal-val2-saltValLessThan500;
            String aChar = new Character((char) val1).toString();
            retVal=retVal+aChar;
        }
        return retVal;
    }

    private static final int saltGeneric=97;
    private static final int saltSpecific1=12;
    private static final int saltSpecific2=399;

    public static String EncGeneric(String pValIn,String pSharedPreferenceToSave)
    {
        String retVal=encrypt(AppUUID, saltGeneric, pValIn);
        GeneralFunctions.Cfg.WriteSharedPreference(pSharedPreferenceToSave,retVal);
        return retVal;
    }
    public static String DecGeneric(String pSharedPreferenceToRetrieve)
    {
        String EncTemp=GeneralFunctions.Cfg.ReadSharedPreference(pSharedPreferenceToRetrieve);
        if (EncTemp.equals("")) return "";
        String retVal=decrypt(AppUUID, saltGeneric, EncTemp);
        return retVal;
    }

    public static String EncSpecific1(String valIn)
    {
        String retVal=encrypt(AppUUID, saltSpecific1, valIn);
        GeneralFunctions.Cfg.WriteSharedPreference("Specific1",retVal);
        return retVal;
    }
    public static String DecSpecific1()
    {
        String EncTemp=GeneralFunctions.Cfg.ReadSharedPreference("Specific1");
        if (EncTemp.equals("")) return "";
        String retVal=decrypt(AppUUID, saltSpecific1, EncTemp);
        return retVal;
    }

    public static String EncSpecific2(String valIn)
    {
        String retVal=encrypt(AppUUID, saltSpecific2, valIn);
        GeneralFunctions.Cfg.WriteSharedPreference("Specific2",retVal);
        return retVal;
    }
    public static String DecSpecific2()
    {
        String EncTemp=GeneralFunctions.Cfg.ReadSharedPreference("Specific2");
        if (EncTemp.equals("")) return "";
        String retVal=decrypt(AppUUID, saltSpecific2, EncTemp);
        return retVal;
    }

}
