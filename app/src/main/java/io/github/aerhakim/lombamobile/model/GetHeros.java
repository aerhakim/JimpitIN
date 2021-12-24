package io.github.aerhakim.lombamobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetHeros {

    @SerializedName("result")
    List<Heros> herosList;
    String error;

    public GetHeros(List<Heros> herosList, String error) {
        this.herosList = herosList;
        this.error = error;
    }

    public List<Heros> getHerosList() {
        return herosList;
    }

    public String getError() {
        return error;
    }


}
