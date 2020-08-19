package com.trendyboys.kidsdresses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trendyboys.kidsdresses.model.FilePathModelClass;
import com.trendyboys.kidsdresses.utill.Global;
import com.trendyboys.kidsdresses.utill.TouchImageView;
import com.trendyboys.kidsdresses.utill.Utills;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity {
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    Intent intent;
    ArrayList<FilePathModelClass> filePathModelClasses;
    ArrayList<String> imagesArrayList;
    int selectedPosition,imgsPos;
    AdView bannerAd;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        filePathModelClasses = new ArrayList<>();
        setTitle(intent.getStringExtra("title"));
        selectedPosition = intent.getIntExtra("sel_position",0);
        imagesArrayList = new ArrayList<>();
        imagesArrayList.clear();
        imgsPos = intent.getIntExtra("position",0);

        bannerAd = (AdView) findViewById(R.id.banner_adv);
        adRequest = new AdRequest.Builder().build();

        if (imgsPos==0){
            imagesArrayList.addAll(Global.getDesign1Images());
        }else if(imgsPos == 1){
            imagesArrayList.addAll(Global.getDesign2Images());
        }else if(imgsPos == 2){
            imagesArrayList.addAll(Global.getDesign3Images());
        }else if(imgsPos == 3){
            imagesArrayList.addAll(Global.getDesign4Images());
        }else if(imgsPos == 4){
            imagesArrayList.addAll(Global.getDesign5Images());
        }
        for (int i= 0;i<imagesArrayList.size();i++){
            FilePathModelClass filePathModelClass = new FilePathModelClass();
            filePathModelClass.setFilePath(imagesArrayList.get(i));
            filePathModelClasses.add(filePathModelClass);
        }
        mCustomPagerAdapter = new CustomPagerAdapter(this,filePathModelClasses);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(selectedPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utills.isOnline(BigImageActivity.this)){
            bannerAd.setVisibility(View.VISIBLE);
            bannerAd.loadAd(adRequest);
        }else {
            bannerAd.setVisibility(View.GONE);
        }
    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<FilePathModelClass> mFilePathModelClasses;

        public CustomPagerAdapter(Context context,ArrayList<FilePathModelClass> filePathModelClasses) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mFilePathModelClasses = filePathModelClasses;

        }

        @Override
        public int getCount() {
            return mFilePathModelClasses.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((FrameLayout) object);
        }
        ProgressBar progressBar;
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.view_pager_item_layout, container, false);

            TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.imageView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress_bar) ;

            mFilePathModelClasses.get(position).setpBar(progressBar);

//            mFilePathModelClasses.get(position).getpBar().setVisibility(View.VISIBLE);
            Log.e("ImagePAth","================"+mFilePathModelClasses.get(position).getFilePath());

            int path = getImage(mFilePathModelClasses.get(position).getFilePath());

            Glide.with(getApplicationContext())
                    .load(path)
                /*    .listener(new RequestListener<String, GlideDrawable>() {

                        @Override
                        public boolean onException(Exception e, String model, com.bumptech.glide.request.target.Target<GlideDrawable> target, boolean isFirstResource) {
                            mFilePathModelClasses.get(position).getpBar().setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, com.bumptech.glide.request.target.Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mFilePathModelClasses.get(position).getpBar().setVisibility(View.GONE);
                            return false;
                        }
                    })*/
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.error_images)
                    .into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((FrameLayout) object);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            case R.id.action_share:
                int pos =  mViewPager.getCurrentItem();
                String imageUrl = filePathModelClasses.get(pos).getFilePath();
                shareImage(imageUrl,BigImageActivity.this);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    static public void shareImage(final String urlStr, final Context context) {
        new AsyncTask<Void,Void,Bitmap>(){

//            private TransparentProgressDialog mProgressbar;

            @Override
            protected void onPreExecute() {
//                mProgressbar = new TransparentProgressDialog(context,
//                        R.drawable.spinner_loading_imag);
//                mProgressbar.show();

            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlStr);
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    return image;
                } catch(IOException e) {
                    System.out.println(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
//                if (mProgressbar!=null){
//                    mProgressbar.dismiss();
//                }

                if (bitmap!=null){
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK );
                    i.setType("image*//*");
                    i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                    context.startActivity(Intent.createChooser(i, "Share Image"));
                }else {
                    Toast.makeText(context,"Failed to share! Check your data connection",Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();


    }
    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public int getImage(String imageName) {

        int drawableResourceId = getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }



}
