package io.github.aerhakim.lombamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.adapter.NotifikasiAdapter;
import io.github.aerhakim.lombamobile.adapter.SampahAdapter;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.model.GetNotifikasi;
import io.github.aerhakim.lombamobile.model.GetSampah;
import io.github.aerhakim.lombamobile.model.Notifikasi;
import io.github.aerhakim.lombamobile.model.Sampah;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UangSampahActivity extends AppCompatActivity {
    List<Sampah> sampahList;
    RecyclerView recyclerView;
    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View bottom_sheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uang_sampah);
        recyclerView = findViewById(R.id.rv_sampah);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getData();
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);

        Button button = findViewById(R.id.btnBayar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();

            }
        });
    }


    public void getData(){

        Call<GetSampah> call2= Config.getInstance().getApi().sampah();
        call2.enqueue(new Callback<GetSampah>() {
            @Override
            public void onResponse(Call<GetSampah> call, Response<GetSampah> response) {

                if(response.isSuccessful()){



                    sampahList = response.body().getSampahList();
                    recyclerView.setAdapter(new SampahAdapter(UangSampahActivity.this, sampahList));
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else{
                    Toast.makeText(UangSampahActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetSampah> call, Throwable t) {
            }
        });

    }

    private void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.sheet_bayar, null);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });

        (view.findViewById(R.id.bt_subscribe)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Makasih ya sudah subscribe", Toast.LENGTH_SHORT).show();
            }
        });

        sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog = null;
            }
        });
    }
}