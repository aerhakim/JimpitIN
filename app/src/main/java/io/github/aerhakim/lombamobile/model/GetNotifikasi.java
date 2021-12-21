package io.github.aerhakim.lombamobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNotifikasi {

    @SerializedName("notifikasi")
    List<Notifikasi> notifikasiList;
    String error;

    public GetNotifikasi(List<Notifikasi> notifikasiList, String error) {
        this.notifikasiList = notifikasiList;
        this.error = error;
    }

    public List<Notifikasi> getNotifikasiList() {
        return notifikasiList;
    }

    public String getError() {
        return error;
    }


}