package com.squaresdevelopers.tyft.dataModels.locationDataModel;

public class SellerLocationModel {
    public String latitude;
    public String longitude;
    public String id;
    public String checkLocation;

    public String name,image1,image2,email;

    public SellerLocationModel(String name,
                               String email,
                               String image1,
                               String image2,
                               String latitude,
                               String longitude,
                               String id,
                               String checkLocation) {
        this.name = name;
        this.email = email;
        this.image1 = image1;
        this.image2 = image2;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.checkLocation = checkLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckLocation() {
        return checkLocation;
    }

    public void setCheckLocation(String checkLocation) {
        this.checkLocation = checkLocation;
    }
}
