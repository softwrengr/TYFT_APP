package com.squaresdevelopers.tyft.dataModels.sellerSignUpModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellerSignUpResponseModel {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SellerSignUpDataModel data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SellerSignUpDataModel getData() {
        return data;
    }

    public void setData(SellerSignUpDataModel data) {
        this.data = data;
    }
}
