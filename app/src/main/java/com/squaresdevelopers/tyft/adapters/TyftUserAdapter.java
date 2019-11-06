package com.squaresdevelopers.tyft.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.AvailableUserModel;

import java.util.List;

public class TyftUserAdapter extends RecyclerView.Adapter<TyftUserAdapter.MyViewHolder>{
    List<AvailableUserModel> dailyList;
    Context context;

    public TyftUserAdapter(Context context, List<AvailableUserModel> dailyList) {
        this.context = context;
        this.dailyList = dailyList;
    }


    @NonNull
    @Override
    public TyftUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_users_layout, parent, false);

        return new TyftUserAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final TyftUserAdapter.MyViewHolder viewHolder, final int position) {
        final AvailableUserModel model = dailyList.get(position);

        if(model.getCheckLocation().equals("1")){
            viewHolder.tvTruckName.setText(model.getName());
            viewHolder.tvEmail.setText(model.getEmail());
            Glide.with(context).load(model.getImage1()).into(viewHolder.ivOne);
            Glide.with(context).load(model.getImage2()).into(viewHolder.ivTwo);
        }
        else {
            viewHolder.layout.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOne,ivTwo;
        TextView tvTruckName,tvEmail;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOne = itemView.findViewById(R.id.iv_one);
            ivTwo = itemView.findViewById(R.id.iv_two);
            tvTruckName = itemView.findViewById(R.id.tv_truck_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}
