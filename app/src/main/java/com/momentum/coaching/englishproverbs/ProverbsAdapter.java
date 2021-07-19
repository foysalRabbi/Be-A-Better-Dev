package com.momentum.coaching.englishproverbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.momentum.coaching.R;
import com.momentum.coaching.model.ProverbsModel;

import java.util.List;

public class ProverbsAdapter extends RecyclerView.Adapter<ProverbsAdapter.MyHolder> {
    private Context context;
    private List<ProverbsModel> proverbsModel;

    public ProverbsAdapter(Context context, List<ProverbsModel> proverbsModel) {
        this.context = context;
        this.proverbsModel = proverbsModel;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.englishproverb, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final ProverbsModel fetchData = proverbsModel.get(position);
        holder.question.setText(fetchData.getQuestion());
        holder.answer.setText("Answer: " + fetchData.getAnsPart());

    }

    @Override
    public int getItemCount() {
        return proverbsModel.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView question,answer;

        public MyHolder(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.txtQSTN);
            answer = itemView.findViewById(R.id.txtANS);
        }
    }
}
