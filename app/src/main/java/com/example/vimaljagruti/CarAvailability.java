package com.example.vimaljagruti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CarAvailability extends AppCompatActivity {
    Button createNew,scanByQr;
    EditText customRegistraionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_availability);
        //createNew=findViewById(R.id.createNew);
        scanByQr=findViewById(R.id.scanQR);

        //customRegistraionEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        scanByQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
