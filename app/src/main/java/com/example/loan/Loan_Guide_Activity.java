package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import feyo.fastcash.personalloan.R;
import feyo.pesonal.adsdk.AppManager;

public class Loan_Guide_Activity extends AppCompatActivity {
    ImageView back;
    ImageView llBusinessLoan;
    ImageView llCarLoan;
    ImageView llEducationLoan;
    ImageView llGoldLoan;
    ImageView llHomeLoan;
    ImageView llPersonalLoan;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_loan_guide);
        AppManager.getInstance(this).showNativeMedium(this, (LinearLayout) findViewById(R.id.native_ad_midum));
        AppManager.getInstance(this).showBannerAd((ViewGroup) findViewById(R.id.banner_ad));
        this.llPersonalLoan = (ImageView) findViewById(R.id.llPersonalLoan);
        this.llHomeLoan = (ImageView) findViewById(R.id.llHomeLoan);
        this.llBusinessLoan = (ImageView) findViewById(R.id.llBusinessLoan);
        this.llCarLoan = (ImageView) findViewById(R.id.llCarLoan);
        this.llGoldLoan = (ImageView) findViewById(R.id.llGoldLoan);
        this.llEducationLoan = (ImageView) findViewById(R.id.llEducationLoan);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Guide_Activity.this).showInterstitialAd(Loan_Guide_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Loan_Guide_Activity.this.startActivity(new Intent(Loan_Guide_Activity.this, Home_Activity.class));
                    }
                });
            }
        });
        this.llPersonalLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Guide_Activity.this).showInterstitialAd(Loan_Guide_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(Loan_Guide_Activity.this, Loan_Personal_Activity.class);
                        intent.putExtra("loan_name", "personal");
                        Loan_Guide_Activity.this.startActivity(intent);
                    }
                });
            }
        });
        this.llHomeLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Guide_Activity.this).showInterstitialAd(Loan_Guide_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(Loan_Guide_Activity.this, Loan_Personal_Activity.class);
                        intent.putExtra("loan_name", "homaloan");
                        Loan_Guide_Activity.this.startActivity(intent);
                    }
                });
            }
        });
        this.llBusinessLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Guide_Activity.this).showInterstitialAd(Loan_Guide_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(Loan_Guide_Activity.this, Loan_Personal_Activity.class);
                        intent.putExtra("loan_name", "buisinessloan");
                        Loan_Guide_Activity.this.startActivity(intent);
                    }
                });
            }
        });
        this.llCarLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Guide_Activity.this).showInterstitialAd(Loan_Guide_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(Loan_Guide_Activity.this, Loan_Personal_Activity.class);
                        intent.putExtra("loan_name", "carloan");
                        Loan_Guide_Activity.this.startActivity(intent);
                    }
                });
            }
        });
        this.llGoldLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Guide_Activity.this).showInterstitialAd(Loan_Guide_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(Loan_Guide_Activity.this, Loan_Personal_Activity.class);
                        intent.putExtra("loan_name", "goldloan");
                        Loan_Guide_Activity.this.startActivity(intent);
                    }
                });
            }
        });
        this.llEducationLoan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Guide_Activity.this).showInterstitialAd(Loan_Guide_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(Loan_Guide_Activity.this, Loan_Personal_Activity.class);
                        intent.putExtra("loan_name", "educationloan");
                        Loan_Guide_Activity.this.startActivity(intent);
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                Loan_Guide_Activity.this.startActivity(new Intent(Loan_Guide_Activity.this, Home_Activity.class));
            }
        });
    }
}
