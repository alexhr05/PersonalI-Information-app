package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowMessage = findViewById(R.id.button);
        btnShowMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtFirstName = findViewById(R.id.txtFirstName);
                TextView txtLastName = findViewById(R.id.txtLastName);
                TextView txtAge = findViewById(R.id.txtAge);

                EditText editTxtFirstName = findViewById(R.id.editTxtFirstName);
                EditText editTxtLastName = findViewById(R.id.editTxtLastName);
                EditText editTxtAge = findViewById(R.id.editTxtAge);

                txtFirstName.setText("First name: "+editTxtFirstName.getText().toString());
                txtLastName.setText("Last name: "+editTxtLastName.getText().toString());
                txtAge.setText("Age " + (Integer.parseInt(editTxtAge.getText().toString())+10));
                Toast.makeText(MainActivity.this, "Успяхте да се регистрирате", Toast.LENGTH_LONG).show();
            }
        });
    }

/*    public void onBtnClick(View view){


    }*/
}