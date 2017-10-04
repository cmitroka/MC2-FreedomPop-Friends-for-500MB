using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;

    /// <summary>
    /// Summary description for AppWebService
    /// </summary>
    [WebService(Namespace = "App.mc2techservices.com")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class AdminAppWs : System.Web.Services.WebService
    {
        [WebMethod]
        public string TemplateMethod()
        {
            string retVal = "";
            AdminAppWsBL bl = new AdminAppWsBL();
            if (bl.gloHacker != "1")            {
                string pAdminName = bl.GetValueFromAdminTable("AdminName");
                string pAdminPWD = bl.GetValueFromAdminTable("AdminPassword");
            }
            bl.CloseIt();
            return retVal;
        }
        [WebMethod]
        public string GetOverrideCode(string pCodeIn, string pKeyIn)
        {
            string retVal = "";
            AdminAppWsBL bl = new AdminAppWsBL();
            if (bl.gloHacker != "1")
            {
                //retVal = bl.GetOverrideCode(pCodeIn, pEnc);
                retVal = "1";
            }
            bl.CloseIt();
            return retVal;
        }
        [WebMethod(EnableSession = true)]
        public string RunQuery(string pFromWhere, string pUsername, string pPassword)
        {
            string retVal = "";
            if (BlockUser(pUsername, pPassword)) return "RunQuery Blocked";
            if (CredentialsOK(pUsername, pPassword))
            {
                retVal = RunSecureQuery(pFromWhere);
            }
            return retVal;
        }
    private string RunSecureQuery(string pFromWhere)
    {
        string retVal = "";
        AdminAppWsBL bl = new AdminAppWsBL();
        if (bl.gloHacker != "1")
        {
            retVal = bl.RunQuery(pFromWhere);
        }
        bl.CloseIt();
        return retVal;
    }

    private bool BlockUser(string pUsername, string pPassword)
    {
        UseSessionVars();
        string sVal = "0";
        int iVal = 0;
        try
        {
            string sTemp = HttpContext.Current.Session["Blocked"].ToString();
            if (sTemp == "1")
            {
                return true;
            }
        }
        catch (Exception ex) { }

        if (CredentialsOK(pUsername, pPassword))
        {
            Session["Logins"] = "0";
            Session["Blocked"] = "0";
            return false;
        }
        else
        {
            try
            {
                sVal = HttpContext.Current.Session["Logins"].ToString();
            }
            catch (Exception ex) { }
            iVal = Convert.ToInt16(sVal);
            iVal++;
            Session["Logins"] = iVal.ToString();
            if (iVal == 3)
            {
                Session["Blocked"] = "1";
            }
        }
        return false;
    }
    private bool CredentialsOK(string pUsername, string pPassword)
    {
        string pAdminName = "";
        string pAdminPWD = "";
        AdminAppWsBL bl = new AdminAppWsBL();
        if (bl.gloHacker != "1")
        {
            pAdminName = bl.GetValueFromAdminTable("AdminName").ToUpper();
            pAdminPWD = bl.GetValueFromAdminTable("AdminPassword").ToUpper();
            bl.CloseIt();
            if ((pAdminName.Length<2)||(pAdminPWD.Length<2))
            {
                return false;
            }
            if ((pUsername.ToUpper() == pAdminName) && (pPassword.ToUpper() == pAdminPWD))
            {
                return true;
            }
        }
        return false;
    }

    private void UseSessionVars()
    {
        try
        {
            Console.WriteLine(Session["Logins"]);
        }
        catch (Exception ex)
        {
            Session.Add("Logins", "0");
        }
        try
        {
            Console.WriteLine(Session["Blocked"]);
        }
        catch (Exception ex)
        {
            Session.Add("Blocked", "0");
        }
    }
}
