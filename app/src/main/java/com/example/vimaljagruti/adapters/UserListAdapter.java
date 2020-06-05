package com.example.vimaljagruti.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimaljagruti.R;
import com.example.vimaljagruti.models.UserBean;
import com.example.vimaljagruti.viewholders.UserListHolder;

import java.util.Collections;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListHolder> {

    List<UserBean> list = Collections.emptyList();
    Context context;
    int i=0;



    public UserListAdapter(List<UserBean> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public UserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.list_card,parent,false);
        UserListHolder viewHolder = new UserListHolder(photoView,this.context);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListHolder holder, int position) {
        holder.phone.setText(list.get(position).getPhone()+"");
        holder.registration.setText(list.get(position).getRegistration()+"");
        holder.username.setText(list.get(position).getUsername()+"");
        holder.email.setText(list.get(position).getEmail()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
