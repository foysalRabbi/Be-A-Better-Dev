package com.momentum.coaching.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.momentum.coaching.Adapter.DepartmentAdapter;
import com.momentum.coaching.R;

public class QuizFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private int[] Quiz_subject_image = {R.drawable.physicslogo,R.drawable.chemistrylogo,R.drawable.mathslogo,R.drawable.englishlogo,
            R.drawable.random,R.drawable.viewscore};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quiz, container, false);

        recyclerView=view.findViewById(R.id.quizRecylerView);
        final String[] quize_subject = getResources().getStringArray(R.array.quiz_subject);
        DepartmentAdapter adapter_p = new DepartmentAdapter(getActivity(),quize_subject,Quiz_subject_image);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter_p);
        return view;
    }

}
