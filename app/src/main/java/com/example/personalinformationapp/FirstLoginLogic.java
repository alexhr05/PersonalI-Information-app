package com.example.personalinformationapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FirstLoginLogic extends AppCompatActivity {
    Button btnContinueSecondStep;
    EditText edTextPin, edTextRepeatPin;
    String pinField, repeatPinField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_time_log);
        btnContinueSecondStep = findViewById(R.id.btnContinueSecondStep);
        edTextPin = findViewById(R.id.edTextPin);
        edTextRepeatPin = findViewById(R.id.edTextRepeatPin);


        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();

            btnContinueSecondStep.setTextColor(ContextCompat.getColor(this, R.color.white));

        } else {
            Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();

        }

        btnContinueSecondStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinField = edTextPin.getText().toString();
                repeatPinField = edTextPin.getText().toString();
                if(!pinField.isEmpty() && !repeatPinField.isEmpty() && pinField.equals(repeatPinField)){
                    if(isExternalStorageWritable()){
                        try {
                            String filename = "Settings.txt";
                            String filepath = "MyDirs";
                            File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                            FileOutputStream file = new FileOutputStream(myExternalFile);

                            String password = "YourPassword";
                            String data = pinField;
                            EncryptionUtils obj = new EncryptionUtils();
                            String encryptedData = obj.encrypt(password, data);

                            file.write(encryptedData.getBytes());
                            file.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(FirstLoginLogic.this, SecondStepLogin.class);
                        startActivity(intent);

                    }
                }else{
                    Toast.makeText(FirstLoginLogic.this, "Някое от полетата не са попълнение или не сте въвели PIN в двете полета", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
