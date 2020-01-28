package com.example.album_admin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.example.album_admin.R;
import com.example.album_admin.fragment.EmployeeSettingFragment;
import com.example.album_admin.fragment.EnquiryFragment;
import com.example.album_admin.model.Login;
import com.example.album_admin.util.CustomSharedPreference;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity{
//        implements NavigationView.OnNavigationItemSelectedListener {
    String frId;
    String frName;
    Login loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        try{
//             frId= CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.frId);
//             frName= CustomSharedPreference.getString(MainActivity.this, CustomSharedPreference.frName);
//            Log.e("Fr Id ","--------------------------------Home Activity---------------------------------"+frId);
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        try {
            String userStr = CustomSharedPreference.getString(getApplication(), CustomSharedPreference.MAIN_KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUser);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (loginUser == null) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new EnquiryFragment(), "Exit");
        ft.commit();
    }

    @Override
    public void onBackPressed() {

      // super.onBackPressed();
        Fragment exit = getSupportFragmentManager().findFragmentByTag("Exit");
        Fragment enquiryFragment = getSupportFragmentManager().findFragmentByTag("EnquiryFragment");

        if (exit instanceof EnquiryFragment && exit.isVisible()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
            builder.setMessage("Exit Application ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(enquiryFragment instanceof EmployeeSettingFragment && enquiryFragment.isVisible())
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EnquiryFragment(), "Exit");
            ft.commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
