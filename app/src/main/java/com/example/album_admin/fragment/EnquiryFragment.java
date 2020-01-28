package com.example.album_admin.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.album_admin.R;
import com.example.album_admin.activity.LoginActivity;
import com.example.album_admin.model.Login;
import com.example.album_admin.myInterface.ApproveEnquiryInterface;
import com.example.album_admin.myInterface.PendingEnquiryInterface;
import com.example.album_admin.myInterface.RejectEnquiryInterface;
import com.example.album_admin.util.CustomSharedPreference;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnquiryFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    Login loginUser;

    FragmentPagerAdapter adapterViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enquiry, container, false);
        getActivity().setTitle("Enquiry List");

        tabLayout = (TabLayout) view.findViewById(R.id.tab);
        tabLayout.setSelectedTabIndicatorHeight(0);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        adapterViewPager = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.MAIN_KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER MAIN : ", "--------USER-------" + loginUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    PendingEnquiryInterface frgPending = (PendingEnquiryInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (frgPending != null) {
                        frgPending.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    ApproveEnquiryInterface fragmentApprove = (ApproveEnquiryInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentApprove != null) {
                        fragmentApprove.fragmentBecameVisible();
                    }
                } else if (position == 2) {
                    RejectEnquiryInterface fragmentReject = (RejectEnquiryInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentReject != null) {
                        fragmentReject.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return new PendingEnquiryFragment();
                case 1:
                    return new ApproveEnquiryFragment();
                case 2:
                    return new RejectEnquiryFragment();

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {

                case 0:
                    return "Pending";
                case 1:
                    return "Approve";
                case 2:
                    return "Rejected";

            }
            return null;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.action_setting);
        item.setVisible(true);

        MenuItem item1 = menu.findItem(R.id.action_logout);
        item1.setVisible(true);

        MenuItem item2 = menu.findItem(R.id.action_Name);
        item2.setTitle(loginUser.getEmpFname());
        item2.setVisible(true);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new EmployeeSettingFragment(), "EnquiryFragment");
                ft.commit();
                return true;

            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Logout");
                builder.setMessage(loginUser.getEmpFname()+" "+loginUser.getEmpSname()+"....  Are you sure ! you want to logout?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CustomSharedPreference.deletePreference(getActivity());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
