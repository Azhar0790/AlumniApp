package com.sarps.alumni.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sarps.alumni.R;

/**
 * Created by Sarps on 1/27/2017.
 */
public class WithdrawFrgament extends Fragment {
    Button btn_update, btn_submit;
    TextView tv_withdraw, tv_acnt_name, tv_acnt_no, tv_bank_name, tv_bank_ifsc, tv_note, tv_note_one, tv_note_two;
    CheckBox cb_data_correct;
    View v;

    public WithdrawFrgament() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_withdraw, container, false);
        init();
        tv_withdraw.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_acnt_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_acnt_no.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_bank_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_bank_ifsc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_note.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_note_one.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_note_two.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        cb_data_correct.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        btn_update.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        btn_submit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();
            }
        });
        return v;
    }

    public void init() {
        btn_update = (Button) v.findViewById(R.id.btn_update);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);
        cb_data_correct = (CheckBox) v.findViewById(R.id.cb_data_correct);
        tv_withdraw = (TextView) v.findViewById(R.id.tv_withdraw);
        tv_acnt_name = (TextView) v.findViewById(R.id.tv_acnt_name);
        tv_acnt_no = (TextView) v.findViewById(R.id.tv_acnt_no);
        tv_bank_name = (TextView) v.findViewById(R.id.tv_bank_name);
        tv_bank_ifsc = (TextView) v.findViewById(R.id.tv_bank_ifsc);
        tv_note = (TextView) v.findViewById(R.id.tv_note);
        tv_note_one = (TextView) v.findViewById(R.id.tv_note_one);
        tv_note_two = (TextView) v.findViewById(R.id.tv_note_two);
    }
}