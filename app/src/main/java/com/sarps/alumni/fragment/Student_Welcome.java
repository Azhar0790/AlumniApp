package com.sarps.alumni.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.sarps.alumni.R;


/**
 * Created by Sarps on 11/3/2016.
 */
public class Student_Welcome extends Fragment {

    private static final String ARG_PARAM = "param1";
    private String mParam;
//    TextView tv_student_descrp,tvFragFirst;
    View v;


    public static Student_Welcome newInstance(String param1) {
        Student_Welcome fragment = new Student_Welcome();
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
        v = inflater.inflate(R.layout.fragment_student, container, false);
        init();

        return v;
    }

    public void init() {
    }

    public String getmParam1() {
        return mParam;
    }

}
