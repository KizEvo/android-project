package com.example.android_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Component extends BaseAdapter {

    private Activity activity;
    private String[] text;
    private String[] textVersion;

    private int[] resId;

    public Component(Activity activity, String[] text, String[] textVersion, int[] resId) {
        this.text = text;
        this.activity = activity;
        this.textVersion = textVersion;
        this.resId = resId;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return text[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();

        view = inflater.inflate(R.layout.item, null);

        TextView textTV = (TextView) view.findViewById(R.id.text);
        textTV.setText(this.text[i]);

        TextView textVersionTV = (TextView) view.findViewById(R.id.textVersion);
        textVersionTV.setText(this.textVersion[i]);

        ImageView imageIV = (ImageView) view.findViewById(R.id.image);
        imageIV.setImageResource(this.resId[i]);

        return view;
    }
}
