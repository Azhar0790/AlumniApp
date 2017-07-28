package com.sarps.alumni.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarps.alumni.R;

/**
 * Created by Sarps on 11/14/2016.
 */
public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.Viewholder> {



    class Viewholder extends RecyclerView.ViewHolder{
        TextView tv_daily_updates;
        public Viewholder(View itemView) {
            super(itemView);
            tv_daily_updates=(TextView)itemView.findViewById(R.id.tv_daily_update);
        }
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
