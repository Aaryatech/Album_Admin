package com.example.album_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.album_admin.R;
import com.example.album_admin.model.Menu;

import java.util.ArrayList;

public class MenuDialogAdapter  extends RecyclerView.Adapter<MenuDialogAdapter.MyViewHolder> {

    private ArrayList<Menu> menuList;
    private Context context;


    public MenuDialogAdapter(ArrayList<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuDialogAdapter.MyViewHolder holder, int position) {
        final Menu model = menuList.get(position);
        holder.tvName.setText(model.getMenuTitle());



        holder.checkBox.setChecked(model.isChecked());

        holder.checkBox.setTag(model);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Menu menu = (Menu) cb.getTag();

                menu.setChecked(cb.isChecked());
                Log.e("OnClick","----------------------------------ADAPTER---------------------------------"+menuList);
                model.setChecked(cb.isChecked());

            }
        });


    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress;
        public CheckBox checkBox;
        public LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            checkBox = itemView.findViewById(R.id.checkBox);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
