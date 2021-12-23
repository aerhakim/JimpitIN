package io.github.aerhakim.lombamobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetJimpitan {
    @SerializedName("result")
    List<Jimpitan> jimpitanList;
    String error;

    public List<Jimpitan> getJimpitanList() {
        return jimpitanList;
    }

    public void setJimpitanListm(List<Jimpitan> jimpitanList) {
        this.jimpitanList = jimpitanList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
