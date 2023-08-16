package com.example.loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import de.hdodenhof.circleimageview.CircleImageView;

public class Instant_Loan_Single_Activity extends AppCompatActivity {
    TextView amount;
    EditText amountET;
    CircleImageView app_icon;
    ImageView apply_loan;
    ImageView back;
    ImageView calculatebutton;
    ImageView get_money;
    TextView interest;
    EditText interestET;
    Spinner interestSpinner;
    TextView loanname;
    TextView monthlyEmiTV;
    String[] months = {"Month"};
    ImageView resetbutton;
    LinearLayout result_layout;
    TextView term;
    TextView totalamountTV;
    TextView totalinterestTV;
    ImageView view_rules;
    EditText yearET;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_instant_loan_single);
        AppManager.getInstance(this).showNativeMedium(this, (LinearLayout) findViewById(R.id.native_ad_midum));
        this.loanname = (TextView) findViewById(R.id.app_name);
        this.amount = (TextView) findViewById(R.id.amount);
        this.interest = (TextView) findViewById(R.id.interest);
        this.term = (TextView) findViewById(R.id.term);
        this.app_icon = (CircleImageView) findViewById(R.id.app_icon);
        this.amountET = (EditText) findViewById(R.id.amountET);
        this.interestET = (EditText) findViewById(R.id.interestET);
        this.interestSpinner = (Spinner) findViewById(R.id.interestSpinner);
        this.yearET = (EditText) findViewById(R.id.yearET);
        this.resetbutton = (ImageView) findViewById(R.id.resetbutton);
        this.calculatebutton = (ImageView) findViewById(R.id.calculatebutton);
        this.result_layout = (LinearLayout) findViewById(R.id.result_layout);
        this.monthlyEmiTV = (TextView) findViewById(R.id.monthlyEmiTV);
        this.totalinterestTV = (TextView) findViewById(R.id.totalinterestTV);
        this.totalamountTV = (TextView) findViewById(R.id.totalamountTV);
        this.apply_loan = (ImageView) findViewById(R.id.apply_loan);
        this.view_rules = (ImageView) findViewById(R.id.view_rules);
        this.get_money = (ImageView) findViewById(R.id.get_money);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Instant_Loan_Single_Activity.this).showInterstitialAd(Instant_Loan_Single_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Instant_Loan_Single_Activity.this.startActivity(new Intent(Instant_Loan_Single_Activity.this, Instant_Loan.class));
                        Instant_Loan_Single_Activity.this.finish();
                    }
                });
            }
        });
        String loansname = getIntent().getStringExtra(AppMeasurementSdk.ConditionalUserProperty.NAME);
        String ruppes = getIntent().getStringExtra("ruppes");
        String interst = getIntent().getStringExtra("interst");
        String month = getIntent().getStringExtra("month");
        this.app_icon.setImageResource(getIntent().getIntExtra("image", 0));
        this.loanname.setText(loansname);
        this.amount.setText(ruppes);
        this.interest.setText(interst);
        this.term.setText(month);
        ArrayAdapter aa = new ArrayAdapter(this, 17367048, this.months);
        aa.setDropDownViewResource(17367049);
        this.interestSpinner.setAdapter(aa);
        this.resetbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Instant_Loan_Single_Activity.this.amountET.setText("");
                Instant_Loan_Single_Activity.this.interestET.setText("");
                Instant_Loan_Single_Activity.this.yearET.setText("");
                Instant_Loan_Single_Activity.this.result_layout.setVisibility(8);
            }
        });
        this.calculatebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Instant_Loan_Single_Activity.this.amountET.getText().toString().isEmpty()) {
                    Instant_Loan_Single_Activity.this.amountET.setError("Enter Amount");
                } else if (Instant_Loan_Single_Activity.this.interestET.getText().toString().isEmpty()) {
                    Instant_Loan_Single_Activity.this.interestET.setError("Enter Interest");
                } else if (Instant_Loan_Single_Activity.this.yearET.getText().toString().isEmpty()) {
                    Instant_Loan_Single_Activity.this.yearET.setError("Enter months");
                } else {
                    float amount = Float.parseFloat(Instant_Loan_Single_Activity.this.amountET.getText().toString());
                    float interst = Float.parseFloat(Instant_Loan_Single_Activity.this.interestET.getText().toString());
                    float month = Float.parseFloat(Instant_Loan_Single_Activity.this.yearET.getText().toString());
                    float interst1 = ((amount * interst) * (month / 12.0f)) / 100.0f;
                    Instant_Loan_Single_Activity.this.result_layout.setVisibility(0);
                    TextView textView = Instant_Loan_Single_Activity.this.totalinterestTV;
                    textView.setText("" + interst1);
                    TextView textView2 = Instant_Loan_Single_Activity.this.totalamountTV;
                    textView2.setText("" + (amount + interst1));
                    TextView textView3 = Instant_Loan_Single_Activity.this.monthlyEmiTV;
                    textView3.setText("" + ((amount + interst1) / month));
                }
            }
        });
        this.apply_loan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Instant_Loan_Single_Activity.this).showInterstitialAd(Instant_Loan_Single_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Instant_Loan_Single_Activity.this.startActivity(new Intent(Instant_Loan_Single_Activity.this, Applay_Loan_Activity.class));
                        Instant_Loan_Single_Activity.this.finish();
                    }
                });
            }
        });
        this.view_rules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Instant_Loan_Single_Activity.this).showInterstitialAd(Instant_Loan_Single_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Instant_Loan_Single_Activity.this.startActivity(new Intent(Instant_Loan_Single_Activity.this, View_Rules_Activity.class));
                        Instant_Loan_Single_Activity.this.finish();
                    }
                });
            }
        });
        this.get_money.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Instant_Loan_Single_Activity.this).showInterstitialAd(Instant_Loan_Single_Activity.this, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Instant_Loan_Single_Activity.this.startActivity(new Intent(Instant_Loan_Single_Activity.this, GEt_Money_Activity.class));
                        Instant_Loan_Single_Activity.this.finish();
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showInterstitialAd(this, new AppManager.InterstitialCallback() {
            public void callbackCall() {
                Instant_Loan_Single_Activity.this.startActivity(new Intent(Instant_Loan_Single_Activity.this, Instant_Loan.class));
                Instant_Loan_Single_Activity.this.finish();
            }
        });
    }
}
