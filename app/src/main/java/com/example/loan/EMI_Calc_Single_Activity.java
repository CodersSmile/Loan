package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EMI_Calc_Single_Activity extends AppCompatActivity {
    ImageView back;
    TextView calculat;
    int cnt = 0;
    int finalMonth = 0;
    EditText interest_enter;
    float month = 0.0f;
    int monthlyemi = 0;
    EditText prime_amount;
    TextView reset;
    float year = 0.0f;
    EditText year_enter;
    int yearmonth = 1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_emi_calc_single);
        AppManager.getInstance(this).showNativeMedium(this, (LinearLayout) findViewById(R.id.native_ad_midum));
        this.prime_amount = (EditText) findViewById(R.id.prime_amount);
        this.interest_enter = (EditText) findViewById(R.id.interest_enter);
        this.year_enter = (EditText) findViewById(R.id.year_btn);
        this.reset = (TextView) findViewById(R.id.reset);
        this.calculat = (TextView) findViewById(R.id.calculat);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(EMI_Calc_Single_Activity.this).showInterstitialAd(EMI_Calc_Single_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        EMI_Calc_Single_Activity.this.startActivity(new Intent(EMI_Calc_Single_Activity.this, Emi.class));
                    }
                });
            }
        });
        this.reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EMI_Calc_Single_Activity.this.prime_amount.setText("");
                EMI_Calc_Single_Activity.this.interest_enter.setText("");
                EMI_Calc_Single_Activity.this.year_enter.setText("");
            }
        });
        this.calculat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (EMI_Calc_Single_Activity.this.prime_amount.getText().toString().isEmpty()) {
                    EMI_Calc_Single_Activity.this.prime_amount.setError("Enter Amount");
                } else if (EMI_Calc_Single_Activity.this.interest_enter.getText().toString().isEmpty()) {
                    EMI_Calc_Single_Activity.this.interest_enter.setError("Enter Interest");
                } else if (EMI_Calc_Single_Activity.this.year_enter.getText().toString().isEmpty()) {
                    EMI_Calc_Single_Activity.this.year_enter.setError("Enter Year");
                } else {
                    float amount = Float.parseFloat(EMI_Calc_Single_Activity.this.prime_amount.getText().toString());
                    float interst = Float.parseFloat(EMI_Calc_Single_Activity.this.interest_enter.getText().toString());
                    float interst1 = ((amount * interst) * Float.parseFloat(EMI_Calc_Single_Activity.this.year_enter.getText().toString())) / 100.0f;
                    final float f = amount + interst1;
                    final float f2 = interst1;
                    final float f3 = interst;
                    final float f4 = amount;
                    AppManager.getInstance(EMI_Calc_Single_Activity.this).showInterstitialAd(EMI_Calc_Single_Activity.this, new AppManager.InterstitialCallback() {
                        public void callbackCall() {
                            Intent intent = new Intent(EMI_Calc_Single_Activity.this, EMI_Caluc_Show_Activity.class);
                            intent.putExtra("totalamount", "" + f);
                            intent.putExtra("totalinterest", "" + f2);
                            intent.putExtra("intetsr", "" + f3);
                            intent.putExtra("loanamount", "" + f4);
                            EMI_Calc_Single_Activity.this.startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                EMI_Calc_Single_Activity.this.startActivity(new Intent(EMI_Calc_Single_Activity.this, Emi.class));
            }
        });
    }
}
