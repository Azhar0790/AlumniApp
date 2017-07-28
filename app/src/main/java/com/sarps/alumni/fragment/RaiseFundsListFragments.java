package com.sarps.alumni.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.R;
import com.sarps.alumni.adapter.RaiseFundsAdapter;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.model.SavedRequestItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarps on 2/25/2017.
 */
public class RaiseFundsListFragments extends Fragment {
    SharedPreferences pref;
    RecyclerView rc_savedrequest;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String uid;
    View v;
    RaiseFundsAdapter adapter;
    ArrayList<SavedRequestItem> list;
    SavedRequestItem savedRequestItem;

    // TextInputLayout inputLayoutdesc ,inputLayouttitle;
    public RaiseFundsListFragments() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_savedrequests, container, false);
        init();

        progressDialog = new ProgressDialog(getActivity());
        list = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "");

        get_data();
        return v;
    }

    public void init() {
        rc_savedrequest = (RecyclerView) v.findViewById(R.id.rc_savedrequest);
    }

    public void get_data() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, ConfigUrl.RAISEDREQLIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    System.out.println("json str" + jsonObject.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String u_fname = jsonObject1.getString("u_fname");
                        String u_gender = jsonObject1.getString("u_gender");
                        String u_image = jsonObject1.getString("u_image");
                        String heading = jsonObject1.getString("heading");
                        String desc = jsonObject1.getString("desc");
                        String req_amt = jsonObject1.getString("req_amt");
                        String stud_id = jsonObject1.getString("stud_id");
                        savedRequestItem = new SavedRequestItem(u_image, u_fname, null, req_amt, desc, stud_id,heading);
                        list.add(savedRequestItem);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        rc_savedrequest.setLayoutManager(layoutManager);
                        adapter = new RaiseFundsAdapter(getActivity(), list);
                        rc_savedrequest.setAdapter(adapter);
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
                return hm;
            }
        };
        requestQueue.add(jsObjRequest);
    }


}
