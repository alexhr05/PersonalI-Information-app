package com.example.personalinformationapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileOutputStream;

public class UninstallReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            // Perform your cleanup actions here before the app is uninstalled
            SharedPreferences sharedPreferences =  context.getSharedPreferences("MyAppPreferences", MODE_PRIVATE);

            // Put the flag firstTime to true to be next time to go through Set up PIN
            boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);
            String filenameInformation = "Information.txt";
            String filenameSettings = "Settings.txt";
            String filepath = "MyDirs";
            File informationTxtFile = new File(context.getExternalFilesDir(filepath), filenameInformation);
            File settingsTxtFile = new File(context.getExternalFilesDir(filepath), filenameInformation);
            informationTxtFile.delete();
            settingsTxtFile.delete();
        }
    }
}
