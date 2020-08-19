package com.trendyboys.kidsdresses.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ItemList implements Parcelable {



	private int ItemID;
	private String ItemName;
	private ArrayList<ItemImage> Images;
	int imgCount;


	protected ItemList(Parcel in) {
		ItemID = in.readInt();
		ItemName = in.readString();
		Images = in.createTypedArrayList(ItemImage.CREATOR);
		imgCount = in.readInt();
	}

	public static final Creator<ItemList> CREATOR = new Creator<ItemList>() {
		@Override
		public ItemList createFromParcel(Parcel in) {
			return new ItemList(in);
		}

		@Override
		public ItemList[] newArray(int size) {
			return new ItemList[size];
		}
	};

	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}



	public ItemList(int itemID, String itemName,
                    ArrayList<ItemImage> itemImages, int imgCount) {
		super();
		ItemID = itemID;
		ItemName = itemName;
		Images = itemImages;
		imgCount = imgCount;

	}


	public int getProductID() {
		return ItemID;
	}

	public void setProductID(int productID) {
		ItemID = productID;
	}


	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}


	public ArrayList<ItemImage> getImages() {
		return Images;
	}

	public void setImages(ArrayList<ItemImage> images) {
		Images = images;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {

		parcel.writeInt(ItemID);
		parcel.writeString(ItemName);
		parcel.writeTypedList(Images);
		parcel.writeInt(imgCount);
	}



	@Override
	public String toString() {
		return "ItemList{" +
				"IID=" + ItemID +
				", ItemName='" + ItemName + '\'' +
				", Images=" + Images +
				"ImgCount=" + imgCount +
				'}';
	}
}