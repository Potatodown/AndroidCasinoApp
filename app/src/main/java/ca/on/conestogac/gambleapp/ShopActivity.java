package ca.on.conestogac.gambleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    private Button buttonSmall;
    private Button buttonMid;
    private Button buttonLarge;
    private TextView bears;
    private TextView cars;
    private TextView houses;
    int money = 0;
    TextView  currency;
    GambleApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        refreshStats();
        ArrayList goff = application.getStats();
        money = Integer.parseInt(goff.get(0).toString());
        View.OnClickListener purchaseListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (R.id.buyBear == view.getId()) {
                    if (money >= 50) {
                        application.buybear(-50, 1);
                    }

                } else if (R.id.buyCar == view.getId()) {
                    if (money >= 2500) {
                        application.buycar(-2500, 1);
                    }

                } else {
                    if (money >= 10000) {
                        application.buyhouse(-10000, 1);
                    }

                }
                currency.setText("Currency: "+money);
                refreshStats();
            }
        };
        buttonSmall = findViewById(R.id.buyBear);
        buttonSmall.setOnClickListener(purchaseListener);

        buttonMid = findViewById(R.id.buyCar);
        buttonMid.setOnClickListener(purchaseListener);

        buttonLarge = findViewById(R.id.buyHouse);
        buttonLarge.setOnClickListener(purchaseListener);
    }

    private void refreshStats(){

        bears = findViewById(R.id.textView2);
        cars = findViewById(R.id.textView3);
        houses = findViewById(R.id.textView4);
        currency = findViewById(R.id.CurrencyText);

        application = (GambleApplication) getApplication();
        ArrayList UserData = application.getPurchases();
        bears.setText("Owned: "+ UserData.get(0));
        cars.setText("Owned: "+ UserData.get(1));
        houses.setText("Owned: "+ UserData.get(2));
        ArrayList moneyData = application.getStats();
        money = (int) moneyData.get(0);
        currency.setText("Currency:" + money);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret = true;
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }
}