using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Web;

public class FPFrienderBL
{
    public string POSDEL= "~_~";
    public string LINEDEL= "XZQX";
    public string gloWSStatus;
    SQLHelper sqlh;
    public FPFrienderBL()
    {
        gloWSStatus = "";
        sqlh = new SQLHelper(SQLHelper.MDBBaseLoc.CurrentDomainBaseDirectory, "App_Data\\FPFriender.accdb");
        string VisitorsIPAddr = GetUserIP();
        DateTime adjDate = DateTime.Now.AddDays(-7);
        sqlh.ExecuteSQLParamed("DELETE * FROM tblTraffic WHERE DateLogged<@P0", adjDate.ToString());
        string[][] data = sqlh.GetMultiValuesOfSQL("SELECT Count(*) FROM tblBlockedIPs WHERE BlockedIP=@P0", VisitorsIPAddr);
        string Amnt = "0";
        try
        {
            Amnt = data[0][0];
        }
        catch (Exception ex)
        {
            gloWSStatus = "DB Locked Up";
            return;
            //throw;
        }

        int iAmnt = Convert.ToInt16(Amnt);
        //iAmnt = 0;
        if (iAmnt > 0)
        {
            gloWSStatus = "User Banned";
            return;
        }
        sqlh.ExecuteSQLParamed("INSERT INTO tblTraffic (RecdIP, DateLogged) VALUES (@P0, @P1)", VisitorsIPAddr, DateTime.Now.ToString());
        adjDate = DateTime.Now.AddDays(-1);
        data = sqlh.GetMultiValuesOfSQL("SELECT Count(*) FROM tblTraffic WHERE RecdIP=@P0 AND DateLogged>@P1", VisitorsIPAddr, adjDate.ToString());
        try
        {
            Amnt = data[0][0];
        }
        catch (Exception ex)
        {
            gloWSStatus = "DB Locked Up";
            return;
            //throw;
        }
        iAmnt = Convert.ToInt16(Amnt);
        //iAmnt = 0;
        if (iAmnt > 300)
        {
            sqlh.ExecuteSQLParamed("INSERT INTO tblBlockedIPs (BlockedIP,DateLogged) VALUES (@P0,@P1)", VisitorsIPAddr, DateTime.Now.ToString());
            gloWSStatus = "User Banned";
        }
    }
    public void CloseIt()
    {
        try {sqlh.CloseIt();}catch (Exception ex){}
    }
    public string IsConnected()
    {
        string retVal = "1";
        return retVal;
    }
    public string LogUsage(string pUUID, string pChannel)
    {
        string retVal = "";
        int retStat = sqlh.ExecuteSQLParamed("INSERT INTO tblAppUsers (UUID,Channel,DateLogged) VALUES (@P0,@P1,@P2)", pUUID, pChannel, DateTime.Now.ToString());
        retStat = sqlh.ExecuteSQLParamed("INSERT INTO tblAppUsage (UUID,DateLogged) VALUES (@P0,@P1)", pUUID, DateTime.Now.ToString());
        retVal = retStat.ToString();
        return retVal;
    }
    public string RevealCode(string pKey1, string pPassword)
    {
        string retVal = "";
        if (pPassword.ToUpper() != "RELPATS6308") return retVal;
        retVal = Decode.ConvertIDtoKey(pKey1);
        return retVal;
    }
    public string LogCredits(string pUUID, string pKey1, string pKey2, string pDetails)
    {
        string retVal = "";
        string OK = ValidateKeys(pKey1, pKey2);
        if (OK=="1")
        {
            OK="";
            if (pDetails == "Ad0" || pDetails == "Ad1" || pDetails == "Ad4" || pDetails == "P5" || pDetails == "P10" || pDetails == "Override") OK = "1";
        }
        if (OK=="1")
        {
            int retStat = sqlh.ExecuteSQLParamed("INSERT INTO tblCredits ([Key],UUID,Details,DateLogged) VALUES (@P0,@P1,@P2,@P3)", pKey1, pUUID, pDetails, DateTime.Now.ToString());
            retVal = retStat.ToString();
        }
        return retVal;
    }
    private string ValidateKeys(string pKey1, string pKey2)
    {
        string retVal = "";
        string pKey1Converted = Decode.ConvertIDtoKey(pKey1);
        if (pKey1Converted == pKey2) retVal = "1";
        return retVal;
    }
    public string GetCreditAmount(string pUUID)
    {
        string retVal = "";
        string CreditAmount = sqlh.GetSingleValuesOfSQL("SELECT CreditsRemaining FROM qryTotalCreditsRemaining WHERE UUID=@P0", pUUID);
        if (CreditAmount =="") CreditAmount ="0";
        retVal = CreditAmount;
        //int iCreditAmount = Convert.ToInt16(CreditAmount);
        return retVal;
    }
    public string MakeRequest(string pUUID, string pRequestType, string pEmail)
    {
        string t = GetCreditAmount(pUUID);
        string retVal;
        string pRequestGroup;
        string pFPFrienderRqPath = "INVALID PATH!";
        try
        {
            pFPFrienderRqPath = System.Configuration.ConfigurationManager.AppSettings["FPFrienderRqPath"].ToString();
        }
        catch (Exception ex)
        {
        }
        //read the web.config to determine where fpfriender is located...
        DateTime d = DateTime.Now;
        string dateString = d.ToString("yyyyMMddHHmmss");
        string pOK = GetCreditAmount(pUUID);
        if (pOK == "0") return "No Revenue Recorded for User";
        //they have credits, but have they used them?

        string b = sqlh.GetSingleValuesOfSQL("SELECT COUNT(*) FROM tblRequests WHERE Email=@P0", pEmail);
        if (b == "0")
        {
            pRequestGroup = "FIRST5";
        }
        else
        {
            //They've done a request already.  It was the first 5, so in this case, we'll do the back half of the 5.
            pRequestGroup = "LAST5";
        }
        if (pRequestType == "10" && b == "0") pRequestGroup = "ALL10";
        try
        {
            StreamWriter sw = new StreamWriter(pFPFrienderRqPath + "\\" + dateString + ".txt");
            sw.WriteLine(pEmail);  //Email
            sw.WriteLine(pRequestGroup);  //First 5, Last 5, or All 10
            sw.Close();
            int retStat = sqlh.ExecuteSQLParamed("INSERT INTO tblRequests (UUID,ReqAmnt,ReqType,Email,DateLogged) VALUES (@P0,@P1,@P2,@P3,@P4)", pUUID, pRequestType,pRequestGroup, pEmail, DateTime.Now.ToString());
            retVal = retStat.ToString();
        }
        catch (Exception)
        {
            retVal = "MakeRequest issue, probably missing web.config for FPFrienderRqPath";
        }
        return retVal;
    }
   private static string GetUserIP()
    {
        string VisitorsIPAddr = string.Empty;
        if (HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"] != null)
        {
            VisitorsIPAddr = HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"].ToString();
        }
        else if (HttpContext.Current.Request.UserHostAddress.Length != 0)
        {
            VisitorsIPAddr = HttpContext.Current.Request.UserHostAddress;
        }
        return VisitorsIPAddr;
    }

}
