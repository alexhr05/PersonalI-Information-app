package com.example.personalinformationapp;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StorageInfo extends AppCompatActivity {
    Button btnAddNewInformation, btnSettings;
    List<Item> items;
    List<String> hideItemPassword;
    RecyclerView recyclerView;
    int numberOfSaves;
    TextView txtWebsiteTitle, txtUserNameTitle, txtPasswordTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_information);
        txtWebsiteTitle = findViewById(R.id.txtWebsiteTitle);
        txtUserNameTitle = findViewById(R.id.txtUserNameTitle);
        txtPasswordTitle = findViewById(R.id.txtPasswordTitle);
        items = new ArrayList<>();
        hideItemPassword = new ArrayList<>();
        recyclerView = findViewById(R.id.item_view);
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;


        btnAddNewInformation = findViewById(R.id.btnAddNewInformation);
        btnSettings = findViewById(R.id.btnSettings);

        String filepath = "MyDirs";
        File file = new File(getExternalFilesDir(filepath), "Information.txt");
        String[] rowInfo;
        int br=0;
        try {
            // Read the contents of the file
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //Toast.makeText(this, "Отваря файл Infromation.txt", Toast.LENGTH_SHORT).show();
            Log.d("ОТваря","Infromation.txt");
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                if(!line.isEmpty()){
                    String password = "YourPassword";
                    EncryptionUtils obj = new EncryptionUtils();
                    String decryptedData = obj.decrypt(password, line);
                    rowInfo = decryptedData.split(";");

                    Log.d("LOGIN",""+ rowInfo[0]+rowInfo[1]+rowInfo[2]);
                    String hidePassword = "";
                    for(int i = 0; i < rowInfo[2].length();i++){
                        hidePassword += "*";
                    }
                    hideItemPassword.add(hidePassword);
                    items.add(new Item(rowInfo[0],rowInfo[1],rowInfo[2]));

                }
            }

            bufferedReader.close();
            Log.d("Zatvarqne","Infromation.txt");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(StorageInfo.this,items,hideItemPassword));
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();
            txtWebsiteTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtUserNameTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtPasswordTitle.setTextColor(ContextCompat.getColor(this, R.color.white));

            btnSettings.setBackgroundResource(R.drawable.settings);


        } else {
            Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();
        }
        btnAddNewInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StorageInfo.this, AddingNewElements.class);
                intent.putExtra("caseFlag", 0); // Pass the position parameter
                startActivity(intent);

            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StorageInfo.this, Settings.class);
                startActivity(intent);

            }
        });
    }
    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    public void setNumberOfSaves(int numberOfSaves){
        this.numberOfSaves = numberOfSaves;
    }

    public int getNumberOfSaves(){
        return numberOfSaves;
    }


}


