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




public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    List<Agenda> agendaList;
    Context context;

    public AgendaAdapter(Context context, List<Agenda> agendaList) {
        this.context = context;
        this.agendaList = agendaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cover_berita_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.agendaJudul.setText(agendaList.get(position).getJudul());
        holder.agendaDeskripsi.setText(agendaList.get(position).getDeskripsi());
        holder.agendaTempat.setText(agendaList.get(position).getTempat());
        holder.agendaTanggal.setText(agendaList.get(position).getTanggal());
        holder.agendaWaktu.setText(agendaList.get(position).getWaktu());
        holder.agendaKategori.setText(agendaList.get(position).getTag());
        Glide.with(holder.itemView.getContext())
                .load(Config.IMAGES_URL + agendaList.get(position).getGambar())
                .apply(new RequestOptions())
                .into(holder.agendaGambar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), DetailAgendaActivity.class);
                mIntent.putExtra("judul", agendaList.get(position).getJudul());
                mIntent.putExtra("deskripsi", agendaList.get(position).getDeskripsi());
                mIntent.putExtra("tanggal", agendaList.get(position).getTanggal());
                mIntent.putExtra("waktu", agendaList.get(position).getWaktu());
                mIntent.putExtra("tempat", agendaList.get(position).getTempat());
                mIntent.putExtra("gambar", agendaList.get(position).getGambar());
                view.getContext().startActivity(mIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return agendaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView agendaJudul, agendaDeskripsi, agendaTempat, agendaTanggal, agendaWaktu, agendaKategori;
        ImageView agendaGambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            agendaJudul=itemView.findViewById(R.id.tv_judul);
            agendaDeskripsi=itemView.findViewById(R.id.tv_deskripsi);
            agendaTempat=itemView.findViewById(R.id.tv_tempat);
            agendaTanggal=itemView.findViewById(R.id.tv_tanggal);
            agendaWaktu=itemView.findViewById(R.id.tv_waktu);
            agendaKategori=itemView.findViewById(R.id.tv_Kategori);
            agendaGambar=itemView.findViewById(R.id.iv_cover);


        }
    }
}
