package com.example.vimaljagruti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CardView createJobCard,createBill,veiwJobCard,veiwBill;

    private static final int CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);

        createJobCard=findViewById(R.id.createJobCard);
        createBill=findViewById(R.id.createBill);
        veiwBill=findViewById(R.id.veiwBill);
        veiwJobCard=findViewById(R.id.veiwJobCard);

        createBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SelectOptionActivity.class);
                startActivity(intent);
            }
        });

        createJobCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SelectOptionActivity.class);
                startActivity(intent);
            }
        });

        veiwBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        veiwJobCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ScanQRActivity.class);
                startActivity(intent);

            }
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);

            // Requesting the permission
            //return false;
        }
        else {
//            Toast.makeText(MainActivity.this,
//                    "Permission already granted",
//                    Toast.LENGTH_SHORT)
//                    .show();
            //return true;
        }
    }
    public void getPermission(String permission, int requestCode)
    {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] { permission },
                requestCode);
    }
}
