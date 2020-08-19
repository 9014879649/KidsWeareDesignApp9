package com.trendyboys.kidsdresses.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Nageswara Rao.CH on 11/24/2016.
 */
public class FilePathModelClass implements Parcelable {
    String mFilePath;
    String mFileName;
    ProgressBar pBar;
    ImageView downloadImage;
    ImageView image;
    boolean isDownloaded;
    public FilePathModelClass(){

    }

    protected FilePathModelClass(Parcel in) {
        mFilePath = in.readString();
        mFileName = in.readString();
        isDownloaded = in.readByte() != 0;
    }

    public static final Creator<FilePathModelClass> CREATOR = new Creator<FilePathModelClass>() {
        @Override
        public FilePathModelClass createFromParcel(Parcel in) {
            return new FilePathModelClass(in);
        }

        @Override
        public FilePathModelClass[] newArray(int size) {
            return new FilePathModelClass[size];
        }
    };

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
    }

    public ImageView getDownloadImage() {
        return downloadImage;
    }

    public void setDownloadImage(ImageView downloadImage) {
        this.downloadImage = downloadImage;
    }

    public ProgressBar getpBar() {
        return pBar;
    }

    public void setpBar(ProgressBar pBar) {
        this.pBar = pBar;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filepath) {
        this.mFilePath = filepath;
    }


    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String filename) {
        this.mFileName = filename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFilePath);
        parcel.writeString(mFileName);
        parcel.writeByte((byte) (isDownloaded ? 1 : 0));
    }
}
