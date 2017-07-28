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
import android.widget.Toast;

import com.sarps.alumni.R;
import com.sarps.alumni.model.Experience_item;

import java.util.ArrayList;

/**
 * Created by Sarps on 11/18/2016.
 */
public class AcheivementAdapter extends RecyclerView.Adapter<AcheivementAdapter.Viewholder> {
    Context context;
    ArrayList<Experience_item> list;
    Experience_item experience_item;


    public AcheivementAdapter(Context context, ArrayList<Experience_item> list) {
        this.context = context;
        this.list = list;
    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_achievements;

        public Viewholder(View itemView) {
            super(itemView);

            tv_achievements = (TextView) itemView.findViewById(R.id.tv_achievements);
        }
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        String input=list.get(position).geteCollege();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        System.out.println("output :- "+output);
        holder.tv_achievements.setText(list.get(position).geteCollege());
        holder.tv_achievements.setTypeface(Typeface.createFromAsset(context.getAssets(),"Raleway-Regular.ttf"));
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.acheivment_item, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}