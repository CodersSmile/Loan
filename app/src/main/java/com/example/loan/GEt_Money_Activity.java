package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class GEt_Money_Activity extends AppCompatActivity {
    ImageView back;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_check_progress);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(GEt_Money_Activity.this).showAppOpenAd(GEt_Money_Activity.this, new AppManager.SplashAdCallback() {
                    public void callbackCall() {
                        GEt_Money_Activity.this.startActivity(new Intent(GEt_Money_Activity.this, Instant_Loan.class));
                        GEt_Money_Activity.this.finish();
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        AppManager.getInstance(this).showAppOpenAd(this, new AppManager.SplashAdCallback() {
            public void callbackCall() {
                GEt_Money_Activity.this.startActivity(new Intent(GEt_Money_Activity.this, Instant_Loan.class));
                GEt_Money_Activity.this.finish();
            }
        });
    }
}
