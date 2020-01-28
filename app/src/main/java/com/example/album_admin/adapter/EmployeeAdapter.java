package com.example.album_admin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.album_admin.R;
import com.example.album_admin.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {
    private ArrayList<Employee> empList;
    private Context context;

    public EmployeeAdapter(ArrayList<Employee> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.employee_list_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.MyViewHolder myViewHolder, final int i) {
        Employee model=empList.get(i);
        myViewHolder.tv_Emp.setText(""+model.getEmpFname()+" "+model.getEmpMname()+" "+model.getEmpSname());

        myViewHolder.checkBox.setChecked(empList.get(i).getChecked());

        myViewHolder.checkBox.setTag(empList.get(i));

        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Employee employee = (Employee) cb.getTag();

                employee.setChecked(cb.isChecked());
                empList.get(i).setChecked(cb.isChecked());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Emp;
        public CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Emp=itemView.findViewById(R.id.tv_Emp);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }
}
