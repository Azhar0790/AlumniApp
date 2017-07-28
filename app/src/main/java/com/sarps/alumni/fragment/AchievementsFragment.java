package com.sarps.alumni.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.R;
import com.sarps.alumni.adapter.AcheivementAdapter;
import com.sarps.alumni.adapter.Experience_Adapter;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.model.Experience_item;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * Created by Sarps on 11/4/2016.
 */
public class AchievementsFragment extends Fragment {

    SharedPreferences pref;
    EditText et_achievements;
    TextView tv_achv;
    static TextView tv_achv_date;
    Button btn_submit;
    String uid;
    RequestQueue requestQueue;
    AcheivementAdapter adapter;
    ArrayList<Experience_item> list;
    Experience_item experience_item;
    RecyclerView rc_achievements;
    ProgressDialog progressDialog;
    View v;

    public AchievementsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_achieve, container, false);
        init();
        tv_achv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_achv_date.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        et_achievements.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        btn_submit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        progressDialog = new ProgressDialog(getActivity());
        list = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "DEFAULT");
        get_data();
        tv_achv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                System.out.println("from clicked :-");
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_achievements.getText().toString().length() > 0) {
                    post_data();
                } else {
                    Toast.makeText(getActivity(), "Pleas write about your achievements", Toast.LENGTH_SHORT).show();
                }

            }
        });
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        return v;
    }

    public void init() {
        tv_achv = (TextView) v.findViewById(R.id.tv_achv);
        et_achievements = (EditText) v.findViewById(R.id.et_achievements);
        tv_achv_date = (TextView) v.findViewById(R.id.tv_achv_date);
        rc_achievements = (RecyclerView) v.findViewById(R.id.rc_achievements);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);
    }

    public void post_data() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading..");
        progressDialog.show();
        final String acheviments = et_achievements.getText().toString();
        final String date = tv_achv_date.getText().toString();
        System.out.println("acheviments :- " + acheviments);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.INSERTACHIVEMENT_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    System.out.println("jsonObject :- " + jsonObject);
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        System.out.println("message :- " + message);
                        Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        et_achievements.setText("");

                        get_data();
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
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hm = new HashMap<>();
                hm.put("stud_id", uid);
                hm.put("achievement", capitalize(acheviments));
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }

    private String capitalize(String line) {
        StringTokenizer token = new StringTokenizer(line);
        String CapLine = "";
        while (token.hasMoreTokens()) {
            String tok = token.nextToken().toString();
            CapLine += Character.toUpperCase(tok.charAt(0)) + tok.substring(1) + " ";
        }
        return CapLine.substring(0, CapLine.length() - 1);
    }

    public void get_data() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.ACHEIVEMENTLIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    String message = jsonObject.getString("message");
                    if (message.equals("Success")) {
                        System.out.println("message :- " + message);
                        JSONArray jsonArray = jsonObject.getJSONArray("info");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String achievement = jsonObject1.getString("achievement");
                            experience_item = new Experience_item(achievement, null);
                            list.add(experience_item);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            rc_achievements.setLayoutManager(layoutManager);
                            adapter = new AcheivementAdapter(getActivity(), list);
                            rc_achievements.setAdapter(adapter);
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
                hm.put("stud_id", uid);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            tv_achv_date.setText(month + "/" + day + "/" + year);
        }

    }

}