package com.sarps.alumni;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.jsonservice.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sarps on 11/19/2016.
 */
public class Login_Email extends AppCompatActivity {
    Button btn_register, btn_login;
    EditText et_email, et_password;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SharedPreferences pref;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        init();

        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_password.addTextChangedListener(new MyTextWatcher(et_password));
        et_email.setTypeface(Typeface.createFromAsset(getAssets(),"Raleway-Regular.ttf"));
        et_password.setTypeface(Typeface.createFromAsset(getAssets(),"Raleway-Regular.ttf"));
        btn_login.setTypeface(Typeface.createFromAsset(getAssets(),"Raleway-Regular.ttf"));
        btn_register.setTypeface(Typeface.createFromAsset(getAssets(),"Raleway-Regular.ttf"));

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(Login_Email.this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                i.putExtra("email", et_email.getText().toString());
                startActivity(i);
            }
        });
    }

    public void init() {
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
    }
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
       post_data();
    }


    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
                inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(et_email);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (et_password.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(et_password);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }



    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
    public void post_data() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.LOGIN_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    System.out.println(".............");
                    String message = jsonObject.getString("message");

                    if (message.equals("Login Successful")) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("userinfo");
                        String uid = jsonObject1.getString("u_id");
                        String u_fname = jsonObject1.getString("u_fname");
                        String u_gender = jsonObject1.getString("u_gender");
                        String u_mobile = jsonObject1.getString("u_mobile");
                        String u_mail = jsonObject1.getString("u_mail");
                        String u_address = jsonObject1.getString("u_address");
                        String u_role = jsonObject1.getString("u_role");
                        String u_dob = jsonObject1.getString("u_dob");
                        String u_image = jsonObject1.getString("u_image");
                        System.out.println("uid :- " + uid);
                        System.out.println("u_fname :- " + u_fname);
                        System.out.println("u_mail :- " + u_mail);
                        System.out.println("u_mail :- " + u_mail);
                        System.out.println("u_image :- " + u_image);

                        SharedPreferences.Editor editor1 = pref.edit();
                        editor1.putString("uid", uid);
                        editor1.putString("pref_email", "pref_email");
                        editor1.putString("role", u_role);
                        editor1.putString("email", u_mail);
                        editor1.putString("username", u_fname);
                        editor1.putString("profile_pic", u_image);
                        editor1.commit();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("message :- " + message);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

                hm.put("emailid", et_email.getText().toString());
                    hm.put("password", et_password.getText().toString());
//                hm.put("login_type", "0");
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
