package edu.utep.cs.cs4330.dumbphone;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class AppStatistics extends AppCompatActivity {


    AppsManager appsManager;
    RingProgressBar ringProgressBar2;
    TextView dailyUsage;

    private ArrayList<String> appNames = new ArrayList<>();
    private ArrayList<Integer> appProgress = new ArrayList<>();

    int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_stats);
        appsManager = AppsManager.getInstance(this);

        ringProgressBar2 = findViewById(R.id.progress_bar_2);
        dailyUsage = findViewById(R.id.dailyAppUsage);

        initRecyclerView();

        ringProgressBar2.setProgress(totalTime);
        dailyUsage.setText(totalTime/60 + " Hours");
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.app_stats_list);
        recyclerView.setLayoutManager(layoutManager);
        AppStatsAdapter adapter = new AppStatsAdapter(this);
        recyclerView.setAdapter(adapter);
        totalTime = adapter.getTotalTime();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
