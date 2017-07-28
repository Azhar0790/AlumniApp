package com.sarps.alumni.fragment;

/**
 * Created by sarps-preeti on 11/11/2016.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.ClassmatesActivity;
import com.sarps.alumni.MainActivity;
import com.sarps.alumni.R;
import com.sarps.alumni.Seek;
import com.sarps.alumni.adapter.CustomList_adapter;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Connections extends Fragment {


    private ListView listView;
    private LinearLayout ll_angel,ll_nurture,ll_classmates,ll_clgmates,ll_snr,ll_mntr,ll_endoresment;
    TextView tv_t1,tv_c_count,tv_cg_count,tv_s_count,tv_m_count,tv_n_count,tv_a_count,tv_e_count;
    Button seek;
    String desc_two;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    SharedPreferences pref;
    String from_year,e_course;
    View v;
    public Connections() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.connections, container, false);
        init();
        progressDialog = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        from_year=pref.getString("from_year","DEFAULT");
        e_course=pref.getString("e_course","DEFAULT");
        System.out.println("from_year :- "+from_year);
        System.out.println("e_course :- "+e_course);
        seek = (Button) v.findViewById(R.id.seek);
        tv_t1 = (TextView) v.findViewById(R.id.tv_t1);
        tv_t1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Seek.class);
                startActivity(i);
            }
        });
        clgmates_count();
        classmates_count();

        ll_classmates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ClassmatesActivity.class);
                i.putExtra("title","Classmates");
                i.putExtra("from_year",from_year);
                i.putExtra("e_course",e_course);
                startActivity(i);
            }
        });
        ll_clgmates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ClassmatesActivity.class);
                i.putExtra("title","Collegemates");
                i.putExtra("from_year",from_year);
                startActivity(i);
            }
        });


        return v;
    }

    public void init(){

        tv_t1 = (TextView) v.findViewById(R.id.tv_t1);
        tv_c_count = (TextView) v.findViewById(R.id.tv_c_count);
        tv_cg_count = (TextView) v.findViewById(R.id.tv_cg_count);
        tv_s_count = (TextView) v.findViewById(R.id.tv_s_count);
        tv_m_count = (TextView) v.findViewById(R.id.tv_m_count);
        tv_n_count = (TextView) v.findViewById(R.id.tv_n_count);
        tv_a_count = (TextView) v.findViewById(R.id.tv_a_count);
        tv_e_count = (TextView) v.findViewById(R.id.tv_e_count);

        ll_angel = (LinearLayout) v.findViewById(R.id.ll_angel);
        ll_nurture = (LinearLayout) v.findViewById(R.id.ll_nurture);
        ll_classmates = (LinearLayout) v.findViewById(R.id.ll_classmates);
        ll_clgmates = (LinearLayout) v.findViewById(R.id.ll_clgmates);
        ll_snr = (LinearLayout) v.findViewById(R.id.ll_snr);
        ll_mntr = (LinearLayout) v.findViewById(R.id.ll_mntr);
        ll_endoresment = (LinearLayout) v.findViewById(R.id.ll_endoresment);
    }
 public void classmates_count(){
     CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.CLASSMATESCOUNT_URL, null, new Response.Listener<JSONObject>() {

         @Override
         public void onResponse(JSONObject jsonObject) {
             try {
                 String message = jsonObject.getString("message");
                 if (message.equals("Success")) {
                     desc_two=jsonObject.getString("info");
                     System.out.println("message :- " + message);
                     tv_c_count.setText(desc_two);
                 } else {
                     System.out.println("message :- " + message);
                     desc_two=jsonObject.getString("0");
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
 public void clgmates_count(){
     CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.CLGMATESCOUNT_URL, null, new Response.Listener<JSONObject>() {

         @Override
         public void onResponse(JSONObject jsonObject) {
             try {
                 String message = jsonObject.getString("message");
                 if (message.equals("Success")) {
                     desc_two=jsonObject.getString("cnt");
                     System.out.println("message :- " + message);
                     tv_cg_count.setText(desc_two);
                 } else {
                     System.out.println("message :- " + message);
                     desc_two=jsonObject.getString("0");
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
