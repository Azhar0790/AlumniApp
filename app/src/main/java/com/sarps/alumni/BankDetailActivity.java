package com.sarps.alumni;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarps on 1/28/2017.
 */
public class BankDetailActivity extends AppCompatActivity {
    ImageView iv_loadimage,iv_back;
    private static int RESULT_LOAD_IMG = 1;
    EditText et_acnt_name,et_acnt_no,et_bank_name,et_bank_ifsc;
Button btn_browse,btn_upload;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String uid;
    SharedPreferences pref;
    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_dialog);
        progressDialog = new ProgressDialog(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        MainActivity.hidetootlbar();
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "");
        init();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMG);
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_profile();
            }
        });
    }

    public void init() {
        iv_loadimage = (ImageView) findViewById(R.id.iv_loadimage);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_acnt_name = (EditText) findViewById(R.id.et_acnt_name);
        et_acnt_no = (EditText) findViewById(R.id.et_acnt_no);
        et_bank_name = (EditText) findViewById(R.id.et_bank_name);
        et_bank_ifsc = (EditText) findViewById(R.id.et_bank_ifsc);
        btn_browse = (Button) findViewById(R.id.btn_browse);
        btn_upload = (Button) findViewById(R.id.btn_upload);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMG) {

                Uri selectedImageURI = data.getData();

                Picasso.with(getApplicationContext()).load(selectedImageURI).noPlaceholder().centerCrop().fit()
                        .into(iv_loadimage);
            }

        }

    }
    public void post_profile() {
        final String ac_name = et_acnt_name.getText().toString();
        final String ac_no = et_acnt_no.getText().toString();
        final String b_name = et_bank_name.getText().toString();
        final String i_code = et_bank_ifsc.getText().toString();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.UPLOADBANKDETAIL_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    System.out.println("jsonObject :- "+jsonObject);
                    String  message = jsonObject.getString("message");
                    System.out.println("message :- "+message);
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("json Error" + volleyError);
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hm = new HashMap<>();
                hm.put("u_id", uid);
                hm.put("acc_name", ac_name);
                hm.put("acc_no", ac_no);
                hm.put("bank_name", b_name);
                hm.put("ifsc_code", i_code);
                return hm;
            }
        };
        requestQueue.add(jsObjRequest);
    }
}
