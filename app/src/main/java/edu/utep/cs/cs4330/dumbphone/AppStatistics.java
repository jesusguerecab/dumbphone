package edu.utep.cs.cs4330.dumbphone;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class AppStatistics extends AppCompatActivity {

    AppsManager appsManager;
    RingProgressBar ringProgressBar1, ringProgressBar2;

    private ArrayList<String> appNames = new ArrayList<>();
    private ArrayList<Integer> appProgress = new ArrayList<>();

    int progress = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_stats);
        appsManager = AppsManager.getInstance(this);

        ringProgressBar2 = findViewById(R.id.progress_bar_2);


        initRecyclerView();
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.app_stats_list);
        recyclerView.setLayoutManager(layoutManager);
        AppStatsAdapter adapter = new AppStatsAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
