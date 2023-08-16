package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Home_Activity extends AppCompatActivity {
    ImageView emi;
    ImageView instant_loan;
    ImageView loan_guide;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home);
        AppManager.getInstance(this).showNativeLarge(this, (LinearLayout) findViewById(R.id.native_ad_large));
        AppManager.getInstance(this).showBannerAd((ViewGroup) findViewById(R.id.banner_ad));
        this.loan_guide = (ImageView) findViewById(R.id.loan_guide);
        this.instant_loan = (ImageView) findViewById(R.id.instant_loan);
        this.emi = (ImageView) findViewById(R.id.emi);
        this.loan_guide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Home_Activity.this).showInterstitialAd(Home_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Home_Activity.this.startActivity(new Intent(Home_Activity.this, Loan_Guide_Activity.class));
                    }
                });
            }
        });
        this.instant_loan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Home_Activity.this).showInterstitialAd(Home_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Home_Activity.this.startActivity(new Intent(Home_Activity.this, Instant_Loan.class));
                    }
                });
            }
        });
        this.emi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Home_Activity.this).showInterstitialAd(Home_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Home_Activity.this.startActivity(new Intent(Home_Activity.this, Emi.class));
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                Home_Activity.this.startActivity(new Intent(Home_Activity.this, LetsStart.class));
            }
        });
    }
}
