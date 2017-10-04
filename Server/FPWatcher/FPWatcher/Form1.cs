using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FPWatcher
{

    public partial class Form1 : Form
    {
        int pDots;
        private NotifyIcon trayIcon;
        private ContextMenu trayMenu;
        public Form1()
        {
            InitializeComponent();
            // Create a simple tray menu with only one item.
            trayMenu = new ContextMenu();
            trayMenu.MenuItems.Add("Toggle Running", mnuToggleRunning);
            trayMenu.MenuItems.Add("Exit", mnuOnExit);

            // Create a tray icon. In this example we use a
            // standard system icon for simplicity, but you
            // can of course use your own custom icon too.
            trayIcon = new NotifyIcon();
            trayIcon.Text = "FP Watcher";
            trayIcon.Icon = new Icon(SystemIcons.Application, 400, 400);

            // Add menu to tray icon and show it.
            trayIcon.ContextMenu = trayMenu;
            trayIcon.Visible = true;
            try
            {
                StreamReader sr = new StreamReader("Settings.txt");
                txtFPFriendRequestLoc.Text = sr.ReadLine();
                txtFrienderEXELoc.Text = sr.ReadLine();
                sr.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Settings.txt error - " + ex.Message);
            }
        }

        private void cmdToggleRunning_Click(object sender, EventArgs e)
        {
            ToggleRunning();
        }
        private void mnuOnExit(object sender, EventArgs e)
        {
            Application.Exit();
        }
        private void mnuToggleRunning(object sender, EventArgs e)
        {
            ToggleRunning();
        }
        private void ToggleRunning()
        {
            if (tmrRunning.Enabled == false)
            {
                tmrRunning.Enabled = true;
            }
            else
            {
                tmrRunning.Enabled = false;
                this.Text = "FP Watcher";
            }
        }
        private void Run()
        {
            tmrRunning.Enabled = false;
            string[] filePaths;
            try
            {
                filePaths = Directory.GetFiles(txtFPFriendRequestLoc.Text + "\\");
            }
            catch (Exception)
            {
                return;
            }
            foreach (string file in filePaths)
            {
                System.Diagnostics.Process p = new System.Diagnostics.Process();
                StreamReader sr = null;
                string templine = "";
                try
                {
                    sr = new StreamReader(file);
                    templine = sr.ReadLine();  //Email
                    templine = templine + "," + sr.ReadLine();  //First 5, Last 5, or All 10
                    sr.Close();
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine(ex.Message);
                    return;
                }
                string fileName = txtFrienderEXELoc.Text;
                string arguments = templine;
                p.StartInfo.FileName = fileName;
                p.StartInfo.Arguments = arguments;
                try{bool isStarted = p.Start();}
                catch (Exception exStart){}
                this.Text = "Waiting for Friender to Exit";
                p.WaitForExit();
                try{File.Delete(file);}
                catch (Exception exDel){}
            }
            tmrRunning.Enabled = true;
        }

        private void tmrRunning_Tick(object sender, EventArgs e)
        {
            pDots++;
            if (pDots>3) pDots=1;
            if (pDots == 1) this.Text = ".";
            if (pDots == 2) this.Text = "..";
            if (pDots == 3) this.Text = "...";
            
            Run();
        }

        private void cmdOpenConfig_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process notePad = new System.Diagnostics.Process();
            notePad.StartInfo.FileName = "notepad.exe";
            notePad.StartInfo.Arguments = "Settings.txt";
            notePad.Start();

        }
    }
}
