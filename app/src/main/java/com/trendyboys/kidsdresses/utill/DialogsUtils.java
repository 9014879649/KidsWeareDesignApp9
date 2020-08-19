package com.trendyboys.kidsdresses.utill;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;

import com.trendyboys.kidsdresses.R;

/**
 * Created by Nageswarao on 15-05-2018.
 */

public class DialogsUtils {
    public static ProgressDialog   progressDialog;

    public static void showProgressDialog(Context context){

        ProgressDialog   progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading,Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    public static void dismissProgressDialog(){
        if (progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    public static Dialog dialog;
    public static void showDialog(AppCompatActivity activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        dialog.show();

    }


    public static void dismissDialog(){
        try {
            dialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
