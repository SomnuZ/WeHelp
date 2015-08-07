package com.wmzl.wehelp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import as.leap.LASDataManager;
import as.leap.LASFile;
import as.leap.LASFileManager;
import as.leap.LASObject;
import as.leap.callback.GetDataCallback;
import as.leap.callback.ProgressCallback;
import as.leap.callback.SaveCallback;
import as.leap.exception.LASException;

/**
 * Created by leo on 15-7-8.
 */
public class Agent {
    private static Agent instance;
    private Agent() {}
    public static Agent getInstance()
    {
        if (instance == null)
        {
            instance = new Agent();
        }
        return instance;
    }

    public static boolean isAddingHelp=false;
    public static void ChangeStatusToAddHelp(boolean b){
    }




    public void UploadFile(Bitmap img){

        // Locate the image in res > drawable-hdpi
        Bitmap bitmap = img;
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        LASFile file = new LASFile("testpic.png", image);
        // Upload the image into Parse Cloud
        LASFileManager.saveInBackground(file, new SaveCallback() {
            @Override
            public void done(LASException e) {

            }
//        }, new ProgressCallback() {
//            @Override
//            public void done(int i) {
//
//            }
        });

        // Create a New Class called "ImageUpload" in Parse
        LASObject imgupload = new LASObject("ImageUploaded");

        // Create a column named "ImageName" and set the string
        imgupload.put("ImageName", "testpic");

        // Create a column named "ImageFile" and insert the image
        imgupload.put("ImageFile", file);

        // Create the class and the columns
        LASDataManager.saveInBackground(imgupload, new SaveCallback() {
            @Override
            public void done(LASException e) {

            }
        });

        LASFile myFile=imgupload.getLASFile("testpic");
//        myFile.getUrl();
//        LASFileManager.getDataInBackground(myFile, new GetDataCallback() {
//            @Override
//            public void done(byte[] bytes, LASException e) {
//
//            }
//        });

        myFile.getName();

    }

    public void DownLoadFile(String fileName){

    }

}
