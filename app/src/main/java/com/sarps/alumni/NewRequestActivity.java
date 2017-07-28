package com.sarps.alumni;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarps on 2/24/2017.
 */
public class NewRequestActivity extends AppCompatActivity {

    SharedPreferences pref;
    ImageView iv_back;
    Button btn_submit, btn_save;
    TextView tv_amount, tv_date;
    TextView tv_note, tv_point1, tv_point2, tv_point3, tv_point4;
    EditText et_title, et_description, et_amnt;
    private TextInputLayout inputLayoutdesc, inputLayouttitle;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    static final int DATE_DIALOG_ID = 0;
    private int mYear, mMonth, mDay;
    String outputDateStr, outputDateStr2;
    SimpleDateFormat sdf;
    Calendar c;
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_rquest);
        MainActivity.hidetootlbar();
        init();
        et_title.addTextChangedListener(new MyTextWatcher(et_title));
        et_description.addTextChangedListener(new MyTextWatcher(et_description));
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "DEFAULT");

        Bundle bundle = getIntent().getExtras();
        try {
            String heading = bundle.getString("heading");
            String amount = bundle.getString("amount");
            String description = bundle.getString("description");


            if (amount != null) {
                btn_save.setVisibility(View.GONE);
                et_amnt.setText(amount);
                et_description.setText(description);
                et_title.setText(heading);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        String d = sdf.format(c.getTime());
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null, date2 = null;
        try {
            date = sdf.parse(d);
            date2 = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        outputDateStr = outputFormat.format(date);
        outputDateStr2 = sdf.format(date2);
        System.out.println("outputDateStr :- " + outputDateStr);
        System.out.println("outputDateStr2 :-" + outputDateStr2);

        tv_date.setText(outputDateStr);
        tv_date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("Clicked date");
                try {
                    showDialog(DATE_DIALOG_ID);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        tv_amount.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));

        tv_note.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_point1.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_point2.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_point3.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));
        tv_point4.setTypeface(Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf"));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heading = et_title.getText().toString();
                String desc = et_description.getText().toString();
                String req_amnt = et_amnt.getText().toString();
                String exp_date = tv_date.getText().toString();

                post_saved(heading, desc, exp_date, req_amnt, uid);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heading = et_title.getText().toString();
                String desc = et_description.getText().toString();
                String req_amnt = et_amnt.getText().toString();
                String exp_date = tv_date.getText().toString();


                post_submit(heading, desc, exp_date, req_amnt, uid);
            }
        });
//        v.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.
//                        INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                return true;
//            }
//        });


    }

    public void init() {
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_note = (TextView) findViewById(R.id.tv_note);
        tv_point1 = (TextView) findViewById(R.id.tv_point1);
        tv_point2 = (TextView) findViewById(R.id.tv_point2);
        tv_point3 = (TextView) findViewById(R.id.tv_point3);
        tv_point4 = (TextView) findViewById(R.id.tv_point4);
        et_title = (EditText) findViewById(R.id.et_title);
        et_amnt = (EditText) findViewById(R.id.et_amnt);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_save = (Button) findViewById(R.id.btn_save);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_description = (EditText) findViewById(R.id.et_description);
        inputLayoutdesc = (TextInputLayout) findViewById(R.id.input_desc);
        inputLayouttitle = (TextInputLayout) findViewById(R.id.input_title);
    }
    private void submitForm() {
        if (!validateName1()) {
            return;
        }

        if (!validateName()) {
            return;
        }

    }
    private boolean validateName() {
        if (et_description.getText().toString().trim().isEmpty()) {
            inputLayoutdesc.setError("Enter Description");
            requestFocus(et_description);
            return false;
        } else {
            inputLayoutdesc.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateName1() {
        if (et_title.getText().toString().trim().isEmpty()) {
            inputLayouttitle.setError("Enter Title");
            requestFocus(et_title);
            return false;
        } else {
            inputLayouttitle.setErrorEnabled(false);
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

                case R.id.et_title:
                    validateName1();
                    break;
                case R.id.et_description:
                    validateName();
                    break;


            }
        }
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(NewRequestActivity.this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }

        return null;

    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String d_license = "" + new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            c.add(Calendar.DAY_OF_YEAR, -30);
            Date today30 = c.getTime();
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdf.parse(d_license);
                date2 = sdf.parse(outputDateStr);

//                    if(date1.before(today30)) {
                if (date1.after(date2)) {
                    tv_date.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
                    System.out.println("date :- " + new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
//                    }else{

//                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please kindly enter the date from the current date to next 30 days only", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    };

    public void post_saved(final String heading, final String desc, final String exp_date, final String req_amnt, final String id) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog = new ProgressDialog(NewRequestActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.SAVEDRAISEDREQ_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Inserted")) {
                        Toast.makeText(getApplicationContext(), "Uploaded data successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
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
                hm.put("heading", heading);
                hm.put("desc", desc);
                hm.put("req_amt", req_amnt);
                hm.put("exp_date", exp_date);
                hm.put("stud_id", id);


                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }

    public void post_submit(final String heading, final String desc, final String exp_date, final String req_amnt, final String id) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog = new ProgressDialog(NewRequestActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("loading....");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ConfigUrl.FINALRAISEDREQ_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    progressDialog.dismiss();
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);
                    if (message.equals("Inserted")) {
                        Toast.makeText(getApplicationContext(), "Uploaded data successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
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
                hm.put("heading", heading);
                hm.put("desc", desc);
                hm.put("req_amt", req_amnt);
                hm.put("exp_date", exp_date);
                hm.put("stud_id", id);


                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
