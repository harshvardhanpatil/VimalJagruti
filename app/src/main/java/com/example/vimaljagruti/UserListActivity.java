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
import com.example.vimaljagruti.adapters.UserListAdapter;
import com.example.vimaljagruti.models.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    UserListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = findViewById(R.id.users_recycler);
        List<UserBean> list = new ArrayList<>();
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
        final List<UserBean> list1 = new ArrayList<>();
        String login_url = getResources().getString(R.string.DB_URL);
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("MODE","RETRIEVE");
            jsonObject.put("ID","*");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest= new JsonObjectRequest(Request.Method.POST, login_url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE",response.toString());

                    if(response.getString("COUNT").equals("-1"))
                    {
                        Toast.makeText(getApplicationContext(),"Maintanence Issue Please Contact Developer !!!",Toast.LENGTH_LONG).show();
                        pullToRefresh.setRefreshing(false);

                    }
                    else if(response.getString("COUNT").equals("0"))
                    {
                        Toast.makeText(getApplicationContext(),"No Entries yet !!!",Toast.LENGTH_LONG).show();
                        pullToRefresh.setRefreshing(false);
                    }
                    else
                    {
                        int count = Integer.parseInt(response.getString("COUNT"));
                        JSONArray array = response.getJSONArray("MESSAGE");
                        for(int i=0;i<count;i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            UserBean entity = new UserBean(
                                    object.getString("USERNAME"),
                                    object.getString("PHONE"),
                                    object.getString("REGISTRATION"),
                                    object.getString("EMAIL")
                                    );
                            list1.add(entity);
                        }
                        adapter=new UserListAdapter(list1,getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        pullToRefresh.setRefreshing(false);

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
