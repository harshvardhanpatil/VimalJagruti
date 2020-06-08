package com.example.vimaljagruti.viewholders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimaljagruti.JobCardActivity;
import com.example.vimaljagruti.R;
import com.example.vimaljagruti.models.JobcardBean;
import com.example.vimaljagruti.models.UserBean;

public class JobcardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView phone,username,jcid,total;
    public Context context;
    public UserBean user;
    public JobcardBean jobcard;

    public JobcardViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        phone = itemView.findViewById(R.id.et_phone);
        username = itemView.findViewById(R.id.et_username);
        jcid = itemView.findViewById(R.id.et_jcid);
        total = itemView.findViewById(R.id.et_total);

        itemView.setOnClickListener(this);
        this.context = context;





    }

    @Override
    public void onClick(View v) {


        //
//        Intent i = new Intent(v.getContext(), JobCardActivity.class)
//                .putExtra("PHONE",phone.getText())
//                .putExtra("NAME",username.getText())
//                .putExtra("EMAIL",user.getEmail())
//                .putExtra("REGISTRATION",user.getRegistration())
//                .putExtra("JCID",jcid.getText())
//                .putExtra("DATE",jobcard.getDATE())
//                .putExtra("ISSUES",jobcard.getISSUES())
//                .putExtra("PRICES",jobcard.getPRICES())
//                .putExtra("TOTAL",total.getText());
//
//
//
//
//        v.getContext().startActivity(i);
        Toast.makeText(context,jcid.getText().toString(),Toast.LENGTH_LONG).show();

    }
}
