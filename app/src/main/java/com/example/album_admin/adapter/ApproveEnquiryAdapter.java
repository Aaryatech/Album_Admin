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
import com.example.album_admin.model.SpCakeAndAlbumEnquiry;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApproveEnquiryAdapter  extends RecyclerView.Adapter<ApproveEnquiryAdapter.MyViewHolder> {
    private ArrayList<SpCakeAndAlbumEnquiry> enquiryList;
    private Context context;

    public ApproveEnquiryAdapter(ArrayList<SpCakeAndAlbumEnquiry> enquiryList, Context context) {
        this.enquiryList = enquiryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ApproveEnquiryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.approve_enquiry_list_adapter, viewGroup, false);
        //approve_enquiry_list_adapter
//        enquiry_list_adapter
       // enquiry_list_approve
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveEnquiryAdapter.MyViewHolder myViewHolder, int i) {
        final SpCakeAndAlbumEnquiry model = enquiryList.get(i);

        myViewHolder.tv_fr_name.setText(model.getFrName());

        myViewHolder.tv_fr_code.setText("Code : " + model.getAlbumCode());
        myViewHolder.tvMaxWt.setText("Max Wt : " + model.getMaxWt());
        myViewHolder.tvMinWt.setText("Min Wt : " + model.getMinWt());
        myViewHolder.tvApprove.setText("" + model.getApprovedUserName());
        myViewHolder.tvPerson.setText("" + model.getCustName());
        myViewHolder.tvType.setText("" + model.getExVar2());

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

        Date AppDate = null;
        try {
            AppDate = formatter.parse(model.getApprovedDateTime());//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String appDate = formatter1.format(AppDate);

        myViewHolder.tv_date.setText("" +enqDate);
        myViewHolder.tv_Appdate.setText(""+appDate);

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
        TextView tv_fr_name, tv_date,tv_fr_code,tvMaxWt,tvMinWt,tvApprove,tvPerson,tvType,tv_Appdate,tvRespTime;
        CircleImageView iv_cake;
        LinearLayout linearLayout;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fr_name = itemView.findViewById(R.id.tv_fr_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_Appdate = itemView.findViewById(R.id.tv_Appdate);

            tvType = itemView.findViewById(R.id.tvType);
            tvRespTime = itemView.findViewById(R.id.tvRespTime);

            tv_fr_code = itemView.findViewById(R.id.tv_fr_code);
            tvMaxWt = itemView.findViewById(R.id.tvMaxWt);

            tvMinWt = itemView.findViewById(R.id.tvMinWt);
            tvApprove = itemView.findViewById(R.id.tvApprove);
            tvPerson = itemView.findViewById(R.id.tvReject);

            iv_cake = itemView.findViewById(R.id.ivPhoto);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
