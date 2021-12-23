package io.github.aerhakim.lombamobile.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.adapter.AgendaAdapter;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.model.Agenda;
import io.github.aerhakim.lombamobile.model.GetAgenda;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    List<Agenda> agendaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        recyclerView = findViewById(R.id.rv_agenda);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AgendaActivity.this, LinearLayoutManager.VERTICAL, false));
        getData();

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
    }
    public void getData(){

        Call<GetAgenda> call2= Config.getInstance().getApi().agenda();
        call2.enqueue(new Callback<GetAgenda>() {
            @Override
            public void onResponse(Call<GetAgenda> call, Response<GetAgenda> response) {

                if(response.isSuccessful()){



                    agendaList = response.body().getAgendaList();
                    recyclerView.setAdapter(new AgendaAdapter(AgendaActivity.this, agendaList));
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else{
                    shimmerFrameLayout.startShimmer();
                    Toast.makeText(AgendaActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetAgenda> call, Throwable t) {
            }
        });

    }
    @Override
    public void onResume() {
        shimmerFrameLayout.stopShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mIntent);
        finish();
        finishAffinity();
    }

}