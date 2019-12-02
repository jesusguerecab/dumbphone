package edu.utep.cs.cs4330.dumbphone;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class AppStatsAdapter extends RecyclerView.Adapter<AppStatsAdapter.ViewHolder>{



    private static final boolean localLOGV = false;
    private AppStatsAdapter.UsageTimeComparator mUsageTimeComparator = new AppStatsAdapter.UsageTimeComparator();
    private Context mContext;
    private UsageStatsManager mUsageStatsManager;
    private PackageManager mPm;
    private final ArrayMap<String, String> mAppLabelMap = new ArrayMap<>();
    private final ArrayList<UsageStats> mPackageStats = new ArrayList<>();

    public AppStatsAdapter(Context mContext) {
        this.mContext = mContext;

        mPm = mContext.getPackageManager();
        mUsageStatsManager = (UsageStatsManager) mContext.getSystemService(Context.USAGE_STATS_SERVICE);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);

        final List<UsageStats> stats =
                mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                        cal.getTimeInMillis(), System.currentTimeMillis());
        if (stats == null) {
            return;
        }
        ArrayMap<String, UsageStats> map = new ArrayMap<>();

        final int statCount = stats.size();
        for (int i = 0; i < statCount; i++) {
            final android.app.usage.UsageStats pkgStats = stats.get(i);

            // load application labels for each application
            try {
                ApplicationInfo appInfo = mPm.getApplicationInfo(pkgStats.getPackageName(), 0);
                String label = appInfo.loadLabel(mPm).toString();
                mAppLabelMap.put(pkgStats.getPackageName(), label);

                UsageStats existingStats =
                        map.get(pkgStats.getPackageName());
                if (existingStats == null) {
                    map.put(pkgStats.getPackageName(), pkgStats);
                } else {
                    existingStats.add(pkgStats);
                }

            } catch (PackageManager.NameNotFoundException e) {
                // This package may be gone.
            }
        }
        mPackageStats.addAll(map.values());

        // Sort list
        sortList();
    }

    private void sortList() {
        if (localLOGV) Log.i("SortList", "Sorting by usage time");
        Collections.sort(mPackageStats, mUsageTimeComparator);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_stats_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int totalTime = getTotalTime();
        UsageStats pkgStats = mPackageStats.get(position);
        if (pkgStats != null) {
            String label = mAppLabelMap.get(pkgStats.getPackageName());
            holder.appName.setText(label);
            int progress = (int) pkgStats.getTotalTimeInForeground() / 60000;
            if(progress<= 0){
                progress = 0;
            }
            holder.progressBar.setProgress(progress);
            holder.progressBar.setMax(totalTime);
            int hours = progress / 60; //since both are ints, you get an int
            int minutes = progress % 60;
            System.out.printf("%d:%02d", hours, minutes);
            if(hours > 0)
                holder.appUsage.setText(hours + " hrs " + minutes +" mins");
            else
                holder.appUsage.setText(progress + " mins");
        }else {
            Log.w("BindViewHolder", "No usage stats info for package:" + position);
        }


    }

    @Override
    public int getItemCount() {
        return mPackageStats.size();
    }

    public int getTotalTime() {
        int totalTime = 0;
        for(UsageStats uS : mPackageStats){
            totalTime += (int) uS.getTotalTimeInForeground() / 60000;
        }
        return totalTime;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RingProgressBar progressBar;
        TextView appName;
        TextView appUsage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.item_progress_bar);
            appName = itemView.findViewById(R.id.app_name_item);
            appUsage = itemView.findViewById(R.id.app_usage);
        }
    }

    public static class UsageTimeComparator implements Comparator<UsageStats> {
        @Override
        public final int compare(UsageStats a, UsageStats b) {
            return (int)(b.getTotalTimeInForeground() - a.getTotalTimeInForeground());
        }
    }


}

