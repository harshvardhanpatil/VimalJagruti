package com.example.vimaljagruti.viewholders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimaljagruti.CarAvailability;
import com.example.vimaljagruti.JobCardActivity;
import com.example.vimaljagruti.R;

public class UserListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView phone,email,username,registration;
    public Context context;

    public UserListHolder(@NonNull View itemView, Context context) {
        super(itemView);
        phone = itemView.findViewById(R.id.et_phone);
        username = itemView.findViewById(R.id.et_name);
        email = itemView.findViewById(R.id.et_email);
        registration = itemView.findViewById(R.id.et_registration);

        itemView.setOnClickListener(this);
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        //Toast.makeText(context,"here is it",Toast.LENGTH_LONG).show();

        //Add The Activity Which You want to go
        Intent i = new Intent(v.getContext(), JobCardActivity.class).putExtra("PHONE",phone.getText());
        v.getContext().startActivity(i);
        Toast.makeText(context,phone.getText().toString(),Toast.LENGTH_LONG).show();
    }
}
