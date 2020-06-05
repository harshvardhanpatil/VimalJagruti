package com.example.vimaljagruti;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {
    EditText Cname,Regno,Email,Mobile;
    Button Submit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_registration);
        Cname=findViewById(R.id.cname);
        Regno=findViewById(R.id.regno);
        Email=findViewById(R.id.email);
        Mobile =findViewById(R.id.mobile);
        Submit=findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit.setEnabled(false);
                Submit.setText("Creating");

                String name=Cname.getText().toString().trim();
                String mob=Mobile.getText().toString().trim();
                String regno=Regno.getText().toString().trim();
                String email=Email.getText().toString().trim();

                System.out.println("Check point 0.0");
                //Durgesh's code
                String login_url = getResources().getString(R.string.DB_URL);
                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("MODE","CREATE");
                    jsonObject.put("PHONE",mob);
                    jsonObject.put("REGISTRATION",regno);
                    jsonObject.put("USERNAME",name);
                    jsonObject.put("EMAIL",email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("Check point 0");



                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                System.out.println("Check point 0.1");
                JsonObjectRequest objectRequest= new JsonObjectRequest(Request.Method.POST, login_url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Check point 1");

                            Log.e("RESPONSE",response.toString());

                            if(response.getString("MESSAGE").equals("SUCCESS"))
                            {
                                Submit.setText("Done \uD83D\uDC4D");
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Submit.setText("SUBMIT");

                                    }
                                },2000);
                                System.out.println("Check point 2");
                                Submit.setEnabled(true);
                                Toast.makeText(context,"Entry Added !!!",Toast.LENGTH_LONG).show();
                                ///Creation success


                            }

                            else
                            {
                                System.out.println("Check point 2.1");
                                Toast.makeText(context,"Failed!! Contact Administrator !!!",Toast.LENGTH_LONG).show();
                                Submit.setEnabled(true);
                                Submit.setText("SUBMIT");

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.toString());
                        Submit.setText("SUBMIT");

                    }
                }
                );


                requestQueue.add(objectRequest);













            }
        });
    }
}
