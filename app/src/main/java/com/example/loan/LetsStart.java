package com.example.loan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

public class LetsStart extends AppCompatActivity {
    ImageView privacy;
    Bundle rating;
    ImageView rating1;
    ImageView share;
    LinearLayout start;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_lets_start);
        AppManager.getInstance(this).showNativeLarge(this, (LinearLayout) findViewById(R.id.native_ad_large));
        AppManager.getInstance(this).showBannerAd((ViewGroup) findViewById(R.id.banner_ad));
        this.start = (LinearLayout) findViewById(R.id.letsstart);
        this.rating1 = (ImageView) findViewById(R.id.rating);
        this.share = (ImageView) findViewById(R.id.share);
        this.privacy = (ImageView) findViewById(R.id.privacy);
        this.start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(LetsStart.this).showInterstitialAd(LetsStart.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        LetsStart.this.startActivity(new Intent(LetsStart.this, Home_Activity.class));
                    }
                });
            }
        });
        this.rating1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LetsStart letsStart = LetsStart.this;
                RateDialogManager.showRateDialog(letsStart, letsStart.rating);
            }
        });
        this.share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Bundle().putString("ACTION", "AppShare");
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", AppManager.app_name);
                intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + LetsStart.this.getPackageName());
                LetsStart.this.startActivity(Intent.createChooser(intent, "choose one"));
            }
        });
        this.privacy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new CustomTabsIntent.Builder().build().launchUrl(LetsStart.this, Uri.parse(AppManager.privacy_link.length() > 0 ? AppManager.privacy_link : "https://feyo.in"));
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Welcome_Activity.class));
    }
}
