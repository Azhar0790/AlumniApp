package com.sarps.alumni.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarps.alumni.R;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomList_adapter extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private Integer[] imageid;
    private Activity context;
    LinearLayout ll_item;

    public CustomList_adapter(Activity context, String[] names, String[] desc, Integer[] imageid) {
        super(context, R.layout.list_layout, names);
        this.context = context;
        this.names = names;
        this.desc = desc;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        final TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        final TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
         ll_item = (LinearLayout) listViewItem.findViewById(R.id.ll_item);

        textViewName.setTypeface(Typeface.createFromAsset(context.getAssets(),"Raleway-Regular.ttf"));
        textViewDesc.setTypeface(Typeface.createFromAsset(context.getAssets(),"Raleway-Regular.ttf"));
        textViewName.setText(names[position]);
        textViewDesc.setText(desc[position]);
        image.setImageResource(imageid[position]);
        return  listViewItem;
    }

    @Override
    public int getCount() {
        // In this adapter, we are supposed to hide the first item of the list view
        // Returning 0 if no of items are already 0

     return super.getCount();
    }


}