package com.sarps.alumni;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.adapter.Classmates_Adapter;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.model.Classmates_item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarps on 11/14/2016.
 */
public class ClassmatesActivity extends AppCompatActivity {
    TextView tv_title;
    ImageView iv_title_image, iv_back;
    RecyclerView rc_classmates;
    Classmates_Adapter adapter;
    ArrayList<Classmates_item> list;
    Classmates_item classmates_item;
    String title, from_year, e_course;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classmate);
        init();
        list = new ArrayList<>();
        progressDialog = new ProgressDialog(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        tv_title.setText(title);
        if (title.equals("Classmates")) {
            iv_title_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.classmates));
            from_year = bundle.getString("from_year");
            e_course = bundle.getString("e_course");
        }
        if (title.equals("Collegemates")) {
            iv_title_image.setBackgroundDrawable(getResources().getDrawable(R.drawable.collegemates));
            from_year = bundle.getString("from_year");
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        classmates_count();
        clgmatesmates_count();
    }

    public void init() {
        rc_classmates = (RecyclerView) findViewById(R.id.rc_classmates);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_title_image = (ImageView) findViewById(R.id.iv_title_image);
    }

    public void classmates_count() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.CLASSMATESLIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("info");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String u_id = jsonObject1.getString("u_id");
                            String e_college = jsonObject1.getString("e_college");
                            String e_course = jsonObject1.getString("e_course");
                            String from_year = jsonObject1.getString("from_year");
                            String percent = jsonObject1.getString("percent");
                            String u_fname = jsonObject1.getString("u_fname");
                            String u_image = jsonObject1.getString("u_image");
                            String u_lname = jsonObject1.getString("u_lname");
                            classmates_item = new Classmates_item(u_id, e_college, e_course, from_year, percent, u_fname, u_image, u_lname);
                            list.add(classmates_item);
                            adapter = new Classmates_Adapter(getApplicationContext(), list);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rc_classmates.setLayoutManager(mLayoutManager);
                            rc_classmates.setAdapter(adapter);
                        }

                    } else {
                        System.out.println("message :- " + message);
                    }


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
                hm.put("from_year", from_year);
                hm.put("e_course", e_course);
                return hm;
            }
        };
        requestQueue.add(jsObjRequest);
    }

    public void clgmatesmates_count() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.CLGMATESLIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("cnt");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String u_id = jsonObject1.getString("u_id");
                            String e_college = jsonObject1.getString("e_college");
                            String e_course = jsonObject1.getString("e_course");
                            String from_year = jsonObject1.getString("from_year");
                            String percent = jsonObject1.getString("percent");
                            String u_fname = jsonObject1.getString("u_fname");
                            String u_image = jsonObject1.getString("u_image");
                            String u_lname = jsonObject1.getString("u_lname");
                            classmates_item = new Classmates_item(u_id, e_course, from_year, u_fname, u_lname, u_image);
                            list.add(classmates_item);
                            adapter = new Classmates_Adapter(getApplicationContext(), list);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rc_classmates.setLayoutManager(mLayoutManager);
                            rc_classmates.setAdapter(adapter);
                        }

                    } else {
                        System.out.println("message :- " + message);
                    }


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
                hm.put("from_year", from_year);
                return hm;
            }
        };
        requestQueue.add(jsObjRequest);
    }
    public void senior_count() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.CLGMATESLIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("cnt");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String u_id = jsonObject1.getString("u_id");
                            String e_college = jsonObject1.getString("e_college");
                            String e_course = jsonObject1.getString("e_course");
                            String from_year = jsonObject1.getString("from_year");
                            String percent = jsonObject1.getString("percent");
                            String u_fname = jsonObject1.getString("u_fname");
                            String u_image = jsonObject1.getString("u_image");
                            String u_lname = jsonObject1.getString("u_lname");
                            classmates_item = new Classmates_item(u_id, e_course, from_year, u_fname, u_lname, u_image);
                            list.add(classmates_item);
                            adapter = new Classmates_Adapter(getApplicationContext(), list);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rc_classmates.setLayoutManager(mLayoutManager);
                            rc_classmates.setAdapter(adapter);
                        }

                    } else {
                        System.out.println("message :- " + message);
                    }


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
                hm.put("from_year", from_year);
                return hm;
            }
        };
        requestQueue.add(jsObjRequest);
    }
}
