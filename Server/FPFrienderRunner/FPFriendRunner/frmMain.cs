using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SHDocVw;
using System.Runtime.InteropServices;
using System.Net.Mail;
using System.Net;
using System.IO;


namespace FPFriender
{
    public partial class frmMain : Form
    {
        int RetryCnt;
        IntPtr MainWindowHWND;
        string pURL;
        string pOverallErrors;
        string pValidateBytesSizeDataLogin;
        string pValidateBytesSizeDataTextarea;
        string pEmailCoordinate;
        string pFriendEmailCoordinate;

        string pEmailGroups;

        string pReactivateCoordinates;
        string pReactivateCloseCoordinates;

        
        bool AutoStart = false;
        public frmMain()
        {
            InitializeComponent();
            LoadConfig();
        }
        public frmMain(string[] commands)
            : this()
        {
            try
            {
                System.Diagnostics.EventLog.WriteEntry("FPFriender", "App started with cmd line " + commands[0].ToString(), System.Diagnostics.EventLogEntryType.Information);
                LoadConfig();
                String trueargsin = "";
                foreach (string value in commands)
                {
                    trueargsin = trueargsin+value+",";
                }
                string[] argsin = trueargsin.Split(',');
                txtEmail.Text = argsin[0].ToString();
                pEmailGroups = argsin[1].ToString();

                
                
                if (pEmailGroups.ToUpper() == "FIRST5")
	            {
		            txtAccountsToUse.Text="00,01,03,04,05";
	            }
                else if (pEmailGroups == "LAST5")
	            {
		            txtAccountsToUse.Text="06,07,08,09,10";
	            }
                else if (pEmailGroups == "ALL10")
                {
                    txtAccountsToUse.Text = "00,01,02,04,05,06,07,08,09,10";
                }
                if (commands.Length > 0)
                {
                    AutoStart = true;
                    tmrAutoStart.Enabled = true;
                }

            }
            catch (Exception)
            {
            }
        }
        /*
        protected override void SetVisibleCore(bool value)
        {
            LoadConfig();
            if (AutoStart == true)
            {
                base.SetVisibleCore(false);
                DoIt();
            }
            else
            {
                base.SetVisibleCore(true);
            }
        }
        */
        private void cmdDoIt_Click(object sender, EventArgs e)
        {
            DoIt();
        }
        private void LoadConfig()
        {
            bool err = false;
            StreamReader sr;
            try
            {
                int p0 = 0;
                int p1 = 0;
                sr = new StreamReader(AppDomain.CurrentDomain.BaseDirectory + "\\ReactivateScript.txt");
                pReactivateCoordinates = sr.ReadLine();
                string[] s1 = GCGCommon.SupportMethods.SplitByString(pReactivateCoordinates, ",");
                p0 = Convert.ToInt16(s1[0]);
                p1 = Convert.ToInt16(s1[1]);
                pReactivateCloseCoordinates = sr.ReadLine();
                string[] s2 = GCGCommon.SupportMethods.SplitByString(pReactivateCloseCoordinates, ",");
                p0 = Convert.ToInt16(s2[0]);
                p1 = Convert.ToInt16(s2[1]);

            }
            catch (Exception ex0)
            {
                err = true;
                LogItBoth("Error with data in ReactivateScript.txt - " + ex0.Message, true);
            }

            try
            {
                int p0 = 0;
                int p1 = 0;
                int p2 = 0;
                int p3 = 0;
                int p4 = 0;
                sr = new StreamReader(AppDomain.CurrentDomain.BaseDirectory + "\\PrimaryScript.txt");
                pValidateBytesSizeDataLogin = sr.ReadLine();
                string[] s0 = GCGCommon.SupportMethods.SplitByString(pValidateBytesSizeDataLogin, ",");
                p0 = Convert.ToInt16(s0[0]);
                p1 = Convert.ToInt16(s0[1]);
                p2 = Convert.ToInt16(s0[2]);
                p3 = Convert.ToInt16(s0[3]);
                p4 = Convert.ToInt16(s0[4]);

                pEmailCoordinate = sr.ReadLine();
                string[] s1 = GCGCommon.SupportMethods.SplitByString(pEmailCoordinate, ",");
                p0 = Convert.ToInt16(s1[0]);
                p1 = Convert.ToInt16(s1[1]);

                pValidateBytesSizeDataTextarea = sr.ReadLine();
                string[] s2 = GCGCommon.SupportMethods.SplitByString(pValidateBytesSizeDataTextarea, ",");
                p0 = Convert.ToInt16(s2[0]);
                p1 = Convert.ToInt16(s2[1]);
                p2 = Convert.ToInt16(s2[2]);
                p3 = Convert.ToInt16(s2[3]);
                p4 = Convert.ToInt16(s2[4]);

                pFriendEmailCoordinate = sr.ReadLine();
                string[] s3 = GCGCommon.SupportMethods.SplitByString(pFriendEmailCoordinate, ",");
                p0 = Convert.ToInt16(s3[0]);
                p1 = Convert.ToInt16(s3[1]);
                sr.Close();
            }
            catch (Exception ex)
            {
                err = true;
                LogItBoth("Error with data in PrimaryScript.txt - " + ex.Message, true);
            }
        }
        private void DoIt()
        {
            pOverallErrors = "";
            pURL = "https://my.freedompop.com/earn-share/friend-invite";  //https://my.freedompop.com/login
            string pLogin = "";
            string pPassword = "Temppass1!";
            //string[] digits = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" };  //"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
            string[] digits = txtAccountsToUse.Text.Split(',');
            foreach (string value in digits)
            {
                pLogin = "mc2fp0" + value + "@gmail.com";
                LogItBoth("Running " + pLogin, false);
                ClearCache();
                RunScript(pLogin, pPassword);
            }
            if (pOverallErrors.Length>0)
            {
                SendMail(pOverallErrors);
                LogItTextbox("Completed with errors; emailing report.");
            }
            else
            {
                LogItTextbox("Completed with no errors.");

            }
            if (AutoStart)
            {
                IEHelper.KillProcess("IEXPLORE");
                Application.Exit();
            }
        }
        private bool SendMail(string pMessage)
        {
            return true;
            string MailType = "";
            bool retVal = true;
            try
            {
                MailType = "FPFriender Error";
                var fromAddress = new MailAddress("temp1@mc2techservices.com", MailType);
                var toAddress = new MailAddress("service@mc2techservices.com", MailType);
                const string fromPassword = "Temppass1";
                string body = "Errors Detected: "+ Environment.NewLine + pMessage;
                var smtp = new SmtpClient
                {
                    Host = "smtp.gmail.com",
                    Port = 587,
                    EnableSsl = true,
                    DeliveryMethod = SmtpDeliveryMethod.Network,
                    UseDefaultCredentials = false,
                    Credentials = new NetworkCredential(fromAddress.Address, fromPassword)
                };
                using (var message = new MailMessage(fromAddress, toAddress)
                {
                    Subject = MailType,
                    Body = body
                })
                    smtp.Send(message);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.Message);
                retVal = false;
            }
            return retVal;
        }

        private string DoHandleTyper(string whatToType)
        {
            string OK = "1";
            DoHandleTyper(whatToType, 1);
            return OK;
        }
        private string DoHandleTyper(string whatToType, int speedToType)
        {
            string OK = "1";
            DoTyper t = new DoTyper(whatToType, speedToType);
            tmrRunning.Enabled = false;
            int stop = 0;
            do
            {
                Application.DoEvents();
                if (t.IsDisposed == true)
                {
                    stop++;
                }
                if (t.Enabled == false)
                {
                    stop++;
                }
            } while (stop == 0);
            tmrRunning.Enabled = true;
            return OK;

        }

        private void LogItTextbox(string pMessage)
        {
            string temp = txtLog.Text;
            string newmsg = pMessage + "\r\n" + temp;
            txtLog.Text = newmsg;
        }
        private void LogItBoth(string pMessage, bool isError)
        {
            string temp = txtLog.Text;
            string newmsg = pMessage + "\r\n" + temp;
            this.Text = pMessage;
            txtLog.Text = newmsg;
            if (isError)
            {
                pOverallErrors = pOverallErrors + Environment.NewLine+ pMessage;
                System.Diagnostics.EventLog.WriteEntry("FPFriender", pMessage, System.Diagnostics.EventLogEntryType.Error);
            }
            else
            {
                System.Diagnostics.EventLog.WriteEntry("FPFriender", pMessage, System.Diagnostics.EventLogEntryType.Information);
            }
        }
        public void ClearCache()
        {
            //System.Diagnostics.Process.Start("rundll32.exe", "InetCpl.cpl,ClearMyTracksByProcess 255");
            LaunchIt("rundll32.exe", "InetCpl.cpl,ClearMyTracksByProcess 255", true, false);
        }        
        public void RunScript(string pLogin, string pPassword)
        {
            try
            {
                LogItTextbox("RunScript Started");
                //MessageBox(0, "AAA", "My Message Box", 0);
                bool pOK;
                object obj = null;
                byte[] aByte = Encoding.UTF8.GetBytes(pLogin);
                object vpost = aByte;
                InternetExplorer IE = new InternetExplorer();
                //IE.DocumentComplete += IE_DocumentComplete;
                IE.Visible = true;
                IE.StatusBar = true;
                IntPtr x = (IntPtr)IE.HWND;
                ExternalCallHelper.AdjustWindow(x, 0, 0, 800, 800);
                //long iPntr = Convert.ToInt64(SupportMethods.FindChromeHNWD());
                //MainWindowHWND = (IntPtr)iPntr;
                //GCGCommon.SupportMethods.AdjustWindow(MainWindowHWND, 0, 0, 800, 800);
                IE.Navigate("https://my.freedompop.com/earn-share/friend-invite");
                //mshtml.IHTMLDocument2 FrameDoc = IEHelper.ConvertIEToIHTMLDocument2(IE, "md-raised md-primary md-button md-ink-ripple");
                //IEHelper.SimInput(FrameDoc, IEHelper.HTMLTagNames.Zinput, IEHelper.HTMLAttributes.Zname, "username", pLogin);
                //LogItTextbox(pValidateBytesSizeDataLogin);
                pOK = DoHandleValidateBytesSize(pValidateBytesSizeDataLogin, pLogin + " - Login; probably previous account did not sign out.");
                if (pOK == false) return;
                int[] Coords1 = GetCoordsFromString(pEmailCoordinate);
                DoUI.DoMouseClick(Coords1[0], Coords1[1]);
                DoDelay(1);
                DoHandleTyper(pLogin);
                DoHandleTyper("{TAB}");
                DoHandleTyper(pPassword+ "{TAB}{ENTER}");
                //If we have a reactivate, we click it, then wait for the close button                
                DoDelay(5);  //We have to wait a few seconds for the page to load to see if we need to reactivate or not.
                mshtml.IHTMLDocument2 htmlDoc = IE.Document as mshtml.IHTMLDocument2;
                string content1 = htmlDoc.body.outerHTML;
                if (content1.Contains("Reactivate Account"))
                {
                    int[] Coords2 = GetCoordsFromString(pReactivateCoordinates);
                    DoUI.DoMouseClick(Coords2[0], Coords2[1]);
                    DoDelay(12);                   //This takes some time...
                    int[] Coords3 = GetCoordsFromString(pReactivateCloseCoordinates);
                    DoUI.DoMouseClick(Coords3[0], Coords3[1]);
                    DoDelay(3);
                }                
                //IE.Navigate("https://my.freedompop.com/earn-share/friend-invite");
                pOK = DoHandleValidateBytesSize(pValidateBytesSizeDataTextarea, pLogin + " - Setting Friend Text Area; probably needs reactivation.");
                if (pOK == false) return;
                int[] Coords4 = GetCoordsFromString(pFriendEmailCoordinate);
                LogItTextbox("Clicking " + Coords4[0].ToString() + "," + Coords4[1].ToString());                
                DoUI.DoMouseClick(Coords4[0], Coords4[1]);
                DoDelay(10);                
                //FrameDoc = IEHelper.ConvertIEToIHTMLDocument2(IE, "input_13");
                //IEHelper.SimInput(FrameDoc, IEHelper.HTMLTagNames.Ztextarea, IEHelper.HTMLAttributes.Zid, "input_13", "mc2tab00@gmail.com");
                DoHandleTyper(txtEmail.Text + "{TAB}{ENTER}");
                DoDelay(3);
                IEHelper.KillProcess("IEXPLORE");            
            }
            catch (Exception ex)
            {
                LogItBoth("DoIt Error: " + ex.Message,true);
                System.Diagnostics.Debug.WriteLine("!");
            }
        }

        void IE_DocumentComplete(object pDisp, ref object URL)
        {
            Debug.WriteLine("Complete");
        }
        private void PossiblyReactivate()
        {

        }

        private bool DoHandleValidateBytesSize(string pAllData, string pHandler)
        {
            bool retVal = false;
            string[] s1 = GCGCommon.SupportMethods.SplitByString(pAllData, ",");
            int p0 = Convert.ToInt16(s1[0]);
            int p1 = Convert.ToInt16(s1[1]);
            int p2 = Convert.ToInt16(s1[2]);
            int p3 = Convert.ToInt16(s1[3]);
            string p4 = s1[4].Trim();
            retVal = DoHandleValidateBytesSize(p0, p1, p2, p3, p4, pHandler);
            return retVal;
        }
        private bool DoHandleValidateBytesSize(int pX, int pY, int pW, int pH, string pSizeShouldBe, string pHandler)
        {
            bool retVal = false;
            string pSize = "-999";
            string OK = "1";
            RetryCnt = 0;
            tmrRunning.Enabled = false;
            do
            {
                pSize = DoUI.ValidateBytesSize2(pX, pY, pW, pH);
                LogItTextbox("Calced Size: " + pSize + " VS Required Size: " + pSizeShouldBe);
                if (pSize == pSizeShouldBe)
                {
                    LogItTextbox("Match Found!");
                    retVal = true;
                    break;
                }
                System.Threading.Thread.Sleep(1000);
                RetryCnt++;
                if (RetryCnt > 10)
                {
                    LogItBoth("Could not match the size for " + pHandler + " - this will need to be fixed.",true);
                    retVal = false;
                    break;
                }
                LogItTextbox("Matching size attempt " + RetryCnt.ToString());
                Application.DoEvents();
            } while (1==1);
            tmrRunning.Enabled = true;
            return retVal;
        }

        public string DoDelay(int pDelayAmnt)
        {
            System.Diagnostics.Debug.WriteLine("Doing Delay...");
            DateTime date1 = DateTime.Now;
            DateTime date2 = date1.AddSeconds(pDelayAmnt);
            do
            {
                Application.DoEvents();
            } while (DateTime.Now < date2);
            return "1";
        }


        public static string LaunchIt(string exe, string args, bool WaitForExit, bool LaunchInvisible)
        {
            string retVal = "";
            try
            {
                System.Diagnostics.Process p = new System.Diagnostics.Process();
                p.StartInfo.FileName = exe;
                if (args == null) args = "";
                if (args.Length > 0)
                {
                    p.StartInfo.Arguments = args;
                }
                if (LaunchInvisible == true)
                {
                    p.StartInfo.CreateNoWindow = false;
                    p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                }
                else
                {
                    p.StartInfo.CreateNoWindow = false;
                    p.StartInfo.WindowStyle = ProcessWindowStyle.Normal;
                }
                Console.WriteLine(p.StartInfo.FileName);
                bool isStarted = p.Start();
                if (isStarted == false) { return exe + " couldn't start"; }
                retVal = p.Id.ToString();
                if (WaitForExit == true)
                {
                    p.WaitForExit();
                }
            }
            catch (Exception ex)
            {
                retVal = "-1";
                return ex.Message;
            }
            return retVal;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Top = 80;
            Left = 800;
        }

        private void cmdOpenConfig_Click(object sender, EventArgs e)
        {
            OpenConfig();
        }
        private void OpenConfig()
        {
            System.Diagnostics.Process notePad = new System.Diagnostics.Process();
            notePad.StartInfo.FileName = "notepad.exe";
            notePad.StartInfo.Arguments = "PrimaryScript.txt";
            notePad.Start();
        }
        private int[] GetCoordsFromString(string pValIn)
        {
            int[] retVal=new int[2];
            string[] pTemp = GCGCommon.SupportMethods.SplitByString(pValIn, ",");
            retVal[0] = Convert.ToInt16(pTemp[0]);
            retVal[1] = Convert.ToInt16(pTemp[1]);
            return retVal;
        }
        private void button1_Click(object sender, EventArgs e)
        {
            InternetExplorer IE = new InternetExplorer();
            IE.Visible = true;
            IntPtr x = (IntPtr)IE.HWND;
            ExternalCallHelper.AdjustWindow(x, 0, 0, 800, 800);
            IE.Navigate("https://my.freedompop.com/earn-share/friend-invite");
            LogItTextbox("Temppass1!");
            LogItTextbox("mc2fp001@gmail.com");
        }

        private void tmrAutoStart_Tick(object sender, EventArgs e)
        {
            tmrAutoStart.Enabled = false;
            if (AutoStart == true) this.WindowState = FormWindowState.Minimized;
            DoIt();
        }

        private void cmdClearCache_Click(object sender, EventArgs e)
        {
            ClearCache();
        }

        private void cmdAbout_Click(object sender, EventArgs e)
        {
            frmConfig a = new frmConfig();
            a.Show();
        }


    
    }



}
