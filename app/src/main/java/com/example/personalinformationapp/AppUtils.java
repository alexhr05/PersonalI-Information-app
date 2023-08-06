package com.example.personalinformationapp;

import android.content.Context;
import android.content.pm.PackageManager;

public class AppUtils {
    public static boolean isMyAppInstalled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            // Attempt to get the application info based on the package name
            packageManager.getApplicationInfo(packageName, 0);
            return true; // Your app is installed
        } catch (PackageManager.NameNotFoundException e) {
            return false; // Your app is not installed
        }
    }
}
