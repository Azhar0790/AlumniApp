package com.sarps.alumni;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.adapter.CustomList_adapter;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.jsonservice.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Sarps on 11/15/2016.
 */
public class ProfileFillUpActivity extends AppCompatActivity {

    SharedPreferences pref;
    ImageView iv_profile;
    TextView tv_username, tv_gender, tv_email, tv_dob, tv_mobile, tv_who;
    EditText et_mobile, dp_dob;
    ListView lv_st;
    GridView gv_gender;
    //    DatePicker dp_dob;
    Button btn_continue;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    String username, tv_m_string, tv_f_string, tv_st_string, tv_ex_string, login_type, email;
    String profile_pic = null;
    String rol = null, gen = null;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    private int previousSelectedPosition = -1;

    private TextInputLayout inputLayoutEmail, inputLayoutPhone, inputLayoutDate;
    private String names[] = {
            "Student - Currently studying in any academic course",
            "Ex-Student - Already passed out and working currently"};

    private String desc[] = {
            "",
            "",
            "",
            "",
            "",
            ""

    };


    private Integer imageid[] = {
            R.drawable.student,
            R.drawable.exstudent,
    };
    private String gender_names[] = {
            "Male",
            "Female"};

    private String gender_desc[] = {
            "",
            "",
            "",
            "",
            "",
            ""

    };


    private Integer gender_imageid[] = {
            R.drawable.male,
            R.drawable.female,
    };
    CustomList_adapter customList, customList2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_form);
        init();
        customList = new CustomList_adapter(ProfileFillUpActivity.this, names, desc, imageid);
        customList2 = new CustomList_adapter(ProfileFillUpActivity.this, gender_names, gender_desc, gender_imageid);
        inputLayoutDate = (TextInputLayout) findViewById(R.id.input_layout_date);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_mob);

        et_mobile.addTextChangedListener(new MyTextWatcher(et_mobile));
        tv_email.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_username.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_gender.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_who.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        btn_continue.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));


//        progressDialog = new ProgressDialog(ProfileFillUpActivity.this);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        profile_pic = bundle.getString("profile_pic");
        login_type = bundle.getString("login_type");
        email = bundle.getString("email");
        tv_email.setText(email);
        tv_username.setText(username);
        Picasso.with(getApplicationContext()).load(profile_pic).into(iv_profile);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });
        lv_st.setAdapter(customList);
        gv_gender.setAdapter(customList2);
        lv_st.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_st_string = ((TextView) view.findViewById(R.id.textViewName)).getText().toString();
            }
        });
        gv_gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_m_string = ((TextView) view.findViewById(R.id.textViewName)).getText().toString();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("tv_st_string :- " + tv_st_string);
                System.out.println("tv_m_string :- " + tv_m_string);
                submitForm();

            }
        });
        post_direct_login(email,login_type);
    }

    public void init() {
        dp_dob = (EditText) findViewById(R.id.dp_dob);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_email = (TextView) findViewById(R.id.tv_email);
        dateView = (EditText) findViewById(R.id.dp_dob);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_who = (TextView) findViewById(R.id.tv_who);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        lv_st = (ListView) findViewById(R.id.lv_st);
        gv_gender = (GridView) findViewById(R.id.gv_gender);
        btn_continue = (Button) findViewById(R.id.btn_continue);

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void post_direct_login(final String email_, final String login_typr) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog = new ProgressDialog(ProfileFillUpActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.REGISTER_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Direct Login")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("info");
                        String uid = jsonObject1.getString("u_id");
                        System.out.println("uid :- " + uid);
                        SharedPreferences.Editor editor1 = pref.edit();
                        editor1.putString("uid", uid);
                        editor1.putString("pref_email", "pref_email");
                        editor1.putString("role",rol);
                        editor1.putString("email", tv_email.getText().toString());
                        editor1.putString("username", username);
                        editor1.putString("profile_pic", profile_pic);
                        editor1.commit();
                        Toast.makeText(getApplicationContext(), "Uploaded data successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (message.equals("Success")){
                        post_data();
                        System.out.println("message :- " + message);
//                        Toast.makeText(getApplicationContext(), "Email is already exist", Toast.LENGTH_SHORT).show();
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
                hm.put("emailid", email_);
                hm.put("login_type", login_typr);

                System.out.println("email :- "+tv_email.getText().toString());
                System.out.println("login_type :- "+login_type);

                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }
    public void post_data() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String name = tv_username.getText().toString();
        final String split[] = name.split("\\s+");
        System.out.println("rol :- " + rol);
        if (tv_st_string == "Student - Currently studying in any academic course") {
            rol = "stud";
        } else {
            rol = "exstud";
        }

        if (tv_m_string == "Male") {
            gen = "male";
        } else {
            gen = "female";
        }


        System.out.println("rol :- " + rol);
        System.out.println("gen :- " + gen);
        progressDialog = new ProgressDialog(ProfileFillUpActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.REGISTER_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    String message = jsonObject.getString("message");
                    if (message.equals("Success")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("info");
                        String uid = jsonObject1.getString("u_id");
                        System.out.println("uid :- " + uid);
                        SharedPreferences.Editor editor1 = pref.edit();
                        editor1.putString("uid", uid);
                        editor1.putString("pref_email", "pref_email");
                        editor1.putString("role",rol);
                        editor1.putString("email", tv_email.getText().toString());
                        editor1.putString("username", username);
                        editor1.putString("profile_pic", profile_pic);
                        editor1.commit();
                        Toast.makeText(getApplicationContext(), "Uploaded data successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        System.out.println("message :- " + message);
                        Toast.makeText(getApplicationContext(), "Email is already exist", Toast.LENGTH_SHORT).show();
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
                hm.put("fname", split[0]);
                hm.put("lname", split[1]);
                hm.put("gender", gen);
                hm.put("mobile", et_mobile.getText().toString());
                hm.put("address", "");
                hm.put("role", rol);
                hm.put("dob", dateView.getText().toString());
                hm.put("imagename", profile_pic);
                hm.put("emailid", tv_email.getText().toString());
                hm.put("password", "0");
                hm.put("login_type", login_type);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }


    }


    private void submitForm() {
        if (!isValidMobile()) {
            return;
        }

        post_data();
//        p_data();
    }


    private boolean isValidMobile() {
        if (et_mobile.length() < 10 || et_mobile.length() > 12) {

            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(et_mobile);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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

                case R.id.et_mobile:
                    isValidMobile();
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
