package com.example.currencyconverter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class BankBill extends AppCompatActivity {

    /* ───────────────────────── navigation ────────────────────────── */
    private DrawerLayout   drawerLayout;
    private NavigationView navigationView;

    /* ───────────────────────── list data ─────────────────────────── */
    private ListView listView;

    private static final String[] TITLE = {
            "Emirati Dirham","Argentine Peso","Australian Dollar","Bulgarian Lev",
            "Bahraini Dinar","Bruneian Dollar","Brazilian Real","Botswana Pula",
            "Canadian Dollar","Swiss Franc","Chilean Peso","Chinese Yuan Renminbi",
            "Colombian Peso","Czech Koruna","Danish Krone","Euro",
            "British Pound","Hong Kong Dollar","Hungarian Forint","Indonesian Rupiah",
            "Indian Rupee","Iranian Rial","Icelandic Krona","Japanese Yen",
            "South Korean Won","Kuwaiti Dinar","Kazakhstani Tenge","Sri Lankan Rupee",
            "Libyan Dinar","Mauritian Rupee","Mexican Peso","Malaysian Ringgit",
            "Norwegian Krone","New Zealand Dollar","Omani Rial","Pakistani Rupee",
            "Polish Zloty","Qatari Riyal","Romanian New Leu","Russian Ruble",
            "Saudi Arabian Riyal","Swedish Krona","Singapore Dollar","Thai Baht",
            "Trinidadian Dollar","Turkish Lira","Taiwan New Dollar","US Dollar",
            "Venezuelan Bolívar","South African Rand"
    };

    private static final String[] ISO = {
            "AED","ARS","AUD","BGN","BHD","BND","BRL","BWP",
            "CAD","CHF","CLP","CNY","COP","CZK","DKK","EUR",
            "GBP","HKD","HUF","IDR","INR","IRR","ISK","JPY",
            "KRW","KWD","KZT","LKR","LYD","MUR","MXN","MYR",
            "NOK","NZD","OMR","PKR","PLN","QAR","RON","RUB",
            "SAR","SEK","SGD","THB","TTD","TRY","TWD","USD",
            "VES","ZAR"
    };

    private static final int[] FLAG = {
            R.drawable.aed, R.drawable.ars, R.drawable.aud, R.drawable.bgn,
            R.drawable.bhd, R.drawable.bnd, R.drawable.brl, R.drawable.bwp,
            R.drawable.cad, R.drawable.chf, R.drawable.clp, R.drawable.cny,
            R.drawable.cop, R.drawable.czk, R.drawable.dkk, R.drawable.eur,
            R.drawable.gbp, R.drawable.hkd, R.drawable.huf, R.drawable.idr,
            R.drawable.inr, R.drawable.irr, R.drawable.isk, R.drawable.jpy,
            R.drawable.krw, R.drawable.kwd, R.drawable.kzt, R.drawable.lkr,
            R.drawable.lyd, R.drawable.mur, R.drawable.mxn, R.drawable.myr,
            R.drawable.nok, R.drawable.nzd, R.drawable.omr, R.drawable.pkr,
            R.drawable.pln, R.drawable.qar, R.drawable.ron, R.drawable.rub,
            R.drawable.sar, R.drawable.sek, R.drawable.sgd, R.drawable.thb,
            R.drawable.ttd, R.drawable.try1, R.drawable.twd, R.drawable.usd,
            R.drawable.ves, R.drawable.zar
    };

    /* ───────────────────────── lifecycle ─────────────────────────── */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_bill);

        /* ①-- toolbar & drawer */
        drawerLayout   = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar tb     = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, tb,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavItem);

        /* ②-- list */
        listView = findViewById(R.id.listViewBills);
        listView.setAdapter(new BillAdapter(this));
        listView.setOnItemClickListener((p,v,pos,id) ->
                Toast.makeText(this, ISO[pos], Toast.LENGTH_SHORT).show());
    }

    /* ───────────────────── nav-drawer handler ────────────────────── */
    private boolean onNavItem(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(this, MainActivity.class));

        } else if (id == R.id.currency_converter) {
            startActivity(new Intent(this, CurrencyConverter.class));

        } else {
            return false;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    /* ───────────────────── custom list adapter ───────────────────── */
    private static class BillAdapter extends ArrayAdapter<String> {
        private final LayoutInflater inf;

        BillAdapter(Context c) {
            super(c, R.layout.row_bank_bill, TITLE);
            this.inf = LayoutInflater.from(c);
        }

        @NonNull @Override
        public View getView(int pos, @Nullable View cv, @NonNull ViewGroup parent) {
            if (cv == null) cv = inf.inflate(R.layout.row_bank_bill, parent, false);
            ((ImageView) cv.findViewById(R.id.imageSE)).setImageResource(FLAG[pos]);
            ((TextView)  cv.findViewById(R.id.textViewT)).setText(TITLE[pos]);
            ((TextView)  cv.findViewById(R.id.textViewTSub)).setText(ISO[pos]);
            return cv;
        }
    }
}
