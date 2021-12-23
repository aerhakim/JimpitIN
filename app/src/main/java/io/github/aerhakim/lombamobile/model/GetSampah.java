package io.github.aerhakim.lombamobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSampah {
    @SerializedName("result")
    List<Sampah> sampahList;
    String error;

    public List<Sampah> getSampahList() {
        return sampahList;
    }

    public void setSampahList(List<Sampah> sampahList) {
        this.sampahList = sampahList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
