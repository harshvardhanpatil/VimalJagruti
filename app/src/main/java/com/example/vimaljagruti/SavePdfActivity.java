package com.example.vimaljagruti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

public class SavePdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_pdf);
        Bundle extras = getIntent().getExtras();

        String data;
        if (extras != null) {
            data = extras.getString("DATA");
        }
        else
        {
            Intent intent=new Intent(getApplicationContext(), CarAvailability.class);
            startActivity(intent);
            finish();
        }
        JSONObject jsonObject=new JSONObject();


    }
}
