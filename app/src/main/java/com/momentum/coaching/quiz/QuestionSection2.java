package com.momentum.coaching.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.momentum.coaching.DbHelper.DbHelper;
import com.momentum.coaching.R;
import com.momentum.coaching.model.QuestionChemistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class QuestionSection2 extends AppCompatActivity {

    private static final String TAG = QuestionSection2.class.getName();
    private List<QuestionChemistry> quesList1;
    public int score=0;
    private int ctr1=1;
    private QuestionChemistry currentQ1;
    private Toolbar toolbar;
    private TextView txtQuestion1;
    private RadioGroup grp;
    private RadioButton rda1, rdb1, rdc1, rdd1;
    private Button butNext1;
    private Random random1 = new Random();
    private ArrayList<Integer> list = new ArrayList<Integer>();
    private TextView textViewTime1;
    public ArrayList<String> wrongQuestListChemistry = new ArrayList<String>();
    public ArrayList<String> selectedAnsChemistry = new ArrayList<String>();
    public ArrayList<String> actualAnswerChemistry= new ArrayList<String>();
    private int number;
    private ProgressBar progressBar;
    private int progress = 1;
    private String tableName = "", catName = "";
    private TextView qstnNo;
    private String quizName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_section2);

        Bundle bundle2 = getIntent().getExtras();
        quizName = bundle2.getString("QuizType");
        toolbar = findViewById(R.id.questsec2_toolbar);
        toolbar.setTitle(quizName);
        setSupportActionBar(toolbar);

        qstnNo =  findViewById(R.id.qstnNo);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            tableName = (String) bundle.get("MOMENTUM");
            catName = (String) bundle.get("level_name");
            Log.d(TAG,tableName);
            Log.d("Level Name", catName);
        }
        number = 0;
        DbHelper db = new DbHelper(this);
        textViewTime1 = (TextView) findViewById(R.id.textViewTime);
        final CounterClass timer = new CounterClass(1800000, 1000);
        timer.start();

        quesList1 = db.getAllQuestions(tableName, catName);
        for (int i = 0; i < 50; i++) {
            while (true) {
                int next = random1.nextInt(50);
                if (!list.contains(next)) {
                    list.add(next);
                    break;
                }
            }
        }
        Log.d("ListSize check",""+quesList1.size());
        currentQ1=quesList1.get(list.get(0));
        txtQuestion1=(TextView)findViewById(R.id.textView1);
        rda1=(RadioButton)findViewById(R.id.radio0);
        rdb1=(RadioButton)findViewById(R.id.radio1);
        rdc1=(RadioButton)findViewById(R.id.radio2);
        rdd1=(RadioButton)findViewById(R.id.radio3);
        butNext1=(Button)findViewById(R.id.button1);
        setQuestionView();
        grp = (RadioGroup) findViewById(R.id.radioGroup1);
        butNext1.setEnabled(false);
        grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== R.id.radio0 || i == R.id.radio1 || i==R.id.radio2 || i == R.id.radio3)
                    butNext1.setEnabled(true);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(30);
        progressBar.setProgress(1);
        butNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = progress+1;
                progressBar.setProgress(progress);
                RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
                //Log.d("yourans", currentQ1.getANSWER1() + " " + answer.getText());
                if (currentQ1.getANSWER().equals(answer.getText())) {
                    score++;
                    //Log.d("score", "Your score" + score1);
                }
                else
                {
                    wrongQuestListChemistry.add(number, currentQ1.getQUESTION());
                    selectedAnsChemistry.add(number, answer.getText().toString());
                    actualAnswerChemistry.add(number, currentQ1.getANSWER());
                    number++;
                }
                grp.clearCheck();
                butNext1.setEnabled(false);
                if (ctr1 < 31) {
                    if (ctr1 == 30) {
                        butNext1.setText("End Test");
                    }
                    currentQ1 = quesList1.get(list.get(ctr1));
                    setQuestionView();
                } else {
                    timer.onFinish();
                    timer.cancel();
                }
            }
        });
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            textViewTime1.setText(hms);
        }

        @Override
        public void onFinish() {
            showResult();
        }
    }

    public void showResult(){
        Intent intent = new Intent(QuestionSection2.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("scoreChemistry", score);//Your score
        b.putString("section",tableName);//Your table name
        b.putString("category",catName);//Your category name
        intent.putStringArrayListExtra("wrongQuestions", wrongQuestListChemistry);
        intent.putStringArrayListExtra("selectedAnswer", selectedAnsChemistry);
        intent.putStringArrayListExtra("actualAnswer", actualAnswerChemistry);
        intent.putExtras(b); //Put your score to your next Intent
        startActivity(intent);
        finish();
    }

    private void setQuestionView(){
        txtQuestion1.setText(currentQ1.getQUESTION());
        rda1.setText(currentQ1.getOPTA());
        rdb1.setText(currentQ1.getOPTB());
        rdc1.setText(currentQ1.getOPTC());
        rdd1.setText(currentQ1.getOPTD());
        if(ctr1<10)
            qstnNo.setText("0" + ctr1 + "/30");
        else
            qstnNo.setText("" + ctr1+ "/30");
        ctr1++;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file

        //Setting message manually and performing action on button click
        builder.setMessage("আপনি কি টেস্ট বাতিল করতে চাচ্ছেন ? বাতিল করলে আপনার নাম্বার যুক্ত করা হবে না।")
                .setCancelable(false)
                .setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("না", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

}