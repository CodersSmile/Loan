package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EMI_Caluc_Show_Activity extends AppCompatActivity {
    ImageView back;
    TextView emi_btn;
    TextView interst_show;
    TextView loan_a;
    TextView month_shjow;
    TextView total_amount;
    TextView total_intere;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_emi_caluc_show);
        AppManager.getInstance(this).showNativeLarge(this, (LinearLayout) findViewById(R.id.native_ad_large));
        this.loan_a = (TextView) findViewById(R.id.loan_a);
        this.interst_show = (TextView) findViewById(R.id.interst_show);
        this.total_amount = (TextView) findViewById(R.id.total_amount);
        this.total_intere = (TextView) findViewById(R.id.total_intere);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(EMI_Caluc_Show_Activity.this).showInterstitialAd(EMI_Caluc_Show_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        EMI_Caluc_Show_Activity.this.startActivity(new Intent(EMI_Caluc_Show_Activity.this, Emi.class));
                    }
                });
            }
        });
        String loanamount = getIntent().getStringExtra("loanamount");
        String interest = getIntent().getStringExtra("intetsr");
        String total_amount1 = getIntent().getStringExtra("totalamount");
        String total_interest = getIntent().getStringExtra("totalinterest");
        Log.e("totalamouint", loanamount);
        this.loan_a.setText(loanamount);
        this.interst_show.setText(interest);
        this.total_amount.setText(total_amount1);
        this.total_intere.setText(total_interest);
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                EMI_Caluc_Show_Activity.this.startActivity(new Intent(EMI_Caluc_Show_Activity.this, Emi.class));
            }
        });
    }
}
