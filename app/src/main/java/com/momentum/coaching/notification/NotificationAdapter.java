package com.momentum.coaching.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momentum.coaching.R;

import org.w3c.dom.Text;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context context;
    private List<NotificationModel> modelList;

    public NotificationAdapter(Context context, List<NotificationModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final NotificationModel notificationModelClass = modelList.get(position);
        holder.notificationHeadLine.setText(notificationModelClass.getNotificationHeadLine());
        holder.notificationDescription.setText(notificationModelClass.getNotificationDescription());
        holder.notificationTime.setText(notificationModelClass.getTime());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView notificationHeadLine;
        private TextView notificationDescription;
        private TextView notificationTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationHeadLine = itemView.findViewById(R.id.notification_title_id);
            notificationDescription = itemView.findViewById(R.id.notifiation_dec_id);
            notificationTime = itemView.findViewById(R.id.currentTime_id);
        }
    }
}
