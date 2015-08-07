package com.wmzl.wehelp;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import as.leap.LASDataManager;
import as.leap.LASObject;
import as.leap.LASQuery;


public class ActivityShowSS extends Activity {
    TextView tvSS;
    List<String> list = new ArrayList<>();
    String readString = new String();
    String res = "";
    String nowDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_ss);
        tvSS = (TextView) findViewById(R.id.tvSS);
        nowDateTime = new java.sql.Timestamp(System.currentTimeMillis()).toString().substring(0, 19);

//        list.add("baidu.com");
//        list.add("parse.com");
//        res = SiteStatisticsManager.getInstance().GetSitePVUV(getApplicationContext(), list);

//        String fileName = "LeoTestSS";
//        String path = android.os.Environment.getExternalStorageDirectory() + "/" + fileName + ".txt";
//        try {
//
//            FileInputStream fileIS = new FileInputStream(path);
//            BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
//
//            //just reading each line and pass it on the debugger
//            readString = buf.readLine();
//            res+=readString;
//
//            fileIS.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String nowDateTime = new java.sql.Timestamp(System.currentTimeMillis()).toString().substring(0, 19);
//        LASObject ssData=new LASObject("SSData");
//        ssData.put("Time",nowDateTime);
//        ssData.put("DetailData",readString);
//        LASDataManager.saveInBackground(ssData);
//        tvSS.setText(readString);

        String folderPath = android.os.Environment.getExternalStorageDirectory().toString() + "/LLSiteData";
        File folder = new File(folderPath);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            for (File f : files) {
                String siteName = f.getName();
                String path = f.getAbsolutePath();
                try {
                    FileInputStream fileIS = new FileInputStream(path);
                    BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
                    //just reading each line and pass it on the debugger
                    readString = buf.readLine();
                    res += readString;
                    fileIS.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LASObject ssData = new LASObject("SSData");
                ssData.put("SiteName", siteName);
                ssData.put("Time", nowDateTime);
                ssData.put("DetailData", readString);
                LASDataManager.saveInBackground(ssData);

            }

            tvSS.setText(res);

            LASQuery query=new LASQuery("SSData");
            query.whereEqualTo("SiteName","baidu.com");


        }
    }


}
