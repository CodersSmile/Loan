package com.example.loan;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pesonal.adsdk.R;
import java.util.HashMap;
import java.util.Map;

public class RateDialogFeedbackFrag extends RateDialogFrag implements View.OnClickListener {
    private static final String RATING_KEY = "rating";
    private EditText etFeedback;
    /* access modifiers changed from: private */
    public float rating;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.setCancelable(false);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_feedback, container);
        this.etFeedback = (EditText) view.findViewById(R.id.et_feedback);
        view.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RateDialogFeedbackFrag.this.dismiss();
            }
        });
        view.findViewById(R.id.bt_send).setOnClickListener(this);
        if (savedInstanceState != null) {
            this.rating = savedInstanceState.getFloat(RATING_KEY);
        }
        return view;
    }

    public void setRating(float rating2) {
        this.rating = rating2;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putFloat(RATING_KEY, this.rating);
        super.onSaveInstanceState(outState);
    }

    public void onClick(View view) {
        String feedback = this.etFeedback.getText().toString();
        if (view.getId() == R.id.bt_send) {
            final String str = feedback;
            Volley.newRequestQueue(getContext()).add(new StringRequest(1, AESSUtils.decrypt(AESSUtils.baseCode()) + ("app/rating/" + getActivity().getPackageName()), new Response.Listener<String>() {
                public void onResponse(String response) {
                    if (RateDialogFeedbackFrag.this.rating >= 4.0f) {
                        RateDialogFeedbackFrag.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + RateDialogFeedbackFrag.this.getActivity().getPackageName())));
                    }
                    RateDialogFeedbackFrag.this.dismiss();
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    if (RateDialogFeedbackFrag.this.rating >= 4.0f) {
                        RateDialogFeedbackFrag.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + RateDialogFeedbackFrag.this.getActivity().getPackageName())));
                    }
                    RateDialogFeedbackFrag.this.dismiss();
                }
            }) {
                /* access modifiers changed from: protected */
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(RateDialogFeedbackFrag.RATING_KEY, String.valueOf(RateDialogFeedbackFrag.this.rating));
                    params.put("feedback", str);
                    return params;
                }
            });
        }
    }
}
