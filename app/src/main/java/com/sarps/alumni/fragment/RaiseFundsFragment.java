package com.sarps.alumni.fragment;

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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sarps.alumni.MainActivity;
import com.sarps.alumni.NewRequestActivity;
import com.sarps.alumni.R;
import com.sarps.alumni.adapter.AcheivementAdapter;
import com.sarps.alumni.adapter.SavedRequestsAdapter;
import com.sarps.alumni.jsonservice.ConfigUrl;
import com.sarps.alumni.jsonservice.CustomRequest;
import com.sarps.alumni.model.Experience_item;
import com.sarps.alumni.model.SavedRequestItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Sarps on 11/4/2016.
 */
public class RaiseFundsFragment extends Fragment {
    SharedPreferences pref;
    Button btn_add_new_request, btn_submit;
    TextView tv_amount, tv_prev_request, tv_date, tv_exp_date;
    RecyclerView rv_savedrequest;
    RequestQueue requestQueue;
    String uid;
    SavedRequestsAdapter adapter;
    ArrayList<SavedRequestItem> list;
    SavedRequestItem savedRequestItem;
    long x;
    View v;
    String distancee;

    // TextInputLayout inputLayoutdesc ,inputLayouttitle;
    public RaiseFundsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_raisefunds, container, false);
        init();
        list = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        uid = pref.getString("uid", "DEFAULT");


        btn_add_new_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewRequestActivity.class));
            }
        });
        tv_prev_request.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Regular.ttf"));

        get_data();

//        get_data_();
        return v;
    }

    public void init() {
        btn_add_new_request = (Button) v.findViewById(R.id.btn_add_new_request);
        tv_prev_request = (TextView) v.findViewById(R.id.tv_prev_request);
        rv_savedrequest = (RecyclerView) v.findViewById(R.id.rv_savedrequest);
    }


    public void get_data() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, ConfigUrl.GETFINALRAISEDREQ_URL + "/" + uid, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    list.clear();
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String number = "" + (i + 1);
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String desc = jsonObject1.getString("desc");
                        String heading = jsonObject1.getString("heading");
                        String req_amt = jsonObject1.getString("req_amt");
                        savedRequestItem = new SavedRequestItem(desc, heading, req_amt, number);
                        list.add(savedRequestItem);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        rv_savedrequest.setLayoutManager(layoutManager);
                        adapter = new SavedRequestsAdapter(getActivity(), list);
                        rv_savedrequest.setAdapter(adapter);
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


    public void get_data_() {

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, ConfigUrl.STYLIST, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
//                    progressDialog.dismiss();

                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        System.out.println("jsonObjectstylist :- " + jsonObject);
                        String st_id = jsonObject2.getString("st_id");
                        // String saloon_id = jsonObject2.getString("saloon_id");
                        String s_name = jsonObject2.getString("s_name");
                        String s_photo = jsonObject2.getString("s_photo");
                        String s_rate = jsonObject2.getString("s_rate");
                        String s_rating = jsonObject2.getString("s_rating");
                        String st_gallery = jsonObject2.getString("st_gallery");
                        String s_miles = jsonObject2.getString("s_miles");
                        String s_address = jsonObject2.getString("s_address");
                        String lat = jsonObject2.getString("lat");
                        String lon = jsonObject2.getString("long");
                        String s_role = jsonObject2.getString("s_role");
                        String s_license = jsonObject2.getString("s_license");
                        String s_email = jsonObject2.getString("s_email");
                        System.out.println("st_id :-" + st_id);
                        //   System.out.println("saloon_id :-"+saloon_id);
                        System.out.println("s_name :-" + s_name);
                        System.out.println("s_photo :-" + s_photo);
                        System.out.println("s_rate :-" + s_rate);
                        System.out.println("s_rating :-" + s_rating);
                        System.out.println("st_gallery :-" + st_gallery);
                        System.out.println("s_miles :-" + s_miles);
                        System.out.println("s_address :-" + s_address);

                        System.out.println("s_role :-" + s_role);
                        System.out.println("s_license :-" + s_license);
                        System.out.println("s_emaillllllll :-" + s_email);


                        double lat2 = Double.parseDouble(lat);
                        double lon2 = Double.parseDouble(lon);
                        double lat1 = Double.parseDouble("19.997453");
                        double lng = Double.parseDouble("73.789802");


                        Double disst1 = distance(lat1, lng, lat2, lon2);
                        x = Math.round(disst1);
                        distancee = Long.toString(x);
                        System.out.println("diastance :- " + x + "miles");
                        Toast.makeText(getActivity(), "Dis:" + x, Toast.LENGTH_SHORT).show();

                        String[] dist = new String[]{distancee};
                        System.out.println("DIST :- " + Arrays.toString(dist));

                        for (int j = 0; j < dist.length; j++) {

                            List<String> listIntegers = Arrays.asList(dist);
                            System.out.println("Before sorting: " + listIntegers);
                            Collections.sort(listIntegers);
                            System.out.println("After sorting: " + listIntegers);
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("json Error" + volleyError);
//                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hm = new HashMap<>();
                //  hm.put("id", id);
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
    }

    private static double distance(double latitude, double longitude, double lat2, double lon2) {
        double theta = longitude - lon2;
        double dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;


//  For KM             dist = dist * 1.609344;
        dist = dist * 0.8684;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    class SavedRequestsAdapter extends RecyclerView.Adapter<SavedRequestsAdapter.Viewholder> {
        Context context;
        ArrayList<SavedRequestItem> list;
        Experience_item experience_item;


        public SavedRequestsAdapter(Context context, ArrayList<SavedRequestItem> list) {
            this.context = context;
            this.list = list;
        }

        class Viewholder extends RecyclerView.ViewHolder {
            TextView tv_heading, tv_price, tv_desc, tv_number;
            LinearLayout ll_savedrequestitem;

            public Viewholder(View itemView) {
                super(itemView);

                tv_heading = (TextView) itemView.findViewById(R.id.tv_heading);
                tv_price = (TextView) itemView.findViewById(R.id.tv_price);
                tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
                tv_number = (TextView) itemView.findViewById(R.id.tv_number);
                ll_savedrequestitem = (LinearLayout) itemView.findViewById(R.id.ll_savedrequestitem);
            }
        }

        @Override
        public void onBindViewHolder(Viewholder holder, final int position) {


            holder.tv_heading.setText("Heading: " + list.get(position).getHeading());
            holder.tv_price.setText("Amount: Rs. " + list.get(position).getReq_amt());
            holder.tv_desc.setText("Description: " + list.get(position).getDesc());
            holder.tv_number.setText(list.get(position).getNumber());


            holder.tv_price.setVisibility(View.GONE);
            holder.tv_desc.setVisibility(View.GONE);

            holder.tv_heading.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
            holder.tv_price.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
            holder.tv_desc.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
            holder.tv_number.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));

            holder.ll_savedrequestitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diaolog(list.get(position).getHeading(), list.get(position).getReq_amt(), list.get(position).getDesc());
                }
            });
        }

        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.savedrequest_item, parent, false);

            return new Viewholder(itemView);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public void diaolog(String heading, String price, String desc) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_request_detail);
            ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.iv_cancel);
            TextView tv_heading = (TextView) dialog.findViewById(R.id.tv_heading);
            TextView tv_price = (TextView) dialog.findViewById(R.id.tv_price);
            TextView tv_desc = (TextView) dialog.findViewById(R.id.tv_desc);


            tv_heading.setText("Heading: " + heading);

            tv_desc.setText("Description: " + desc);

            if (price != null)
                tv_price.setVisibility(View.VISIBLE);
            tv_price.setText("Amount: Rs. " + price);

            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

}