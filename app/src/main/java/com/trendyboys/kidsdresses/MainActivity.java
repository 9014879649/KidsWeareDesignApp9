package com.trendyboys.kidsdresses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.trendyboys.kidsdresses.adapter.ParentAdapter;
import com.trendyboys.kidsdresses.model.ItemImage;
import com.trendyboys.kidsdresses.model.ItemList;
import com.trendyboys.kidsdresses.utill.DialogsUtils;
import com.trendyboys.kidsdresses.utill.Global;
import com.trendyboys.kidsdresses.utill.RecyclerTouchListener;
import com.trendyboys.kidsdresses.utill.RecyclerViewClickListener;
import com.trendyboys.kidsdresses.utill.Utills;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    int currentOffset = 0;
    int mMaxDisplay_Size = 6;
    int mTotal_Size = 0;
    int selPos;
    ProgressDialog progressDialog;

    String categories[] = {"Birthday Special","Casual Dresses","Formal Dresses","Home Made Dresses ","Party Dresses"};

    ArrayList<String> imagesList = new ArrayList<>();
    ArrayList<ItemImage> Pathitems = new ArrayList<>();
    private List<ItemList> mItemList = new ArrayList<>();


    private ParentAdapter mAdapter;
    RecyclerView recyclerView;

    InterstitialAd interstitial;
    AdRequest adFullScreenRequest;
    ProgressDialog myDialog;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.categories_rv);

            loadBigAd();




        for (int i=0;i<categories.length;i++){
            int k=10;//categories wise images size

            if (i == 0){
                //design 1
                prepareGalleryData(Global.getDesign1Images(),k,categories[i],"date");

            }else if(i == 1){
                //design 2
                prepareGalleryData(Global.getDesign2Images(),k,categories[i],"date");

            }else if(i == 2){
                //design 3
                prepareGalleryData(Global.getDesign3Images(),k,categories[i],"date");

            }else if(i == 3){
                //design 3
                prepareGalleryData(Global.getDesign4Images(),k,categories[i],"date");

            }else if(i == 4){
                //design 3
                prepareGalleryData(Global.getDesign5Images(),k,categories[i],"date");

            }

        }
        LinearLayoutManager mLayoutManager =  new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ParentAdapter(getApplicationContext(), mItemList, mMaxDisplay_Size, mTotal_Size,recyclerView);

        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, final int position) {

                selPos = position;

//                new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected void onPreExecute() {
//                       DialogsUtils.showDialog(MainActivity.this);
//
//                    }
//
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//
//
//                        try {
//                           Thread.sleep(500);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
////                        DialogsUtils.dismissProgressDialog();
//                        DialogsUtils.dismissDialog();
//
//
//
//
//                    }
//                }.execute();


                if (i==0){
                    if (interstitial!=null && interstitial.isLoaded()){
                        interstitial.show();
                        i++;
                    }else {
                        navigateToAnotherActivity(selPos);
                    }

                }else {
                    navigateToAnotherActivity(selPos);
                }


            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Utills.isOnline(MainActivity.this)){
            if(interstitial!=null){
                interstitial.loadAd(adFullScreenRequest);
            }
        }
    }

    public void loadBigAd(){
        // AdMob full screen ad
        adFullScreenRequest = new AdRequest.Builder().build();
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.initial_ad_id));

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                navigateToAnotherActivity(selPos);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }
        });
    }


    public void navigateToAnotherActivity(int pos){

        Intent intent = new Intent(getApplicationContext(),SelectedItemsActivity.class);
        intent.putExtra("position",pos);
        startActivity(intent);
    }

    private void prepareGalleryData(ArrayList<String> imagesList, int k, String title, String date) {

        int size = imagesList.size();

        ArrayList<ItemImage> mPathitems = new ArrayList<>();
        boolean isCol2Avail = false;
        Pathitems.clear();
        if (size>0) {
            ItemImage i1 = new ItemImage(1, imagesList.get(0), imagesList.get(0));
            int colSpan1 = Math.random() < 0.2f ? 2 : 1;
            int rowSpan1 = colSpan1;
            if (colSpan1 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan1 == 2 && isCol2Avail)
                colSpan1 = 1;

            i1.setColumnSpan(colSpan1);
            i1.setRowSpan(rowSpan1);
            i1.setPosition(currentOffset + 0);
            Pathitems.add(i1);
        }


        if (size>1) {
            ItemImage i2 = new ItemImage(2, imagesList.get(1), imagesList.get(1));
            int colSpan2 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan2 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan2 == 2 && isCol2Avail)
                colSpan2 = 1;

            int rowSpan2 = colSpan2;
            i2.setColumnSpan(colSpan2);
            i2.setRowSpan(rowSpan2);
            i2.setPosition(currentOffset + 1);
            Pathitems.add(i2);
        }

        if (size>2) {
            ItemImage i3 = new ItemImage(3, imagesList.get(2), imagesList.get(2));
            int colSpan3 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan3 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan3 == 2 && isCol2Avail)
                colSpan3 = 1;

            int rowSpan3 = colSpan3;
            i3.setColumnSpan(colSpan3);
            i3.setRowSpan(rowSpan3);
            i3.setPosition(currentOffset + 2);
            Pathitems.add(i3);
        }

        if (size>3) {
            ItemImage i4 = new ItemImage(4, imagesList.get(3), imagesList.get(3));
            int colSpan4 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan4 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan4 == 2 && isCol2Avail)
                colSpan4 = 1;

            int rowSpan4 = colSpan4;
            i4.setColumnSpan(colSpan4);
            i4.setRowSpan(rowSpan4);
            i4.setPosition(currentOffset + 3);
            Pathitems.add(i4);
        }

        if (size>4) {
            ItemImage i5 = new ItemImage(5, imagesList.get(4), imagesList.get(4));
            int colSpan5 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan5 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan5 == 2 && isCol2Avail)
                colSpan5 = 1;

            int rowSpan5 = colSpan5;
            i5.setColumnSpan(colSpan5);
            i5.setRowSpan(rowSpan5);
            i5.setPosition(currentOffset + 4);
            Pathitems.add(i5);

        }

        if (size>5) {
            ItemImage i6 = new ItemImage(6, imagesList.get(5), imagesList.get(5));
            int colSpan6 = Math.random() < 0.2f ? 2 : 1;

            if (colSpan6 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan6 == 2 && isCol2Avail)
                colSpan6 = 1;

            int rowSpan6 = colSpan6;
            i6.setColumnSpan(colSpan6);
            i6.setRowSpan(rowSpan6);
            i6.setPosition(currentOffset + 5);
            Pathitems.add(i6);
        }
        if (size>6) {
            ItemImage i7 = new ItemImage(7, imagesList.get(6), imagesList.get(6));
            int colSpan7 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan7 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan7 == 2 && isCol2Avail)
                colSpan7 = 1;

            int rowSpan7 = colSpan7;
            i7.setColumnSpan(colSpan7);
            i7.setRowSpan(rowSpan7);
            i7.setPosition(currentOffset + 6);
            Pathitems.add(i7);
        }
        if (size>7) {
            ItemImage i8 = new ItemImage(8, imagesList.get(7), imagesList.get(7));
            int colSpan8 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan8 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan8 == 2 && isCol2Avail)
                colSpan8 = 1;

            int rowSpan8 = colSpan8;
            i8.setColumnSpan(colSpan8);
            i8.setRowSpan(rowSpan8);
            i8.setPosition(currentOffset + 7);
            Pathitems.add(i8);
        }
        if (size>8) {
            ItemImage i9 = new ItemImage(9, imagesList.get(8), imagesList.get(8));
            int colSpan9 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan9 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan9 == 2 && isCol2Avail)
                colSpan9 = 1;

            int rowSpan9 = colSpan9;
            i9.setColumnSpan(colSpan9);
            i9.setRowSpan(rowSpan9);
            i9.setPosition(currentOffset + 8);
            Pathitems.add(i9);
        }





        if (mMaxDisplay_Size<Pathitems.size()){
            for(int i = 0; i < mMaxDisplay_Size;i++)
            {
                mPathitems.add(Pathitems.get(i));
            }
        }else {
            for(int i = 0; i < Pathitems.size();i++)
            {
                mPathitems.add(Pathitems.get(i));
            }
        }




//        ItemList itemList = new ItemList(k,"User "+(k+1),mPathitems,size);
        ItemList itemList = new ItemList(k,title+"-"+date,mPathitems,size);
        itemList.setImgCount(size);
        mItemList.add(itemList);
        currentOffset += mPathitems.size();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
