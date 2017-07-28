package com.sarps.alumni.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarps.alumni.R;
import com.sarps.alumni.adapter.Home_Adapter;

/**
 * Created by Sarps on 11/14/2016.
 */
public class HomePageFragment extends Fragment {
    Home_Adapter adapter;
    public HomePageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);

        return v;
    }
}
