package com.momentum.coaching.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momentum.coaching.R;
import com.momentum.coaching.diploma.DiplomaAdapter;
import com.momentum.coaching.model.ListModel;

import java.util.ArrayList;

public class WrongAdapter extends RecyclerView.Adapter<WrongAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ListModel> objects;

    public WrongAdapter(Context context, ArrayList<ListModel> objects) {
        this.context = context;
        this.objects = objects;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //ListModel listModel = objects.get(position);
        holder.txtqstn.setText("Question: " + objects.get(position).getQuestion());
        holder.txtselectedANS.setText("Selected Answer : " + objects.get(position).getSelectedAnswer());
        holder.txtactualANS.setText("Right Answer : " + objects.get(position).getActualAnswer());

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtqstn ;
        TextView txtselectedANS;
        TextView txtactualANS;

        public ViewHolder(View itemView) {
            super(itemView);

            txtqstn = itemView.findViewById(R.id.txtQSTN);
            txtselectedANS = itemView.findViewById(R.id.txtSelectedANS);
            txtactualANS = itemView.findViewById(R.id.txtActualANS);
        }
    }
}
