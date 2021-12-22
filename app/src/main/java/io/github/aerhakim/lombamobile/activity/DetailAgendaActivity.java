package io.github.aerhakim.lombamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.api.Config;

public class DetailAgendaActivity extends AppCompatActivity {
    TextView judul,deskripsi, tanggal, waktu, tempat;
    ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agenda);
        judul = findViewById(R.id.tvJudul);
        gambar = findViewById(R.id.ivFoto);
        deskripsi = findViewById(R.id.tvDeskripsiAgenda);
        tanggal = findViewById(R.id.tvtTanggal);
        waktu = findViewById(R.id.tvWaktu);
        tempat = findViewById(R.id.tvTempat);

        Intent mIntent = getIntent();
        judul.setText(mIntent.getStringExtra("judul"));
        deskripsi.setText(mIntent.getStringExtra("deskripsi"));
        tanggal.setText(mIntent.getStringExtra("tanggal"));
        waktu.setText(mIntent.getStringExtra("waktu"));
        tempat.setText(mIntent.getStringExtra("tempat"));
        // Masukan Gambar Ke Image View Gunakan Glide
        Glide.with(DetailAgendaActivity.this)
                .load(Config.IMAGES_URL + mIntent.getStringExtra("gambar"))
                .apply(new RequestOptions().override(700, 400))
                .into(gambar);
    }
}