package io.github.aerhakim.lombamobile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAgenda {

    @SerializedName("result")
    List<Agenda> agendaList;
    String error;

    public GetAgenda(List<Agenda> agendaList, String error) {
        this.agendaList = agendaList;
        this.error = error;
    }

    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public String getError() {
        return error;
    }


}
