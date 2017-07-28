package com.sarps.alumni.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sarps.alumni.R;


/**
 * Created by Sarps on 11/4/2016.
 */
public class MessagesFragment extends Fragment {
    TextView tv_msg, tv_too, tv_new_msg;
    Button btn_submit;
    EditText et_to;

    View v;
    private TextInputLayout inputLayoutmessage;
    public MessagesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.message, container, false);
        init();
        et_to.addTextChangedListener(new MyTextWatcher(et_to));
        btn_submit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_msg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
//        tv_too.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));
        tv_new_msg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Raleway-Regular.ttf"));


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
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
        inputLayoutmessage = (TextInputLayout) v.findViewById(R.id.input_layout_message);
      et_to=(EditText)v.findViewById(R.id.et_to);

        btn_submit = (Button) v.findViewById(R.id.btn_submit);
        tv_msg = (TextView)v.findViewById(R.id.tv_msg);
//        tv_too = (TextView)v.findViewById(R.id.tv_too);
        tv_new_msg = (TextView)v.findViewById(R.id.tv_new_msg);
        btn_submit=(Button)v.findViewById(R.id.btn_submit);
    }





    private void submitForm() {
        if (!validateName()) {
            return;
        }
//        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (et_to.getText().toString().trim().isEmpty()) {
            inputLayoutmessage.setError("Enter Name");
            requestFocus(et_to);
            return false;
        } else {
            inputLayoutmessage.setErrorEnabled(false);
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
                case R.id.et_to:
                    validateName();
                    break;


            }
        }
    }

}