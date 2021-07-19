package com.momentum.coaching.englishpreposition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momentum.coaching.R;
import com.momentum.coaching.model.ProverbsModel;

import java.util.List;

public class PrepositionAdapter extends RecyclerView.Adapter<PrepositionAdapter.ViewHolder> {
    private Context mCtx;
    private List<ProverbsModel> mcqModelList;

    public PrepositionAdapter(Context mCtx, List<ProverbsModel> mcqModelList) {
        this.mCtx = mCtx;
        this.mcqModelList = mcqModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prepostiondesign, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String mQuestion = mcqModelList.get(position).getQuestion();
        final String mAnsPart = mcqModelList.get(position).getAnsPart();

        holder.Question.setText(mQuestion);
        holder.viewAnswer.setText("Ans: "+mAnsPart);

        holder.button_view_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.button_view_text.getText().toString().equals("View Answer")) {
                    holder.viewAnswer.setVisibility(View.VISIBLE);
                    holder.button_view_text.setText("Hide Answer");
                } else {
                    holder.viewAnswer.setVisibility(View.GONE);
                    holder.button_view_text.setText("View Answer");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcqModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Question, viewAnswer,button_view_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Question = itemView.findViewById(R.id.preposition_question);
            viewAnswer = itemView.findViewById(R.id.tv_answer);
            button_view_text = itemView.findViewById(R.id.btn_view_answer);

        }
    }
}
