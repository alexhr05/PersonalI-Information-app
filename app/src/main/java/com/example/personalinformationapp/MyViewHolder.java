package com.example.personalinformationapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView placeToLog, emailUsername, password;
    Button btnViewInfo, btnEditInfo, btnDeleteInfo;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        placeToLog = itemView.findViewById(R.id.placeToLog);
        emailUsername = itemView.findViewById(R.id.emailUsername);
        password = itemView.findViewById(R.id.password);
        btnViewInfo = itemView.findViewById(R.id.btnViewInfo);
        btnEditInfo = itemView.findViewById(R.id.btnEditInfo);
        btnDeleteInfo = itemView.findViewById(R.id.btnDeleteInfo);

     //   imgBtnEditMode.setId(R.id.placeToLog);


    }
}

