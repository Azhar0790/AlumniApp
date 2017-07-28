package com.sarps.alumni.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarps.alumni.OtherProfileActivity;
import com.sarps.alumni.R;
import com.sarps.alumni.model.Classmates_item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sarps on 11/14/2016.
 */
public class Classmates_Adapter extends RecyclerView.Adapter<Classmates_Adapter.Viewholder> {

    Context context;
    ArrayList<Classmates_item> list;

    public Classmates_Adapter(Context context, ArrayList<Classmates_item> list) {
        this.context = context;
        this.list = list;
    }

    class Viewholder extends RecyclerView.ViewHolder{
        LinearLayout ll_stud_classmates,ll_ex_stud_classmates,ll_class_item;
        TextView tv_mentor,tv_angel,tv_company,tv_name;
        ImageView iv_profile_image;
        public Viewholder(View itemView) {
            super(itemView);
            ll_stud_classmates=(LinearLayout) itemView.findViewById(R.id.ll_stud_classmates);
            ll_ex_stud_classmates=(LinearLayout)itemView.findViewById(R.id.ll_ex_stud_classmates);
            tv_mentor=(TextView)itemView.findViewById(R.id.tv_mentor);
            tv_angel=(TextView)itemView.findViewById(R.id.tv_angel);
            tv_company=(TextView)itemView.findViewById(R.id.tv_company);
            tv_name=(TextView)itemView.findViewById(R.id.tv_username);
            iv_profile_image=(ImageView) itemView.findViewById(R.id.iv_profile_image);
            ll_class_item=(LinearLayout) itemView.findViewById(R.id.ll_class_item);

        }
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        holder.ll_ex_stud_classmates.setVisibility(View.VISIBLE);
        holder.tv_name.setText(list.get(position).getU_fname()+" "+list.get(position).getU_lname());
        Picasso.with(context).load(list.get(position).getU_image()).into(holder.iv_profile_image);
        if(list.get(position).getE_college()!=null){
            holder.tv_company.setText(list.get(position).getE_college());
        }else if(list.get(position).getE_course()!=null){
                holder.tv_company.setText(list.get(position).getE_course()+", "+list.get(position).getFrom_year());
        }else {
            holder.tv_company.setText(list.get(position).getE_course()+", "+list.get(position).getFrom_year());
        }
        System.out.println("image :- "+list.get(position).getU_image());

        holder.ll_class_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,OtherProfileActivity.class);
                i.putExtra("id",list.get(position).getU_id());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
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
