package edu.utep.cs.cs4330.dumbphone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppsListActivity extends AppCompatActivity {

    private AppsManager appsManager;
    private PackageManager manager;
    private List<App> apps;
    private ListView apps_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);

        manager = getPackageManager();
        appsManager = AppsManager.getInstance(this);
        apps = appsManager.getApps();

        loadListView();
    }

    private void loadListView(){
        apps_list = (ListView) findViewById(R.id.apps_list);

        ArrayAdapter<App> adapter = new ArrayAdapter<App>(this, R.layout.app, apps) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.app, null);
                }

                ImageView appIcon = (ImageView) convertView.findViewById(R.id.icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appName = (TextView) convertView.findViewById(R.id.name);
                appName.setText(apps.get(position).name);

                return convertView;
            }
        };

        apps_list.setAdapter(adapter);
        addClickListener();
    }

    private void addClickListener(){
        apps_list.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = manager.getLaunchIntentForPackage(apps.get(position).label.toString());
            startActivity(i);
        });
    }
}
