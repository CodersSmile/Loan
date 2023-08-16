package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends SDK_SplashActivity {
    boolean hasAndroidPermissions;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash);
        getWindow().setFlags(1024, 1024);
        InitSDK(this, new SDK_SplashActivity.AppDataListner() {
            public void onSuccess() {
                AppManager.getInstance(SplashActivity.this).loadInterstitialAd(SplashActivity.this);
                AppManager.getInstance(SplashActivity.this).loadNativeAd("LARGE", SplashActivity.this);
                if (AppManager.show_appopen == 1) {
                    AppManager.getInstance(SplashActivity.this).initAppOpen(SplashActivity.this, new AppManager.SplashAdCallback() {
                        public void callbackCall() {
                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, Welcome_Activity.class));
                            SplashActivity.this.finish();
                        }
                    });
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            AppManager.getInstance(SplashActivity.this).showInterstitialAd(SplashActivity.this, new AppManager.InterstitialCallback() {
                                public void callbackCall() {
                                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, Welcome_Activity.class));
                                    SplashActivity.this.finish();
                                }
                            });
                        }
                    }, 4000);
                }
            }

            public void onReload() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this.getApplication(), SplashActivity.class));
                SplashActivity.this.finish();
            }
        });
    }
}
