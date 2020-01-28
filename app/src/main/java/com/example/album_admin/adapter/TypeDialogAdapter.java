package com.example.album_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.album_admin.R;
import com.example.album_admin.model.Type;

import java.util.ArrayList;

public class TypeDialogAdapter extends RecyclerView.Adapter<TypeDialogAdapter.MyViewHolder> {
    private ArrayList<Type> typeList;
    private Context context;


    public TypeDialogAdapter(ArrayList<Type> typeList, Context context) {
        this.typeList = typeList;
        this.context = context;
    }

    @NonNull
    @Override
    public TypeDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speical_cake_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeDialogAdapter.MyViewHolder holder, int position) {
        final Type model = typeList.get(position);

        holder.tvName.setText(model.getTypeName());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerDataIntent = new Intent();
                customerDataIntent.setAction("CUSTOMER_DATA_TYPE");
                customerDataIntent.putExtra("name", model.getTypeName());
                customerDataIntent.putExtra("id", model.getTypeId());
                customerDataIntent.putExtra("frId", model.getFrIds());
                Log.e("Fr Id","-------------------------Adapter-------------------------"+model.getFrIds());
                LocalBroadcastManager.getInstance(context).sendBroadcast(customerDataIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public void updateList(ArrayList<Type> list) {
        typeList = list;
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
