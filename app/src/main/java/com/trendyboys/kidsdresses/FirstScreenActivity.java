package com.trendyboys.kidsdresses;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class FirstScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Button mStartBt,mRateBt,mMoreBt,mShareBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        getSupportActionBar().hide();
//RecipesActivity
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.e("FirstScreen","Ad Mob InitializationStatus ========"+String.valueOf(initializationStatus));
            }
        });


        mStartBt = (Button)findViewById(R.id.start_bt);
        mStartBt.setOnClickListener(this);
        mRateBt = (Button)findViewById(R.id.rateApp_bt);
        mRateBt.setOnClickListener(this);
        mMoreBt = (Button)findViewById(R.id.moreApps_bt);
        mMoreBt.setOnClickListener(this);
        mShareBt = (Button)findViewById(R.id.shareApp_bt);
        mShareBt.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_bt:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.rateApp_bt:
                rateThisApp();
                break;
            case R.id.moreApps_bt:
                moreApps();
                break;
            case R.id.shareApp_bt:
                shareAppLink();
                break;
        }
    }



    public  void  moreApps(){
        try
        {
            Intent view = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=TRENDYBOYS"));
            startActivity(view);
            return;
        }
        // Misplaced declaration of an exception variable
        catch (Exception e)
        {
            Log.e("more apps button", e.getMessage());
        }

    }

    public void shareAppLink() {

        String data = getResources().getString(R.string.app_name) +"\n Free Download Link \n"+ "\n" + "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
        Intent sendIntent = new Intent();
        // Set the action to be performed i.e 'Send Data'
        sendIntent.setAction(Intent.ACTION_SEND);
        // Add the text to the intent
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        // Set the type of data i.e 'text/plain'
        sendIntent.setType("text/plain");
        // Launches the activity; Open 'Text editor' if you set it as default app to handle Text
        startActivity(sendIntent);
    }
    public void rateThisApp(){
        String packageName = getApplicationContext().getPackageName();
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }


}
