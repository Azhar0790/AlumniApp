package com.sarps.alumni.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.R;
import com.sarps.alumni.adapter.Experience_Adapter;
import com.sarps.alumni.app.AppController;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.jsonservice.ServiceHandler;
import com.sarps.alumni.model.Experience_item;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * Created by Sarps on 11/4/2016.
 */
public class EducationFragment extends Fragment {
    SharedPreferences pref;
    String uid, still_cont;
    EditText et_univeristy, et_percentage;
    Spinner sp_course;
    RecyclerView lv_course;
    static TextView tv_from, tv_to;
    TextView tv_education, tv_univeristy, tv_course, tv_duration, tv_score, tv_percentage, tv_too;
    CheckBox cb_stillcontinuing;
    Button btn_submit;
    String course, coursename;
    ArrayList<String> sp_list, tmp_splist;
    RequestQueue requestQueue;
    Experience_Adapter adapter;
    ArrayList<Experience_item> list;
    Experience_item experience_item;
    ProgressDialog progressDialog;
    View v;
    ArrayAdapter<String> dataAdapter;
    private TextInputLayout inputLayoutcollege;

    public EducationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_education, container, false);
        init();
        tmp_splist = new ArrayList<>();
        tmp_splist.add("courses");

        et_univeristy.addTextChangedListener(new MyTextWatcher(et_univeristy));
        btn_submit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_from.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_to.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_education.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
//        tv_univeristy.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
//        tv_course.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_duration.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_score.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_percentage.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_too.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));

        progressDialog = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        sp_list = new ArrayList<>();
        list = new ArrayList<>();
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "DEFAULT");
        tv_from.setPaintFlags(tv_from.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_to.setPaintFlags(tv_to.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        tv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment_();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm();
            }
        });
        cb_stillcontinuing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tv_to.setVisibility(View.GONE);
                } else {
                    tv_to.setVisibility(View.VISIBLE);

                }
            }
        });
//        get_data();
//        get_data_course();
        new CourseAsynctask().execute();
        new EducationList().execute();


        sp_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = i + 1;
                course = "" + pos;
                coursename = sp_course.getSelectedItem().toString();
                System.out.println("pos :- " + pos);
                System.out.println("coursename :- " + coursename);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        inputLayoutcollege = (TextInputLayout) v.findViewById(R.id.input_layout_college);

        et_univeristy = (EditText) v.findViewById(R.id.et_univeristy);
        sp_course = (Spinner) v.findViewById(R.id.sp_course);
        et_percentage = (EditText) v.findViewById(R.id.et_percentage);
        tv_from = (TextView) v.findViewById(R.id.tv_from);
        tv_to = (TextView) v.findViewById(R.id.tv_to);
        tv_education = (TextView) v.findViewById(R.id.tv_education);
        tv_duration = (TextView) v.findViewById(R.id.tv_duration);
        tv_score = (TextView) v.findViewById(R.id.tv_score);
        tv_percentage = (TextView) v.findViewById(R.id.tv_percentage);
        tv_too = (TextView) v.findViewById(R.id.tv_too);
        cb_stillcontinuing = (CheckBox) v.findViewById(R.id.cb_stillcontinuing);
        lv_course = (RecyclerView) v.findViewById(R.id.lv_course);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        } else if (tv_from.getText().toString() == "") {
            Toast.makeText(getActivity(), "Please select the month and year from date", Toast.LENGTH_SHORT).show();
        } else if (et_percentage.getText().toString().length()==0) {
            Toast.makeText(getActivity(), "Please insert percentage below 100", Toast.LENGTH_SHORT).show();
        } else {
            post_data();
        }
    }

    private boolean validateName() {

        if (et_univeristy.getText().toString().trim().isEmpty()) {
            inputLayoutcollege.setError("Enter Name");
            requestFocus(et_univeristy);
            return false;
        } else {
            inputLayoutcollege.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_univeristy:
                    validateName();
                    break;

            }
        }
    }

    public void post_data() {

        if (cb_stillcontinuing.isChecked()) {
            still_cont = "yes";
        } else {
            still_cont = "no";
        }
        final String university = et_univeristy.getText().toString();
        final String percentage = et_percentage.getText().toString();
        final String from = tv_from.getText().toString();
        final String to = tv_to.getText().toString();
        String[] from_1 = from.split("/");
        String from_m = from_1[0];
        String from_d = from_1[1];
        final String from_y = from_1[2];
        System.out.println("from_y :- " + from_y);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.INSERTEDUCATION_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    list.clear();

                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        System.out.println("message :- " + message);
                        Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("from_year", from_y);
                        editor.putString("e_course", course);
                        editor.commit();
                        new EducationList().execute();
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
                hm.put("college", capitalize(university));
                hm.put("course", capitalize(course));
                hm.put("is_continue", still_cont);
                hm.put("from_year", from_y);
                hm.put("to_year", to);
                hm.put("percent", percentage);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);

    }
    private String capitalize(String line)
    {
        StringTokenizer token =new StringTokenizer(line);
        String CapLine="";
        while(token.hasMoreTokens())
        {
            String tok = token.nextToken().toString();
            CapLine += Character.toUpperCase(tok.charAt(0))+ tok.substring(1)+" ";
        }
        return CapLine.substring(0,CapLine.length()-1);
    }

    class EducationList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("stud_id", uid));
            String jsnStr = sh.makeServiceCall(ConfigUrl.EDUCATIONLIST_URL, ServiceHandler.POST, param);
            if (jsnStr != null) {
                try {

                    list.clear();
                    JSONObject jsonObject = new JSONObject(jsnStr);
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        System.out.println("message :- " + message);
                        JSONArray jsonArray = jsonObject.getJSONArray("info");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String e_college = jsonObject1.getString("e_college");
                            String e_course = jsonObject1.getString("c_name");
                            experience_item = new Experience_item(e_college, e_course);
                            list.add(experience_item);

                        }


                    } else {
                        System.out.println("message education list :- " + message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            lv_course.setLayoutManager(layoutManager);
            adapter = new Experience_Adapter(getActivity(), list);
            lv_course.setAdapter(adapter);
        }


    }

    class CourseAsynctask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            ServiceHandler sh = new ServiceHandler();
            String jsnStr = sh.makeServiceCall(ConfigUrl.LISTCOURSE_URL, ServiceHandler.GET);
            if (jsnStr != null) {
                sp_list.clear();
                try {
                    JSONObject jsonObject = new JSONObject(jsnStr);
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("courses");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String c_name = jsonObject1.getString("c_name");
                            sp_list.add(c_name);
                        }
                    } else {
                        System.out.println("message :- " + message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (sp_list.equals("")) {
                try {
                    dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tmp_splist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_course.setAdapter(dataAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sp_list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_course.setAdapter(dataAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


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
            tv_from.setText(month + "/" + day + "/" + year);

        }

    }

    public static class SelectDateFragment_ extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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
            tv_to.setText(month + "/" + day + "/" + year);
        }

    }
}