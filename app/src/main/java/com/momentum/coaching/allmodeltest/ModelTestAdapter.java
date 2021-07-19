package com.momentum.coaching.allmodeltest;

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
import com.momentum.coaching.bcspreliminary.FileUploadModel;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.momentum.coaching.allmodeltest.ModelTestActivity.modelTestDepartment;

public class ModelTestAdapter extends RecyclerView.Adapter<ModelTestAdapter.ViewHolder> {

    private Context context;
    private List<FileUploadModel> modelTestList;
    private StorageReference modelTestStorage;
    private long downloadID;


    public ModelTestAdapter(Context context, List<FileUploadModel> modelTestList) {
        this.context = context;
        this.modelTestList = modelTestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bcsdesign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final FileUploadModel takeFile = modelTestList.get(position);
        holder.modelTestImage.setImageResource(R.drawable.momentumlogo);
        holder.modelTestText.setText(takeFile.getBcsSubjectName());
        Glide.with(context).load(R.raw.downloads).into(holder.downloadProgress);

        File file = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/" + takeFile.getBcsSubjectName() + ".pdf");
        if (file.exists()) {
            holder.downloadComplete.setVisibility(View.VISIBLE);
            holder.downloadButton.setVisibility(View.GONE);
            holder.downloadProgress.setVisibility(View.GONE);
        } else {
            holder.downloadComplete.setVisibility(View.GONE);
            holder.downloadButton.setVisibility(View.VISIBLE);
            holder.downloadProgress.setVisibility(View.GONE);
        }

        context.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/" + takeFile.getBcsSubjectName() + ".pdf");
                if (file.exists()) {
                    //Open file from External Storage
                    Intent intent = new Intent(context, PdfView.class);
                    intent.putExtra("Momentum", takeFile.getBcsSubjectName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "File not downloaded", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.downloadButton.setVisibility(View.GONE);
                holder.downloadProgress.setVisibility(View.VISIBLE);
                switch (modelTestDepartment) {
                    case "Computer":
                        modelTestStorage = FirebaseStorage.getInstance().getReference("Computer Model Test");
                        break;
                    case "Civil":
                        modelTestStorage = FirebaseStorage.getInstance().getReference("Civil Model Test");
                        break;
                    case "Electrical":
                        modelTestStorage = FirebaseStorage.getInstance().getReference("Electrical Model Test");
                        break;
                    case "Mechanical":
                        modelTestStorage = FirebaseStorage.getInstance().getReference("Mechanical Model Test");
                        break;
                    case "All Model Test":
                        modelTestStorage = FirebaseStorage.getInstance().getReference("Non Model Test");
                        break;
                    default:
                        Toast.makeText(context, "Data Base Error ", Toast.LENGTH_SHORT).show();
                }
                try {
                    modelTestStorage.child(takeFile.getBcsSubjectName()).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    File file = new File(Environment.getExternalStorageDirectory(), "Download/.Momentum/" + takeFile.getBcsSubjectName() + ".pdf");
                                    Toast.makeText(context, "Download Started ( IN BACKGROUND) ", Toast.LENGTH_SHORT).show();
                                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                                    DownloadManager.Request request = new DownloadManager.Request(uri)
                                            .setTitle("File Download")
                                            .setDescription(takeFile.getBcsSubjectName())
                                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                                            .setDestinationUri(Uri.fromFile(file));
                                    downloadID = downloadManager.enqueue(request);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Error Occurred : " + e, Toast.LENGTH_SHORT).show();

                                }
                            });

                } catch (Exception e) {
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
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
                notifyDataSetChanged();
                context.unregisterReceiver(onDownloadComplete);
            }
        }

    };

    @Override
    public int getItemCount() {
        return modelTestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView modelTestImage;
        private ImageView downloadButton, downloadProgress, downloadComplete;
        private TextView modelTestText;

        public ViewHolder(View itemView) {
            super(itemView);

            modelTestImage = itemView.findViewById(R.id.bcsImage_id);
            modelTestText = itemView.findViewById(R.id.bcsImageName_id);
            downloadComplete = itemView.findViewById(R.id.downloadCompleted);
            downloadButton = itemView.findViewById(R.id.downloadButton);
            downloadProgress = itemView.findViewById(R.id.downloadProgress);
        }
    }
}
