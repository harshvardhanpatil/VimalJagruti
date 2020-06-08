package com.example.vimaljagruti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vimaljagruti.adapters.JobcardAdapter;
import com.example.vimaljagruti.adapters.UserListAdapter;
import com.example.vimaljagruti.models.JobcardBean;
import com.example.vimaljagruti.models.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewJobcardsActivity extends AppCompatActivity {
    SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    JobcardAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobcards);
        recyclerView = findViewById(R.id.jobcards_recycler);
        List<UserBean> users = new ArrayList<>();
        List<JobcardBean> jobcards = new ArrayList<>();
        getEntries();


        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEntries();
            }
        });
    }


    private void getEntries()
    {
     final    List<UserBean> users = new ArrayList<>();
     final   List<JobcardBean> jobcards = new ArrayList<>();
        String login_url = getResources().getString(R.string.DB_URL);
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("MODE","JOBCARD_RETRIEVE");
            jsonObject.put("JCID","*");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest= new JsonObjectRequest(Request.Method.POST, login_url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE",response.toString());

                    if(response.getInt("COUNT")==-1)
                    {
                        Toast.makeText(getApplicationContext(),"Maintanence Issue Please Contact Developer !!!",Toast.LENGTH_LONG).show();
                        pullToRefresh.setRefreshing(false);

                    }
                    else if(response.getInt("COUNT")==0)
                    {
                        Toast.makeText(getApplicationContext(),"No Entries yet !!!",Toast.LENGTH_LONG).show();
                        pullToRefresh.setRefreshing(false);
                    }
                    else
                    {
                        int count = Integer.parseInt(response.getString("COUNT"));
                        JSONArray array = response.getJSONArray("RESULT");
                        for(int i=0;i<count;i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            UserBean entity = new UserBean(
                                    object.getString("USERNAME"),
                                    object.getString("PHONE"),
                                    object.getString("REGISTRATION"),
                                    object.getString("EMAIL")
                            );
                            JobcardBean entity1 = new JobcardBean(object.getInt("JCID"),object.getString("DATE"),object.getString("PHONE"),object.getString("ISSUES"),object.getString("PRICE"),object.getString("TOTAL"));
                            users.add(entity);
                            jobcards.add(entity1);
                        }
                        adapter=new JobcardAdapter(users,jobcards,getApplicationContext());
                        Log.w("DEBUG ADAPTER","CHECK POINT 1");
                        recyclerView.setAdapter(adapter);
                        Log.w("DEBUG ADAPTER","CHECK POINT 2");
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        Log.w("DEBUG ADAPTER","CHECK POINT 3");
                        pullToRefresh.setRefreshing(false);
                        Log.w("DEBUG ADAPTER","CHECK POINT 4");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );


        requestQueue.add(objectRequest);


        return;
    }

}
