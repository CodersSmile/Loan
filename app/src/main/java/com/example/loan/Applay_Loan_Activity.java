package com.example.loan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Applay_Loan_Activity extends AppCompatActivity {
    ImageView back;
    ImageView btn_submit;
    TextView dob;
    EditText monthly_income;
    EditText tv_address;
    EditText tv_email_address;
    EditText tv_firstname;
    EditText tv_lastname;
    EditText tv_mobile;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_applay_loan);
        AppManager.getInstance(this).showNativeMedium(this, (LinearLayout) findViewById(R.id.native_ad_midum));
        this.tv_firstname = (EditText) findViewById(R.id.tv_firstname);
        this.tv_lastname = (EditText) findViewById(R.id.tv_lastname);
        this.tv_address = (EditText) findViewById(R.id.tv_address);
        this.dob = (TextView) findViewById(R.id.dob);
        this.tv_email_address = (EditText) findViewById(R.id.tv_email_address);
        this.tv_mobile = (EditText) findViewById(R.id.tv_mobile);
        this.monthly_income = (EditText) findViewById(R.id.monthly_income);
        this.btn_submit = (ImageView) findViewById(R.id.btn_submit);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Applay_Loan_Activity.this).showAppOpenAd(Applay_Loan_Activity.this, new AppManager.SplashAdCallback() {
                    public void callbackCall() {
                        Applay_Loan_Activity.this.startActivity(new Intent(Applay_Loan_Activity.this, Instant_Loan_Single_Activity.class));
                        Applay_Loan_Activity.this.finish();
                    }
                });
            }
        });
        this.dob.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                new DatePickerDialog(Applay_Loan_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar.getInstance().set(year, monthOfYear, dayOfMonth);
                        TextView textView = Applay_Loan_Activity.this.dob;
                        textView.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, newCalendar.get(1), newCalendar.get(2), newCalendar.get(5)).show();
            }
        });
        this.btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String firtnamre = Applay_Loan_Activity.this.tv_firstname.getText().toString();
                String lastname = Applay_Loan_Activity.this.tv_lastname.getText().toString();
                String address = Applay_Loan_Activity.this.tv_address.getText().toString();
                String emailid = Applay_Loan_Activity.this.tv_email_address.getText().toString();
                String moibilenuber = Applay_Loan_Activity.this.tv_mobile.getText().toString();
                String income = Applay_Loan_Activity.this.monthly_income.getText().toString();
                if (firtnamre.isEmpty()) {
                    Applay_Loan_Activity.this.tv_firstname.setError("Enter First Name");
                } else if (lastname.isEmpty()) {
                    Applay_Loan_Activity.this.tv_lastname.setError("Enter Last name");
                } else if (Applay_Loan_Activity.this.dob.getText().toString().isEmpty()) {
                    Applay_Loan_Activity.this.dob.setError("Please Select Birth Date");
                } else if (address.isEmpty()) {
                    Applay_Loan_Activity.this.tv_address.setError("Enter Your Address");
                } else if (emailid.isEmpty() || !emailid.endsWith("@gmail.com")) {
                    Applay_Loan_Activity.this.tv_email_address.setError("Enter Valid Address");
                } else if (moibilenuber.isEmpty() || moibilenuber.length() != 10) {
                    Applay_Loan_Activity.this.tv_mobile.setError("Enter Valid Mobile NUmber");
                } else if (income.isEmpty()) {
                    Applay_Loan_Activity.this.monthly_income.setError("Enter Your Encome");
                } else {
                    Applay_Loan_Activity.this.Dailoge();
                }
            }
        });
    }

    public void Dailoge() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.girldialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -1;
        lp.gravity = 17;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        ((ImageView) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Applay_Loan_Activity.this).showAppOpenAd(Applay_Loan_Activity.this, new AppManager.SplashAdCallback() {
                    public void callbackCall() {
                        Applay_Loan_Activity.this.startActivity(new Intent(Applay_Loan_Activity.this, Instant_Loan.class));
                        Applay_Loan_Activity.this.finish();
                    }
                });
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance(this).showAppOpenAd(this, new AppManager.SplashAdCallback() {
            public void callbackCall() {
                Applay_Loan_Activity.this.startActivity(new Intent(Applay_Loan_Activity.this, Instant_Loan_Single_Activity.class));
                Applay_Loan_Activity.this.finish();
            }
        });
    }
}
