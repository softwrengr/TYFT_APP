package com.squaresdevelopers.tyft.adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.AvailableUserModel;
import com.squaresdevelopers.tyft.views.tyft.ui.list.TruckDetailActivity;

import java.util.List;

public class TyftUserAdapter extends RecyclerView.Adapter<TyftUserAdapter.MyViewHolder> {
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

        viewHolder.tvTruckName.setText(model.getStrName());
        Glide.with(context).load(model.getStrImageOne()).into(viewHolder.ivOne);
        viewHolder.tvDate.setText("Will be avaiable on\n" + model.getDate());

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", model.getStrName());
                bundle.putString("image_one", model.getStrImageOne());
                bundle.putString("image_two", model.getStrImageTwo());
                bundle.putString("date", model.getDate());
                bundle.putString("start_time", model.getStartTime());
                bundle.putString("end_time", model.getEndTime());
                bundle.putString("lat", model.getStrLatitude());
                bundle.putString("lng", model.getStrLongitude());
                context.startActivity(new Intent(context, TruckDetailActivity.class).putExtras(bundle));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOne;
        TextView tvTruckName, tvDate;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOne = itemView.findViewById(R.id.iv_one);
            tvTruckName = itemView.findViewById(R.id.tv_truck_name);
            tvDate = itemView.findViewById(R.id.tv_truck_date);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}
