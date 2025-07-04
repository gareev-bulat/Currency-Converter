package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView listView;
    private Toolbar toolbar;


    private final List<ListItemClass> arrayList = new ArrayList<>();
    private CustomArrayAdapter adapter;
    private final Map<String, String> rateMap = new HashMap<>();

    private Thread fetchThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout   = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        listView       = findViewById(R.id.listView);
        toolbar        = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        initList();
        navigationView.setNavigationItemSelectedListener(this::onNavItemSelected);
    }



    private void fetchRates() {
        try {
            Document doc = Jsoup
                    .connect("https://www.x-rates.com/table/?amount=1&from=USD")
                    .userAgent("Mozilla/5.0")
                    .get();

            Element tbody = doc.selectFirst("table.tablesorter.ratesTable tbody");
            if (tbody == null) return;

            arrayList.clear();
            rateMap.clear();

            List<Integer> flagsTmp = new ArrayList<>();

            for (Element row : tbody.select("tr")) {
                Elements cells = row.select("td");
                if (cells.size() < 3) continue;

                Element link = row.selectFirst("a");
                String iso = link.attr("href")
                        .substring(link.attr("href").lastIndexOf('=') + 1)
                        .toUpperCase(Locale.US);
                String fullName = link.text();

                String unitsPerUsd = cells.get(1).text();
                String usdPerUnit  = cells.get(2).text();
                String changePct   = cells.size() > 3 ? cells.get(3).text() : "–";

                ListItemClass item = new ListItemClass();
                item.setData_1(iso);
                item.setData_2(fullName);
                item.setData_3(unitsPerUsd);
                item.setData_4(usdPerUnit);
                item.setData_5(changePct);
                arrayList.add(item);

                rateMap.put(iso, usdPerUnit);

            }


            runOnUiThread(() -> adapter.notifyDataSetChanged());

        } catch (IOException e) {
            Log.e("MainActivity", "Jsoup error", e);
        }
    }

    private void initList() {
        adapter = new CustomArrayAdapter(this, R.layout.list_item_1, arrayList, getLayoutInflater());
        listView.setAdapter(adapter);
        fetchThread = new Thread(this::fetchRates);
        fetchThread.start();
    }

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            Toast.makeText(this, "Currency Rates", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        if (id == R.id.currency_converter) {
            if (rateMap.isEmpty()) {
                Toast.makeText(this, "Wait: loading…", Toast.LENGTH_SHORT).show();
                return true;
            }
            String usdPerRubStr = rateMap.get("RUB");
            if (usdPerRubStr == null || usdPerRubStr.isEmpty()) {
                Toast.makeText(this, "RUB not loaded", Toast.LENGTH_SHORT).show();
                return true;
            }
            double rubPerUsd = 1.0 / Double.parseDouble(usdPerRubStr);

            String[] codes  = getResources().getStringArray(R.array.currencies);
            String[] rates  = new String[codes.length];

            for (int i = 0; i < codes.length; i++) {
                String iso = codes[i];
                double rubPerUnit;
                if ("RUB".equals(iso)) {
                    rubPerUnit = 1.0;
                } else if ("USD".equals(iso)) {
                    rubPerUnit = rubPerUsd;
                } else {
                    String usdPerUnitStr = rateMap.get(iso);
                    rubPerUnit = (usdPerUnitStr == null || usdPerUnitStr.isEmpty())
                            ? 0.0
                            : Double.parseDouble(usdPerUnitStr) * rubPerUsd;
                }
                rates[i] = String.valueOf(rubPerUnit);
            }

            Intent intent = new Intent(this, CurrencyConverter.class);
            intent.putExtra("codes",  codes);
            intent.putExtra("rates",  rates);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        if (id == R.id.bank_bill_types) {
            Toast.makeText(this, "Types of currencies", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BankBill.class));
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }
}
