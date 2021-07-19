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

public class Formulas extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private int [] formulasImage =  {R.drawable.momentumlogo,R.drawable.momentumlogo,R.drawable.momentumlogo,R.drawable.momentumlogo,
            R.drawable.momentumlogo,R.drawable.momentumlogo,R.drawable.momentumlogo};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_formulas, container, false);
        final String[] formulasName = getResources().getStringArray(R.array.formulas);

        mRecyclerView = view.findViewById(R.id.formulasRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        DepartmentAdapter adapter_p = new DepartmentAdapter(getActivity(),formulasName,formulasImage);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setAdapter(adapter_p);

        return view;
    }
}
