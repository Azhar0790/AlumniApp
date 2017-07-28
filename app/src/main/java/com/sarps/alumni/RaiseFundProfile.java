package com.sarps.alumni;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.PlusShare;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarps on 3/1/2017.
 */
public class RaiseFundProfile extends AppCompatActivity {
    TextView tv_username, tv_c_name, tv_raising_amount, tv_request_title, tv_description;
    ImageView iv_profile, iv_emptystar, iv_fillstar;
    ImageView ff, tt, ll, gg;
    Button btn_report;
    EditText et_report;
    String id, heading, desc, amnt, img_link, name;
    TextInputLayout inputLayoutreport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_raise_money);
        init();

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        heading = bundle.getString("heading");
        amnt = bundle.getString("amnt");
        desc = bundle.getString("desc");
        img_link = bundle.getString("img_link");
        name = bundle.getString("name");

        tv_raising_amount.setText("Raising amount: Rs" + amnt);
        tv_request_title.setText(heading);
        tv_description.setText(desc);
        tv_username.setText(name);
        Picasso.with(getApplicationContext()).load(img_link).into(iv_profile);

        iv_emptystar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_fillstar.setVisibility(View.VISIBLE);
                iv_emptystar.setVisibility(View.GONE);
            }
        });
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = "App: Aludent" + "\n" + "Name of the Student: " + name + "\n" + "Raising Money: Rs" + amnt + "\n" + "Description: " + desc;

                try {

                    getPackageManager().getPackageInfo("com.twitter.katana", 0);
                    Intent intent1 = new Intent();
                    intent1.setClassName("com.facebook.katana", "com.facebook.katana.activity.composer.ImplicitShareIntentHandler");
                    intent1.setAction("android.intent.action.SEND");
                    intent1.setType("text/plain");
                    intent1.setType("image/*");
                    intent1.putExtra(android.content.Intent.EXTRA_SUBJECT, "Aludent");
                    intent1.putExtra("android.intent.extra.TEXT", text1);
                    startActivity(intent1);
                } catch (Exception e) {
                    // If we failed (not native FB app installed), try share through SEND
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + text1;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                    startActivity(intent);
                }


            }
        });
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = "App: Aludent" + "\n" + "Name of the Student: " + name + "\n" + "Raising Money: Rs" + amnt + "\n" + "Description: " + desc;
//                try
//                {
//                    // Check if the Twitter app is installed on the phone.
//                    getPackageManager().getPackageInfo("com.twitter.android", 0);
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
//                    intent.setType("text/plain");
//                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Aludent");
//                    intent.putExtra(Intent.EXTRA_TEXT, text1);
//                    startActivity(intent);
//
//                }
//                catch (Exception e)
//                {
//                    Toast.makeText(getApplicationContext(),"Twitter is not installed on this device",Toast.LENGTH_LONG).show();
//
//                }

                Intent linkedinIntent = new Intent(Intent.ACTION_SEND);
                linkedinIntent.setType("text/plain");
                linkedinIntent.putExtra(Intent.EXTRA_TEXT, text1);

                boolean linkedinAppFound = false;
                List<ResolveInfo> matches2 = getPackageManager().queryIntentActivities(linkedinIntent, 0);

                for (ResolveInfo info : matches2) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter.android")) {
                        linkedinIntent.setPackage(info.activityInfo.packageName);
                        linkedinAppFound = true;
                        break;
                    }
                }

                if (linkedinAppFound) {
                    startActivity(linkedinIntent);
                } else {
                    Toast.makeText(RaiseFundProfile.this, "Twitter app not Insatlled in your mobile", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text1 = "App: Aludent" + "\n" + "Name of the Student: " + name + "\n" + "Raising Money: Rs" + amnt + "\n" + "Description: " + desc;
                Intent linkedinIntent = new Intent(Intent.ACTION_SEND);
                linkedinIntent.setType("text/plain");
                linkedinIntent.putExtra(Intent.EXTRA_TEXT, text1);

                boolean linkedinAppFound = false;
                List<ResolveInfo> matches2 = getPackageManager().queryIntentActivities(linkedinIntent, 0);

                for (ResolveInfo info : matches2) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.linkedin")) {
                        linkedinIntent.setPackage(info.activityInfo.packageName);
                        linkedinAppFound = true;
                        break;
                    }
                }

                if (linkedinAppFound) {
                    startActivity(linkedinIntent);
                } else {
                    Toast.makeText(RaiseFundProfile.this, "LinkedIn app not Insatlled in your mobile", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = "App: Aludent" + "\n" + "Name of the Student: " + name + "\n" + "Raising Money: Rs" + amnt + "\n" + "Description: " + desc;

                Intent shareIntent = new PlusShare.Builder(RaiseFundProfile.this)
                        .setType("text/plain")
                        .setText(text1)
                        .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                        .getIntent();

                startActivityForResult(shareIntent, 0);

            }
        });


    }

    public void init() {
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_c_name = (TextView) findViewById(R.id.tv_c_name);
        tv_raising_amount = (TextView) findViewById(R.id.tv_raising_amount);
        tv_request_title = (TextView) findViewById(R.id.tv_request_title);
        tv_description = (TextView) findViewById(R.id.tv_description);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        iv_emptystar = (ImageView) findViewById(R.id.iv_emptystar);
        iv_fillstar = (ImageView) findViewById(R.id.iv_fillstar);
        ff = (ImageView) findViewById(R.id.ff);
        tt = (ImageView) findViewById(R.id.tt);
        gg = (ImageView) findViewById(R.id.gg);
        ll = (ImageView) findViewById(R.id.ll);
        btn_report = (Button) findViewById(R.id.btn_report);

    }

    public void dialog() {
        final Dialog dialog = new Dialog(RaiseFundProfile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_report);
        ImageView iv_wrong = (ImageView) dialog.findViewById(R.id.iv_wrong);
        et_report = (EditText) dialog.findViewById(R.id.et_report);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        et_report.addTextChangedListener(new MyTextWatcher(et_report));
        iv_wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm(et_report.getText().toString());
            }
        });

        dialog.show();
    }

    private void submitForm(String report) {
        if (!validateEmail(report)) {
            return;
        }

//        post_data();
    }


    private boolean validateEmail(String report) {

        if (report.isEmpty() || !isValidEmail(report)) {
            inputLayoutreport.setError(getString(R.string.err_msg_report));
            requestFocus(et_report);
            return false;
        } else {
            inputLayoutreport.setErrorEnabled(false);
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
        String report;

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
                    validateEmail(report);
                    break;
            }
        }
    }

    private Intent getShareIntent(String type, String text) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
        System.out.println("resinfo: " + resInfo);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
//                    share.putExtra(Intent.EXTRA_SUBJECT,  subject);
                    share.putExtra(Intent.EXTRA_TEXT, text);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return null;

            return share;
        }
        return null;
    }
}
