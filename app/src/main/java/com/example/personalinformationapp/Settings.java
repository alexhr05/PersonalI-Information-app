package com.example.personalinformationapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings extends AppCompatActivity {
    private Switch btnSwitchFingerprint, btnSwitchPin;
    private TextView txtHelp, txtHelpArrow, txtAuthorInfo, txtAuthorInfoArrow, txtBack, txtBackArrow, txtTitle;
    private String fingerprintStatement;
    private ImageView imgViewFingerPrint, imgViewPin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
        btnSwitchFingerprint = findViewById(R.id.btnSwitchFingerprint);
        imgViewFingerPrint = findViewById(R.id.imgViewFingerPrint);
        imgViewPin = findViewById(R.id.imgViewPinIcon);
        btnSwitchPin = findViewById(R.id.btnSwitchPin);

        txtHelp = findViewById(R.id.txtHelp);
        txtHelpArrow = findViewById(R.id.txtHelp);

        txtAuthorInfo = findViewById(R.id.txtAuthorInfo);
        txtAuthorInfoArrow = findViewById(R.id.txtAuthorInfoArrow);

        txtBack = findViewById(R.id.txtBack);
        txtBackArrow = findViewById(R.id.txtBackArrow);

        txtTitle = findViewById(R.id.txtTitle);
        String[] seperateInfoFromFile;
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();
            txtTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
            btnSwitchFingerprint.setTextColor(ContextCompat.getColor(this, R.color.white));

            txtHelp.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtHelpArrow.setTextColor(ContextCompat.getColor(this, R.color.white));

            txtAuthorInfo.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtAuthorInfoArrow.setTextColor(ContextCompat.getColor(this, R.color.white));

            txtBack.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtBackArrow.setTextColor(ContextCompat.getColor(this, R.color.white));
            imgViewFingerPrint.setImageResource(R.drawable.fingerprint_white);


        } else {
            Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();
            imgViewFingerPrint.setImageResource(R.drawable.fingerprint_black);
        }

        try {
            String filename = "Settings.txt";
            String filepath = "MyDirs";
            File myExternalFile = new File(getExternalFilesDir(filepath), filename);

            BufferedReader reader = new BufferedReader(new FileReader(myExternalFile));
            String line;

            while ((line = reader.readLine()) != null) {
                fingerprintStatement += line + " ";
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        seperateInfoFromFile = fingerprintStatement.split(" ");

        btnSwitchFingerprint.setChecked(Boolean.parseBoolean(seperateInfoFromFile[1]));
        btnSwitchPin.setChecked(Boolean.parseBoolean(seperateInfoFromFile[2]));
        // lambda function
        txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, HelpPage.class);
                startActivity(intent);
            }
        });

        txtHelpArrow.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, HelpPage.class);
            startActivity(intent);
        });

        txtAuthorInfo.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, AboutAuthor.class);
            startActivity(intent);
        });

        txtAuthorInfoArrow.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, AboutAuthor.class);
            startActivity(intent);
        });

        txtBack.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, StorageInfo.class);
            startActivity(intent);
        });

        txtBackArrow.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, StorageInfo.class);
            startActivity(intent);
        });

        btnSwitchFingerprint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isExternalStorageWritable()) {
                    try {
                        String filename = "Settings.txt";
                        String filepath = "MyDirs";
                        File myExternalFile = new File(getExternalFilesDir(filepath), filename);

                        BufferedReader reader = new BufferedReader(new FileReader(myExternalFile));
                        StringBuilder content = new StringBuilder();
                        String line;
                        int lineToModify = 2; // The line number to modify

                        int currentLine = 1;
                        while ((line = reader.readLine()) != null) {
                            if (currentLine == lineToModify) {
                                // Modify the desired line
                                line = "" + isChecked;
                            }
                            content.append(line).append(System.lineSeparator());
                            currentLine++;
                        }
                        reader.close();

                        // Write the modified content back to the file
                        BufferedWriter writer = new BufferedWriter(new FileWriter(myExternalFile));
                        writer.write(content.toString());
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        btnSwitchPin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCHeckedPin) {
                if (isExternalStorageWritable()) {
                    try {
                        String filename = "Settings.txt";
                        String filepath = "MyDirs";
                        File myExternalFile = new File(getExternalFilesDir(filepath), filename);

                        BufferedReader reader = new BufferedReader(new FileReader(myExternalFile));
                        StringBuilder content = new StringBuilder();
                        String line;
                        int lineToModify = 3; // The line number to modify

                        int currentLine = 1;
                        while ((line = reader.readLine()) != null) {
                            if (currentLine == lineToModify) {
                                // Modify the desired line
                                line = "" + isCHeckedPin;
                            }
                            content.append(line).append(System.lineSeparator());
                            currentLine++;
                        }
                        reader.close();

                        // Write the modified content back to the file
                        BufferedWriter writer = new BufferedWriter(new FileWriter(myExternalFile));
                        writer.write(content.toString());
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });


    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
