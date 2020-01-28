package com.example.album_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.album_admin.R;
import com.example.album_admin.model.SpecialCake;

import java.util.ArrayList;

public class SpecialCakeDialogAdapter extends RecyclerView.Adapter<SpecialCakeDialogAdapter.MyViewHolder> {

    private ArrayList<SpecialCake> spList;
    private Context context;

    public SpecialCakeDialogAdapter(ArrayList<SpecialCake> spList, Context context) {
        this.spList = spList;
        this.context = context;
    }

    @NonNull
    @Override
    public SpecialCakeDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speical_cake_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialCakeDialogAdapter.MyViewHolder holder, int position) {
        final SpecialCake model = spList.get(position);

        holder.tvName.setText(model.getSpCode());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA");
                customerDataIntent.putExtra("name", model.getSpCode());
                customerDataIntent.putExtra("id", model.getSpId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return spList.size();
    }

    public void updateList(ArrayList<SpecialCake> list) {
        spList = list;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress;
        public LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
