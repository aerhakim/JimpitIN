package io.github.aerhakim.lombamobile.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.activity.AgendaActivity;
import io.github.aerhakim.lombamobile.activity.NotifikasiActivity;
import io.github.aerhakim.lombamobile.activity.UangJimpitanActivity;
import io.github.aerhakim.lombamobile.activity.UangSampahActivity;
import io.github.aerhakim.lombamobile.adapter.AgendaAdapter;
import io.github.aerhakim.lombamobile.adapter.SliderAdapter;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.model.Agenda;
import io.github.aerhakim.lombamobile.model.GetAgenda;
import io.github.aerhakim.lombamobile.model.SliderModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageView notif;
    FirebaseUser user;
    TextView nama, showmore;
    GridLayout uangsampah, uangjimpitan;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    List<Agenda> agendaList;
    String userId;
    // Urls of our images.
    String url1 = "http://192.168.219.120:8080/lombamobile/assets/files/image/g6.jpg";
    String url2 = "http://192.168.219.120:8080/lombamobile/assets/files/image/g2.jpeg";
    String url3 = "http://192.168.219.120:8080/lombamobile/assets/files/image/g3.jpeg";
    String url4 = "http://192.168.219.120:8080/lombamobile/assets/files/image/g5.jpg";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_home, container, false);
        fAuth = FirebaseAuth.getInstance();
        nama = view.findViewById(R.id.nama);
        showmore = view.findViewById(R.id.showmore);
        uangjimpitan = view.findViewById(R.id.uangjimpitan);
        uangsampah = view.findViewById(R.id.uangsampah);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);
        notif = view.findViewById(R.id.notif);
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.rv_agenda);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    nama.setText(documentSnapshot.getString("fName"));

                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), NotifikasiActivity.class);
                view.getContext().startActivity(mIntent);
            }
        });
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), AgendaActivity.class);
                view.getContext().startActivity(mIntent);
            }
        });
        uangjimpitan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), UangJimpitanActivity.class);
                view.getContext().startActivity(mIntent);
            }
        });
        uangsampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), UangSampahActivity.class);
                view.getContext().startActivity(mIntent);
            }
        });

        // Slide Image
        ArrayList<SliderModel> sliderDataArrayList = new ArrayList<>();

        SliderView sliderView = view.findViewById(R.id.slider);

        sliderDataArrayList.add(new SliderModel(url1));
        sliderDataArrayList.add(new SliderModel(url2));
        sliderDataArrayList.add(new SliderModel(url3));
        sliderDataArrayList.add(new SliderModel(url4));

        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();
        // End Slide Image

        getData();
        return view;
    }

    public void getData(){

        Call<GetAgenda> call2= Config.getInstance().getApi().agenda();
        call2.enqueue(new Callback<GetAgenda>() {
            @Override
            public void onResponse(Call<GetAgenda> call, Response<GetAgenda> response) {

                if(response.isSuccessful()){



                    agendaList = response.body().getAgendaList();
                    recyclerView.setAdapter(new AgendaAdapter(getActivity(), agendaList));
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
                else{
                    shimmerFrameLayout.startShimmer();
                    Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
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
        getData();
        super.onResume();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }


}