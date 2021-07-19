package com.momentum.coaching.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;
import com.momentum.coaching.R;
import com.momentum.coaching.activity.PdfView;
import com.momentum.coaching.allmodeltest.ModelTestActivity;
import com.momentum.coaching.englishpreposition.PrepositionActivity;
import com.momentum.coaching.englishproverbs.EnglishProverbs;
import com.momentum.coaching.quiz.RandomActivity;
import com.momentum.coaching.quiz.User_Level1;
import com.momentum.coaching.quiz.User_Level2;
import com.momentum.coaching.quiz.User_Level3;
import com.momentum.coaching.scoreboard.UserLevel_Score;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {
    private Context context;
    private String[] subject_name;
    private int[] subject_image;
    private ConnectivityManager connectivityManager;
    private InterstitialAd mInterstitialAd;

    public DepartmentAdapter(Context context, String[] subject_name, int[] subject_image) {
        this.context = context;
        this.subject_name = subject_name;
        this.subject_image = subject_image;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardexpenditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(subject_name[position]);
        holder.imageView.setImageResource(subject_image[position]);
        final String subject = holder.textView.getText().toString();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (subject.equals("Physics Quiz")) {
                    Intent intent = new Intent(context, User_Level1.class);
                    intent.putExtra("MOMENTUM", "questPhysicBasic");
                    intent.putExtra("SubjectName", subject);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (subject.equals("Chemistry Quiz")) {
                    Intent intent = new Intent(context, User_Level2.class);
                    intent.putExtra("MOMENTUM", "questChemistry");
                    intent.putExtra("SubjectName", subject);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (subject.equals("Math Quiz")) {
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();


                } else if (subject.equals("English Quiz")) {
                    Intent intent = new Intent(context, User_Level3.class);
                    intent.putExtra("MOMENTUM", "questEnglish");
                    intent.putExtra("SubjectName", subject);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (subject.equals("Random")) {
                    Intent intent = new Intent(context, RandomActivity.class);
                    intent.putExtra("MOMENTUM", "questRandom");
                    intent.putExtra("SubjectName", subject);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (subject.equals("View Score")) {
                    Intent intent = new Intent(context, UserLevel_Score.class);
                    intent.putExtra("MOMENTUM", subject);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Activity activity = (Activity) context;
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (subject.equals("Computer")) {
                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(context, ModelTestActivity.class);
                        intent.putExtra("Momentum", subject);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //Interestital ad
                        InterstitialMethod();
                    } else {
                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }

                } else if (subject.equals("Civil")) {
                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(context, ModelTestActivity.class);
                        intent.putExtra("Momentum", subject);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                } else if (subject.equals("Electrical")) {
                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        Intent intent = new Intent(context, ModelTestActivity.class);
                        intent.putExtra("Momentum", subject);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //Interestital ad
                        InterstitialMethod();

                    } else {
                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                } else if (subject.equals("Mechanical")) {
                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        Intent intent = new Intent(context, ModelTestActivity.class);
                        intent.putExtra("Momentum", subject);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                } else if (subject.equals("All Model Test")) {
                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(context, ModelTestActivity.class);
                        intent.putExtra("Momentum", subject);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //Interestital ad
                        InterstitialMethod();

                    } else {
                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                } else if (subject.equals("Eng Proverbs")) {
                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        Intent intent = new Intent(context, EnglishProverbs.class);
                        intent.putExtra("Momentum", subject);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        //Interestital ad
                        InterstitialMethod();

                    } else {
                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                } else if (subject.equals("Eng Preposition"))
                {
                    connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                        Intent intent = new Intent(context, PrepositionActivity.class);
                        intent.putExtra("Momentum", subject);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        InterstitialMethod();

                    } else {
                        Snackbar.make(v, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Intent intent = new Intent(context, PdfView.class);
                    intent.putExtra("Momentum", subject);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }


            }
        });

    }

    private void InterstitialMethod() {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.mInterstitialAd));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
                                          @Override
                                          public void onAdLoaded() {
                                              if (mInterstitialAd.isLoaded()) {
                                                  mInterstitialAd.show();
                                              }
                                              super.onAdLoaded();
                                          }

                                      }
        );
    }

    @Override
    public int getItemCount() {
        return subject_name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardExpendImage);
            textView = itemView.findViewById(R.id.cardExpendText);
        }
    }

}
