package com.example.android_project.appUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.android_project.R;

public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private String[] labels;
    private String[] descriptions1;
    private String[] descriptions2;
    private int[] icons;

    public CustomListAdapter(Context context, String[] labels, String[] descriptions1, String[] descriptions2, int[] icons) {
        this.context = context;
        this.labels = labels;
        this.descriptions1 = descriptions1;
        this.descriptions2 = descriptions2;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return labels.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_list, parent, false);
        }

        ImageView item_iconIV = convertView.findViewById(R.id.item_iconIV);
        TextView item_titleTV = convertView.findViewById(R.id.item_titleTV);
        TextView item_dateNtimeTV = convertView.findViewById(R.id.item_dateNtimeTV);
        TextView item_roomNslotTV = convertView.findViewById(R.id.item_roomNslotTV);

        String dateNTimeStr = "Time: " + formatDateTime(descriptions1[position]);
        String roomNSlot = "Room & Seat: " + formatRoomNSlot(descriptions2[position]);
        item_iconIV.setImageResource(icons[position]);
        item_titleTV.setText(labels[position]);
        item_dateNtimeTV.setText(dateNTimeStr);
        item_roomNslotTV.setText(roomNSlot);
        return convertView;
    }

    private static String formatDateTime(String input) {
        // Extracting date & time components using substring
        String year = input.substring(4, 8);
        String day = input.substring(8, 10);
        String month = input.substring(10, 12);
        String hour = input.substring(12, 14);
        String minute = input.substring(14, 16);

        // Formatting and returning the result
        return day + "/" + month + "/" + year + " - " + hour + ":" + minute;
    }

    private static String formatRoomNSlot(String input) {
        return input.substring(0, 6) + " - " + input.substring(6);
    }
}