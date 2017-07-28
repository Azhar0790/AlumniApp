package com.sarps.alumni.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarps.alumni.R;
import com.sarps.alumni.model.Experience_item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarps on 11/17/2016.
 */
public class Experience_Adapter extends RecyclerView.Adapter<Experience_Adapter.Viewholder> {
    Context context;
    ArrayList<Experience_item> list;
    Experience_item experience_item;


    public Experience_Adapter(Context context, ArrayList<Experience_item> list) {
        this.context = context;
        this.list = list;
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_username, tv_studying, tv_percentage, tv_year;
        ImageView iv_profile_image;

        public Viewholder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_studying = (TextView) itemView.findViewById(R.id.tv_studying);
            tv_percentage = (TextView) itemView.findViewById(R.id.tv_percentage);
            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
        }
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        holder.tv_username.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        holder.tv_studying.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        holder.tv_percentage.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        holder.tv_year.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        holder.tv_username.setText(list.get(position).geteCollege());
        holder.tv_studying.setText(list.get(position).geteCourse());

        if (list.get(position).getPercent() != null) {
            holder.tv_percentage.setVisibility(View.VISIBLE);
            holder.tv_year.setVisibility(View.VISIBLE);
            holder.tv_percentage.setText(list.get(position).getPercent());

            if(list.get(position).getTo_year().equals("0")){
                holder.tv_year.setText("Still Studying");
            }else {
                holder.tv_year.setText(list.get(position).getFrom_year() + " - " + list.get(position).getTo_year());
            }
        }
        holder.tv_username.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.tv_studying.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.tv_percentage.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.tv_year.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.experience_item, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}