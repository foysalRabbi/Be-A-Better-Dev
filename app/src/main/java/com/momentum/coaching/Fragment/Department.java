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

public class Department extends Fragment {
    private View view;
    private RecyclerView myRecyclerView;
    private int [] subject_image = {R.drawable.c,R.drawable.data_structure,R.drawable.mathslogo,R.drawable.digital_elec,R.drawable.windows,
            R.drawable.system_analysis,R.drawable.network,R.drawable.microprocessor, R.drawable.dbms,R.drawable.transistor,R.drawable.electricity,R.drawable.prev_questions,};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_department, container, false);

        final String[] subject_name = getResources().getStringArray(R.array.Subject_Name);

        myRecyclerView = view.findViewById(R.id.dRecycler_id);
        myRecyclerView.setHasFixedSize(true);
        DepartmentAdapter recyclerAdapter = new DepartmentAdapter(getContext(),subject_name,subject_image);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        myRecyclerView.setAdapter(recyclerAdapter);
        return view;
    }
}
