package com.example.personalinformationapp;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Item> items;
    List<String> hideItemPassword;
    Button button;
    LinearLayout linearLayout;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ActivityManager.TaskDescription.Builder linearLayout;
        TextView placeToLog, emailUsername, password;
        Button  btnViewInfo, btnEditInfo, btnDeleteInfo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            placeToLog = itemView.findViewById(R.id.placeToLog);
            emailUsername = itemView.findViewById(R.id.emailUsername);
            password = itemView.findViewById(R.id.password);
            btnEditInfo = itemView.findViewById(R.id.btnEditInfo);
            btnViewInfo = itemView.findViewById(R.id.btnViewInfo);
            btnDeleteInfo = itemView.findViewById(R.id.btnDeleteInfo);
           //linearLayout = itemView.findViewById(R.id.LineBorder);
        }
    }
    public MyAdapter(Context context, List<Item> items, List<String> hideItemPassword){
        this.context = context;
        this.items = items;
        this.hideItemPassword = hideItemPassword;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.information_view,parent,false));
    }

   // @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        linearLayout = holder.itemView.findViewById(R.id.LineBorder);
        holder.placeToLog.setText(items.get(position).getPlaceToLog());
        holder.emailUsername.setText(items.get(position).getEmailUsername());
        holder.password.setText(hideItemPassword.get(holder.getAdapterPosition()));

        //holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        //String passwordField = items.get(position).getPassword();
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            //Toast.makeText(context, "DARK MODE", Toast.LENGTH_SHORT).show();
            holder.placeToLog.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.emailUsername.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.password.setTextColor(ContextCompat.getColor(context, R.color.white));
            linearLayout.setBackgroundResource(R.drawable.white_background_resource_linearlayout);

            holder.btnViewInfo.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.btnViewInfo.setBackgroundColor(context.getResources().getColor(R.color.blue_dark_mode));
            holder.btnDeleteInfo.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.btnDeleteInfo.setBackgroundColor(context.getResources().getColor(R.color.blue_dark_mode));
            holder.btnEditInfo.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.btnEditInfo.setBackgroundColor(context.getResources().getColor(R.color.blue_dark_mode));

        } else {
        //    Toast.makeText(context, "LIGHT MODE", Toast.LENGTH_SHORT).show();
        }
    //    Log.d("hidePassword",hideItemPassword.get(holder.getAdapterPosition()));
        // Show info in Edit mode
        holder.placeToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the navigation here
                Intent intent = new Intent(context, AddingNewElements.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("caseFlag", 1); // Pass the position parameter
                intent.putExtra("placetolog", ""+items.get(holder.getAdapterPosition()).getPlaceToLog());
                intent.putExtra("username", ""+items.get(holder.getAdapterPosition()).getEmailUsername());
                intent.putExtra("password", ""+items.get(holder.getAdapterPosition()).getPassword());
                intent.putExtra("position", holder.getAdapterPosition());

                context.startActivity(intent);
            }
        });

        // Show info in Edit mode
        holder.emailUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the navigation here
                Intent intent = new Intent(context, AddingNewElements.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("caseFlag", 1); // Pass the position parameter
                intent.putExtra("placetolog", ""+items.get(holder.getAdapterPosition()).getPlaceToLog());
                intent.putExtra("username", ""+items.get(holder.getAdapterPosition()).getEmailUsername());
                intent.putExtra("password", ""+items.get(holder.getAdapterPosition()).getPassword());
                intent.putExtra("position", holder.getAdapterPosition());

                context.startActivity(intent);
            }
        });
        StorageInfo obj = new StorageInfo();
        // Show info in Edit mode
        holder.password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the navigation here
                Intent intent = new Intent(context, AddingNewElements.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("caseFlag", 1); // Pass the position parameter
                intent.putExtra("placetolog", ""+items.get(holder.getAdapterPosition()).getPlaceToLog());
                intent.putExtra("username", ""+items.get(holder.getAdapterPosition()).getEmailUsername());
                intent.putExtra("password", ""+items.get(holder.getAdapterPosition()).getPassword());
                intent.putExtra("position", holder.getAdapterPosition());

                context.startActivity(intent);
            }
        });
        //RecyclerView recyclerView = findViewById(R.id.item_view);
        // Show password to certain field
        holder.btnViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyViewHolder clickedViewHolder = holder;
         //       Log.d("RAZGRANICHITEL","----------------------------");
                clickedViewHolder.password.setText(items.get(holder.getAdapterPosition()).getPassword());

                for(int i = 0; i < getItemCount(); i++){
                    if(holder.getAdapterPosition() != i){


         //               Log.d("index",""+i);
        //                Log.d("HidePassword",hideItemPassword.get(i));
        //                Log.d("Password",items.get(i).getPassword());
                       // holder.password.setText(hideItemPassword.get(i));
                        notifyItemChanged(i);
                        //items.get(i).setPassword(hidePassword);
                        /*StorageInfo recyclerViewHolder = new StorageInfo();
                        RecyclerView recyclerView = recyclerViewHolder.getRecyclerView();

                        MyViewHolder viewHolderElement = (MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        viewHolderElement.password.setText(hideItemPassword.get(i));*/

                    }


                }
        //        Log.d("holder.getAdapterPosition()=",""+holder.getAdapterPosition());

            }
        });

        // Show info in Edit mode
        holder.btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the navigation here
                Intent intent = new Intent(context, AddingNewElements.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("caseFlag", 1); // Pass the position parameter
                intent.putExtra("placetolog", ""+items.get(holder.getAdapterPosition()).getPlaceToLog());
                intent.putExtra("username", ""+items.get(holder.getAdapterPosition()).getEmailUsername());
                intent.putExtra("password", ""+items.get(holder.getAdapterPosition()).getPassword());
                intent.putExtra("position", holder.getAdapterPosition());

                context.startActivity(intent);
            }
        });

        // Delete info
        holder.btnDeleteInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the pop-up window layout
//                Log.d("POP_UP_WINDOW","minava");
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Deleting contents")
                        .setMessage("Are you sure you want to delete the information about " + holder.placeToLog.getText().toString() + " ?")
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Log.d("index Item=",""+holder.getAdapterPosition());
                                items.remove(holder.getAdapterPosition());
                                try{
                                    String filepath = "MyDirs";
                                    File file = new File(context.getExternalFilesDir(filepath), "Information.txt");
                                    BufferedReader reader = new BufferedReader(new FileReader(file));
                                    StringBuilder fileContents = new StringBuilder();
                                    String currentLine;
                                    int lineNumber = 0;
                                    while((currentLine= reader.readLine())!=null){
                                        if(holder.getAdapterPosition() == lineNumber){
                                            lineNumber++;
                                            continue;
                                        }

                                        fileContents.append(currentLine).append("\n");
                                        lineNumber++;
                                    }
                                    reader.close();

                                    // Write the updated contents back to the file
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                                    writer.write(fileContents.toString());
                                    writer.close();

                                }catch (IOException e){

                                }
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //dialog.cancel();
                            }
                        });
//                Log.d("context",""+context);
                builder.show();

                // Find views and set click listener for any buttons or views inside the pop-up window

                // Show the pop-up window
 //               Log.d("POP_UP_WINDOW","minava");
 //               Log.d("POP_UP_WINDOW","TRQBVA DA STANE");
                //popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}

