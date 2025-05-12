package com.example.android_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Component extends BaseAdapter {

    private Activity activity;
    private String[] text;
    private String[] textVersion;
    private List<Student> students;
    private int[] resId;

    public Component(Activity activity, List<Student> students, String[] text, String[] textVersion, int[] resId) {
        this.text = text;
        this.activity = activity;
        this.textVersion = textVersion;
        this.students = students;
        this.resId = resId;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return students.get(i);
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
        String id = Integer.toString(this.students.get(i).getId());
        textTV.setText(id);

        TextView textVersionTV = (TextView) view.findViewById(R.id.textVersion);
        textVersionTV.setText(this.students.get(i).getName());

        ImageView imageIV = (ImageView) view.findViewById(R.id.image);
        imageIV.setImageResource(this.students.get(i).getResId());

        return view;
    }
}
