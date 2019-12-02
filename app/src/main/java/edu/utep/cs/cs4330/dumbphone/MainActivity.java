package edu.utep.cs.cs4330.dumbphone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    BroadcastReceiver mReceiver;

    int i = 0;
    TextView timeTextView, dateTextView;
    Button app_list_button,app_stats_button,newSchedule,unlockButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        KeyguardManager.KeyguardLock k1;

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        KeyguardManager km =(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        k1= km.newKeyguardLock("IN");
        k1.disableKeyguard();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new MyReciever();
        registerReceiver(mReceiver, filter);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        newSchedule=findViewById(R.id.newScheduleButton);

        newSchedule.setOnClickListener(view -> openNewSchopenNewSchedule());
        app_list_button = findViewById(R.id.app_list_button);
        app_list_button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AppsListActivity.class);
            startActivity(intent);
        });

        app_stats_button = findViewById(R.id.app_stats_button);
        app_stats_button.setOnClickListener(view -> {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            Intent intent = new Intent(getApplicationContext(), AppStatistics.class);
            startActivity(intent);
        });

        dateTextView = findViewById(R.id.date);
        timeTextView = findViewById(R.id.timeTextView);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        Log.d("ui", "run: ");
        dateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));

        String time = format.format(calendar.getTime());

        timeTextView.setText(time);
        new Thread() {
            public void run() {
                while(dateTextView.getVisibility() == View.VISIBLE) {

                    runOnUiThread(() -> {
                        timeTextView.setText(format.format(Calendar.getInstance().getTime()));
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        unlockButton = findViewById(R.id.unlockButton);

        unlockButton.setOnClickListener(view -> {
            app_list_button.setVisibility(View.VISIBLE);
            app_stats_button.setVisibility(View.VISIBLE);
            newSchedule.setVisibility(View.VISIBLE);
            timeTextView.setVisibility(View.INVISIBLE);
            dateTextView.setVisibility(View.INVISIBLE);
            unlockButton.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MyReciever.wasScreenOn){
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
            MyReciever.wasScreenOn = true;
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MAIN", "onRestoreInstanceState: ");
    }

    private void openNewSchopenNewSchedule(){
        Intent intent= new Intent(this,NewScheduleActivity.class);
        startActivity(intent);

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.i("Home Button","Clicked");
        }
        Log.d("KeyDown", String.valueOf(keyCode));
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

}