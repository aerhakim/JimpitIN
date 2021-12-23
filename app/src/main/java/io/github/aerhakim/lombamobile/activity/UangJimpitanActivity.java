package io.github.aerhakim.lombamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.adapter.JimpitanAdapter;
import io.github.aerhakim.lombamobile.adapter.NotifikasiAdapter;
import io.github.aerhakim.lombamobile.adapter.SampahAdapter;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.model.GetJimpitan;
import io.github.aerhakim.lombamobile.model.GetNotifikasi;
import io.github.aerhakim.lombamobile.model.GetSampah;
import io.github.aerhakim.lombamobile.model.Jimpitan;
import io.github.aerhakim.lombamobile.model.Notifikasi;
import io.github.aerhakim.lombamobile.model.Sampah;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UangJimpitanActivity extends AppCompatActivity {
    List<Jimpitan> jimpitanList;
    RecyclerView recyclerView;
    BottomSheetBehavior sheetBehavior;
    View bottom_sheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uang_jimpitan);
        recyclerView = findViewById(R.id.rv_jimpitan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getData();
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        ImageView back = findViewById(R.id.ivBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mIntent);
                finish();
                finishAffinity();
            }
        });
        Button button = findViewById(R.id.btnBayar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        UangJimpitanActivity.this,R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.sheet_bayar,
                                (LinearLayout)findViewById(R.id.bottomsheet)
                        );
                bottomSheetView.findViewById(R.id.bayar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mIntent = new Intent(UangJimpitanActivity.this, InsertActivity.class);
                        startActivity(mIntent);
                        finish();
                        finishAffinity();
                    }
                });
                bottomSheetView.findViewById(R.id.batal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }


    public void getData(){

        Call<GetJimpitan> call2= Config.getInstance().getApi().jimpitan();
        call2.enqueue(new Callback<GetJimpitan>() {
            @Override
            public void onResponse(Call<GetJimpitan> call, Response<GetJimpitan> response) {

                if(response.isSuccessful()){



                    jimpitanList = response.body().getJimpitanList();
                    recyclerView.setAdapter(new JimpitanAdapter(UangJimpitanActivity.this, jimpitanList));
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else{
                    Toast.makeText(UangJimpitanActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetJimpitan> call, Throwable t) {
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mIntent);
        finish();
        finishAffinity();
    }
}