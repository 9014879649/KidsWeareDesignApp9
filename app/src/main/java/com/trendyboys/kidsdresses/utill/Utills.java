package com.trendyboys.kidsdresses.utill;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by Nageswara Rao.CH on 5/1/2018.
 */

public class Utills {

   public static boolean isOnline(Context context){
       ConnectivityManager conMgr = (ConnectivityManager) context
               .getSystemService(Context.CONNECTIVITY_SERVICE);
       if (conMgr.getActiveNetworkInfo() != null
               && conMgr.getActiveNetworkInfo().isAvailable()
               && conMgr.getActiveNetworkInfo().isConnected()) {
          return true;
       } else {
           Toast.makeText(context, "Check your Network Connection",
                   Toast.LENGTH_SHORT).show();
           return false;
       }
    }
}
