package com.sarps.alumni.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.MainActivity;
import com.sarps.alumni.R;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarps on 11/18/2016.
 */
public class About_usFragment extends Fragment {
TextView tv_aboutus1,tv_aboutus2,tv_aboutus3,tv_address,tv_link;
    EditText et_msg;
    Button btn_submit;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String email;
    SharedPreferences pref;
    public About_usFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        progressDialog = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        email = pref.getString("email", "DEFAULT");
        tv_aboutus1=(TextView)v.findViewById(R.id.tv_aboutus1);
        tv_aboutus2=(TextView)v.findViewById(R.id.tv_aboutus2);
        tv_aboutus3=(TextView)v.findViewById(R.id.tv_aboutus3);
        tv_address=(TextView)v.findViewById(R.id.tv_address);
        tv_link=(TextView)v.findViewById(R.id.tv_link);
        tv_aboutus1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_aboutus2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_aboutus3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_address.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_link.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));

        tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_message();
            }
        });

        return v;
    }

    public void dialog_message()
    {
        final Dialog dialog=new Dialog((MainActivity)getActivity());
        dialog.setContentView(R.layout.message_dialog);
        dialog.setTitle("Send message to admin");
        et_msg=(EditText)dialog.findViewById(R.id.et_msg);
        btn_submit=(Button) dialog.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_about(et_msg.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void post_about(final String msg) {

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.SENDMAIL_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    System.out.println(".............");
                    String message = jsonObject.getString("message");
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
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
                hm.put("u_mail", email);
                hm.put("u_message",msg);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }
}