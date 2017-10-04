using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

/// <summary>
/// Summary description for AppWS
/// </summary>
[WebService(Namespace = "fpfriender.mc2techservices.com")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
[System.ComponentModel.ToolboxItem(false)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
[System.Web.Script.Services.ScriptService]
public class FPFriender : System.Web.Services.WebService
{
    [WebMethod]
    public string IsConnected()
    {
        string retVal = "";
        FPFrienderBL bl = new FPFrienderBL();
        retVal = bl.gloWSStatus;
        if (bl.gloWSStatus == "")
        {
            retVal = bl.IsConnected();
        }
        bl.CloseIt();
        return retVal;
    }
    [WebMethod]
    public string RevealCode(string pKey1, string pPassword)
    {
        string retVal = "";
        FPFrienderBL bl = new FPFrienderBL();
        retVal = bl.gloWSStatus;
        if (bl.gloWSStatus == "")
        {
            retVal = bl.RevealCode(pKey1, pPassword);
        }
        bl.CloseIt();
        return retVal;
    }
    [WebMethod]
    public string LogCredits(string pUUID, string pKey1, string pKey2, string pDetails)
    {
        string retVal = "";
        FPFrienderBL bl = new FPFrienderBL();
        retVal = bl.gloWSStatus;
        if (bl.gloWSStatus == "")
        {
            retVal = bl.LogCredits(pUUID, pKey1, pKey2, pDetails);
        }
        bl.CloseIt();
        return retVal;
    }
    [WebMethod]
    public string GetCreditAmount(string pUUID)
    {
        string retVal = "";
        FPFrienderBL bl = new FPFrienderBL();
        retVal = bl.gloWSStatus;
        if (bl.gloWSStatus == "")
        {
            retVal = bl.GetCreditAmount(pUUID);
        }
        bl.CloseIt();
        return retVal;
    }

    [WebMethod]
    public string LogUsage(string pUUID, string pChannel)
    {
        string retVal = "";
        FPFrienderBL bl = new FPFrienderBL();
        retVal = bl.gloWSStatus;
        if (bl.gloWSStatus == "")
        {
            retVal = bl.LogUsage(pUUID, pChannel);
        }
        bl.CloseIt();
        return retVal;
    }
    [WebMethod]
    public string MakeRequest(string pUUID, string pRequestType, string pEmail)
    {
        string retVal = "";
        FPFrienderBL bl = new FPFrienderBL();
        retVal = bl.gloWSStatus;
        if (bl.gloWSStatus == "")
        {
            retVal = bl.MakeRequest(pUUID, pRequestType, pEmail);
        }
        bl.CloseIt();
        return retVal;
    }

}
