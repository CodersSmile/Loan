package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome_Activity extends AppCompatActivity {
    ImageView back;
    LinearLayout welcomebt;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_welcome);
        AppManager.getInstance(this).showNativeLarge(this, (LinearLayout) findViewById(R.id.native_ad_large));
        AppManager.getInstance(this).showBannerAd((ViewGroup) findViewById(R.id.banner_ad));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.welcomebt);
        this.welcomebt = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Welcome_Activity.this).showInterstitialAd(Welcome_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Welcome_Activity.this.startActivity(new Intent(Welcome_Activity.this, LetsStart.class));
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
