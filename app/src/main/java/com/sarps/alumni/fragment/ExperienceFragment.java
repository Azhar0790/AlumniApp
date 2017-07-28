package com.sarps.alumni.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.MainActivity;
import com.sarps.alumni.R;
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
 * Created by Sarps on 11/16/2016.
 */
public class ExperienceFragment extends Fragment {
    SharedPreferences pref;
    String uid, still_cont;
    EditText et_designation, et_response;
    AutoCompleteTextView et_company;
    RecyclerView lv_course;
    static TextView tv_from, tv_to;
    TextView tv_resp, tv_company, tv_designation, tv_duration, tv_too, tv_experience;
    CheckBox cb_stillcontinuing;
    Button btn_submit, btn_skip;
    String course;
    RequestQueue requestQueue;
    Experience_Adapter adapter;
    ArrayList<Experience_item> list;
    ArrayList<String> company_list;
    Experience_item experience_item;
    ProgressDialog progressDialog;
    View v;
    private TextInputLayout inputLayoutname, inputLayoutdesc;

    public ExperienceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_experience, container, false);
        init();
        et_company.addTextChangedListener(new MyTextWatcher(et_company));
        et_designation.addTextChangedListener(new MyTextWatcher(et_designation));
        btn_submit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        btn_skip.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_from.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_to.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_resp.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
//        tv_company.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_too.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
//        tv_designation.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_duration.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));
        tv_experience.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));

        progressDialog = new ProgressDialog(getActivity());
        list = new ArrayList<>();
        company_list = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
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
        get_companyname();
        get_data();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences.Editor editor=pref.edit();
//                editor.putString("role", "role");
//                editor.apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
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
        inputLayoutname = (TextInputLayout) v.findViewById(R.id.input_layout_name);
        inputLayoutdesc = (TextInputLayout) v.findViewById(R.id.input_layout_desc);

        et_company = (AutoCompleteTextView) v.findViewById(R.id.et_company);
        et_designation = (EditText) v.findViewById(R.id.et_designation);
        et_response = (EditText) v.findViewById(R.id.et_response);
        tv_from = (TextView) v.findViewById(R.id.tv_from);
        tv_to = (TextView) v.findViewById(R.id.tv_to);
        tv_too = (TextView) v.findViewById(R.id.tv_too);
        tv_resp = (TextView) v.findViewById(R.id.tv_resp);
//        tv_company = (TextView) v.findViewById(R.id.tv_company);
//        tv_designation = (TextView) v.findViewById(R.id.tv_designation);
        tv_duration = (TextView) v.findViewById(R.id.tv_duration);
        tv_experience = (TextView) v.findViewById(R.id.tv_experience);

        cb_stillcontinuing = (CheckBox) v.findViewById(R.id.cb_stillcontinuing);
        lv_course = (RecyclerView) v.findViewById(R.id.lv_course);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);
        btn_skip = (Button) v.findViewById(R.id.btn_skip);
    }


    public void post_data() {
        if (cb_stillcontinuing.isChecked()) {
            still_cont = "yes";
        } else {
            still_cont = "no";
        }
        final String company = et_company.getText().toString();
        final String designation = et_designation.getText().toString();
        final String response = et_response.getText().toString();
        final String from = tv_from.getText().toString();
        final String to = tv_to.getText().toString();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading..");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.INSERTEXPERIENCE_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    progressDialog.dismiss();
                    System.out.println(".............");
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        System.out.println("message :- " + message);
                        Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        get_data();
                    } else {
                        Toast.makeText(getActivity(), "Above data is not added yet", Toast.LENGTH_SHORT).show();
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
                hm.put("company", capitalize(company));
                hm.put("ex_designation", capitalize(designation));
                hm.put("responsibilities", capitalize(response));
                hm.put("is_working", still_cont);
                hm.put("experience_from", from);
                hm.put("experience_to", to);
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

    public void get_companyname() {

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, ConfigUrl.COMPANYLIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    company_list.clear();
                    String message = jsonObject.getString("message");
                    if (message.equals("Success")) {
                        System.out.println("message :- " + message);
                        JSONArray jsonArray = jsonObject.getJSONArray("companies");
                        System.out.println("jsonArray :- " + jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String c_name = jsonObject1.getString("c_name");
                            System.out.println("c_name :- " + c_name);
                            company_list.add(c_name);
                            ArrayAdapter<String> c_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, company_list);
                            et_company.setAdapter(c_adapter);
                            et_company.showDropDown();
                            c_adapter.notifyDataSetChanged();
                        }
                    } else {
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

    public void get_data() {

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.EXPERIENCELIST_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Success")) {
                        System.out.println("message :- " + message);
                        JSONArray jsonArray = jsonObject.getJSONArray("info");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String ex_company = jsonObject1.getString("ex_company");
                            String ex_designation = jsonObject1.getString("ex_designation");
                            experience_item = new Experience_item(ex_company, ex_designation);
                            list.add(experience_item);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            lv_course.setLayoutManager(layoutManager);
                            adapter = new Experience_Adapter(getActivity(), list);
                            lv_course.setAdapter(adapter);
                        }
                    } else {
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


    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validateName1()) {
            return;
        }
        post_data();

    }

    private boolean validateName() {
        if (et_company.getText().toString().trim().isEmpty()) {
            inputLayoutname.setError("Enter Company name");
            requestFocus(et_company);
            return false;
        } else {
            inputLayoutname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName1() {
        if (et_designation.getText().toString().trim().isEmpty()) {
            inputLayoutdesc.setError("Enter designation");
            requestFocus(et_designation);
            return false;
        } else {
            inputLayoutdesc.setErrorEnabled(false);
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
                case R.id.et_company:
                    validateName();
                    break;
                case R.id.et_designation:
                    validateName1();
                    break;
            }
        }
    }

}