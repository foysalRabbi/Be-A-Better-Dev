package com.momentum.coaching.diploma;

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
import com.momentum.coaching.allmodeltest.ModelTestActivity;
import com.momentum.coaching.webview.WebViewActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiplomaAdapter extends RecyclerView.Adapter<DiplomaAdapter.MyViewHolder> {

    private Context context;
    private String[] diploaSubjectName;
    private int [] diplomaSubjectImage;
    private ConnectivityManager connectivityManager;

    public DiplomaAdapter(Context context, String[] diploaSubjectName, int[] diplomaSubjectImage) {
        this.context = context;
        this.diploaSubjectName = diploaSubjectName;
        this.diplomaSubjectImage = diplomaSubjectImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bcsdesign, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.imageView.setVisibility(View.GONE);
        holder.diplomaImage.setImageResource(diplomaSubjectImage[position]);
        holder.dimplomaName.setText(diploaSubjectName[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = holder.dimplomaName.getText().toString();
                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("Momentum",subjectName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    Snackbar.make(view, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return diploaSubjectName.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView diplomaImage;
        private TextView dimplomaName;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            diplomaImage = itemView.findViewById(R.id.bcsImage_id);
            dimplomaName = itemView.findViewById(R.id.bcsImageName_id);
            imageView = itemView.findViewById(R.id.downloadButton);
        }
    }
}
