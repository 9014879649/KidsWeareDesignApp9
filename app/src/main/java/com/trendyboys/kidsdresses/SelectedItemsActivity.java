package com.trendyboys.kidsdresses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.trendyboys.kidsdresses.quiltview.QuiltView;
import com.trendyboys.kidsdresses.utill.DialogsUtils;
import com.trendyboys.kidsdresses.utill.Global;
import com.trendyboys.kidsdresses.utill.Utills;

import java.util.ArrayList;

public class SelectedItemsActivity extends AppCompatActivity {

    QuiltView quiltView;
    ArrayList<String> imagesList = new ArrayList<>();
    Intent intent;
    int pos;

    int selPos,actualPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        intent = getIntent();
        pos = intent.getIntExtra("position",0);
        setTitle(Global.categories[pos]);
        quiltView = (QuiltView) findViewById(R.id.quilt_view);
        quiltView.setChildPadding(5);

        addImagesQuilts(getImages());

    }
    private ArrayList<String> getImages(){
        imagesList.clear();

        if (pos == 0){
            imagesList.addAll(Global.getDesign1Images());
        }else  if(pos == 1){
            imagesList.addAll(Global.getDesign2Images());
        }else  if(pos == 2){
            imagesList.addAll(Global.getDesign3Images());
        }else  if(pos == 3){
            imagesList.addAll(Global.getDesign4Images());
        }else  if(pos == 4){
            imagesList.addAll(Global.getDesign5Images());
        }

        return imagesList;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }



    public void navigateToAnotherActivity(int selPos,int actualPos){

        Intent intent = new Intent(getApplicationContext(),BigImageActivity.class);
        intent.putExtra("position",actualPos);
        intent.putExtra("sel_position",selPos);
        intent.putExtra("title",getTitle());
        startActivity(intent);
    }

    public void addImagesQuilts(final ArrayList<String> imagesList) {
        int num = imagesList.size();
        ArrayList<ImageView> images = new ArrayList<ImageView>();
        for (int i = 0; i < num; i++) {
            final int index = i;
            ImageView image = new ImageView(this.getApplicationContext());
            image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    selPos = index;
                    actualPos = pos;

                    navigateToAnotherActivity(selPos,actualPos);




                }
            });
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getApplicationContext())
                    .load(getImage(imagesList.get(i)))
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.error_images)
                    .into(image);

            images.add(image);
        }
        quiltView.addPatchImages(images);
    }

    public int getImage(String imageName) {

        int drawableResourceId = getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
