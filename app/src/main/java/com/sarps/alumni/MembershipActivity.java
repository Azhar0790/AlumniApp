package com.sarps.alumni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by Sarps on 1/28/2017.
 */
public class MembershipActivity extends AppCompatActivity {
    Spinner sp_membership;
    EditText et_membshp_acnt;
    Button btn_upload;
    ImageView iv_back;
    String[] mem_plan = new String[]{"Free", "Premium Rs 100", "Explore Rs 1000"};
    ArrayAdapter adapter;

    @Nullable

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_membership);
        init();
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, mem_plan);
        sp_membership.setAdapter(adapter);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void init() {
        sp_membership = (Spinner) findViewById(R.id.sp_membership);
        et_membshp_acnt = (EditText) findViewById(R.id.et_membshp_acnt);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }
}
