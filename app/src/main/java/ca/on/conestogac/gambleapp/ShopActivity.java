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
        ArrayList jeff = application.getPurchases();
        int money = Integer.parseInt(goff.get(0).toString());
        View.OnClickListener purchaseListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (R.id.buybear == view.getId()) {
                    if (money >= 50) {
                        application.buybear(-50, 1);
                    }

                }
                else if (R.id.buyCar == view.getId()) {
                    if (money >= 2500) {
                        application.buycar(-2500, 1);
                    }

                }
                else {
                    if(money >= 10000){
                        application.buyhouse(-10000, 1);
                    }

                }
            }
        };
        buttonSmall.findViewById(R.id.buybear);
        buttonMid.findViewById(R.id.buyCar);
        buttonLarge.findViewById(R.id.buyhouse);
        buttonSmall.setOnClickListener(purchaseListener);
        buttonMid.setOnClickListener(purchaseListener);
        buttonLarge.setOnClickListener(purchaseListener);
    }

    private void refreshStats(){

        bears = findViewById(R.id.textView2);
        cars = findViewById(R.id.textView3);
        houses = findViewById(R.id.textView4);

        application = (GambleApplication) getApplication();
        ArrayList jeff = application.getPurchases();
        bears.setText("Owned: "+ jeff.get(0));
        cars.setText("Owned: "+ jeff.get(1));
        houses.setText("Owned: "+ jeff.get(2));

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