package io.github.aerhakim.lombamobile.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.activity.NotifikasiActivity;
import io.github.aerhakim.lombamobile.adapter.HerosAdapter;
import io.github.aerhakim.lombamobile.adapter.NotifikasiAdapter;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.api.bayar.ApiClient;
import io.github.aerhakim.lombamobile.api.bayar.ApiInterface;
import io.github.aerhakim.lombamobile.model.GetHeros;
import io.github.aerhakim.lombamobile.model.GetNotifikasi;
import io.github.aerhakim.lombamobile.model.Heros;
import io.github.aerhakim.lombamobile.model.Notifikasi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransaksiFragment extends Fragment {

    List<Heros> herosList;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_transaksi, container, false);
        recyclerView = view.findViewById(R.id.rv_heros);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        getData();
        return view;
    }

    public void getData(){

        Call<GetHeros> call2= Config.getInstance().getApi().getHeros();
        call2.enqueue(new Callback<GetHeros>() {
            @Override
            public void onResponse(Call<GetHeros> call, Response<GetHeros> response) {

                if(response.isSuccessful()){
                    herosList = response.body().getHerosList();
                    recyclerView.setAdapter(new HerosAdapter(getActivity(), herosList));
//                    shimmerFrameLayout.startShimmer();
//                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else{
//                    shimmerFrameLayout.startShimmer();
                    Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetHeros> call, Throwable t) {
            }
        });

    }
}