package com.sarps.alumni.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sarps.alumni.R;


/**
 * Created by Sarps on 11/3/2016.
 */
public class Ex_Student_Welcome extends Fragment {
    private static final String ARG_PARAM = "param1";
    private String mParam;




    public static Ex_Student_Welcome newInstance(String param1) {
        Ex_Student_Welcome fragment = new Ex_Student_Welcome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_ex_student,container, false);
    }
    public String getmParam1() {
        return mParam;
    }
}