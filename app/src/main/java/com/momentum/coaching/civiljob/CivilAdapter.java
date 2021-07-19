package com.momentum.coaching.civiljob;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.momentum.coaching.R;
import com.momentum.coaching.bcspreliminary.BCSCustomAdapter;
import com.momentum.coaching.leaderboard.physics.LeaderBoardPhyB;
import com.momentum.coaching.webview.WebViewActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class CivilAdapter extends RecyclerView.Adapter<CivilAdapter.ViewHolder> {
    private Context context;
    private String[] civilName;
    private int[] civilImage;
    private ConnectivityManager connectivityManager;

    public CivilAdapter(Context context, String[] civilName, int[] civilImage) {
        this.context = context;
        this.civilName = civilName;
        this.civilImage = civilImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bcsdesign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.downloadButton.setVisibility(View.GONE);
        holder.civilTextName.setText(civilName[position]);
        holder.civilTextImage.setImageResource(civilImage[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    String topicName = holder.civilTextName.getText().toString();
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("Momentum",topicName);
                    context.startActivity(intent);

                } else {

                    Snackbar.make(view, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return civilImage.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civilTextImage;
        private TextView civilTextName;
        private ImageView downloadButton;


        public ViewHolder(View itemView) {
            super(itemView);

            civilTextImage = itemView.findViewById(R.id.bcsImage_id);
            civilTextName = itemView.findViewById(R.id.bcsImageName_id);
            downloadButton = itemView.findViewById(R.id.downloadButton);
        }
    }
}
