package edu.utep.cs.cs4330.dumbphone;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class AppsManager {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static AppsManager apps_manager_instance = null;
    private PackageManager manager;
    private List<App> apps;
    private Context context;

    private AppsManager(Context context){
        this.context = context;
        this.manager = context.getPackageManager();
    }

    public boolean usageStatsAllowed() {
        try{
            ApplicationInfo applicationInfo = manager.getApplicationInfo(context.getPackageName(),0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Couldn't find any usage stats manager", Toast.LENGTH_SHORT);
            return false;
        }
    }

    public void loadApps(){
        if(apps != null)
            return;

        apps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for(ResolveInfo ri:availableActivities){
            App app = new App();
            app.label = ri.activityInfo.packageName;
            app.name = ri.loadLabel(manager);
            app.icon = ri.loadIcon(manager);
            apps.add(app);
        }
    }

    public void loadAppUsage(){

    }

    public static AppsManager getInstance(Context context){
        if(apps_manager_instance == null)
            apps_manager_instance = new AppsManager(context);
        return apps_manager_instance;
    }

    public List<App> getApps(){
        loadApps();
        return apps;
    }

    public ArrayList<String> getAppNames(){
        loadApps();

        ArrayList<String> appNames = new ArrayList<>();
        for(App app : apps){
            appNames.add((String) app.name);
        }

        return appNames;
    }

    public ArrayList<Integer> getAppUsages(){
        ArrayList<String> appNames = getAppNames();

        ArrayList<Long> appUsages = new ArrayList<>();
        for(String app:appNames){
            appUsages.add(sharedPreferences.getLong(app, 0));
        }

        double totalUsage = 0;
        for(int i = 0; i < appUsages.size(); i++)
            totalUsage += appUsages.get(i);

        ArrayList<Integer> appProgress = new ArrayList<>();
        for(Long usage : appUsages)
            appProgress.add((int) (usage/totalUsage));

        return appProgress;
    }

}
