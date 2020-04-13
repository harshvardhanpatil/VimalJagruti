package com.example.vimaljagruti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionActivity extends AppCompatActivity {
    Button createNew,scanByQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        createNew=findViewById(R.id.createNew);
        scanByQr=findViewById(R.id.scanQR);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CreateJobCard.class);
                startActivity(intent);
            }
        });
        scanByQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ScanQRActivity.class);
                startActivity(intent);
            }
        });
    }
}
