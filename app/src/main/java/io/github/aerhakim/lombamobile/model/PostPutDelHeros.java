package io.github.aerhakim.lombamobile.model;

import com.google.gson.annotations.SerializedName;

public class PostPutDelHeros {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    io.github.aerhakim.lombamobile.model.Heros mHeros;
    @SerializedName("message")
    String message;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public io.github.aerhakim.lombamobile.model.Heros getHeros() {
        return mHeros;
    }
    public void setHeros(io.github.aerhakim.lombamobile.model.Heros Heros) {
        mHeros = Heros;
    }
}
