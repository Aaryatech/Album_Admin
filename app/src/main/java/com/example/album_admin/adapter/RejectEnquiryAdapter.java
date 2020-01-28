package com.example.album_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.album_admin.R;
import com.example.album_admin.activity.ImageZoomActivity;
import com.example.album_admin.constants.Constants;
import com.example.album_admin.model.AlbumEnquiry;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RejectEnquiryAdapter extends RecyclerView.Adapter<RejectEnquiryAdapter.MyViewHolder> {

    private ArrayList<AlbumEnquiry> enquiryList;
    private Context context;

    public RejectEnquiryAdapter(ArrayList<AlbumEnquiry> enquiryList, Context context) {
        this.enquiryList = enquiryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RejectEnquiryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.enquiry_list_reject, viewGroup, false);
//        enquiry_list_adapter
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectEnquiryAdapter.MyViewHolder myViewHolder, int i) {
        final AlbumEnquiry model = enquiryList.get(i);

        myViewHolder.tv_cust_name.setText(""+model.getExVar3());


        myViewHolder.tvAssignEmp.setText("" + model.getCustName());

        myViewHolder.tvReject.setText("" + model.getApprovedUserName());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");

        Date AppDate = null;
        try {
            AppDate = formatter.parse(model.getApprovedDateTime());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String appDate = formatter1.format(AppDate);

        Date TODate = null;
        try {
            TODate = formatter.parse(model.getEnquiryDateTime());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String enqDate = formatter1.format(TODate);
        myViewHolder.tv_date.setText("" +enqDate);
        myViewHolder.tv_Rejdate.setText("" +appDate);



        List<String> list = Arrays.asList(model.getEnquiryDateTime().split(" "));
        String timeEnq=list.get(1);

        List<String> list1 = Arrays.asList(model.getApprovedDateTime().split(" "));
        String timeApp=list1.get(1);

        Log.e("Time","--------------------------ENQ---------------------------"+timeEnq);
        Log.e("Time","---------------------------APP--------------------------"+timeApp);

        String diff = "";

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        Date date1 = null, date2 = null;
        try {
            date1 = format.parse(timeEnq);
            date2 = format.parse(timeApp);

            Log.e("PARAMETER1","             TIME 1    "+date1 +"           TIME 2             "+date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();
        int days = (int) (difference / (1000*60*60*12));
        int hours = (int) ((difference - (1000*60*60*12*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*12*days) - (1000*60*60*hours)) / (1000*60);
        if(hours < 0){
            hours+=12;
        }if(min < 0){
            float  newone = (float)min/60 ;
            min +=60;
            hours =(int) (hours +newone);}

        diff=hours + "."+min;
        Log.e("Total Hrs","-----------------------------TOTAL-----------------------------------"+diff);
        myViewHolder.tvRespTime.setText("Responce Time : "+diff);



        String imageUri = String.valueOf(model.getPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL +imageUri).placeholder(context.getResources().getDrawable(R.drawable.logo)).into(myViewHolder.iv_cake);

        } catch (Exception e) {

        }

        myViewHolder.iv_cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageZoomActivity.class);
                intent.putExtra("image", Constants.IMAGE_URL + model.getPhoto());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return enquiryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cust_name, tv_date,tvAssignEmp,tvReject,tv_Rejdate,tvRespTime;
        CircleImageView iv_cake;
        LinearLayout linearLayout;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_cust_name = itemView.findViewById(R.id.tv_cust_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_Rejdate = itemView.findViewById(R.id.tv_Rejdate);

            tvAssignEmp = itemView.findViewById(R.id.tvAssignEmp);
            tvRespTime = itemView.findViewById(R.id.tvRespTime);

            tvReject = itemView.findViewById(R.id.tvReject);

            iv_cake = itemView.findViewById(R.id.ivPhoto);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
