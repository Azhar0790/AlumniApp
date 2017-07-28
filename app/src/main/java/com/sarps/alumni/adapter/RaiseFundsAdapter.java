package com.sarps.alumni.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarps.alumni.R;
import com.sarps.alumni.RaiseFundProfile;
import com.sarps.alumni.model.SavedRequestItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sarps on 11/18/2016.
 */
public class RaiseFundsAdapter extends RecyclerView.Adapter<RaiseFundsAdapter.Viewholder> {
    Context context;
    ArrayList<SavedRequestItem> list;


    public RaiseFundsAdapter(Context context, ArrayList<SavedRequestItem> list) {
        this.context = context;
        this.list = list;
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_username, tv_company;
        ImageView iv_profile_image;
        LinearLayout ll_ex_stud_classmates, ll_class_item;

        public Viewholder(View itemView) {
            super(itemView);

            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_company = (TextView) itemView.findViewById(R.id.tv_company);
            iv_profile_image = (ImageView) itemView.findViewById(R.id.iv_profile_image);
            ll_ex_stud_classmates = (LinearLayout) itemView.findViewById(R.id.ll_ex_stud_classmates);
            ll_class_item = (LinearLayout) itemView.findViewById(R.id.ll_class_item);

        }
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {

        try {
            holder.ll_ex_stud_classmates.setVisibility(View.VISIBLE);
            Picasso.with(context).load(list.get(position).getImg()).into(holder.iv_profile_image);
            holder.tv_username.setText(list.get(position).getName());
            holder.tv_company.setText(list.get(position).getDesc());


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("e :- " + e);
        }

        holder.tv_username.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.tv_company.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));

        final String id = list.get(position).getStud_id();
        holder.ll_class_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RaiseFundProfile.class);
                intent.putExtra("id", id);
                intent.putExtra("heading", list.get(position).getHeading());
                intent.putExtra("desc", list.get(position).getDesc());
                intent.putExtra("amnt", list.get(position).getReq_amt());
                intent.putExtra("img_link", list.get(position).getImg());
                intent.putExtra("name", list.get(position).getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.classmate_item, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}