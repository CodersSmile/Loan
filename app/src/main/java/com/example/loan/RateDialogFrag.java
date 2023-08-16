package com.example.loan;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import com.pesonal.adsdk.R;

public class RateDialogFrag extends DialogFragment implements RatingBar.OnRatingBarChangeListener {
    public static final String KEY = "fragment_rate";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.setCancelable(false);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rate, container);
        ((RatingBar) view.findViewById(R.id.rb_stars)).setOnRatingBarChangeListener(this);
        getDialog().getWindow().requestFeature(1);
        ((Button) view.findViewById(R.id.bt_send)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(RateDialogFrag.this.getActivity(), "Please select 5 star rating!", 0).show();
            }
        });
        ((LinearLayoutCompat) view.findViewById(R.id.bt_no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RateDialogFrag.this.dismiss();
            }
        });
        return view;
    }

    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        RateDialogManager.showRateDialogFeedback(getActivity(), rating);
        dismiss();
    }
}
