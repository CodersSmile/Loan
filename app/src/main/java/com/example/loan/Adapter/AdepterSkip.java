package com.example.loan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import feyo.fastcash.personalloan.R;

public class AdepterSkip extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context mContext;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView ImgPopularTheme;
        TextView txtAppName;
        LinearLayout txt_install;

        RecyclerViewHolder(View view) {
            super(view);
            this.ImgPopularTheme = (ImageView) view.findViewById(R.id.imageview);
            this.txtAppName = (TextView) view.findViewById(R.id.txt_appname);
            this.txt_install = (LinearLayout) view.findViewById(R.id.txt_install);
        }
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.skip_item_file_horizontal, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
    }

    public int getItemCount() {
        return 0;
    }
}
