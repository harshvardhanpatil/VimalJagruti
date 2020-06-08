package com.example.vimaljagruti.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimaljagruti.R;
import com.example.vimaljagruti.models.JobcardBean;
import com.example.vimaljagruti.models.UserBean;
import com.example.vimaljagruti.viewholders.JobcardViewHolder;
import com.example.vimaljagruti.viewholders.UserListHolder;

import java.util.Collections;
import java.util.List;

public class JobcardAdapter extends RecyclerView.Adapter<JobcardViewHolder> {
    List<UserBean> users = Collections.emptyList();
    List<JobcardBean> jobcards = Collections.emptyList();
    Context context;
    int i=0;

    public JobcardAdapter(List<UserBean> users, List<JobcardBean> jobcards, Context context) {
        this.users = users;
        this.jobcards = jobcards;
        this.context = context;
    }

    @NonNull
    @Override
    public JobcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.jobcard_list_card,parent,false);
        JobcardViewHolder viewHolder = new JobcardViewHolder(photoView,this.context);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobcardViewHolder holder, int position) {

        holder.phone.setText(jobcards.get(position).getPHONE()+"");
        holder.total.setText(jobcards.get(position).getTOTAL()+"");
        holder.username.setText(users.get(position).getUsername()+"");
        holder.jcid.setText(jobcards.get(position).getJCID()+"");
        holder.user = users.get(position);
        holder.jobcard = jobcards.get(position);

    }

    @Override
    public int getItemCount() {
       return users.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
