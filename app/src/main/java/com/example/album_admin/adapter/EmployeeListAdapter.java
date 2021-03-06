package com.example.album_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.album_admin.R;
import com.example.album_admin.model.Employee;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder> {
    private ArrayList<Employee> empList;
    private Context context;

    public EmployeeListAdapter(ArrayList<Employee> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.selected_employee_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.MyViewHolder myViewHolder, int i) {
        Employee model=empList.get(i);
        myViewHolder.tv_Emp.setText(""+model.getEmpFname()+" "+model.getEmpMname()+" "+model.getEmpSname());

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Emp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Emp=itemView.findViewById(R.id.tv_Emp);
        }
    }
}
