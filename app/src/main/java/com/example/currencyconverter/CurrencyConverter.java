package com.example.currencyconverter;

import android.content.Intent;                 // ① NEW
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;                 // ① NEW
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;           // ② NEW
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends AppCompatActivity {

    private static final String BASE = "RUB";

    private final Map<String, Double> rubPerUnit = new HashMap<>();
    private String curFrom = "USD";
    private String curTo   = "EUR";

    private EditText etInput, etOutput;

    private DrawerLayout   drawerLayout;
    private NavigationView navigationView;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_currency_converter);

        drawerLayout   = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar tb     = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ActionBarDrawerToggle t =
                new ActionBarDrawerToggle(this, drawerLayout, tb,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(t); t.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavItem);   // listener wired

        /* ---------- rates sent from MainActivity ---------- */
        String[] codes = getIntent().getStringArrayExtra("codes");
        String[] rates = getIntent().getStringArrayExtra("rates");
        if (codes == null || rates == null || codes.length != rates.length){
            Toast.makeText(this, "Rates not delivered", Toast.LENGTH_SHORT).show();
            finish(); return;
        }
        for (int i=0;i<codes.length;i++){
            try { rubPerUnit.put(codes[i], Double.parseDouble(rates[i])); }
            catch (NumberFormatException e){ rubPerUnit.put(codes[i],0.0); }
        }

        /* ---------- UI wiring ---------- */
        etInput  = findViewById(R.id.first_num);
        etOutput = findViewById(R.id.second_num);

        AutoCompleteTextView acFrom = findViewById(R.id.auto_complete_txt);
        AutoCompleteTextView acTo   = findViewById(R.id.auto_complete_txt2);

        ArrayAdapter<String> ad = new ArrayAdapter<>(this, R.layout.list_item, codes);
        acFrom.setAdapter(ad);  acTo.setAdapter(ad);

        acFrom.setOnItemClickListener((p,v,pos,id)-> curFrom = p.getItemAtPosition(pos).toString());
        acTo  .setOnItemClickListener((p,v,pos,id)-> curTo   = p.getItemAtPosition(pos).toString());

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> convert());
    }

    /** Nav-drawer click-handler */
    private boolean onNavItem(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            startActivity(new Intent(this, MainActivity.class));

        } else if (id == R.id.bank_bill_types) {
            startActivity(new Intent(this, BankBill.class));

        } else {
            return false;          // let the drawer keep the item “un-handled”
        }

        drawerLayout.closeDrawers();
        return true;               // we handled the click
    }

    /* ---------- conversion ---------- */
    private void convert() {
        String txt = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(txt)){ toast("Enter a number"); etOutput.setText("-"); return; }
        double amount;
        try { amount = Double.parseDouble(txt); }
        catch (NumberFormatException e){ toast("Not a number"); return; }

        if (curFrom.equals(curTo)){ toast("Same currency"); return; }

        Double rubFrom = rubPerUnit.get(curFrom), rubTo = rubPerUnit.get(curTo);
        if (rubFrom == null || rubTo == null || rubFrom==0 || rubTo==0){
            toast("Rate missing"); return;
        }
        double result = amount * rubFrom / rubTo;
        etOutput.setText(new DecimalFormat("#.###").format(result));
    }

    private void toast(String m){ Toast.makeText(this,m,Toast.LENGTH_SHORT).show(); }
}
