package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;

import java.util.List;
import java.util.Locale;

public class CustomArrayAdapter extends ArrayAdapter<ListItemClass> {

    private final LayoutInflater inflater;
    private final int placeholder;          // shown when flag is missing

    public CustomArrayAdapter(@NonNull Context ctx,
                              int resource,
                              @NonNull List<ListItemClass> items,
                              LayoutInflater inf) {
        super(ctx, resource, items);
        this.inflater    = inf;
        this.placeholder = R.drawable.ic_flag_placeholder;   // add 32×24 dp PNG/vector
    }

    @NonNull
    @Override public View getView(int pos, View cv, @NonNull ViewGroup parent) {
        if (cv == null)
            cv = inflater.inflate(R.layout.list_item_1, parent, false);

        ListItemClass it = getItem(pos);
        if (it == null) return cv;   // extra safety

        /* ISO + name (single bold line) */
        ((TextView) cv.findViewById(R.id.tvName))
                .setText(it.getData_1() + " — " + it.getData_2());

        // New chips ↓
        ((Chip) cv.findViewById(R.id.chUnits))
                .setText(it.getData_3() + " / USD");

        ((Chip) cv.findViewById(R.id.chUsd))
                .setText("$" + it.getData_4());

        /* ---------- flag lookup ---------- */
        String isoLower = it.getData_1().toLowerCase(Locale.US);   // e.g. "usd"
        int resId = getContext().getResources()
                .getIdentifier(isoLower, "drawable", getContext().getPackageName());

        ImageView img = cv.findViewById(R.id.imgFlag);
        img.setImageResource(resId == 0 ? placeholder : resId);    // fallback if missing

        return cv;
    }
}
