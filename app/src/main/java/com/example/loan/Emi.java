package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Emi extends AppCompatActivity {
    ImageView back;
    TextView bus_cal;
    TextView edu_btn;
    TextView home_btn;
    TextView instant_btn;
    TextView other_btn;
    TextView personal_id;
    TextView vehicle_btn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_emi);
        AppManager.getInstance(this).showNativeMedium(this, (LinearLayout) findViewById(R.id.native_ad_midum));
        AppManager.getInstance(this).showBannerAd((ViewGroup) findViewById(R.id.banner_ad));
        this.bus_cal = (TextView) findViewById(R.id.bus_cal);
        this.edu_btn = (TextView) findViewById(R.id.edu_btn);
        this.home_btn = (TextView) findViewById(R.id.home_btn);
        this.other_btn = (TextView) findViewById(R.id.other_btn);
        this.instant_btn = (TextView) findViewById(R.id.instant_btn);
        this.vehicle_btn = (TextView) findViewById(R.id.vehicle_btn);
        this.personal_id = (TextView) findViewById(R.id.personal_id);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, Home_Activity.class));
                    }
                });
            }
        });
        this.bus_cal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, EMI_Calc_Single_Activity.class));
                    }
                });
            }
        });
        this.edu_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, EMI_Calc_Single_Activity.class));
                    }
                });
            }
        });
        this.home_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, EMI_Calc_Single_Activity.class));
                    }
                });
            }
        });
        this.other_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, EMI_Calc_Single_Activity.class));
                    }
                });
            }
        });
        this.instant_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, EMI_Calc_Single_Activity.class));
                    }
                });
            }
        });
        this.vehicle_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, EMI_Calc_Single_Activity.class));
                    }
                });
            }
        });
        this.personal_id.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Emi.this).showInterstitialAd(Emi.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Emi.this.startActivity(new Intent(Emi.this, EMI_Calc_Single_Activity.class));
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                Emi.this.startActivity(new Intent(Emi.this, Home_Activity.class));
            }
        });
    }
}
