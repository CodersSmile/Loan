package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class View_Rules_Activity extends AppCompatActivity {
    ImageView back;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_terms_condition);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(View_Rules_Activity.this).showAppOpenAd(View_Rules_Activity.this, new AppManager.SplashAdCallback() {
                    public void callbackCall() {
                        View_Rules_Activity.this.startActivity(new Intent(View_Rules_Activity.this, Instant_Loan_Single_Activity.class));
                        View_Rules_Activity.this.finish();
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        AppManager.getInstance(this).showAppOpenAd(this, new AppManager.SplashAdCallback() {
            public void callbackCall() {
                View_Rules_Activity.this.startActivity(new Intent(View_Rules_Activity.this, Instant_Loan_Single_Activity.class));
                View_Rules_Activity.this.finish();
            }
        });
    }
}
