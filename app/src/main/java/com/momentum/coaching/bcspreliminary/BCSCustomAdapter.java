package com.momentum.coaching.bcspreliminary;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.momentum.coaching.R;
import com.momentum.coaching.activity.PdfView;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.DOWNLOAD_SERVICE;

public class BCSCustomAdapter extends RecyclerView.Adapter<BCSCustomAdapter.MyViewHolder> {

    private Context context;
    private List<FileUploadModel> mBCSList;
    private StorageReference bcsStorage;
    private long downloadID;
    private boolean update;


    public BCSCustomAdapter(Context context, List<FileUploadModel> mBCSList) {
        this.context = context;
        this.mBCSList = mBCSList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bcsdesign, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FileUploadModel fetchFile = mBCSList.get(position);
        holder.bcsImage.setImageResource(R.drawable.bcs);
        holder.bcsImageName.setText(fetchFile.getBcsSubjectName());
        Glide.with(context).load(R.raw.downloads).into(holder.downloadProgress);

        File file = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/" + fetchFile.getBcsSubjectName() + ".pdf");
        if (file.exists()) {
            holder.downloadComplete.setVisibility(View.VISIBLE);
            holder.downLoadButton.setVisibility(View.GONE);
            holder.downloadProgress.setVisibility(View.GONE);
        } else {
            holder.downloadComplete.setVisibility(View.GONE);
            holder.downLoadButton.setVisibility(View.VISIBLE);
            holder.downloadProgress.setVisibility(View.GONE);
        }
        context.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/" + fetchFile.getBcsSubjectName() + ".pdf");
                if (file.exists()) {
                    //Open file from External Storage
                    Intent intent = new Intent(context, PdfView.class);
                    intent.putExtra("Momentum", fetchFile.getBcsSubjectName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "File not downloaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.downLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.downLoadButton.setVisibility(View.GONE);
                holder.downloadProgress.setVisibility(View.VISIBLE);
                bcsStorage = FirebaseStorage.getInstance().getReference("BCS Preliminary");
                try {
                    bcsStorage.child(fetchFile.getBcsSubjectName()).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                                    File file = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/" + fetchFile.getBcsSubjectName() + ".pdf");
                                    Toast.makeText(context, "Download Started ( IN BACKGROUND) ", Toast.LENGTH_SHORT).show();
                                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                                    DownloadManager.Request request = new DownloadManager.Request(uri)
                                            .setTitle("File Download")
                                            .setDescription(fetchFile.getBcsSubjectName())
                                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                                            .setDestinationUri(Uri.fromFile(file));

                                    downloadID = downloadManager.enqueue(request);

                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(context, "Error Occurred : " + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (Exception ignored) {
                }

            }
        });
    }

    BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();
                //refreshActivity(customAdapter, context);
                notifyDataSetChanged();
                context.unregisterReceiver(onDownloadComplete);

            }
        }

    };


    @Override
    public int getItemCount() {
        return mBCSList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView bcsImage;
        private ImageView downloadComplete;
        private ImageView downLoadButton;
        private TextView bcsImageName;
        private ImageView downloadProgress;

        public MyViewHolder(View itemView) {
            super(itemView);

            bcsImage = itemView.findViewById(R.id.bcsImage_id);
            bcsImageName = itemView.findViewById(R.id.bcsImageName_id);
            downloadComplete = itemView.findViewById(R.id.downloadCompleted);
            downloadProgress = itemView.findViewById(R.id.downloadProgress);
            downLoadButton = itemView.findViewById(R.id.downloadButton);

        }


    }
}
