package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loan.Adapter.Instant_Loan_Adapter;
import com.example.loan.Model.Loan_Data_class;

import java.util.ArrayList;

public class Instant_Loan extends AppCompatActivity {
    ImageView back;
    RecyclerView loan_list;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_instant_loan);
        AppManager.getInstance(this).showBannerAd((ViewGroup) findViewById(R.id.banner_ad));
        this.loan_list = (RecyclerView) findViewById(R.id.loan_list);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Instant_Loan.this).showInterstitialAd(Instant_Loan.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Instant_Loan.this.startActivity(new Intent(Instant_Loan.this, Home_Activity.class));
                    }
                });
            }
        });
        Loan_Data();
    }

    public void Loan_Data() {
        ArrayList<Loan_Data_class> arrayList = new ArrayList<>();
        arrayList.add(new Loan_Data_class("₹5,000", R.drawable.ic_app_icon, "SweetRupee", "0.098%/ Day", "8 months"));
        arrayList.add(new Loan_Data_class("₹1,000-500,0000", R.drawable.appicon3, "MudraKwik", "0.08%/ Day", "6-36 months"));
        arrayList.add(new Loan_Data_class("₹500-5,000", R.drawable.appicon4, "mPokket", "0.035%/ Day", "3-6 months"));
        arrayList.add(new Loan_Data_class("₹8,000-200,000", R.drawable.appicon5, "EarlySalary", "0.08%/ Day", "8-24 months"));
        arrayList.add(new Loan_Data_class("₹5000", R.drawable.appicon6, "RupeeMax", "0.098%/ Day", "6 months"));
        arrayList.add(new Loan_Data_class("₹5,000", R.drawable.appicon7, "CashBowl", "0.098%/ Day", "6 months"));
        arrayList.add(new Loan_Data_class("₹3,000-8,000", R.drawable.appicon8, "FlashCash", "0.098%/ Day", "12-24 months"));
        arrayList.add(new Loan_Data_class("₹3,000-500,000", R.drawable.appicon_9, "MoneyTap", "13-24.3%/year", "12-36 months"));
        arrayList.add(new Loan_Data_class("₹5,000-10,000", R.drawable.appicon9, "Moneed", "0.5%/ Day", "4-12 months"));
        arrayList.add(new Loan_Data_class("₹5,000", R.drawable.appicon10, "CrazyRupee", "0.06%/ Day", "6-12 months"));
        arrayList.add(new Loan_Data_class("₹3,000-10,000", R.drawable.appicon11, "KreditBee", "0.15%/ Day", "6-12 months"));
        arrayList.add(new Loan_Data_class("₹15,00-60,000", R.drawable.appicon12, "CashBean", "0.08%/ Day", "18-36 months"));
        arrayList.add(new Loan_Data_class("₹1,000-5,000", R.drawable.appicon13, "CashMama", "0.098%/ Day", "3-8 months"));
        arrayList.add(new Loan_Data_class("₹5,000-3,00,000", R.drawable.appicon14, "CASHe", "0.083%/ Day", "3-18 months"));
        arrayList.add(new Loan_Data_class("₹10,000-5,00,000", R.drawable.appicon15, "Money View", "0.098%Day", "9-24 months"));
        arrayList.add(new Loan_Data_class("₹5,000-₹10,000", R.drawable.appicon16, "Ocash", "0.098%/ Day", "3-16 months"));
        arrayList.add(new Loan_Data_class("₹2,000-20,000", R.drawable.appicon17, "LoanFront", "0.01%/ Day", "3-12 months"));
        this.loan_list.setAdapter(new Instant_Loan_Adapter(this, arrayList));
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                Instant_Loan.this.startActivity(new Intent(Instant_Loan.this, Home_Activity.class));
            }
        });
    }
}
