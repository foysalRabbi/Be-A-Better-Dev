package com.momentum.coaching.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.momentum.coaching.Adapter.WrongAdapter;
import com.momentum.coaching.R;
import com.momentum.coaching.model.ListModel;

import java.util.ArrayList;

public class WrongQuestion extends AppCompatActivity {

    public ArrayList<String> wrongQuests = new ArrayList<String>();
    public ArrayList<String> selectedAnswers = new ArrayList<String>();
    public ArrayList<String> actualAnswers = new ArrayList<String>();

    private ArrayList<ListModel> m_parts = new ArrayList<>();
    private RecyclerView recyclerView;
    ResultActivity resultActivity = new ResultActivity();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question);

        toolbar = findViewById(R.id.showResult_toolbar);
        toolbar.setTitle("Answer Key");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView =  findViewById(R.id.listView1);

        wrongQuests = resultActivity.wrongQuests;
        selectedAnswers = resultActivity.selectedAnswers;
        actualAnswers = resultActivity.actualAnswers;



        wrongQuests = getIntent().getStringArrayListExtra("wrongQuestions");
        selectedAnswers = getIntent().getStringArrayListExtra("selectedAnswer");
        actualAnswers = getIntent().getStringArrayListExtra("actualAnswer");

        String[] strQstn = new String[wrongQuests.size()];
        String[] strsAns = new String[selectedAnswers.size()];
        String[] straAns = new String[actualAnswers.size()];

        strQstn = wrongQuests.toArray(strQstn);
        strsAns = selectedAnswers.toArray(strsAns);
        straAns = actualAnswers.toArray(straAns);

        //Toast.makeText(getApplicationContext(), "ArrayList Size is "+ wrongQuests.size(), Toast.LENGTH_LONG).show();

        for(int i=0; i<strQstn.length;i++) {
            m_parts.add(new ListModel(strQstn[i], strsAns[i],straAns[i]));
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WrongAdapter listAdapter = new WrongAdapter(this, m_parts);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}

