package com.example.personalinformationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout constraintLayout;
    EditText pinStandard;
    TextView textViewAttempt, txtTitleForPIN, txtTitleForLogin;
    Button btnLogin, btnShowFingerPrint;
    String pinString;
    int pin;
    int attempts;
    private BiometricManager biometricManager;
    String decryptedPin, logInPin, logInBiometric;
    SecondAuthentication obj;
    List<String> booleanValues;
    boolean isFingerprintAuthenticationSuccessful;
    // PIS - Pesonal Information Stuff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinStandard =  (EditText) findViewById(R.id.editTextPin);
        textViewAttempt = findViewById(R.id.textViewAttempt);
        txtTitleForPIN = findViewById(R.id.txtTitleForPIN);
        txtTitleForLogin = findViewById(R.id.txtTitleForLogin);

        btnLogin = findViewById(R.id.btnEnter);
        btnShowFingerPrint = findViewById(R.id.btnShowFingerPrint);
        attempts = 5;
        logInPin = "";
        logInBiometric = "";
        booleanValues = new ArrayList<String>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);

        // Check if the "firstTime" flag is set to true
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);
        isFingerprintAuthenticationSuccessful = false;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");


        //Toast.makeText(this, "isFirstTime="+isFirstTime, Toast.LENGTH_SHORT).show();
        if (isFirstTime) {
            // This is the first time the app is being launched
            try {
                String filenameInformation = "Information.txt";
                String filenameSettings = "Settings.txt";
                String filepath = "MyDirs";
                File myExternalFile = new File(getExternalFilesDir(filepath), filenameInformation);
                FileOutputStream fileInformation = new FileOutputStream(myExternalFile);
                File myExternalFileSettings = new File(getExternalFilesDir(filepath), filenameSettings);
                FileOutputStream fileSettings = new FileOutputStream(myExternalFileSettings);


                fileInformation.write("".getBytes());
                fileSettings.write("".getBytes());
                fileInformation.close();
                fileSettings.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Put the flag firstTime to true to be next time to go through Set up PIN
            editor.putBoolean("firstTime", false);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, FirstLoginLogic.class);
            startActivity(intent);

        } else {
            // The app has been launched before
        }



        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
         //   Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();

            btnLogin.setTextColor(ContextCompat.getColor(this, R.color.white));

            textViewAttempt.setTextColor(ContextCompat.getColor(this, R.color.white));
            pinStandard.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtTitleForPIN.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtTitleForLogin.setTextColor(ContextCompat.getColor(this, R.color.white));

        } else {
          //  Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();

        }

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Device doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Fingerprint is currently unavailable", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No fingerprint Assigned", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_SUCCESS:
                // Fingerprint authentication can be used.
                break;
        }
        Executor executor = ContextCompat.getMainExecutor(this);



        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                biometricPrompt.authenticate(promptInfo);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                if(logInBiometric.equals("true") && logInPin.equals("true")){
                    isFingerprintAuthenticationSuccessful = true;
                }else if(logInBiometric.equals("true") && logInPin.equals("false")){
                    Intent intent = new Intent(MainActivity.this, StorageInfo.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                biometricPrompt.authenticate(promptInfo);
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Personal Information Credentials- (PIC)")
                .setSubtitle("Firstly, Login in with your finger print and then you can log with username and password")
                .setDescription("After you login it you will have access the save and look all of your information")
                .setNegativeButtonText("X")
                .build();

        String filepath = "MyDirs";
        File file = new File(getExternalFilesDir(filepath), "Settings.txt");
        String decryptedPin;
        obj = new SecondAuthentication();
        try {
            // Read the contents of the file
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;


            while ((line = bufferedReader.readLine()) != null) {
                if(!line.isEmpty()) {
                    booleanValues.add(line);
                }
            }

            String password = "YourPassword";
            EncryptionUtils object = new EncryptionUtils();
            decryptedPin = object.decrypt(password, booleanValues.get(LineInSettingsFile.pinLine.ordinal()));

            obj.setPin(Integer.parseInt(decryptedPin));

            // Mark with true/false flag the using of Fingerprint
            logInBiometric = booleanValues.get(LineInSettingsFile.useFingerprintLine.ordinal());

            // Mark with true/false flag the using of PIN
            logInPin = booleanValues.get(LineInSettingsFile.usePinLine.ordinal());


            if(logInBiometric.equals("true") && logInPin.equals("false")){
                btnLogin.setVisibility(View.GONE);
                textViewAttempt.setVisibility(View.GONE);
                pinStandard.setVisibility(View.GONE);
                txtTitleForPIN.setVisibility(View.GONE);

            }else if(logInBiometric.equals("false") && logInPin.equals("true")){
                btnShowFingerPrint.setVisibility(View.GONE);

            }else if(logInBiometric.equals("false") && logInPin.equals("false")){
                btnShowFingerPrint.setVisibility(View.GONE);
                textViewAttempt.setVisibility(View.GONE);
                pinStandard.setVisibility(View.GONE);
                txtTitleForPIN.setVisibility(View.GONE);
            }


            if(logInBiometric.equals("true")){
                biometricPrompt.authenticate(promptInfo);
            }



            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnShowFingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinString = pinStandard.getText().toString();
                if(!pinString.isEmpty()){
                    pin = Integer.parseInt(pinString);
                }

                if(isFingerprintAuthenticationSuccessful && logInPin.equals("true")){
                    UsingPINWithWithoutBiometric();
                }else if(logInBiometric.equals("false") && logInPin.equals("true")){
                    UsingPINWithWithoutBiometric();
                }else if(logInBiometric.equals("false") && logInPin.equals("false")) {
                    Intent intent = new Intent(MainActivity.this, StorageInfo.class);
                    startActivity(intent);
                }

            }
        });



    }
    public void UsingPINWithWithoutBiometric(){
        if(obj.checkPin(pin)){
            Intent intent = new Intent(MainActivity.this, StorageInfo.class);
            startActivity(intent);
        }else{
            // textViewAttempt.setText(""+obj.checkPin(pin));
            textViewAttempt.setText("Сбъркахте пина си! Имате " + attempts + " оставащи опита.");

            attempts--;

            if(attempts < 0 ){
                System.exit(0);
            }
        }
    }

}