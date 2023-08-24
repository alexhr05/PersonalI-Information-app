package com.example.personalinformationapp;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Item> {
    public CustomListAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.information_view, parent, false);
        }

        Item item = getItem(position);

        TextView placeToLog = convertView.findViewById(R.id.placeToLog);
        TextView emailUsername = convertView.findViewById(R.id.emailUsername);
        TextView password = convertView.findViewById(R.id.password);
        Button btnEditInfo = convertView.findViewById(R.id.btnEditInfo);
        Button btnDeleteInfo = convertView.findViewById(R.id.btnDeleteInfo);
        Button btnViewInfo = convertView.findViewById(R.id.btnViewInfo);

        placeToLog.setText(item.getPlaceToLog());
        emailUsername.setText(item.getEmailUsername());
        password.setText(item.getPassword());

        int currentNightMode = convertView.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            Toast.makeText(convertView.getContext(), "DARK MODE", Toast.LENGTH_SHORT).show();
            placeToLog.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.white));
            emailUsername.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.white));
            password.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.white));

            btnViewInfo.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.white));
            btnViewInfo.setBackgroundColor(convertView.getResources().getColor(R.color.blue_dark_mode));
            btnDeleteInfo.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.white));
            btnDeleteInfo.setBackgroundColor(convertView.getResources().getColor(R.color.blue_dark_mode));
            btnEditInfo.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.white));
            btnEditInfo.setBackgroundColor(convertView.getResources().getColor(R.color.blue_dark_mode));

        } else {
            Toast.makeText(convertView.getContext(), "LIGHT MODE", Toast.LENGTH_SHORT).show();
        }

        // Set click listeners for buttons if needed

        return convertView;
    }
}
