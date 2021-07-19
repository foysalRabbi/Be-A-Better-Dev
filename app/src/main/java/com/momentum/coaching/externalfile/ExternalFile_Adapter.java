package com.momentum.coaching.externalfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.momentum.coaching.R;
import com.momentum.coaching.activity.PdfView;

import java.io.File;
import java.util.List;


public class ExternalFile_Adapter extends RecyclerView.Adapter<ExternalFile_Adapter.myViewHolder> {
    private Context mContext;
    private List<String> externalFileList;

    public ExternalFile_Adapter(Context mContext, List<String> mArrayListPPT) {
        this.mContext = mContext;
        this.externalFileList = mArrayListPPT;
    }

    @NonNull
    @Override
    public ExternalFile_Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.externaldesign, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExternalFile_Adapter.myViewHolder holder, final int position) {
        final String uploadCurrent = externalFileList.get(position);
        final String FileName = uploadCurrent.substring(0, uploadCurrent.lastIndexOf("."));
        holder.textView.setText(FileName);
        holder.externalFileImage.setImageResource(R.drawable.momentumlogo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PdfView.class);
                intent.putExtra("Momentum", FileName);
                mContext.startActivity(intent);
            }
        });
        holder.externalFileDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                //Setting message manually and performing action on button click
                builder.setCancelable(false)
                        .setPositiveButton(Html.fromHtml("<font color=\"#8C0000\" >" + "Yes" + "</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                externalFileList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, externalFileList.size());

                                File root = android.os.Environment.getExternalStorageDirectory();
                                String path = root.getAbsolutePath() + "/Download/.Momentum/";
                                Log.d("Files", "Path: " + path);
                                File directory = new File(path);
                                File[] files = directory.listFiles();
                                Log.d("Files", "Size: " + files.length);
                                String fileName = files[position].getName();
                                String recordingUri = root.getAbsolutePath() + "/Download/.Momentum/" + fileName;
                                File myfile = new File(recordingUri);
                                myfile.delete();
                                Toast.makeText(mContext, holder.textView.getText().toString() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color=\"#8C0000\" >" + "No" + "</font>"), null);
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(Html.fromHtml("<font color=\"#8C0000\" >" + "<b>" + "DELETE FILE" + "</b>" + "</font>"));
                alert.setMessage("Are you sure you want to delete?");
                alert.show();
                //externalFileList.clear();
            }
        });
    }

    @Override
    public int getItemCount() {
        return externalFileList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView externalFileDelete;
        private ImageView externalFileImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.bcsImageName_id);
            externalFileImage = itemView.findViewById(R.id.bcsImage_id);
            externalFileDelete = itemView.findViewById(R.id.downloadCompleted);
        }
    }
}
