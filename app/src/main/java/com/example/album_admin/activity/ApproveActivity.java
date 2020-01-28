package com.example.album_admin.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.album_admin.R;
import com.example.album_admin.adapter.MenuDialogAdapter;
import com.example.album_admin.adapter.SpecialCakeDialogAdapter;
import com.example.album_admin.adapter.TypeDialogAdapter;
import com.example.album_admin.constants.Constants;
import com.example.album_admin.fragment.EnquiryFragment;
import com.example.album_admin.model.Album;
import com.example.album_admin.model.AlbumEnquiry;
import com.example.album_admin.model.Info;
import com.example.album_admin.model.Login;
import com.example.album_admin.model.Menu;
import com.example.album_admin.model.MenuConfigure;
import com.example.album_admin.model.SpecialCake;
import com.example.album_admin.model.Type;
import com.example.album_admin.util.CommonDialog;
import com.example.album_admin.util.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView tvSpecialCake,tvSpecialCakeId,tvType,tvTypeId,tvMenu,tvMenuId;
    public EditText edAlbumCode,edAlbumName,edMinWt,edMaxWt,edDesc;
    public RadioGroup rgType;
    public Button btnSubmit;
    public RadioButton rbYes,rbNo;
    public ImageView ivPhoto;
    ArrayList<SpecialCake> specialCakeList = new ArrayList<>();
    ArrayList<MenuConfigure> menuConfigureList = new ArrayList<>();
    ArrayList<Type> typeList = new ArrayList<>();
    ArrayList<Menu> menuList = new ArrayList<>();

    Dialog dialog;
    int cakeVisible;
    SpecialCakeDialogAdapter spAdapter;
    TypeDialogAdapter typeAdapter;
    MenuDialogAdapter menuAdapter;
    private BroadcastReceiver mBroadcastReceiver;
    public static ArrayList<Menu> assignStaticMenuList = new ArrayList<>();
    String stringId,stringName;
    AlbumEnquiry albumEnquiry;
    CommonDialog commonDialog;
    String frId;
    Album album1;
    Login loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvSpecialCake=(TextView)findViewById(R.id.tvSpecialCake);
        tvSpecialCakeId=(TextView)findViewById(R.id.tvSpecialCakeId);
        tvType=(TextView)findViewById(R.id.tvType);
        tvTypeId=(TextView)findViewById(R.id.tvTypeId);
        tvMenu=(TextView)findViewById(R.id.tvMenu);
        tvMenuId=(TextView)findViewById(R.id.tvMenuId);

        edAlbumCode=(EditText)findViewById(R.id.ed_AlbumeCode);
        edAlbumName=(EditText)findViewById(R.id.edAlbumName);
        edMinWt=(EditText)findViewById(R.id.edMinWt);
        edMaxWt=(EditText)findViewById(R.id.edMaxWt);
        edDesc=(EditText)findViewById(R.id.edDesc);

        ivPhoto=(ImageView)findViewById(R.id.ivPhoto);

        btnSubmit=(Button) findViewById(R.id.btnSubmit);

        rbYes=(RadioButton) findViewById(R.id.rbYes);
        rbNo=(RadioButton)findViewById(R.id.rbNo);

        rgType=(RadioGroup) findViewById(R.id.rgType);

        String enquiry = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        try {
            albumEnquiry = gson.fromJson(enquiry, AlbumEnquiry.class);
            Log.e("Enquiry bin","------------------------------------------------------------"+albumEnquiry);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {

            Picasso.with(ApproveActivity.this).load(Constants.IMAGE_URL +albumEnquiry.getPhoto()).placeholder(ApproveActivity.this.getResources().getDrawable(R.drawable.logo)).into(ivPhoto);

        } catch (Exception e) {

        }

        getSpecialCake();
        getType();
        getMenu(5,4,0);

        rbYes.setChecked(true);

        tvSpecialCake.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvMenu.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e("Broad cast data","---------------"+intent);
                if (intent.getAction().equals("CUSTOMER_DATA")) {
                    handlePushNotification(intent);

                } else if(intent.getAction().equals("CUSTOMER_DATA_TYPE"))
                {
                    handlePushNotification1(intent);
                }
//                else if(intent.getAction().equals("CUSTOMER_DATA2"))
//                {
//                    handlePushNotification2(intent);
//                }
            }
        };
    }

    private void getMenu(int catId, int isSameDay, int delStatus) {
        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Menu>> listCall = Constants.myInterface.getMenuForAlbum(catId,isSameDay,delStatus);
            listCall.enqueue(new Callback<ArrayList<Menu>>() {
                @Override
                public void onResponse(Call<ArrayList<Menu>> call, Response<ArrayList<Menu>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Type   : ", "------------" + response.body());

                            menuList.clear();
                            menuList=response.body();

                            assignStaticMenuList.clear();
                            assignStaticMenuList = menuList;

                            for (int i = 0; i < assignStaticMenuList.size(); i++) {
                                assignStaticMenuList.get(i).setChecked(false);
                            }

                            Log.e("Type List : ", "****************" +menuList);

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
                public void onFailure(Call<ArrayList<Menu>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getType() {
        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Type>> listCall = Constants.myInterface.getTypeWiseFrNameList();
            listCall.enqueue(new Callback<ArrayList<Type>>() {
                @Override
                public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Type   : ", "------------" + response.body());

                            typeList.clear();
                            typeList=response.body();

                            Log.e("Type List : ", "****************" +specialCakeList);

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
                public void onFailure(Call<ArrayList<Type>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getSpecialCake() {
        if (Constants.isOnline(getApplicationContext())) {
            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<SpecialCake>> listCall = Constants.myInterface.getSpCakeCatSuppList();
            listCall.enqueue(new Callback<ArrayList<SpecialCake>>() {
                @Override
                public void onResponse(Call<ArrayList<SpecialCake>> call, Response<ArrayList<SpecialCake>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Special Cake   : ", "------------" + response.body());

                            specialCakeList.clear();
                            specialCakeList=response.body();

                            Log.e("Special Cake List : ", "****************" +specialCakeList);

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
                public void onFailure(Call<ArrayList<SpecialCake>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tvSpecialCake)
        {
            Log.e("onClcik","-----------------------------------");
            showSpecialCakeDialog();
        }else if(v.getId()==R.id.tvType)
        {
            showTypeDialog();
        }else if(v.getId()==R.id.tvMenu)
        {
            showMenuDialog();
        }else if(v.getId()==R.id.btnSubmit)
        {
            String strSpecialCake,strAlbumCode,strAlbumName,strMinWt,strMaxWt,strType,strMenu,strDesc,strSpecialCakeId,strMenuId,strTypeId;
            boolean isValidSpecialCake = false,isValidAlbumCode = false,isValidAlbumName = false,isValidMinWt = false,isValidMaxWt = false,isValidType = false,isValidMenu = false,isValidDesc = false;

            strSpecialCake=tvSpecialCake.getText().toString().trim();
            strSpecialCakeId=tvSpecialCakeId.getText().toString().trim();
            strAlbumCode=edAlbumCode.getText().toString().trim();
            strAlbumName=edAlbumName.getText().toString().trim();
            strMinWt=edMinWt.getText().toString().trim();
            strMaxWt=edMaxWt.getText().toString().trim();
            strType=tvType.getText().toString().trim();
            strTypeId=tvTypeId.getText().toString().trim();
            strMenu=tvMenu.getText().toString().trim();
            strMenuId=tvMenuId.getText().toString().trim();
            strDesc=edDesc.getText().toString().trim();

            float minWt=0,maxWt=0;
            int  spId=0;
            try{
                minWt= Float.parseFloat(strMinWt);
                maxWt= Float.parseFloat(strMaxWt);
                spId= Integer.parseInt(strSpecialCakeId);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            if (strSpecialCake.isEmpty()) {
                tvSpecialCake.setError("required");
            } else {
                tvSpecialCake.setError(null);
                isValidSpecialCake = true;
            }

            if (strAlbumCode.isEmpty()) {
                edAlbumCode.setError("required");
            } else {
                edAlbumCode.setError(null);
                isValidAlbumCode = true;
            }

            if (strAlbumName.isEmpty()) {
                edAlbumName.setError("required");
            } else {
                edAlbumName.setError(null);
                isValidAlbumName = true;
            }

            if (strMinWt.isEmpty()) {
                edMinWt.setError("required");
            } else {
                edMinWt.setError(null);
                isValidMinWt = true;
            }

            if (strMaxWt.isEmpty()) {
                edMaxWt.setError("required");
            }else if (Float.compare(maxWt , minWt) < 0) {
                edMaxWt.setError("Max Wt Greater than Min Wt");
            }else {
                edMaxWt.setError(null);
                isValidMaxWt = true;
            }

            if (strType.isEmpty()) {
                tvType.setError("required");
            } else {
                tvType.setError(null);
                isValidType = true;
            }

            if (strMenu.isEmpty()) {
                tvMenu.setError("required");
            } else {
                tvMenu.setError(null);
                isValidMenu = true;
            }

            if (strDesc.isEmpty()) {
                edDesc.setError("required");
            } else {
                edDesc.setError(null);
                isValidDesc = true;
            }
            cakeVisible = 0;
            if (rbYes.isChecked()) {
                cakeVisible = 0;
            } else if (rbNo.isChecked()) {
                cakeVisible = 1;
            }

            if(isValidSpecialCake && isValidAlbumCode && isValidAlbumName && isValidMaxWt && isValidMinWt && isValidType && isValidMenu && isValidDesc)
            {
                Album album = new Album(0,strAlbumCode,strAlbumName,albumEnquiry.getPhoto(),"",minWt,maxWt,0,spId,"",1,cakeVisible,strDesc);
                addAlbum(album);

            }
        }
    }

    private void addAlbum(Album album) {
        Log.e("PARAMETER","---------------------------------------Album --------------------------"+album);

        if (Constants.isOnline(getApplicationContext())) {
            commonDialog = new CommonDialog(ApproveActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Album> listCall = Constants.myInterface.saveAlbum(album);
            listCall.enqueue(new Callback<Album>() {
                @Override
                public void onResponse(Call<Album> call, Response<Album> response) {
                    try {
                        if (response.body() != null) {
                            album1=response.body();
                            Log.e("SAVE ALBUM : ", " ------------------------------SAVE ALBUM ------------------------ " + response.body());
                            updateEnquiry(albumEnquiry.getEnquiryNo(),response.body().getAlbumId());
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("" + ApproveActivity.this.getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("" + getApplicationContext().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<Album> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("" + getApplicationContext().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
            Toast.makeText(ApproveActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateEnquiry(int enquiryNo, int albumId) {
        Log.e("PARAMETER","             Enq Id       "+enquiryNo+"              Album Id         "+albumId);
        if (Constants.isOnline(getApplicationContext())) {
//            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateAlbumId(enquiryNo,albumId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Update Enquiry   : ", "----------------------------------------" + response.body());

                            String strMenuId=tvMenuId.getText().toString().trim();
                            int typeId=0,menuId=0;

                            Log.e("Type Id   : ", "----------------------------------------" + frId);
                            Log.e("Menu Id   : ", "----------------------------------------" +strMenuId);

                            ArrayList<String> typeList = new ArrayList<>();
                            typeList.add(frId);

                            ArrayList<String> menuList = new ArrayList<>();
                            menuList.add(strMenuId);

                            Log.e("Type Id   : ", "------------------LIST----------------------" + typeList);
                            Log.e("Menu Id   : ", "--------------------LIST--------------------" +menuList);

                            getConfigureList(typeList,menuList);

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
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getConfigureList(ArrayList<String> typeList, final ArrayList<String> menuList) {
        Log.e("PARAMETER","         TYPE LIST       "+typeList+"               MENU LIST       "+menuList);

        if (Constants.isOnline(getApplicationContext())) {
//            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<ArrayList<MenuConfigure>> listCall = Constants.myInterface.getConfigListByFrAndMenu(typeList,menuList);
            listCall.enqueue(new Callback<ArrayList<MenuConfigure>>() {
                @Override
                public void onResponse(Call<ArrayList<MenuConfigure>> call, Response<ArrayList<MenuConfigure>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Menu Configure   : ", "------------" + response.body());

                            menuConfigureList.clear();
                            menuConfigureList=response.body();

                            Log.e("Menu Configure List : ", "****************" +menuConfigureList);


                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            try {
                                String userStr = CustomSharedPreference.getString(getApplication(), CustomSharedPreference.MAIN_KEY_USER);
                                Gson gson = new Gson();
                                loginUser = gson.fromJson(userStr, Login.class);
                                Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUser);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            for(int i=0;i<menuConfigureList.size();i++)
                            {
                                if(!menuConfigureList.get(i).getItemShow().contains(String.valueOf(album1.getAlbumId())))
                                {
                                    String menuItem = menuConfigureList.get(i).getItemShow()+","+album1.getAlbumId();
                                    Log.e("Concate","---------------------------------------------Item-------------------------"+menuConfigureList.get(i).getItemShow());
                                    Log.e("Concate","---------------------------------------------Item-String------------------------"+menuItem);
                                    getUpdateItem(menuItem,menuConfigureList.get(i).getSettingId(),albumEnquiry.getEnquiryNo());
                                }
                            }


//                            String strType=tvType.getText().toString().trim();
//
//                            AlbumEnquiry albumEnquiry1=new AlbumEnquiry(albumEnquiry.getEnquiryNo(),albumEnquiry.getFrId(),albumEnquiry.getCustName(),albumEnquiry.getMobileNo(),albumEnquiry.getPhoto(),albumEnquiry.getEnquiryDate(),albumEnquiry.getEnquiryDateTime(),sdf.format(System.currentTimeMillis()),loginUser.getEmpId(),loginUser.getEmpFname()+" "+loginUser.getEmpMname()+" "+loginUser.getEmpSname(),album1.getAlbumId(),1,albumEnquiry.getDelStatus(),albumEnquiry.getNoNotifictnFired(),albumEnquiry.getExVar1(),strType,albumEnquiry.getExVar3(),albumEnquiry.getExInt1(),albumEnquiry.getExInt2(),albumEnquiry.getExInt3());
//                            getUpdateEnquiry(albumEnquiry1,menuConfigureList);



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
                public void onFailure(Call<ArrayList<MenuConfigure>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getUpdateItem(String menuItem, Integer settingId, final int albumEnquiryNo) {
        Log.e("PARAMETER","             Menu Item      "+menuItem+"              Setting Id         "+settingId);
        if (Constants.isOnline(getApplicationContext())) {
//            final CommonDialog commonDialog = new CommonDialog(getApplicationContext(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateFrConfItemShow(menuItem,settingId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Update Item   : ", "----------------------------------------" + response.body());

                            getUpdateEnq(albumEnquiryNo,1);

//                            FragmentTransaction ft = ApproveActivity.this.getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.content_frame, new EnquiryFragment(), "HomeFragment");
//                            ft.commit();
                            
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
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void getUpdateEnq(int enquiryNo, int status) {

        Log.e("PARAMETER","                   Enquiry No              "+enquiryNo+"                       Status           "+status);

        if (Constants.isOnline(ApproveActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ApproveActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateAlbumEnquiry(enquiryNo,status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE ALBUM : ", " ------------------------------UPDATE ALBUM ENQUIRY------------------------ " + response.body());

                           // MainActivity activity = (MainActivity) get;
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new EnquiryFragment(), "HomeFragment");
                            ft.commit();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("" + ApproveActivity.this.getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("" + ApproveActivity.this.getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("" + ApproveActivity.this.getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
            Toast.makeText(ApproveActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }



    private void getUpdateEnquiry(final AlbumEnquiry albumEnquiry1, final ArrayList<MenuConfigure> menuConfigure) {
        Log.e("PARAMETER","---------------------------------------Album Enquiry--------------------------"+albumEnquiry1);

        if (Constants.isOnline(ApproveActivity.this)) {
//            final CommonDialog commonDialog = new CommonDialog(ApproveActivity.this, "Loading", "Please Wait...");
//            commonDialog.show();

            Call<AlbumEnquiry> listCall = Constants.myInterface.saveAlbumEnquiry(albumEnquiry1);
            listCall.enqueue(new Callback<AlbumEnquiry>() {
                @Override
                public void onResponse(Call<AlbumEnquiry> call, Response<AlbumEnquiry> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE ALBUM : ", " ------------------------------UPDATE ALBUM ENQUIRY------------------------ " + response.body());

                            for(int i=0;i<menuConfigure.size();i++)
                            {
                                if(!menuConfigure.get(i).getItemShow().contains(String.valueOf(album1.getAlbumId())))
                                {
                                    String menuItem = menuConfigure.get(i).getItemShow()+","+album1.getAlbumId();
                                    Log.e("Concate","---------------------------------------------Item-------------------------"+menuConfigureList.get(i).getItemShow());
                                    Log.e("Concate","---------------------------------------------Item-String------------------------"+menuItem);
                                   // getUpdateItem(menuItem,menuConfigure.get(i).getSettingId(),albumEnquiry1);
                                }
                            }
//                            FragmentTransaction ft = ApproveActivity.this.getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.content_frame, new EnquiryFragment(), "HomeFragment");
//                            ft.commit();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle("" + ApproveActivity.this.getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle("" + ApproveActivity.this.getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<AlbumEnquiry> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ApproveActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle("" + ApproveActivity.this.getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
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
            Toast.makeText(ApproveActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void showMenuDialog() {
        dialog = new Dialog(ApproveActivity.this, android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) ApproveActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.custom_dialog_multiple_search, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
        EditText edSearch = dialog.findViewById(R.id.edSearch);
        Button btnSubmit=dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ArrayList<Menu> assignedArray = new ArrayList<>();
                final ArrayList<Integer> assignedItemIdArray = new ArrayList<>();
                final ArrayList<String> assignedItemNameArray = new ArrayList<>();

                if (assignStaticMenuList != null) {
                    if (assignStaticMenuList.size() > 0) {
                        assignedArray.clear();
                        for (int i = 0; i < assignStaticMenuList.size(); i++) {
                            if (assignStaticMenuList.get(i).isChecked()) {
                                assignedArray.add(assignStaticMenuList.get(i));
                                assignedItemIdArray.add(assignStaticMenuList.get(i).getMenuId());
                                assignedItemNameArray.add(assignStaticMenuList.get(i).getMenuTitle());

                            }
                        }
                    }
                    Log.e("ASSIGN ITEM", "---------------------------------" + assignedArray);
                    Log.e("ASSIGN ITEM ID", "---------------------------------" + assignedItemIdArray);
                    Log.e("ASSIGN ITEM Name", "---------------------------------" + assignedItemNameArray);

                    String empIds = assignedItemIdArray.toString().trim();
                    Log.e("ASSIGN EMP ID", "---------------------------------" + empIds);

                    String a1 = "" + empIds.substring(1, empIds.length() - 1).replace("][", ",") + "";
                    stringId = a1.replaceAll("\\s", "");

                    Log.e("ASSIGN EMP ID STRING", "---------------------------------" + stringId);
                    Log.e("ASSIGN EMP ID STRING1", "---------------------------------" + a1);

                    String empName = assignedItemNameArray.toString().trim();
                    Log.e("ASSIGN EMP NAME", "---------------------------------" + empName);

                    stringName = "" + empName.substring(1, empName.length() - 1).replace("][", ",") + "";

                    Log.e("ASSIGN EMP NAME STRING", "---------------------------------" + stringName);


                    tvMenu.setText("" + stringName);
                    tvMenuId.setText("" + stringId);
                }
            }
        });



        Log.e("List","-------------------------------------Dialog--------------------"+specialCakeList);

        menuAdapter = new MenuDialogAdapter(menuList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(menuAdapter);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (menuAdapter != null) {
                        filterType(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }

    private void showTypeDialog() {
        dialog = new Dialog(ApproveActivity.this, android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) ApproveActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.custom_dialog_fullscreen_search, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
        EditText edSearch = dialog.findViewById(R.id.edSearch);

        Log.e("List","-------------------------------------Dialog--------------------"+specialCakeList);

        typeAdapter = new TypeDialogAdapter(typeList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(typeAdapter);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (typeAdapter != null) {
                        filterType(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }

    void filterType(String text) {
        ArrayList<Type> temp = new ArrayList();
        for (Type d : typeList) {
            if (d.getTypeName().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }
        }
        //update recyclerview
        typeAdapter.updateList(temp);
    }

    private void showSpecialCakeDialog() {
        dialog = new Dialog(ApproveActivity.this, android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) ApproveActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.custom_dialog_fullscreen_search, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView rvCustomerList = dialog.findViewById(R.id.rvCustomerList);
        EditText edSearch = dialog.findViewById(R.id.edSearch);

        Log.e("List","-------------------------------------Dialog--------------------"+specialCakeList);

        spAdapter = new SpecialCakeDialogAdapter(specialCakeList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCustomerList.setLayoutManager(mLayoutManager);
        rvCustomerList.setItemAnimator(new DefaultItemAnimator());
        rvCustomerList.setAdapter(spAdapter);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (spAdapter != null) {
                        filterSpecialCake(editable.toString());
                    }
                } catch (Exception e) {
                }
            }
        });

        dialog.show();
    }
    void filterSpecialCake(String text) {
        ArrayList<SpecialCake> temp = new ArrayList();
        for (SpecialCake d : specialCakeList) {
            if (d.getSpCode().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }
        }
        //update recyclerview
        spAdapter.updateList(temp);
    }

    private void handlePushNotification(Intent intent) {
        Log.e("handlePushNotification", "------------------------------------**********");
        dialog.dismiss();
        String spName = intent.getStringExtra("name");
        int spId = intent.getIntExtra("id", 0);
        Log.e("CUSTOMER NAME : ", " " + spName);
        tvSpecialCake.setText("" + spName);
        tvSpecialCakeId.setText("" + spId);


    }

    private void handlePushNotification1(Intent intent) {
        Log.e("handlePushNotification", "------------------------------------**********");
        dialog.dismiss();
        String typeName = intent.getStringExtra("name");
        int typeId = intent.getIntExtra("id", 0);
        frId = intent.getStringExtra("frId");
        Log.e("CUSTOMER NAME : ", " " + typeName);
        Log.e("Fr Id : ", "-------------------------------------------- " + frId);
        tvType.setText("" + typeName);
        tvTypeId.setText("" + typeId);


    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(ApproveActivity.this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("CUSTOMER_DATA"));
        LocalBroadcastManager.getInstance(ApproveActivity.this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("CUSTOMER_DATA_TYPE"));
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
//                new IntentFilter("CUSTOMER_DATA2"));

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(ApproveActivity.this).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
