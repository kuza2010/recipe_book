package com.example.myapplication.framework.retrofit.model.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {
    @SerializedName("id_recipe")
    private Integer idRecipe;

    @SerializedName("id_image")
    private Integer imageId;

    @SerializedName("name")
    private String name;

    @SerializedName("rating")
    private float rating;

    @SerializedName("total_time")
    private String coockingTime;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCoockingTime() {
        return coockingTime;
    }

    public void setCoockingTime(String coockingTime) {
        this.coockingTime = coockingTime;
    }

    public Integer getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Integer idRecipe) {
        this.idRecipe = idRecipe;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    protected Recipe(Parcel in) {
        if (in.readByte() == 0) {
            idRecipe = null;
        } else {
            idRecipe = in.readInt();
        }
        if (in.readByte() == 0) {
            imageId = null;
        } else {
            imageId = in.readInt();
        }
        name = in.readString();
        rating = in.readFloat();
        coockingTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idRecipe == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idRecipe);
        }
        if (imageId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageId);
        }
        dest.writeString(name);
        dest.writeFloat(rating);
        dest.writeString(coockingTime);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
