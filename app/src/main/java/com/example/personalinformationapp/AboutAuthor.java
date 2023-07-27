package com.example.personalinformationapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutAuthor extends AppCompatActivity {
    TextView txtParagraph,txtBack, txtBackArrow, txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_author);

        txtParagraph = findViewById(R.id.txtParagraph);
        txtTitle = findViewById(R.id.txtTitle);

        txtBack = findViewById(R.id.txtBack);
        txtBackArrow = findViewById(R.id.txtBackArrow);

        String aboutAuthor = readFileFromRawResource(this, R.raw.about_author);

        txtParagraph.setText(aboutAuthor);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            Toast.makeText(this, "DARK MODE", Toast.LENGTH_SHORT).show();

            txtTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtParagraph.setTextColor(ContextCompat.getColor(this, R.color.white));

            txtBack.setTextColor(ContextCompat.getColor(this, R.color.white));
            txtBackArrow.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            Toast.makeText(this, "LIGHT MODE", Toast.LENGTH_SHORT).show();
        }

        // lambda function
        txtBack.setOnClickListener(view -> {
            Intent intent = new Intent(AboutAuthor.this, Settings.class);
            startActivity(intent);
        });

        txtBackArrow.setOnClickListener(view -> {
            Intent intent = new Intent(AboutAuthor.this, Settings.class);
            startActivity(intent);
        });
    }

    private String readFileFromRawResource(Context context, int resourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Resources resources = context.getResources();
            InputStream inputStream = resources.openRawResource(resourceId);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

