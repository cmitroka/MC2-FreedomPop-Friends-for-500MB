using System;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;


namespace FPFriender
{
    public static class ExternalCallHelper
    {
        [DllImport("user32.dll")]
        private static extern int GetWindowText(IntPtr hWnd, StringBuilder strText, int maxCount);
        [DllImport("user32.dll")]
        private static extern bool SetForegroundWindow(IntPtr hWnd);
        [DllImport("user32.dll", CharSet = CharSet.Unicode)]
        private static extern int GetWindowTextLength(IntPtr hWnd);
        [DllImport("user32.dll")]
        private static extern bool IsWindowVisible(IntPtr hWnd);
        [DllImport("user32.dll", SetLastError = true)]
        internal static extern bool MoveWindow(IntPtr hWnd, int X, int Y, int nWidth, int nHeight, bool bRepaint);
        [DllImport("user32.dll")]
        private static extern Int32 GetWindow(Int32 hWnd, Int32 uCmd);
        [DllImport("user32.dll", EntryPoint = "SetWindowPos")]
        public static extern IntPtr SetWindowPos(IntPtr hWnd, int hWndInsertAfter, int x, int Y, int cx, int cy, int wFlags);
        [DllImport("user32.dll", SetLastError = true)]
        private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);
        [DllImport("user32.dll")]
        private static extern bool ShowWindow(IntPtr hwnd, int nCmdShow);
        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool IsIconic(IntPtr hWnd);
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
        public static string FindChromeHNWD()
        {
            string retVal = "-1";
            Process[] procs = Process.GetProcessesByName("chrome");
            if (procs.Length == 0)
            {
                Console.WriteLine("Google Chrome is not currently open");
                return retVal;
            }

            IntPtr hWnd = IntPtr.Zero;
            int numTabs = procs.Length - 4; // assuming there are 4 other processes apart from the tabs

            foreach (Process p in procs)
            {
                if (p.MainWindowTitle.Length > 0)
                {
                    hWnd = p.MainWindowHandle;
                    retVal = hWnd.ToString();
                    break;
                }
            }
            return retVal;
        }
        public static IntPtr GetChromePointer()
        {
            //Getting the HWND of Chrome
            IntPtr chromeWindow = (IntPtr)1243435;//FindWindow("Chrome_WidgetWin_1", "");
            int z = (int)chromeWindow;
            AdjustWindow(chromeWindow, 0, 0, 800, 800);

            int chrome = GetWindow(z, 1);
            IntPtr y = (IntPtr)chrome;
            AdjustWindow(y, 0, 0, 800, 800);

            return chromeWindow;
            //Setting the window to the foreground (implies focus and activating)
            //SetForegroundWindow(chrome);
        }
        public static string LaunchIt(string exe, string args)
        {
            string retVal = "";
            retVal = LaunchIt(exe, args, false, false);
            return retVal;
        }
        public static string LaunchIt(string EXE)
        {
            string retVal = "-1";
            retVal = LaunchIt(EXE, "");
            return retVal;
        }
        public static bool AdjustWindow(IntPtr hWnd, int X, int Y, int nWidth, int nHeight)
        {
            bool OK = MoveWindow(hWnd, X, Y, nWidth, nHeight, true);
            return OK;
        }

    }
}
