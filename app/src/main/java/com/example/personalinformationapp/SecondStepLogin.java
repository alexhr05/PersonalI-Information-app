package com.example.personalinformationapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

public class SecondStepLogin extends AppCompatActivity {
    Button btnActivateFingerprint, btnContinueToLogin;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    boolean logWithBiometric, logWithPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_step_to_login);
        btnActivateFingerprint = findViewById(R.id.btnActivateFingerprint);
        btnContinueToLogin = findViewById(R.id.btnContinueToLogin);
        logWithBiometric = false;
        logWithPIN = true;

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();

            btnActivateFingerprint.setTextColor(ContextCompat.getColor(this, R.color.white));
            btnContinueToLogin.setTextColor(ContextCompat.getColor(this, R.color.white));

        } else {
            Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();

        }

        btnActivateFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricManager biometricManager = BiometricManager.from(SecondStepLogin.this);
                switch (biometricManager.canAuthenticate()){
                    case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                        Toast.makeText(SecondStepLogin.this, "Device doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                        Toast.makeText(SecondStepLogin.this, "Fingerprint is currently unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                        Toast.makeText(SecondStepLogin.this, "No fingerprint Assigned", Toast.LENGTH_SHORT).show();
                        break;
                    case BiometricManager.BIOMETRIC_SUCCESS:
                        // Fingerprint authentication can be used.
                        break;
                }
                Executor executor = ContextCompat.getMainExecutor(SecondStepLogin.this);

                biometricPrompt = new BiometricPrompt(SecondStepLogin.this, executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                       // biometricPrompt.authenticate(promptInfo);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(SecondStepLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                        logWithBiometric = true;
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                      //  biometricPrompt.authenticate(promptInfo);
                    }
                });
                promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Activate Fingerprint")
                        .setSubtitle("To activate biometric key, you have to test if you have it. Under this text app will show fingerprint who you will try first and" +
                                " if validation is ready, click 'Continue to login'")
                        .setNegativeButtonText("X")
                        .build();

                biometricPrompt.authenticate(promptInfo);
            }
        });

        btnContinueToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExternalStorageWritable()){
                    try {
                        String filename = "Settings.txt";
                        String filepath = "MyDirs";
                        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                        FileOutputStream file = new FileOutputStream(myExternalFile,true);

                        file.write((""+logWithBiometric).getBytes());
                        file.write(("\n"+logWithPIN).getBytes());
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(SecondStepLogin.this, MainActivity.class);
                    // This is for just installed app to make password(PIN)
                    int isGoRegister = 1;
                    intent.putExtra("isGoRegister", isGoRegister);
                    startActivity(intent);

                }

            }
        });

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
