package com.mc2techservices.fpfriendsfor500mb;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 11/8/2017.
 */

public class WebComm {
    public String wcURL;
    public String wcWebResponse;
    private String resp;
    private String isOnline;
    public WebComm()
    {
        wcWebResponse = "...";
    }
    public void ExecuteWebRequest(String pURL, String pParams) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                wcWebResponse = "...";
                publishProgress("Sleeping..."); // Calls onProgressUpdate()
                try {
                    String url = params[0];
                    String urlParameters = params[1];
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj
                            .openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("User-Agent", "Chrome");
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                    con.setDoOutput(true);
                    Log.d("GF AsyncWebCall", "Ready to send...: " + url);

                    try {
                        DataOutputStream wr = new DataOutputStream(
                                con.getOutputStream());
                        wr.writeBytes(urlParameters);
                        wr.flush();
                        wr.close();
                    } catch (Exception e) {
                        Log.d("GF AsyncWebCall", "Error 001");
                        Log.d("GF AsyncWebCall", e.getMessage());
                        resp = "CallWebService Exception Occured 1";
                    }

                    int responseCode = con.getResponseCode();

                    Log.d("GF AsyncWebCall",
                            "\nSending 'POST' request to URL : " + url);
                    Log.d("GF AsyncWebCall", "Post parameters : "
                            + urlParameters);
                    Log.d("GF AsyncWebCall", "Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                    Log.d("GF AsyncWebCall", response.toString());
                    resp = response.toString();
                } catch (Exception e) {
                    Log.d("GF AsyncWebCall", "Error 002");
                    Log.d("GF AsyncWebCall", e.getMessage());
                    resp = "CallWebService Exception Occured 2";
                }
                return resp;
            }

            @Override
            protected void onPostExecute(String result) {
                //This does come back; what to do with it/how to handle it... not sure.
                Log.d("GF AsyncWebCall", "onPostExecute");
                Log.d("GF AsyncWebCall", "onPostExecute Raw Result:" + result);
                if (result.equals("CallWebService Exception Occured 2")) //Means the server or user is offline
                {
                    isOnline = "0";
                } else {
                    isOnline = "1";
                }
                String Response = GeneralFunctions.Comm.GetResonseData(result); // "http://AsyncWebCall.mc2techservices.com/Barcodes/9876543321.jpg";
                Log.d("GF AsyncWebCall", "onPostExecute Clean Response" + Response);
                wcWebResponse = Response;
            }

            @Override
            protected void onPreExecute() {
                Log.d("GF AsyncWebCall", "onPreExecute");
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                Log.d("GF AsyncWebCall", "onProgressUpdate");
            }
        }.execute(pURL, pParams);
    }

    public class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        @Override
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            Log.d("GF DownloadFilesTask", "Start");
            for (int i = 0; i < count; i++) {
                //totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Log.d("GF DownloadFilesTask", "onProgressUpdate");
            //setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
            Log.d("GF DownloadFilesTask", "onPostExecute");
            //showDialog("Downloaded " + result + " bytes");
        }
    }
}
