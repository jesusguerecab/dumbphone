package edu.utep.cs.cs4330.dumbphone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AppSelectList extends AppCompatActivity {

    private AppsManager appsManager;
    private PackageManager manager;
    private List<App> apps;
    private ListView apps_list_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_select_list);

        manager = getPackageManager();
        appsManager = AppsManager.getInstance(this);
        apps = appsManager.getApps();

        loadListView();
    }

    private void loadListView(){
        apps_list_select = findViewById(R.id.apps_list_select);

        ArrayAdapter<App> adapter = new ArrayAdapter<App>(this, R.layout.checkable_app, apps) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.checkable_app, null);
                }

                ImageView appIcon = convertView.findViewById(R.id.iconC);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appName = convertView.findViewById(R.id.nameC);
                appName.setText(apps.get(position).name);

                CheckBox appSelected=convertView.findViewById(R.id.checkBoxApp);
                appSelected.setChecked(false);

                return convertView;
            }
        };

        apps_list_select.setAdapter(adapter);
        addClickListener();
    }

    private void addClickListener(){
        apps_list_select.setOnItemClickListener((parent, view, position, id) -> {

            //here get if it is checked or not
        });
    }
}
