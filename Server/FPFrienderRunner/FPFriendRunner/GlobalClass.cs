using System;
using System.Collections.Generic;
using System.Text;

namespace Shell_Execution_Profiler
{
    public static class GlobalClass
    {
        public static string initDir;

        public static void SetInitDir(string fileLocation)
        {
            string[] temp = GlobalClass.GetFilePathPieces(fileLocation);
            initDir = temp[1];
        }
        public static string[] GetFilePathPieces(string fileLocation)
        {
            try
            {
                string temp = fileLocation.Substring(0, 1);
                if (temp == "\"")
                {
                    fileLocation = fileLocation.Substring(1, fileLocation.Length - 2);
                }
            }
            catch (Exception ex)
            {
            }

            string[] retme = new string[5];
            try
            {
                retme[0] = System.IO.Path.GetFileName(fileLocation);
            }
            catch (Exception ex0) { }
            try
            {
                retme[1] = System.IO.Path.GetDirectoryName(fileLocation);
            }
            catch (Exception ex1) { }
            try
            {
                retme[2] = System.IO.Path.GetExtension(fileLocation);
            }
            catch (Exception ex2) { }
            try
            {
                retme[3] = System.IO.Path.GetFileNameWithoutExtension(fileLocation);
            }
            catch (Exception ex3) { }
            try
            {
                retme[4] = System.IO.Path.GetPathRoot(fileLocation);
            }
            catch (Exception ex4) { }
            return retme;
        }

    }
}
