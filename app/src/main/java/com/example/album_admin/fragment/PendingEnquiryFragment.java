package com.example.album_admin.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.album_admin.R;
import com.example.album_admin.adapter.PendingEnquiryAdapter;
import com.example.album_admin.constants.Constants;
import com.example.album_admin.model.AlbumEnquiry;
import com.example.album_admin.myInterface.PendingEnquiryInterface;
import com.example.album_admin.util.CommonDialog;
import com.example.album_admin.util.PermissionUtil;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.album_admin.constants.Constants.FOLDER_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingEnquiryFragment extends Fragment implements PendingEnquiryInterface {

    public RecyclerView recyclerView;
    public SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<AlbumEnquiry> enquiryList = new ArrayList<>();
    ArrayList<AlbumEnquiry> enquiryPendingList = new ArrayList<>();

    /*  --------------------------------------Dialog  declration------------------------*/
    public ImageView ivPhoto,ivCamera;
    public TextView tvPhoto;
    /*  --------------------------------------End Dialog  declration------------------------*/

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, FOLDER_NAME);
    File f;

    Bitmap myBitmap1 = null;
    public static String path1, imagePath1 = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pending_enquiry, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        if (PermissionUtil.checkAndRequestPermissions(getActivity())) {
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getEnquiry();
            }
        });

        createFolder();
        getEnquiry();
        return view;
    }

    private void getEnquiry() {

        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<AlbumEnquiry>> listCall = Constants.myInterface.getAllEnquiry();
            listCall.enqueue(new Callback<ArrayList<AlbumEnquiry>>() {
                @Override
                public void onResponse(Call<ArrayList<AlbumEnquiry>> call, Response<ArrayList<AlbumEnquiry>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Enquiry List  : ", "------------" + response.body());

                            enquiryList.clear();
                            enquiryPendingList.clear();
                            enquiryList=response.body();

                            for(int i=0;i<enquiryList.size();i++)
                            {
                                if(enquiryList.get(i).getStatus()==0)
                                {
                                    enquiryPendingList.add(enquiryList.get(i));
                                }
                            }

                            Log.e("Enquiry List Model : ", "****************" +enquiryPendingList);

                            PendingEnquiryAdapter mAdapter = new PendingEnquiryAdapter(enquiryPendingList, getActivity());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);

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
                public void onFailure(Call<ArrayList<AlbumEnquiry>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
