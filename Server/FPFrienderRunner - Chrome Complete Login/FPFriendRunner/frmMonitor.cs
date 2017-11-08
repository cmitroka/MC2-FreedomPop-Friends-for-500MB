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
    public partial class frmMonitor : Form
    {
        public frmMonitor()
        {
            InitializeComponent();
        }

        private void frmMonitor_FormClosing(object sender, FormClosingEventArgs e)
        {
            GCGCommon.SupportMethods.LaunchIt(AppDomain.CurrentDomain.BaseDirectory + "\\KillFPFriender.bat", "", true, true);
            Application.Exit();
        }
    }
}
