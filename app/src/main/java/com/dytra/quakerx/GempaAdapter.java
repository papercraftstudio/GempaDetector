package com.dytra.quakerx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class GempaAdapter extends ArrayAdapter<Gempa> {

    GempaAdapter(Context ctx, int textViewResourceId, List<Gempa> sites) {
        super(ctx, textViewResourceId, sites);
    }

    @NonNull
    @Override
    public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
        RelativeLayout row = (RelativeLayout) convertView;
        Log.i("StackSites", "getView pos = " + pos);
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) parent
                    .getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout) inflater.inflate(R.layout.row_site, null);
        }
        TextView magnitudeText = row.findViewById(R.id.magnitudeText);
        TextView kedalamanText = row.findViewById(R.id.kedalamanText);
        TextView wilayahText = row.findViewById(R.id.wilayahText);
        TextView tanggalText = row.findViewById(R.id.tanggalText);
        magnitudeText.setText(getItem(pos).getMagnitude());
        kedalamanText.setText(getItem(pos).getKedalaman());
        wilayahText.setText(getItem(pos).getWilayah());
        tanggalText.setText(getItem(pos).getTanggal() + " " + getItem(pos).getJam());
        return row;
    }
}