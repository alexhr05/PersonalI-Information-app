package com.example.personalinformationapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

public class AddingNewElements extends AppCompatActivity {
    Button btnSaveInformation;
    Button btnSaveimg;
    EditText placeToLog, emailUsername, password;
    TextView textView, txtTitle, txtWebsite, txtEmailUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int caseField = getIntent().getIntExtra("caseFlag", -1);
        //FileEncryptionExample fileEncryptionExample = new FileEncryptionExample();

        Toast.makeText(this, "username="+getIntent().getStringExtra("username"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "caseField"+getIntent().getIntExtra("caseFlag", -1), Toast.LENGTH_SHORT).show();
        if(caseField == 0){

            setContentView(R.layout.adding_new_information);
            btnSaveInformation = findViewById(R.id.btnSaveInformation);
            placeToLog = findViewById(R.id.edTxtWebsite);
            emailUsername = findViewById(R.id.edTxtEmailUsername);
            password = findViewById(R.id.edTxtPassword);
            txtTitle = findViewById(R.id.txtTitle);
            txtWebsite = findViewById(R.id.txtWebsite);
            txtEmailUsername = findViewById(R.id.txtEmailUsername);
            txtPassword = findViewById(R.id.txtPassword);



            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();
                txtTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
                txtWebsite.setTextColor(ContextCompat.getColor(this, R.color.white));
                txtEmailUsername.setTextColor(ContextCompat.getColor(this, R.color.white));
                txtPassword.setTextColor(ContextCompat.getColor(this, R.color.white));

                btnSaveInformation.setBackgroundColor(getResources().getColor(R.color.blue_dark_mode));
                btnSaveInformation.setTextColor(ContextCompat.getColor(this, R.color.white));
            } else {
                Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();

            }

            btnSaveInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String txtPlaceToLog = placeToLog.getText().toString();
                    String txtEmailUsername = emailUsername.getText().toString();
                    String txtpassword = password.getText().toString();

                    if(!txtPlaceToLog.isEmpty() && !txtEmailUsername.isEmpty() && !txtpassword.isEmpty()){

                        if(isExternalStorageWritable()){
                            try {
                                String filename = "Information.txt";
                                String filepath = "MyDirs";
                                File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                                FileOutputStream file = new FileOutputStream(myExternalFile,true);


                                String password = "YourPassword";
                                String data = txtPlaceToLog+";"+txtEmailUsername+";"+txtpassword;
                                EncryptionUtils obj = new EncryptionUtils();
                                String encryptedData = obj.encrypt(password, data);
                                Log.d("Encryption", "Encrypted Data: " + encryptedData);

                                //Log.d("Decryption", "Decrypted Data: " + decryptedData);

                                file.write(encryptedData.getBytes());
                                file.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(AddingNewElements.this, StorageInfo.class);
                            startActivity(intent);

                        }

                    }else{
                        Toast.makeText(AddingNewElements.this, "Някое от полетата не е попълнено", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else if(caseField > 0){
            setContentView(R.layout.editing_existing_elements);

            btnSaveInformation = findViewById(R.id.btnSaveInformation);

            String placeToLogToCheck = String.valueOf(getIntent().getStringExtra("placetolog"));
            String usernameToCheck = String.valueOf(getIntent().getStringExtra("username"));
            String passwordToCheck = String.valueOf(getIntent().getStringExtra("password"));
            int positionElementToCheck = getIntent().getIntExtra("position",-1);

            placeToLog = findViewById(R.id.edTxtWebsite);
            emailUsername = findViewById(R.id.edTxtEmailUsername);
            password = findViewById(R.id.edTxtPassword);
            // When I go to edit mode show info for this field
            placeToLog.setText(placeToLogToCheck);
            emailUsername.setText(usernameToCheck);
            password.setText(passwordToCheck);
            txtTitle = findViewById(R.id.txtTitle);
            txtWebsite = findViewById(R.id.txtWebsite);
            txtEmailUsername = findViewById(R.id.txtEmailUsername);
            txtPassword = findViewById(R.id.txtPassword);

            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();
                txtTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
                txtWebsite.setTextColor(ContextCompat.getColor(this, R.color.white));

                txtEmailUsername.setTextColor(ContextCompat.getColor(this, R.color.white));
                txtPassword.setTextColor(ContextCompat.getColor(this, R.color.white));

                btnSaveInformation.setTextColor(ContextCompat.getColor(this, R.color.white));
                btnSaveInformation.setBackgroundColor(getResources().getColor(R.color.blue_dark_mode));

            } else {
                Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();

            }

            btnSaveInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String txtPlaceToLog = placeToLog.getText().toString();
                    String txtEmailUsername = emailUsername.getText().toString();
                    String txtpassword = password.getText().toString();
                    // Get new info from edit text field and then write it in file
                    if(!txtPlaceToLog.isEmpty() && !txtEmailUsername.isEmpty() && !txtpassword.isEmpty()){

                        //Toast.makeText(AddingNewElements.this, "place="+txtPlaceToLog, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddingNewElements.this, "EmailUsername="+txtEmailUsername, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddingNewElements.this, "Password="+txtpassword, Toast.LENGTH_SHORT).show();
                        String filename = "Information.txt";
                        String filepath = "MyDirs";
                        // Create a File object representing the specific file
                        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                        if(isExternalStorageWritable()){


                            //  Read the contents of the file
                            List<String> lines = new ArrayList<>();

                            try {
                                FileInputStream fis = new FileInputStream(myExternalFile);
                                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                                String line;

                                // Read the file line by line
                                while ((line = reader.readLine()) != null) {
                                    lines.add(line);
                                }

                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // Modify the desired row in the lines list
                            String updatedRow = txtPlaceToLog+";"+txtEmailUsername+";"+txtpassword;
                            // Encrypt the data
                            String encryptedData = "";
                            try {
                                String password = "YourPassword";
                                String data = updatedRow;
                                EncryptionUtils obj = new EncryptionUtils();
                                encryptedData = obj.encrypt(password, data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (positionElementToCheck >= 0 && positionElementToCheck < lines.size()) {
                                lines.set(positionElementToCheck, encryptedData);
                            }
                            Toast.makeText(AddingNewElements.this, ""+lines.get(positionElementToCheck), Toast.LENGTH_SHORT).show();

                            // Write the modified content back to the file
                            try {
                                FileOutputStream fos = new FileOutputStream(myExternalFile);

                                for (String line : lines) {
                                    fos.write(line.getBytes());
                                    fos.write("\n".getBytes()); // Add newline character after each line
                                }

                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //storageInfo.getItems().add(new Item(txtPlaceToLog,txtEmailUsername,txtpassword));
                            //storageInfo.getRecyclerView().setAdapter(new MyAdapter(getApplicationContext(),storageInfo.items));
                            //textView = findViewById(R.id.textView7);
                            /*textView.setText(""+storageInfo.items.get(storageInfo.items.size()-1).getPlaceToLog()+";"+storageInfo.items.get(storageInfo.items.size()-1).getEmailUsername()+
                                    ";"+storageInfo.items.get(storageInfo.items.size()-1).getPassword());*/
                            Intent intent = new Intent(AddingNewElements.this, StorageInfo.class);
                            startActivity(intent);

                        }

                    }else{
                        Toast.makeText(AddingNewElements.this, "Някое от полетата не е попълнено", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddingNewElements.this, "txtPlaceToLog.isEmpty()="+txtPlaceToLog.isEmpty(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddingNewElements.this, "txtEmailUsername.isEmpty()="+txtEmailUsername.isEmpty(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddingNewElements.this, "txtpassword.isEmpty()="+txtpassword.isEmpty(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
