package edu.utep.cs.cs4330.dumbphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReciever extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    private String TAG = "MyReciever";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d(TAG, "onReceive:1 ");
            wasScreenOn=false;
            Intent intent11 = new Intent(context,MainActivity.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent11);

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.d(TAG, "onReceive: 2");
            wasScreenOn=true;
            Intent intent11 = new Intent(context,MainActivity.class);
            context.startActivity(intent11);

        }
        else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            Log.d(TAG, "onReceive: 3");
            Intent intent11 = new Intent(context, MainActivity.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent11);


        }

    }
}
