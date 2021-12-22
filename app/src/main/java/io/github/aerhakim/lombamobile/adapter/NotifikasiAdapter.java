package io.github.aerhakim.lombamobile.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;
import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.activity.DetailAgendaActivity;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.model.Agenda;
import io.github.aerhakim.lombamobile.model.Notifikasi;


public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.ViewHolder> {

    List<Notifikasi> notifikasiList;
    Context context;

    public NotifikasiAdapter(Context context, List<Notifikasi> notifikasiList) {
        this.context = context;
        this.notifikasiList = notifikasiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_notifikasi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.notifikasiJudul.setText(notifikasiList.get(position).getJudul());
        holder.notifikasiDetail.setText(notifikasiList.get(position).getDetail());
        holder.notifikasiTempat.setText(notifikasiList.get(position).getTempat());
        holder.notifikasiTanggal.setText(notifikasiList.get(position).getTanggal());
        holder.notifikasiWaktu.setText(notifikasiList.get(position).getWaktu());
        holder.notifikasiId.setText(notifikasiList.get(position).getId());
        String anjayani = notifikasiList.get(position).getDetail() + "\n\nTempat : " + notifikasiList.get(position).getTempat()
                + "\n Tanggal : " + notifikasiList.get(position).getTanggal()
                + "\n Waktu : " + notifikasiList.get(position).getWaktu();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) context)

                        .setTitle(notifikasiList.get(position).getJudul())
                        .setMessage(anjayani, TextAlignment.START)
                        .setCancelable(false)
                        .setPositiveButton("Tutup", new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
//                        .setNegativeButton("Cancel", new BottomSheetMaterialDialog.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                dialogInterface.dismiss();
//                            }
//                        })
                        .build();

                // Show Dialog
                mBottomSheetDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return notifikasiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView notifikasiJudul, notifikasiDetail, notifikasiTempat, notifikasiTanggal, notifikasiWaktu, notifikasiId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notifikasiJudul=itemView.findViewById(R.id.tv_judul);
            notifikasiDetail=itemView.findViewById(R.id.tv_detaiil);
            notifikasiTempat=itemView.findViewById(R.id.tv_tempat);
            notifikasiTanggal=itemView.findViewById(R.id.tv_tanggal);
            notifikasiWaktu=itemView.findViewById(R.id.tv_waktu);
            notifikasiId=itemView.findViewById(R.id.tv_id);



        }
    }
}
