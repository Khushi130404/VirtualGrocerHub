package com.example.virtualgrocerhub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CheckoutActivity extends Activity
{
    ListView lvItem;
    TextView tvDate, tvTime;
    static TextView  tvAmount, tvTax, tvFinalBill;
    LocalDate today;
    LocalTime time;
    static CheckoutAdapter cadd;
    ShopGroceryActivity shop;
    Button btPay;
    static int amount=0;
    ImageView gifCart;
    private static CheckoutActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        lvItem = findViewById(R.id.lvItem);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvAmount = findViewById(R.id.tvAmount);
        tvTax = findViewById(R.id.tvTax);
        tvFinalBill = findViewById(R.id.tvFinalBill);
        btPay = findViewById(R.id.btPay);
        gifCart = findViewById(R.id.gifCart);

        Glide.with(this)
                .asGif()
                .load(R.raw.cart2)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gifCart);

        shop = new ShopGroceryActivity();

        instance = this;
        today = LocalDate.now();
        time = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        tvTime.setText(tvTime.getText().toString()+time.format(formatter));
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tvDate.setText(tvDate.getText().toString()+today.format(formatter));

        amount = Integer.parseInt(getIntent().getStringExtra("amount"));
        tvAmount.setText(tvAmount.getText().toString()+amount);
        tvTax.setText(tvTax.getText().toString()+(int)(0.18*amount));
        tvFinalBill.setText(tvFinalBill.getText().toString()+(int)(1.18*amount));
        cadd = new CheckoutAdapter(getApplicationContext(),R.layout.checkout_adapter,shop.bill,shop.qty,shop.grocery);
        lvItem.setAdapter(cadd);

        btPay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(amount!=0)
                {
                    Intent i = new Intent(getApplicationContext(),ShopperAddressActivity.class);
                    i.putExtra("amount",(int)(1.18*amount));
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Empty Cart...!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void finishActivity()
    {
        if(instance!=null) {
            instance.finish();
        }
    }
}