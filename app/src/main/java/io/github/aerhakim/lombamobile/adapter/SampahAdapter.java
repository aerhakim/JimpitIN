package io.github.aerhakim.lombamobile.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.activity.DetailAgendaActivity;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.model.Agenda;
import io.github.aerhakim.lombamobile.model.Jimpitan;
import io.github.aerhakim.lombamobile.model.Sampah;


public class SampahAdapter extends RecyclerView.Adapter<SampahAdapter.ViewHolder> {

    List<Sampah> sampahList;
    Context context;

    public SampahAdapter(Context context, List<Sampah> sampahList) {
        this.context = context;
        this.sampahList = sampahList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_tagihan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tagihanTanggal.setText(sampahList.get(position).getTanggal());
        holder.tagihanJumlah.setText(sampahList.get(position).getJumlah());


    }


    @Override
    public int getItemCount() {
        return sampahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tagihanTanggal, tagihanJumlah;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagihanTanggal=itemView.findViewById(R.id.tv_judul);
            tagihanJumlah=itemView.findViewById(R.id.textView);



        }
    }
}
