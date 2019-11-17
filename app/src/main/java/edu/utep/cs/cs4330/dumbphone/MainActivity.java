package edu.utep.cs.cs4330.dumbphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newSchedule=findViewById(R.id.newScheduleButton);

        newSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewSchopenNewSchedule();
            }
        });
    }
    
    private void openNewSchopenNewSchedule(){
        Intent intent= new Intent(this,NewScheduleActivity.class);
        startActivity(intent);
        
    }
}