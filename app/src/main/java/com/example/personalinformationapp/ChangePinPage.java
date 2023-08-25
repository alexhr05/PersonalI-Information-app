package com.example.personalinformationapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChangePinPage extends AppCompatActivity {
    TextView txtTitle, txtPIN, txtNewPIN, txtNewPINRepeat, txtError;
    EditText edTextOldPin, edTextNewPin, edTextRepeatPin;
    Button btnSaveNewPIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pin);
        txtTitle = findViewById(R.id.txtTitle);
        txtPIN = findViewById(R.id.txtPIN);
        txtNewPIN = findViewById(R.id.txtNewPIN);
        txtNewPINRepeat = findViewById(R.id.txtNewPINRepeat);
        txtError = findViewById(R.id.txtError);

        edTextOldPin = findViewById(R.id.edTextOldPin);
        edTextNewPin = findViewById(R.id.edTextNewPin);
        edTextRepeatPin = findViewById(R.id.edTextRepeatPin);

        btnSaveNewPIN = findViewById(R.id.btnSaveNewPIN);


        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            //Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();

            txtTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtPIN.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtNewPIN.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtNewPINRepeat.setTextColor(ContextCompat.getColor(this, R.color.white));

            edTextOldPin.setTextColor(ContextCompat.getColor(this, R.color.white));
            edTextNewPin.setTextColor(ContextCompat.getColor(this, R.color.white));
            edTextRepeatPin.setTextColor(ContextCompat.getColor(this, R.color.white));

            btnSaveNewPIN.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
           // Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();

        }

        btnSaveNewPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPin = edTextOldPin.getText().toString();
                String newPin = edTextNewPin.getText().toString();
                String newPinRepeat = edTextRepeatPin.getText().toString();


                if(!oldPin.isEmpty() && !newPin.isEmpty() && !newPinRepeat.isEmpty()){
                    String password = "YourPassword";
                    EncryptionUtils objectEncryption = new EncryptionUtils();
                    ArrayList<String> allSettingsInfo = new ArrayList<>();

                    if(isExternalStorageWritable()){
                        // Write the modified content back to the file
                        try {
                            String filename = "Settings.txt";
                            String filepath = "MyDirs";
                            File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                            InputStream inputStream = new FileInputStream(myExternalFile);
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String line = "";
                        //    Toast.makeText(ChangePinPage.this, "myExternalFile="+myExternalFile, Toast.LENGTH_SHORT).show();
                            while ((line = bufferedReader.readLine()) != null) {
                                allSettingsInfo.add(line);
                            }
                            String decryptedPin = objectEncryption.decrypt(password, allSettingsInfo.get(LineInSettingsFile.pinLine.ordinal()));

                            int positionElementToCheck = LineInSettingsFile.pinLine.ordinal();

                            if(decryptedPin.equals(oldPin)){
                                StringBuilder content = new StringBuilder();


                                String encryptedData = "";
                                String encodedStringWithoutNewlines = "";

                                try {
                                    encryptedData = objectEncryption.encrypt(password, newPin);
                                    encodedStringWithoutNewlines = encryptedData.replace("\n", "").replace("\r", "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (positionElementToCheck >= 0 && positionElementToCheck < allSettingsInfo.size()) {
                                    allSettingsInfo.set(positionElementToCheck, encodedStringWithoutNewlines);
                                }

                                // Write the modified content back to the file
                                try {
                                    FileOutputStream fos = new FileOutputStream(myExternalFile);

                                    for (String allSettingsInfoByRow : allSettingsInfo) {
                                        fos.write(allSettingsInfoByRow.getBytes());
                                        fos.write("\n".getBytes()); // Add newline character after each line
                                    }

                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(ChangePinPage.this, Settings.class);
                                startActivity(intent);
                            }else{
                                txtError.setText("Не сте въвели правилно стария си ПИН!");
                            }

                            bufferedReader.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }else{
                    txtError.setText("Трябва да попълните всички полета");
                }
            }
        });
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
