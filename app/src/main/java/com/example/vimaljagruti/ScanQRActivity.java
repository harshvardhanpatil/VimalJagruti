package com.example.vimaljagruti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    ZXingScannerView veiw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        veiw=new ZXingScannerView(this);
        setContentView(veiw);
    }

    @Override
    public void handleResult(Result result) {
        Toast toast=Toast. makeText(getApplicationContext(),result.getText(),Toast. LENGTH_SHORT);
        toast.show();
    }
    @Override
    protected void onPause(){
        super.onPause();
        veiw.stopCamera();

    }
    @Override
    protected  void onResume()
    {
        super.onResume();
        veiw.setResultHandler(this);
        veiw.startCamera();

    }




}
