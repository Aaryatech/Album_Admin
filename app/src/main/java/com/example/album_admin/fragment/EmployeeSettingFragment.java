package com.example.album_admin.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.album_admin.R;
import com.example.album_admin.adapter.EmployeeAdapter;
import com.example.album_admin.adapter.EmployeeListAdapter;
import com.example.album_admin.constants.Constants;
import com.example.album_admin.model.Employee;
import com.example.album_admin.model.Info;
import com.example.album_admin.model.SettingValues;
import com.example.album_admin.util.CommonDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeSettingFragment extends Fragment implements View.OnClickListener{

    public TextView tvEmp,tvAdmin,tvSup;
    public Button btnSubmit;
    public RecyclerView recyclerViewEmp,recyclerViewSup,recyclerViewAdmin;
    ArrayList<Employee> empList = new ArrayList<>();
    ArrayList<Employee> supList = new ArrayList<>();
    ArrayList<Employee> adminList = new ArrayList<>();
    String strEmpId,strSupId,strAdminId;
     CommonDialog commonDialog,commonDialog1;

    ArrayList<Employee> empArray = new ArrayList<>();
    ArrayList<Employee> empSubArray = new ArrayList<>();
    ArrayList<Employee> empAdminArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employee_setting, container, false);
        getActivity().setTitle("Employee Setting List");

        tvEmp=(TextView)view.findViewById(R.id.tvEmp);
        tvAdmin=(TextView)view.findViewById(R.id.tvAdmin);
        tvSup=(TextView)view.findViewById(R.id.tvSup);
        btnSubmit=(Button) view.findViewById(R.id.btnSubmit);
        recyclerViewEmp=(RecyclerView) view.findViewById(R.id.recyclerViewEmp);
        recyclerViewSup=(RecyclerView) view.findViewById(R.id.recyclerViewSup);
        recyclerViewAdmin=(RecyclerView) view.findViewById(R.id.recyclerViewAdmin);

        getEmployee();

        tvEmp.setOnClickListener(this);
        tvSup.setOnClickListener(this);
        tvAdmin.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        return view;
    }

    private void getAdminEmployee(int settingId) {
        Log.e("PARAMETER","              SETTING ID         "+settingId);
        if (Constants.isOnline(getActivity())) {
//            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<SettingValues> listCall = Constants.myInterface.getSettingValueById(settingId);
            listCall.enqueue(new Callback<SettingValues>() {
                @Override
                public void onResponse(Call<SettingValues> call, Response<SettingValues> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("ADMIN LIST : ", " -----------------------------------ADMIN LIST---------------------------- " + response.body());
                           SettingValues settingValues=response.body();
                           strAdminId=settingValues.getSettingValue1();

                            List<String> list = Arrays.asList(settingValues.getSettingValue1().split(","));
                            Log.e("LIST","-------------------------------------"+list);
                            Log.e("EMP LIST","-------------------------------------"+adminList);


                            for(int i=0;i<adminList.size();i++)
                            {
                                Log.e("For","--------------------iiiiiiiiiii-----------------"+adminList.get(i));
                                for(int j=0;j<list.size();j++)
                                {
                                    Log.e("For","--------------------jjjjjjjjjjj-----------------"+list.get(j));
                                    if(adminList.get(i).getEmpId()==Integer.parseInt(list.get(j)))
                                    {
                                        Log.e("If","------------------------------------------"+adminList.get(i).getEmpId());
                                        empAdminArray.add(adminList.get(i));
                                        break;
                                    }
                                }

                            }

                            Log.e("ADMIN : ", " -----------------------------------SELECTED ADMIN---------------------------- " + empAdminArray);

                            EmployeeListAdapter empAdapter = new EmployeeListAdapter(empAdminArray, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewAdmin.setLayoutManager(mLayoutManager);
                            recyclerViewAdmin.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewAdmin.setAdapter(empAdapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SettingValues> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tvEmp)
        {
            new EmpDialog(getActivity(),"Emp").show();
        }else if(v.getId()==R.id.tvSup)
        {
            new EmpDialog(getActivity(),"Superwiser").show();
        }else if(v.getId()==R.id.tvAdmin)
        {
            new EmpDialog(getActivity(),"Admin").show();
        }else if(v.getId()==R.id.btnSubmit)
        {
                saveAdmin(3,strAdminId);
        }
    }

    private void saveAdmin(int settingId, String strAdminId) {
        Log.e("PARAMETER","             SETTING ID      "+settingId+"              VALUES         "+strAdminId);
        if (Constants.isOnline(getActivity())) {
            commonDialog1 = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog1.show();

            Call<Info> listCall = Constants.myInterface.updateSettingValueById(settingId,strAdminId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Sucess   : ", "-----------------ADMIN-----------------------" + response.body());
                            saveSup(4,strSupId);

                            commonDialog1.dismiss();
                        } else {
                            commonDialog1.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog1.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSup(int settingId, String strSupId) {
        Log.e("PARAMETER","             SETTING ID      "+settingId+"              VALUES         "+strSupId);
        if (Constants.isOnline(getActivity())) {
//            commonDialog1 = new CommonDialog(getActivity(), "Loading", "Please Wait...");
//            commonDialog1.show();

            Call<Info> listCall = Constants.myInterface.updateSettingValueById(settingId,strSupId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Sucess   : ", "-----------------SUP-----------------------" + response.body());
                            saveEmp(5,strEmpId);

                            commonDialog1.dismiss();
                        } else {
                            commonDialog1.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog1.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveEmp(int settingId, String strEmpId) {
        Log.e("PARAMETER","             SETTING ID      "+settingId+"              VALUES         "+strEmpId);
        if (Constants.isOnline(getActivity())) {
//            commonDialog1 = new CommonDialog(getActivity(), "Loading", "Please Wait...");
//            commonDialog1.show();

            Call<Info> listCall = Constants.myInterface.updateSettingValueById(settingId,strEmpId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Sucess   : ", "-----------------EMP-----------------------" + response.body());
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new EmployeeSettingFragment(), "EnquiryFragment");
                            ft.commit();
                            commonDialog1.dismiss();
                        } else {
                            commonDialog1.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog1.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getEmployee() {
        if (Constants.isOnline(getActivity())) {
             commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Employee>> listCall = Constants.myInterface1.allEmployees();
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " -----------------------------------EMPLOYEE LIST---------------------------- " + response.body());
                            empList.clear();
                            supList.clear();
                            adminList.clear();
                            empList = response.body();
                            supList = response.body();
                            adminList = response.body();

                            for(int i=0;i<empList.size();i++)
                            {
                                empList.get(i).setChecked(false);

                            }
                            for(int i=0;i<supList.size();i++)
                            {
                                supList.get(i).setChecked(false);
                            }
                            for(int i=0;i<adminList.size();i++)
                            {
                                adminList.get(i).setChecked(false);
                            }

                            getAdminEmployee(3);
                            getSupEmployee(4);
                            getEmp(5);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEmp(int settingId) {
        Log.e("PARAMETER","              SETTING ID         "+settingId);
        if (Constants.isOnline(getActivity())) {
//            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<SettingValues> listCall = Constants.myInterface.getSettingValueById(settingId);
            listCall.enqueue(new Callback<SettingValues>() {
                @Override
                public void onResponse(Call<SettingValues> call, Response<SettingValues> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("ADMIN LIST : ", " -----------------------------------ADMIN LIST---------------------------- " + response.body());
                            SettingValues settingValues=response.body();
                            strEmpId=settingValues.getSettingValue1();

                            List<String> list = Arrays.asList(settingValues.getSettingValue1().split(","));
                            Log.e("LIST","-------------------------------------"+list);
                            Log.e("EMP LIST","-------------------------------------"+adminList);


                            for(int i=0;i<adminList.size();i++)
                            {
                                Log.e("For","--------------------iiiiiiiiiii-----------------"+adminList.get(i));
                                for(int j=0;j<list.size();j++)
                                {
                                    Log.e("For","--------------------jjjjjjjjjjj-----------------"+list.get(j));
                                    if(adminList.get(i).getEmpId()==Integer.parseInt(list.get(j)))
                                    {
                                        Log.e("If","------------------------------------------"+adminList.get(i).getEmpId());
                                        empArray.add(adminList.get(i));
                                        break;
                                    }
                                }

                            }

                            Log.e("EMP : ", " -----------------------------------SELECTED EMP---------------------------- " + empAdminArray);

                            EmployeeListAdapter empAdapter = new EmployeeListAdapter(empArray, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewEmp.setLayoutManager(mLayoutManager);
                            recyclerViewEmp.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewEmp.setAdapter(empAdapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SettingValues> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getSupEmployee(int settingId) {
        Log.e("PARAMETER","              SETTING ID         "+settingId);
        if (Constants.isOnline(getActivity())) {
//            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<SettingValues> listCall = Constants.myInterface.getSettingValueById(settingId);
            listCall.enqueue(new Callback<SettingValues>() {
                @Override
                public void onResponse(Call<SettingValues> call, Response<SettingValues> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("ADMIN LIST : ", " -----------------------------------ADMIN LIST---------------------------- " + response.body());
                            SettingValues settingValues=response.body();
                            strSupId=settingValues.getSettingValue1();
                            List<String> list = Arrays.asList(settingValues.getSettingValue1().split(","));
                            Log.e("LIST","-------------------------------------"+list);
                            Log.e("EMP LIST","-------------------------------------"+adminList);


                            for(int i=0;i<adminList.size();i++)
                            {
                                Log.e("For","--------------------iiiiiiiiiii-----------------"+adminList.get(i));
                                for(int j=0;j<list.size();j++)
                                {
                                    Log.e("For","--------------------jjjjjjjjjjj-----------------"+list.get(j));
                                    if(adminList.get(i).getEmpId()==Integer.parseInt(list.get(j)))
                                    {
                                        Log.e("If","------------------------------------------"+adminList.get(i).getEmpId());
                                        empSubArray.add(adminList.get(i));
                                        break;
                                    }
                                }

                            }

                            Log.e("SUP : ", " -----------------------------------SELECTED SUP---------------------------- " + empAdminArray);

                            EmployeeListAdapter empAdapter = new EmployeeListAdapter(empSubArray, getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewSup.setLayoutManager(mLayoutManager);
                            recyclerViewSup.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewSup.setAdapter(empAdapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SettingValues> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private class EmpDialog extends Dialog {
        public Button btnCancel,btnSubmit;
        public TextView tv_heading;
        public RecyclerView recyclerViewDialog;
        EmployeeAdapter adapter;
        String strDesig;

        public EmpDialog(@NonNull Context context,String strDesig) {
            super(context);
            this.strDesig = strDesig;
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_emp);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btn_Cancel);
            btnSubmit = (Button) findViewById(R.id.btn_Submit);

            tv_heading = (TextView) findViewById(R.id.tv_heading);
            recyclerViewDialog = (RecyclerView) findViewById(R.id.recyclerViewDialog);


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();

                        if(strDesig.equalsIgnoreCase("Emp"))
                        {
                            ArrayList<Employee> empArray = new ArrayList<>();
                             ArrayList<Integer> empIdArray = new ArrayList<>();
                            if(empList!=null) {
                                for (int i = 0; i < empList.size(); i++) {
                                    if (empList.get(i).getChecked()) {
                                        empArray.add(empList.get(i));
                                        empIdArray.add(empList.get(i).getEmpId());
                                    }
                                }

                                String empIds=empIdArray.toString().trim();
                                Log.e("ASSIGN EMP ID","---------------------------------"+empIds);

                               String empId = ""+empIds.substring(1, empIds.length()-1).replace("][", ",")+"";
                               strEmpId= empId.replaceAll("\\s+","");

                                Log.e("ASSIGN EMP ID STRING","---------------------------------"+strEmpId);

                                EmployeeListAdapter empAdapter = new EmployeeListAdapter(empArray, getContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerViewEmp.setLayoutManager(mLayoutManager);
                                recyclerViewEmp.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewEmp.setAdapter(empAdapter);
                            }
                        }else if(strDesig.equalsIgnoreCase("Superwiser"))
                        {
                            ArrayList<Employee> supArray = new ArrayList<>();
                            ArrayList<Integer> supIdArray = new ArrayList<>();
                            if(supList!=null) {
                                for (int i = 0; i < supList.size(); i++) {
                                    if (supList.get(i).getChecked()) {
                                        supArray.add(supList.get(i));
                                        supIdArray.add(supList.get(i).getEmpId());
                                    }
                                }

                                String empIds=supIdArray.toString().trim();
                                Log.e("ASSIGN SUP ID","---------------------------------"+empIds);

                                String supId = ""+empIds.substring(1, empIds.length()-1).replace("][", ",")+"";
                                strSupId= supId.replaceAll("\\s+","");
                                Log.e("ASSIGN SUP ID STRING","---------------------------------"+strSupId);

                                EmployeeListAdapter empAdapter = new EmployeeListAdapter(supArray, getContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerViewSup.setLayoutManager(mLayoutManager);
                                recyclerViewSup.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewSup.setAdapter(empAdapter);
                            }
                        }else if(strDesig.equalsIgnoreCase("Admin"))
                        {
                            ArrayList<Employee> adminArray = new ArrayList<>();
                            ArrayList<Integer> adminIdArray = new ArrayList<>();
                            if(adminList!=null) {
                                for (int i = 0; i < adminList.size(); i++) {
                                    if (adminList.get(i).getChecked()) {
                                        adminArray.add(adminList.get(i));
                                        adminIdArray.add(adminList.get(i).getEmpId());
                                    }
                                }

                                String empIds=adminIdArray.toString().trim();
                                Log.e("ASSIGN ADMIN ID","---------------------------------"+empIds);

                                String adminId = ""+empIds.substring(1, empIds.length()-1).replace("][", ",")+"";
                                strAdminId= adminId.replaceAll("\\s+","");
                                Log.e("ASSIGN ADMIN ID STRING","---------------------------------"+strAdminId);

                                EmployeeListAdapter empAdapter = new EmployeeListAdapter(adminArray, getContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerViewAdmin.setLayoutManager(mLayoutManager);
                                recyclerViewAdmin.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewAdmin.setAdapter(empAdapter);
                            }
                        }

                    }


            });


            if(strDesig.equalsIgnoreCase("Emp")) {

                for(int i=0;i<empList.size();i++)
                {
                    empList.get(i).setChecked(false);

                    for(int j=0;j<empArray.size();j++)
                    {
                        if(empArray.get(j).getEmpId()==empList.get(i).getEmpId())
                        {
                            empList.get(i).setChecked(true);
                        }
                    }
                }

                Log.e("Emp","---------------------------------------------"+empList);
                adapter = new EmployeeAdapter(empList, getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewDialog.setLayoutManager(mLayoutManager);
                recyclerViewDialog.setItemAnimator(new DefaultItemAnimator());
                recyclerViewDialog.setAdapter(adapter);
            }else if(strDesig.equalsIgnoreCase("Superwiser")) {

                for(int i=0;i<supList.size();i++)
                {
                    supList.get(i).setChecked(false);
                    for(int j=0;j<empSubArray.size();j++)
                    {
                        if(empSubArray.get(j).getEmpId()==empList.get(i).getEmpId())
                        {
                            empList.get(i).setChecked(true);
                        }
                    }
                }
                Log.e("Sup","---------------------------------------------"+supList);
                adapter = new EmployeeAdapter(supList, getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewDialog.setLayoutManager(mLayoutManager);
                recyclerViewDialog.setItemAnimator(new DefaultItemAnimator());
                recyclerViewDialog.setAdapter(adapter);
            }else if(strDesig.equalsIgnoreCase("Admin"))
            {

                for(int i=0;i<adminList.size();i++)
                {
                    adminList.get(i).setChecked(false);
                    for(int j=0;j<empAdminArray.size();j++)
                    {
                        if(empAdminArray.get(j).getEmpId()==empList.get(i).getEmpId())
                        {
                            empList.get(i).setChecked(true);
                        }
                    }
                }

                Log.e("Admin","---------------------------------------------"+adminList);
                adapter = new EmployeeAdapter(adminList, getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewDialog.setLayoutManager(mLayoutManager);
                recyclerViewDialog.setItemAnimator(new DefaultItemAnimator());
                recyclerViewDialog.setAdapter(adapter);
            }

        }
    }

}
