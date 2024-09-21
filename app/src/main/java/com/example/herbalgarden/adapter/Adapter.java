package com.example.herbalgarden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.herbalgarden.R;
import com.example.herbalgarden.model.Data;

import java.util.List;

public class Adapter extends ArrayAdapter<Data> {
    private final Context context;
    private final List<Data> itemList;

    public Adapter(@NonNull Context context, @NonNull List<Data> objects) {
        super(context, 0, objects);
        this.context = context;
        this.itemList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        }

        Data data = itemList.get(position);

        TextView nama = convertView.findViewById(R.id.tvnamaHG);
        TextView manfaat = convertView.findViewById(R.id.tvmanfaatHG);

        nama.setText(data.getNama());
        manfaat.setText(data.getManfaat());

        return convertView;
    }
}
