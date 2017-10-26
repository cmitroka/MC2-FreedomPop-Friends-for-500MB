using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using System.Threading.Tasks;
using mshtml;


    class IEHelper
    {
        public enum HTMLTagNames { Za, Zarea, Zbutton, Zdiv, Zform, Zhr, Zimg, Zinput, Zselect, Ztextarea };
        public enum HTMLAttributes { Zalt, Zclass, Zhref, Zid, Zname, ZinnerHTML, ZInnerText, ZouterHtml, ZOuterText, Zsrc, Zvalue, };
        public static string SimInput(IHTMLDocument2 iHTMLDocument2, HTMLTagNames zHTMLTagNames, HTMLAttributes zHTMLAttributes, string attName, string FillWithVal)
        {
            string retVal = "";
            retVal = DoElementAction(iHTMLDocument2, zHTMLTagNames, zHTMLAttributes, attName, FillWithVal, 1);
            return retVal;
        }

        private static string DoElementAction(IHTMLDocument2 FrameDoc, HTMLTagNames HTMLTagNames, HTMLAttributes zHTMLAttributes, string attName, string FillWithVal, int FoundInLoop)
        {
            int FoundInLoopCount = 0;
            int foreachcount = 0;
            string retVal = "-1";
            attName = attName.ToUpper();
            string pHTMLTagNames = HTMLTagNames.ToString().Substring(1, HTMLTagNames.ToString().Length - 1);
            string pHTMLEnumAttribute = zHTMLAttributes.ToString().Substring(1, zHTMLAttributes.ToString().Length - 1);
            try
            {

                mshtml.IHTMLElementCollection c = ((mshtml.HTMLDocumentClass)(FrameDoc)).getElementsByTagName(pHTMLTagNames);
                foreach (IHTMLElement div in c)
                {
                    foreachcount++;
                    if (attName == "ctl00_mainContentPlaceHolder_checkGiftCardBalance".ToUpper())
                    {
                        if (foreachcount < 500) continue;
                    }
                    if (c.length == 0) break;
                    System.Diagnostics.Debug.WriteLine("foreachcount: " + foreachcount.ToString() + " of " + c.length.ToString());
                    System.Diagnostics.Debug.WriteLine("OutterHTML: " + div.outerHTML);
                    System.Diagnostics.Debug.WriteLine("OutterText: " + div.outerText);
                    System.Diagnostics.Debug.WriteLine("InnerHTML: " + div.innerText);
                    System.Diagnostics.Debug.WriteLine("InnerText: " + div.innerHTML);

                    //System.Diagnostics.Debug.WriteLine("Name: " + element.GetAttribute("name"));
                    System.Diagnostics.Debug.WriteLine("ID: " + div.id);
                    bool TryIt = false;
                    string testUcaseVal = "ALWAYSFAILATTHISPOINT";
                    if (pHTMLEnumAttribute.ToUpper() == "OUTERHTML") attName = "*%" + attName;
                    if (pHTMLEnumAttribute.ToUpper() == "SRC") attName = "*%" + attName;
                    //{
                    //    if (div.outerHTML.ToUpper().Contains(attName)) TryIt = true;
                    //}
                    //else
                    //{
                    try
                    {
                        testUcaseVal = div.getAttribute(pHTMLEnumAttribute).ToString().ToUpper();
                        System.Diagnostics.Debug.WriteLine(pHTMLEnumAttribute + ": " + testUcaseVal);
                    }
                    catch (Exception ex)
                    {
                        System.Diagnostics.Debug.WriteLine(ex.Message);
                    }
                    if (testUcaseVal.Contains(attName))
                    {
                        bool tempTryIt = true;
                    }
                    TryIt = CompareElements(attName, testUcaseVal);
                    //}
                    if (div.outerHTML == null) continue;
                    if (TryIt == true)
                    {
                        string Xcoord = "";
                        string Ycoord = "";
                        Xcoord = div.offsetLeft.ToString();
                        Ycoord = div.offsetTop.ToString();
                        FoundInLoopCount++;
                        if (FoundInLoopCount < FoundInLoop) continue; ;
                        if (FillWithVal == "")
                        {
                            div.click();
                            retVal = "1";
                            break;
                        }
                        else if (FillWithVal == "focus")
                        {
                            IHTMLElement2 focusOnIt = (IHTMLElement2)div;
                            focusOnIt.focus();
                            retVal = "1";
                            break;
                        }
                        else
                        {
                            div.setAttribute("value", FillWithVal);
                            retVal = "1";
                            break;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                retVal = "-1";
            }
            return retVal;
        }
        private static bool CompareElements(string LookFor, string SearchInThis)
        {
            bool retVal = false;
            bool WildcardSearch = false;
            LookFor = LookFor.ToUpper();
            SearchInThis = SearchInThis.ToUpper();
            if (LookFor.Contains("*%"))
            {
                WildcardSearch = true;
            }
            LookFor = LookFor.Replace("*%", "");
            string temp;
            System.Diagnostics.Debug.WriteLine("Do Comparison");
            if (WildcardSearch == true)
            {
                if (SearchInThis.Contains(LookFor))
                {
                    retVal = true;
                }
            }
            else
            {
                if (LookFor == SearchInThis)
                {
                    retVal = true;
                }
            }
            return retVal;
        }
        public static IHTMLDocument2 ConvertIEToIHTMLDocument2(SHDocVw.InternetExplorer myIE, string SearchFor)
        {
            bool FoundIt = false;
            IHTMLDocument2 FrameDoc2 = null;
            try
            {
                mshtml.HTMLDocument htmlDoc = (mshtml.HTMLDocument)myIE.Document;
                for (int i = 0; i < htmlDoc.frames.length; i++)
                {
                    IHTMLWindow2 htmlWindow = (IHTMLWindow2)htmlDoc.frames.item(i);
                    FrameDoc2 = CrossFrameIE.GetDocumentFromWindow(htmlWindow);
                    string HTMLTest = "";
                    HTMLTest = FrameDoc2.body.innerHTML;
                    if (HTMLTest == null)
                    {
                        System.Diagnostics.Debug.WriteLine("Not sure if it's in frame " + i);
                        HTMLTest = "";
                        FoundIt = true;
                        break;
                    }
                    HTMLTest = HTMLTest.ToUpper();
                    //GCGCommon.SupportMethods.WriteFile("C:\\test_"+i, HTMLTest, true);
                    if (HTMLTest.Contains(SearchFor.ToUpper()))
                    {
                        FoundIt = true;
                        //GCGMethods.WriteFile("C:\\HTMLTest.txt", HTMLTest, true);
                        System.Diagnostics.Debug.WriteLine("IS in frame " + i);
                        break;
                    }
                    else
                    {
                        System.Diagnostics.Debug.WriteLine("Not in frame " + i);
                    }
                }
                if (FoundIt == false)
                {
                    FrameDoc2 = (IHTMLDocument2)htmlDoc;
                    string HTMLTest = FrameDoc2.body.innerHTML;
                    HTMLTest = HTMLTest.ToUpper();
                    if (HTMLTest.Contains(SearchFor.ToUpper()))
                    //GCGCommon.SupportMethods.WriteFile("C:\\test", HTMLTest, true);
                    {
                        FoundIt = true;
                        System.Diagnostics.Debug.WriteLine("IS in base document");
                    }
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.Message);
            }
            if (FoundIt == true)
            {
                return FrameDoc2;
            }
            else
            {
                return null;
            }
        }
        public static int FindWhatFrameItsIn(SHDocVw.InternetExplorer myIE, string SearchFor)
        {
            int retVal = -999;
            bool FoundIt = false;
            IHTMLDocument2 FrameDoc2 = null;
            try
            {
                mshtml.HTMLDocument htmlDoc = (HTMLDocument)myIE.Document;
                for (int i = 0; i < htmlDoc.frames.length; i++)
                {
                    IHTMLWindow2 htmlWindow = (IHTMLWindow2)htmlDoc.frames.item(i);
                    FrameDoc2 = CrossFrameIE.GetDocumentFromWindow(htmlWindow);
                    string HTMLTest = FrameDoc2.body.innerHTML;
                    if (HTMLTest == null)
                    {
                        System.Diagnostics.Debug.WriteLine("No HTML in frame " + i);
                        continue;
                    }
                    HTMLTest = HTMLTest.ToUpper();
                    if (HTMLTest.Contains(SearchFor.ToUpper()))
                    {
                        FoundIt = true;
                        System.Diagnostics.Debug.WriteLine("IS in frame " + i);
                        retVal = i;
                        break;
                    }
                    else
                    {
                        System.Diagnostics.Debug.WriteLine("Not in frame " + i);
                    }
                }
                if (FoundIt == false)
                {
                    FrameDoc2 = (IHTMLDocument2)htmlDoc;
                    string HTMLTest = FrameDoc2.body.innerHTML;
                    try
                    {
                        HTMLTest = HTMLTest.ToUpper();
                        if (HTMLTest.Contains(SearchFor.ToUpper()))
                        //GCGCommon.SupportMethods.WriteFile("C:\\test", HTMLTest, true);
                        {
                            FoundIt = true;
                            retVal = -1;
                            System.Diagnostics.Debug.WriteLine("IS in base document");
                        }
                    }
                    catch (Exception ex1)
                    {
                        System.Diagnostics.Debug.WriteLine("No HTML Content");
                    }
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.Message);
            }
            return retVal;
        }
        public static int IsRunning(string pProcessName)
        {
            int processcount = 0;
            Process[] pname = Process.GetProcessesByName(pProcessName);
            foreach (Process theprocess in pname)
            {
                if (theprocess.ProcessName == pProcessName)
                {
                    processcount++;
                }
            }
            return processcount;
        }
        public static void KillProcess(string NameOfProcess)
        {
            //Tools | Internet Options | Advanced | Browsing | Enable automatice crash recovery*
            System.Diagnostics.Process[] Ps = System.Diagnostics.Process.GetProcessesByName(NameOfProcess);
            foreach (System.Diagnostics.Process process in Ps)
            {
                try
                {
                    process.Kill();
                }
                catch (Exception)
                {
                }
            }
        }

    }
