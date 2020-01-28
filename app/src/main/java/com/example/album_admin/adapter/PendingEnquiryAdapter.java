package com.example.album_admin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.album_admin.R;
import com.example.album_admin.activity.ApproveActivity;
import com.example.album_admin.activity.ImageZoomActivity;
import com.example.album_admin.activity.MainActivity;
import com.example.album_admin.constants.Constants;
import com.example.album_admin.fragment.EnquiryFragment;
import com.example.album_admin.model.AlbumEnquiry;
import com.example.album_admin.model.Info;
import com.example.album_admin.model.Login;
import com.example.album_admin.util.CommonDialog;
import com.example.album_admin.util.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingEnquiryAdapter extends RecyclerView.Adapter<PendingEnquiryAdapter.MyViewHolder> {
    private ArrayList<AlbumEnquiry> enquiryList;
    private Context context;
    Login loginUser;

    public PendingEnquiryAdapter(ArrayList<AlbumEnquiry> enquiryList, Context context) {
        this.enquiryList = enquiryList;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingEnquiryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enquiry_list_pending, parent, false);
//        enquiry_list_adapter
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingEnquiryAdapter.MyViewHolder holder, final int position) {
        final AlbumEnquiry model = enquiryList.get(position);

        holder.tv_cust_name.setText(""+model.getExVar3());

        holder.tvFrId.setText("Fr Id : " + model.getFrId());
        holder.tvAssignEmp.setText("" + model.getCustName());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

        Date TODate = null;
        try {
            TODate = formatter.parse(model.getEnquiryDateTime());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String enqDate = formatter1.format(TODate);
        holder.tv_date.setText("" +enqDate);

        String imageUri = String.valueOf(model.getPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL +imageUri).placeholder(context.getResources().getDrawable(R.drawable.logo)).into(holder.iv_cake);

        } catch (Exception e) {

        }

        holder.iv_cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageZoomActivity.class);
                intent.putExtra("image", Constants.IMAGE_URL + model.getPhoto());
                context.startActivity(intent);
            }
        });

        holder.tvApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DeptDialog(context).show();

                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent = new Intent(context, ApproveActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        holder.tvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    String userStr = CustomSharedPreference.getString(context, CustomSharedPreference.MAIN_KEY_USER);
                    Gson gson = new Gson();
                    loginUser = gson.fromJson(userStr, Login.class);
                    Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getUpdateEnq(model.getEnquiryNo(),2);

            }
        });
    }

    @Override
    public int getItemCount() {
        return enquiryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cust_name, tv_date,tvApprove,tvReject,tvFrId,tvAssignEmp;
        CircleImageView iv_cake;
        LinearLayout linearLayout;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_cust_name = itemView.findViewById(R.id.tv_cust_name);
            tv_date = itemView.findViewById(R.id.tv_date);

            tvApprove = itemView.findViewById(R.id.tvApprove);
            tvReject = itemView.findViewById(R.id.tvReject);

            tvFrId = itemView.findViewById(R.id.tvFrId);
            tvAssignEmp = itemView.findViewById(R.id.tvAssignEmp);

            iv_cake = itemView.findViewById(R.id.ivPhoto);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    private void getUpdateEnquiry(final AlbumEnquiry albumEnquiry1) {
        Log.e("PARAMETER","---------------------------------------Album Enquiry--------------------------"+albumEnquiry1);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<AlbumEnquiry> listCall = Constants.myInterface.saveAlbumEnquiry(albumEnquiry1);
            listCall.enqueue(new Callback<AlbumEnquiry>() {
                @Override
                public void onResponse(Call<AlbumEnquiry> call, Response<AlbumEnquiry> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE ALBUM : ", " ------------------------------ ALBUM ENQUIRY------------------------ " + response.body());

                            getUpdateEnq(albumEnquiry1.getEnquiryNo(),2);

//                            MainActivity activity = (MainActivity) context;
//                            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.content_frame, new EnquiryFragment(), "HomeFragment");
//                            ft.commit();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("" + context.getResources().getString(R.string.app_name));
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                        builder.setTitle("" + context.getResources().getString(R.string.app_name));
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                    builder.setTitle("" + context.getResources().getString(R.string.app_name));
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
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUpdateEnq(int enquiryNo, int status) {

        Log.e("PARAMETER","                   Enquiry No              "+enquiryNo+"                       Status           "+status);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateAlbumEnquiry(enquiryNo,status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE ALBUM : ", " ------------------------------UPDATE ALBUM ENQUIRY------------------------ " + response.body());

                            MainActivity activity = (MainActivity) context;
                            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new EnquiryFragment(), "HomeFragment");
                            ft.commit();
                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                            builder.setTitle("" + context.getResources().getString(R.string.app_name));
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                        builder.setTitle("" + context.getResources().getString(R.string.app_name));
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                    builder.setTitle("" + context.getResources().getString(R.string.app_name));
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
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
