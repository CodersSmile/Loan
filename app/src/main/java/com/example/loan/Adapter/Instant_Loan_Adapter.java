package com.example.loan.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import feyo.fastcash.inastant_loan.Activity.Instant_Loan;
import feyo.fastcash.inastant_loan.Activity.Instant_Loan_Single_Activity;
import feyo.fastcash.inastant_loan.Model.Loan_Data_class;
import feyo.fastcash.personalloan.R;
import feyo.pesonal.adsdk.AppManager;
import java.util.ArrayList;

public class Instant_Loan_Adapter extends RecyclerView.Adapter<Instant_Loan_Class> {
    ArrayList<Loan_Data_class> arrayList;
    Instant_Loan instant_loan_activity;

    public Instant_Loan_Adapter(Instant_Loan instant_loan_activity2, ArrayList<Loan_Data_class> arrayList2) {
        this.instant_loan_activity = instant_loan_activity2;
        this.arrayList = arrayList2;
    }

    public Instant_Loan_Class onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Instant_Loan_Class(LayoutInflater.from(this.instant_loan_activity).inflate(R.layout.loan_data_item, parent, false));
    }

    public void onBindViewHolder(Instant_Loan_Class holder, int position) {
        final Loan_Data_class loan_data_class = this.arrayList.get(position);
        holder.company_icon.setImageResource(loan_data_class.getIc_app_icon());
        holder.name.setText(loan_data_class.getSweetRupee());
        holder.ruppes.setText(loan_data_class.getRuppes());
        TextView textView = holder.interst;
        textView.setText("Interest Rate: " + loan_data_class.getInterset());
        TextView textView2 = holder.month;
        textView2.setText("Month: " + loan_data_class.getMonth());
        holder.next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppManager.getInstance(Instant_Loan_Adapter.this.instant_loan_activity).showInterstitialAd(Instant_Loan_Adapter.this.instant_loan_activity, new AppManager.InterstitialCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(Instant_Loan_Adapter.this.instant_loan_activity, Instant_Loan_Single_Activity.class);
                        intent.putExtra(AppMeasurementSdk.ConditionalUserProperty.NAME, loan_data_class.getSweetRupee());
                        intent.putExtra("ruppes", loan_data_class.getRuppes());
                        intent.putExtra("interst", loan_data_class.getInterset());
                        intent.putExtra("month", loan_data_class.getMonth());
                        intent.putExtra("image", loan_data_class.getIc_app_icon());
                        Instant_Loan_Adapter.this.instant_loan_activity.startActivity(intent);
                    }
                });
            }
        });
        if (position % 2 == 0) {
            holder.smallnatieve.setVisibility(0);
        } else {
            holder.smallnatieve.setVisibility(8);
        }
    }

    public int getItemCount() {
        return this.arrayList.size();
    }

    public class Instant_Loan_Class extends RecyclerView.ViewHolder {
        ImageView company_icon;
        TextView interst;
        TextView month;
        TextView name;
        LinearLayout next;
        ImageView reddom_now;
        TextView ruppes;
        RelativeLayout smallnatieve;

        public Instant_Loan_Class(View itemView) {
            super(itemView);
            this.reddom_now = (ImageView) itemView.findViewById(R.id.reddomnow);
            this.company_icon = (ImageView) itemView.findViewById(R.id.company_icon);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.ruppes = (TextView) itemView.findViewById(R.id.ruppes);
            this.interst = (TextView) itemView.findViewById(R.id.intrest);
            this.month = (TextView) itemView.findViewById(R.id.month);
            this.next = (LinearLayout) itemView.findViewById(R.id.next);
            this.smallnatieve = (RelativeLayout) itemView.findViewById(R.id.smallnatieve);
        }
    }
}
