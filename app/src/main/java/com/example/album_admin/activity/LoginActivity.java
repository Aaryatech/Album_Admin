package com.example.album_admin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.album_admin.R;
import com.example.album_admin.constants.Constants;
import com.example.album_admin.fcm.SharedPrefManager;
import com.example.album_admin.model.Info;
import com.example.album_admin.model.Login;
import com.example.album_admin.util.CommonDialog;
import com.example.album_admin.util.CustomSharedPreference;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edDSCCode;
    private Button btnSubmit;
     CommonDialog  commonDialog;
     String strIntent;

    private final String TAG = "LOGIN ACT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        edDSCCode = findViewById(R.id.edDSCCode);

        btnSubmit = findViewById(R.id.btnSubmit);

        try {
            //strIntent = getIntent().getStringExtra("model");
            Intent intent = getIntent();
            strIntent = intent.getExtras().getString("model");
            Log.e("String", "--------------------------" + strIntent);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            String strDSCCode;
            boolean isValidDSCCode = false;

            strDSCCode=edDSCCode.getText().toString();

            if (strDSCCode.isEmpty()) {
                edDSCCode.setError("required");
            } else {
                edDSCCode.setError(null);
                isValidDSCCode = true;
            }

            if (isValidDSCCode) {

                doLogin(strDSCCode);

            }
        }
    }

    private void doLogin(String strDSCCode) {

        Log.e("PARAMETERS : ", "       DSC CODE : " + strDSCCode);
        if (Constants.isOnline(this)) {
              commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Login> listCall = Constants.myInterface1.doLogin(strDSCCode);
            listCall.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Empoyee Data : ", "------------" + response.body());

                            Login data = response.body();
                            if (data==null) {
                                commonDialog.dismiss();
                                //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle(getResources().getString(R.string.app_name));
                                builder.setMessage("Oops something went wrong! please check username & password.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {
                                //commonDialog.dismiss();
                                Gson gson = new Gson();
                                String json = gson.toJson(data);

                                CustomSharedPreference.putString(LoginActivity.this, CustomSharedPreference.MAIN_KEY_USER, json);

                                String token = SharedPrefManager.getmInstance(LoginActivity.this).getDeviceToken();
                                Log.e("Token : ", "----**********************************************-----" + token);

                                updateToken(data.getEmpId(), token,1);

                            }
                        } else {
                            commonDialog.dismiss();

                            Log.e("Data Null1 : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle(getResources().getString(R.string.app_name));
                            builder.setMessage("Oops something went wrong! please check username & password.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle(getResources().getString(R.string.app_name));
                        builder.setMessage("Oops something went wrong! please check username & password.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.app_name));
                    builder.setMessage("Oops something went wrong! please check username & password.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateToken(Integer empId, String token,int type) {

        Log.e("PARAMETERS : ", "       EMP ID : " + empId +"             TOKEN:"  +token);

        if (Constants.isOnline(this)) {
//            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
//            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateAppEmpToken(empId,token,type);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("INFO Data : ", "-----------------------------Update Token---------------------" + response.body());

                            Info data = response.body();
                            if (data.getError()) {
                                commonDialog.dismiss();

                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("model", strIntent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                commonDialog.dismiss();
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("model", strIntent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("model", strIntent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    // Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }



}
