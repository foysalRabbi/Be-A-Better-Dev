package com.momentum.coaching.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.momentum.coaching.Adapter.DepartmentAdapter;
import com.momentum.coaching.R;


public class NonDepartment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private int[] nonSubject_image = {R.drawable.physicslogo,R.drawable.chemistrylogo,R.drawable.mathslogo,R.drawable.englishlogo,
    R.drawable.englishlogo,R.drawable.englishlogo};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_non_department, container, false);
        final String[] non_subject = getResources().getStringArray(R.array.Non_Subject_Name);

        recyclerView = view.findViewById(R.id.nonRecyclerView);
        recyclerView.setHasFixedSize(true);
        DepartmentAdapter adapter_p = new DepartmentAdapter(getActivity(),non_subject,nonSubject_image);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter_p);
        return view;
    }

}
