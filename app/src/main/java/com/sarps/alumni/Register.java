package com.sarps.alumni;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarps on 11/19/2016.
 */
public class Register extends AppCompatActivity {
    Button btn_register, btn_browse;
    EditText et_password, et_username, et_email;
    TextView tv_gender, tv_who;
    ImageView iv_profile;
    EditText et_mobile;
    ListView lv_st;
    GridView gv_gender;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    String tv_m_string, tv_f_string, tv_st_string, tv_ex_string;
    String profile_pic = null, rol, gen;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    SharedPreferences pref;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private TextInputLayout inputLayoutDate, inputLayoutEmail, inputLayoutPassword, inputLayoutPhone, inputLayoutname;
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
        setContentView(R.layout.activity_register);
        init();
        customList = new CustomList_adapter(Register.this, names, desc, imageid);
        customList2 = new CustomList_adapter(Register.this, gender_names, gender_desc, gender_imageid);
        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_password.addTextChangedListener(new MyTextWatcher(et_password));
        et_username.addTextChangedListener(new MyTextWatcher(et_username));
        et_mobile.addTextChangedListener(new MyTextWatcher(et_mobile));

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutDate = (TextInputLayout) findViewById(R.id.input_layout_date);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_mob);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_pass);
        inputLayoutname = (TextInputLayout) findViewById(R.id.input_layout_name);
        et_username.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_gender.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        et_email.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_who.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        et_password.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        btn_register.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));

        progressDialog = new ProgressDialog(Register.this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
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


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
//                post_data();
            }
        });
        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
    }

    public void init() {
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_browse = (Button) findViewById(R.id.btn_browse);
        et_email = (EditText) findViewById(R.id.et_email);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        dateView = (TextView) findViewById(R.id.dp_dob);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_who = (TextView) findViewById(R.id.tv_who);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        lv_st = (ListView) findViewById(R.id.lv_st);
        gv_gender = (GridView) findViewById(R.id.gv_gender);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                iv_profile.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

                iv_profile.buildDrawingCache();
                Bitmap bmap = iv_profile.getDrawingCache();
                profile_pic = Base64.encodeToString(getBytesFromBitmap(bmap),
                        Base64.NO_WRAP);
                System.out.println("profile_pic :- " + profile_pic);
                System.out.println("imgDecodableString :- " + imgDecodableString);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
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

    public void post_data() {
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
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.REGISTER_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    System.out.println(".............");
                    String message = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    if (message.equals("Success")) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("info");
                        String uid = jsonObject1.getString("u_id");
                        System.out.println("uid :- " + uid);
                        SharedPreferences.Editor editor1 = pref.edit();
                        editor1.putString("uid", uid);
                        editor1.putString("role",rol);
                        editor1.commit();
                        Toast.makeText(getApplicationContext(), "Uploaded data successfully", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), Login_Email.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Email is already exist", Toast.LENGTH_SHORT).show();
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
                hm.put("fname", et_username.getText().toString());
                hm.put("lname", "");
                hm.put("gender", gen);
                hm.put("mobile", et_mobile.getText().toString());
                hm.put("address", "");
                hm.put("role", rol);
                hm.put("dob", dateView.getText().toString());
                hm.put("imagename", profile_pic);
                hm.put("emailid", et_email.getText().toString());
                hm.put("password", et_password.getText().toString());
                hm.put("login_type", "0");
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }


    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }
        if (!isValidMobile()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        post_data();
    }

    private boolean validateName() {
        if (et_username.getText().toString().trim().isEmpty()) {
            inputLayoutname.setError("Enter Username");
            requestFocus(et_username);
            return false;
        } else {
            inputLayoutname.setErrorEnabled(false);
        }

        return true;
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
                case R.id.et_username:
                    validateName();
                    break;
                case R.id.et_email:
                    validateEmail();
                    break;
                case R.id.et_password:
                    validatePassword();
                    break;
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