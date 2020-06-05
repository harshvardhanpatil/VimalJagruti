package com.example.vimaljagruti;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobCardActivity extends AppCompatActivity {
    EditText price,problem;
    Button button,createJobCard;
    ListView listView;
    Double total=0.0;
    TextView textView;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            phone = extras.getString("PHONE");
        }
        else
        {
            Intent intent=new Intent(getApplicationContext(), CarAvailability.class);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_card);
        price=findViewById(R.id.price);
        problem=findViewById(R.id.complaint);
        button=findViewById(R.id.addtojobcard);
        listView=findViewById(R.id.listofcomplaints);
        textView=findViewById(R.id.setTotal);
        createJobCard=findViewById(R.id.createJobCard);
        final List<String> prices=new ArrayList<>();
        final List<String> problems=new ArrayList<String>();
        final List<String> merge=new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, merge);
        listView.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pr=price.getText().toString().trim();
                String  prob=problem.getText().toString().trim();
                problems.add(prob);
                prices.add(pr);
                merge.add(prob+" - "+pr);
                arrayAdapter.notifyDataSetChanged();
                price.setText("");
                problem.setText("");
                total+=(Double.parseDouble(pr));
                textView.setText("TOTAL :"+total);



                //arrayAdapter.add(prob+" - "+pr);




            }
        });


        // Push to db
        createJobCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createJobCard.setEnabled(false);
                int flag=0;
                String issues="";
                String amount="";
                for (int i = 0; i < problems.size(); i++) {
                    //System.out.println(crunchifyList.get(i));
                    if(flag==0){
                        issues=problems.get(i);
                        amount=prices.get(i);
                        flag=1;

                    }
                    else{
                        issues=issues+","+problems.get(i);
                        amount=amount+","+prices.get(i);
                    }
                }





                JSONObject jsonObject=new JSONObject();

                try {
                    JSONObject jsonObject1= new JSONObject();
                    jsonObject1.put("ISSUES",issues);
                    jsonObject1.put("PRICES",amount);
                    jsonObject1.put("TOTAL",total.toString());

                    jsonObject.put("MODE","JOBCARD_CREATE");
                    jsonObject.put("PHONE",phone);
                    jsonObject.put("PARTICULARS",jsonObject1);
                    Log.w("DEBUG",jsonObject.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String login_url = getResources().getString(R.string.DB_URL);

                // req to push
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
                                createJobCard.setText("Done \uD83D\uDC4D");
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        createJobCard.setText("CREATE JOB CARD");

                                    }
                                },2000);
                                System.out.println("Check point 2");
                                createJobCard.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Entry Added !!!",Toast.LENGTH_LONG).show();
                                ///Creation success


                            }

                            else
                            {
                                System.out.println("Check point 2.1");
                                Toast.makeText(getApplicationContext(),"Failed!! Contact Administrator !!!",Toast.LENGTH_LONG).show();
                                createJobCard.setEnabled(true);
                                createJobCard.setText("CREATE JOB CARD");

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.toString());
                        createJobCard.setText("SUBMIT");

                    }
                }
                );


                requestQueue.add(objectRequest);



            }
        });






    }
}
