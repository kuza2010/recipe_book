package com.example.myapplication.framework.retrofit.model.category;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Category {
    @SerializedName("name")
    private String name;
    @SerializedName("image_id")
    private Integer imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", imageId=" + imageId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(getName(), category.getName()) &&
                Objects.equals(getImageId(), category.getImageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getImageId());
    }
}