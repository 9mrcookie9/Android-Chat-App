package com.officialcookiegames.androidjsontests;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Kacper on 2016-06-23.
 */
public class CustomAdapter extends BaseAdapter {
    private final Adapter[] adapters;
    private Activity context;

    public CustomAdapter(Activity Context, Adapter[] adapters){
        this.adapters = adapters;
        this.context = Context;
    }

    @Override
    public int getCount() {
        return adapters.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.sample_display, null);
        TextView Name = (TextView) rowView.findViewById(R.id.nick);
        TextView Date = (TextView) rowView.findViewById(R.id.date);
        TextView Message = (TextView) rowView.findViewById(R.id.message);
        Name.setText(adapters[i].name);
        Date.setText(adapters[i].date);
        Message.setText(adapters[i].message);
        return rowView;
    }
}
