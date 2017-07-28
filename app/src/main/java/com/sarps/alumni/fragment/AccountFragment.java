package com.sarps.alumni.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sarps.alumni.R;


/**
 * Created by Sarps on 11/4/2016.
 */
public class AccountFragment extends Fragment {
    Button btn_add, btn_withdraw;
    TextView tv_balance,tv_balance_amnt,tv_date,tv_debit,tv_name,tv_amnt;
    View v;

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_accounts, container, false);
        init();
        btn_withdraw.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        btn_add.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_balance.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_balance_amnt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_debit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_amnt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));

        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new WithdrawFrgament();
                getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
            }
        });
        return v;
    }

    public void init() {
        btn_add = (Button) v.findViewById(R.id.btn_add);
        btn_withdraw = (Button) v.findViewById(R.id.btn_withdraw);
        tv_balance    = (TextView) v.findViewById(R.id.tv_balance);
        tv_balance_amnt = (TextView) v.findViewById(R.id.tv_balance_amnt);
        tv_date = (TextView) v.findViewById(R.id.tv_date);
        tv_debit = (TextView) v.findViewById(R.id.tv_debit);
        tv_name = (TextView) v.findViewById(R.id.tv_name);
        tv_amnt = (TextView) v.findViewById(R.id.tv_amnt);
    }
}