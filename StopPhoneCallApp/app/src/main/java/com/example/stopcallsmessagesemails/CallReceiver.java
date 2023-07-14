package com.example.stopcallsmessagesemails;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.lang.reflect.Method;
import java.time.LocalDate;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                // Handle incoming call
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_CARRIER_NAME);
                Toast.makeText(context, "number:"+incomingNumber, Toast.LENGTH_SHORT).show();
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                try {
                    // End the call using the TelecomManager API
                    TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                    if (telecomManager != null) {
                        showNotification(context, incomingNumber);
                        telecomManager.endCall();
                        Toast.makeText(context, "phone account:" + telecomManager.getSelfManagedPhoneAccounts().toString(), Toast.LENGTH_SHORT).show();


                    }
                } catch (SecurityException e) {
                    // Handle the case where the app does not have permission to end calls
                }
            }
        }

    }

    private void showNotification(Context context, String phoneNumber) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create notification channel for Android 8.0 and higher
            NotificationChannel channel = new NotificationChannel("incoming_call_channel", "Incoming Call", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notification for incoming call");
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "incoming_call_channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Incoming call")
                .setContentText("Call from " + phoneNumber)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        notificationManager.notify(87992485, builder.build());
        //notificationManager.no;
    }
}
/*public class CallReceiver extends BroadcastReceiver {
    private static final String TAG = "CallInterceptor";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // Get the telephony manager
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            // Get the incoming call state
            int callState = telephonyManager.getCallState();

            // Check if the incoming call state is ringing
            if (callState == TelephonyManager.CALL_STATE_RINGING) {
                // Get the declared method "getITelephony" from the TelephonyManager class
                Method method = telephonyManager.getClass().getDeclaredMethod("getITelephony", (Class[]) null);
                // Make the method accessible
                method.setAccessible(true);
                // Get the ITelephony interface object from the TelephonyManager
                Object telephony = method.invoke(telephonyManager);
                // Get the endCall method from the ITelephony interface
                Method endCall = telephony.getClass().getDeclaredMethod("endCall");
                // Invoke the endCall method to end the incoming call
                endCall.invoke(telephony);
                Log.i(TAG, "Call ended");
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }



}*/
