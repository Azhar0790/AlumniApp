package com.sarps.alumni;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.adapter.Experience_Adapter;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.jsonservice.ServiceHandler;
import com.sarps.alumni.model.Experience_item;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sarps on 2/14/2017.
 */
public class OtherProfileActivity extends AppCompatActivity {

    RecyclerView rc_educationlist, rc_experlist, rc_explist;
    ImageView iv_profile, iv_back;
    TextView tv_username, tv_status, tv_about, tv_title,tv_about_text;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String uid;
    SharedPreferences pref;
    Experience_item experience_item;
    ArrayList<Experience_item> list;
    Experience_Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_profile_layout);
        init();
        list = new ArrayList<>();
        progressDialog = new ProgressDialog(OtherProfileActivity.this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("id");
        tv_username.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_status.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_about.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_about_text.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        get_profile();
        get_educationlist();
    }

    public void init() {
        rc_educationlist = (RecyclerView) findViewById(R.id.rc_educationlist);
        rc_experlist = (RecyclerView) findViewById(R.id.rc_experlist);
        rc_explist = (RecyclerView) findViewById(R.id.rc_explist);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_about_text = (TextView) findViewById(R.id.tv_about_text);
        iv_back = (ImageView) findViewById(R.id.iv_back);
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
                    String u_fname = message.getString("u_fname");
                    String u_lname = message.getString("u_lname");
                    String u_role = message.getString("u_role");
                    String u_image = message.getString("u_image");
                    String u_about = message.getString("u_about");
                    Picasso.with(getApplicationContext()).load(u_image).into(iv_profile);
                    tv_username.setText("" + u_fname + " " + u_lname);
                    tv_title.setText("" + u_fname + " " + u_lname);
                    tv_status.setText(u_role);
                    tv_about_text.setText(u_about);

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

    public void get_educationlist() {

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.EDUCATIONLIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    progressDialog.dismiss();
                    list.clear();
                    String message = jsonObject.getString("message");
                    if (message.equals("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("info");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String e_college = jsonObject1.getString("e_college");
                            String e_course = jsonObject1.getString("c_name");
                            String percent = jsonObject1.getString("percent");
                            String from_year = jsonObject1.getString("from_year");
                            String to_year = jsonObject1.getString("to_year");
                            String is_continue = jsonObject1.getString("is_continue");
                            experience_item = new Experience_item(e_college, percent, from_year, is_continue, e_course, to_year);

                            list.add(experience_item);
                        }
                    } else {
                        System.out.println("message education list :- " + message);
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rc_educationlist.setLayoutManager(layoutManager);
                    adapter = new Experience_Adapter(getApplicationContext(), list);
                    rc_educationlist.setAdapter(adapter);

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
                hm.put("stud_id", uid);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }


}
