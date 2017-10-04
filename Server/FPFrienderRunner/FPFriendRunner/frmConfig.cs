using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FPFriender
{
    public partial class frmConfig : Form
    {
        public frmConfig()
        {
            InitializeComponent();
        }

        private void About_Load(object sender, EventArgs e)
        {
            txtVBS.Text = "17, 95, 100, 40, 1342" + Environment.NewLine + "107, 165" + Environment.NewLine + "12, 144, 200, 30, 3130" + Environment.NewLine + "677,477";
            lblPrimary.Text="Position to validate Login Screen" + Environment.NewLine + "Where to click to login email" + Environment.NewLine + "Position to validate Friend Screen" + Environment.NewLine +  "Where to click to send email";
            lblReactivate.Text = "Position to click Reactivate" + Environment.NewLine + "Position to click Close";
            txtReactivate.Text = "400,500" + Environment.NewLine + "510,420";
        }

        private void cmdOpenVBS_Click(object sender, EventArgs e)
        {
            OpenVBS();
        }
        private void OpenVBS()
        {
            System.Diagnostics.Process notePad = new System.Diagnostics.Process();
            notePad.StartInfo.FileName = "notepad.exe";
            notePad.StartInfo.Arguments = "PrimaryScript.txt";
            notePad.Start();
        }

        private void cmdOpenConfig_Click(object sender, EventArgs e)
        {
            System.Diagnostics.Process notePad = new System.Diagnostics.Process();
            notePad.StartInfo.FileName = "notepad.exe";
            notePad.StartInfo.Arguments = "ReactivateScript.txt";
            notePad.Start();

        }
    }
}
