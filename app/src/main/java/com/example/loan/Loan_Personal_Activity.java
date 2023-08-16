package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Loan_Personal_Activity extends AppCompatActivity {
    ImageView back;
    ImageView imagemain;
    TextView tvmain;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_loan_personal);
        AppManager.getInstance(this).showNativeMedium(this, (LinearLayout) findViewById(R.id.native_ad_midum));
        String loan_name = getIntent().getStringExtra("loan_name");
        this.imagemain = (ImageView) findViewById(R.id.imagemain);
        this.tvmain = (TextView) findViewById(R.id.tvmain);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Loan_Personal_Activity.this).showInterstitialAd(Loan_Personal_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Loan_Personal_Activity.this.startActivity(new Intent(Loan_Personal_Activity.this, Loan_Guide_Activity.class));
                    }
                });
            }
        });
        if (loan_name.equals("personal")) {
            this.imagemain.setImageResource(R.drawable.personal_loan2);
            this.tvmain.setText(R.string.personal_loan_paragraph);
        } else if (loan_name.equals("homaloan")) {
            this.imagemain.setImageResource(R.drawable.home_loan2);
            this.tvmain.setText(R.string.home_loan_paragraph);
        } else if (loan_name.equals("buisinessloan")) {
            this.imagemain.setImageResource(R.drawable.business_loan2);
            this.tvmain.setText(R.string.business_loan_paragraph);
        } else if (loan_name.equals("carloan")) {
            this.imagemain.setImageResource(R.drawable.vehicle_loan2);
            this.tvmain.setText(R.string.car_loan_paragraph);
        } else if (loan_name.equals("goldloan")) {
            this.imagemain.setImageResource(R.drawable.gold_loan2);
            this.tvmain.setText(R.string.gold_loan_paragraph);
        } else if (loan_name.equals("educationloan")) {
            this.imagemain.setImageResource(R.drawable.education_loan2);
            this.tvmain.setText(R.string.education_loan_paragraph);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                Loan_Personal_Activity.this.startActivity(new Intent(Loan_Personal_Activity.this, Loan_Guide_Activity.class));
            }
        });
    }
}
