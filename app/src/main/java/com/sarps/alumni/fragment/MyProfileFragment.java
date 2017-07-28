package com.sarps.alumni.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.BankDetailActivity;
import com.sarps.alumni.MainActivity;
import com.sarps.alumni.MembershipActivity;
import com.sarps.alumni.R;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarps on 1/27/2017.
 */
public class MyProfileFragment extends Fragment {
    Button btn_upload,btn_upgrade,btn_submit;
    ImageView iv_profile_image,iv_loadimage;
    TextView tv_username,tv_join_date,tv_txt_gender,tv_gender_male,tv_dob,tv_txt_dob,tv_txt_email,tv_email,tv_txt_mobile,tv_mobile,tv_txt_status,tv_status,tv_txt_acnt_name,tv_acnt_name,tv_txt_acnt_no,tv_acnt_no,tv_txt_bank_name,tv_bank_name,tv_txt_bank_ifsc,tv_bank_ifsc,tv_txt_free;
    EditText et_write_about;
    ImageView iv_email_ver,iv_email_nt_ver,iv_mobile_ver,iv_mobile_nt_ver;
    LinearLayout ll_male,ll_female;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String uid;
    SharedPreferences pref;

    View v;
    public MyProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_myprofile, container, false);
        init();
        progressDialog = new ProgressDialog((MainActivity)getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "DEFAULT");
        tv_username.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
//        tv_join_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_gender_male.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_gender.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_dob.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_dob.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_email.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_email.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_mobile.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_mobile.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_status.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_status.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_acnt_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_acnt_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_acnt_no.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_bank_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_bank_ifsc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_bank_ifsc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_txt_free.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        et_write_about.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), BankDetailActivity.class));
            }
        });
        btn_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MembershipActivity.class));
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_about();
            }
        });
        get_profile();
        get_bank_detail();
        return v;
    }
    public void init(){
        btn_upload=(Button)v.findViewById(R.id.btn_upload);
        btn_upgrade=(Button)v.findViewById(R.id.btn_upgrade);
        btn_submit=(Button)v.findViewById(R.id.btn_submit);
        iv_profile_image=(ImageView)v.findViewById(R.id.iv_profile_image);
        iv_email_ver=(ImageView)v.findViewById(R.id.iv_email_ver);
        iv_email_nt_ver=(ImageView)v.findViewById(R.id.iv_email_nt_ver);
        iv_mobile_ver=(ImageView)v.findViewById(R.id.iv_mobile_ver);
        iv_mobile_nt_ver=(ImageView)v.findViewById(R.id.iv_mobile_nt_ver);
        ll_male=(LinearLayout)v.findViewById(R.id.ll_male);
        ll_female=(LinearLayout)v.findViewById(R.id.ll_female);
        et_write_about=(EditText) v.findViewById(R.id.et_write_about);
        tv_username=(TextView) v.findViewById(R.id.tv_username);
        tv_txt_gender=(TextView) v.findViewById(R.id.tv_txt_gender);
        tv_gender_male=(TextView) v.findViewById(R.id.tv_gender_male);
        tv_dob=(TextView) v.findViewById(R.id.tv_dob);
        tv_txt_dob=(TextView) v.findViewById(R.id.tv_txt_dob);
        tv_txt_email=(TextView) v.findViewById(R.id.tv_txt_email);
        tv_email=(TextView) v.findViewById(R.id.tv_email);
        tv_txt_mobile=(TextView) v.findViewById(R.id.tv_txt_mobile);
        tv_txt_status=(TextView) v.findViewById(R.id.tv_txt_status);
        tv_status=(TextView) v.findViewById(R.id.tv_status);
        tv_txt_acnt_name=(TextView) v.findViewById(R.id.tv_txt_acnt_name);
        tv_acnt_name=(TextView) v.findViewById(R.id.tv_acnt_name);
        tv_txt_acnt_no=(TextView) v.findViewById(R.id.tv_txt_acnt_no);
        tv_acnt_no=(TextView) v.findViewById(R.id.tv_acnt_no);
        tv_txt_bank_name=(TextView) v.findViewById(R.id.tv_txt_bank_name);
        tv_bank_name=(TextView) v.findViewById(R.id.tv_bank_name);
        tv_txt_bank_ifsc=(TextView) v.findViewById(R.id.tv_txt_bank_ifsc);
        tv_bank_ifsc=(TextView) v.findViewById(R.id.tv_bank_ifsc);
        tv_txt_free=(TextView) v.findViewById(R.id.tv_txt_free);
        tv_mobile=(TextView) v.findViewById(R.id.tv_mobile);
    }

    public void get_profile() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.PROFILE_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();

                    JSONObject message = jsonObject.getJSONObject("message");
                    String u_fname=message.getString("u_fname");
                    String u_lname=message.getString("u_lname");
                    String u_gender=message.getString("u_gender");
                    String u_mobile=message.getString("u_mobile");
                    String u_mail=message.getString("u_mail");
                    String u_address=message.getString("u_address");
                    String u_dob=message.getString("u_dob");
                    String u_role=message.getString("u_role");
                    String u_image=message.getString("u_image");
                    System.out.println("u_image :- "+u_image);
                    Picasso.with(getActivity()).load(u_image).into(iv_profile_image);
                    tv_username.setText(""+ u_fname + " " + u_lname);
                    if(u_gender.equals("male")){
                        ll_male.setVisibility(View.VISIBLE);
                        ll_female.setVisibility(View.GONE);
                    }else{
                        ll_female.setVisibility(View.VISIBLE);
                        ll_male.setVisibility(View.GONE);
                    }
                    tv_mobile.setText(u_mobile);
                    tv_dob.setText(u_dob);
                    tv_status.setText(u_role);
                    tv_email.setText(u_mail);

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
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }
    public void get_bank_detail() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.BANKDETAIL_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    System.out.println(".............");
                    JSONObject message = jsonObject.getJSONObject("message");
                    String acc_name=message.getString("acc_name");
                    String acc_no=message.getString("acc_no");
                    String ifsc_code=message.getString("ifsc_code");
                    String bank_name=message.getString("bank_name");

                    tv_acnt_name.setText(acc_name);
                    tv_acnt_no.setText(acc_no);
                    tv_bank_name.setText(bank_name);
                    tv_bank_ifsc.setText(ifsc_code);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("json Error" + volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hm = new HashMap<>();
                hm.put("u_id", uid);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }
    public void post_about() {
        final String about_urself = et_write_about.getText().toString();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.ABOUTURSELF_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    System.out.println(".............");
                    String message = jsonObject.getString("message");
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("about_urself",message);
                    editor.commit();

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
                hm.put("about", about_urself);
                hm.put("u_id", uid);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }
}