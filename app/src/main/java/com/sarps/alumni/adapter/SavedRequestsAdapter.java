package com.sarps.alumni.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarps.alumni.NewRequestActivity;
import com.sarps.alumni.R;
import com.sarps.alumni.model.Experience_item;
import com.sarps.alumni.model.SavedRequestItem;

import java.util.ArrayList;

/**
 * Created by azhar-sarps on 3/2/2017.
 */
public class SavedRequestsAdapter extends RecyclerView.Adapter<SavedRequestsAdapter.Viewholder> {
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

        holder.tv_heading.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.tv_price.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.tv_desc.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.tv_number.setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
        holder.ll_savedrequestitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewRequestActivity.class);
                i.putExtra("heading", list.get(position).getHeading());
                i.putExtra("amount", list.get(position).getReq_amt());
                i.putExtra("description", list.get(position).getDesc());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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
}