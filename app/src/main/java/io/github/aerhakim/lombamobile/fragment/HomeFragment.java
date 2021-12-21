package io.github.aerhakim.lombamobile.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.activity.AgendaActivity;
import io.github.aerhakim.lombamobile.activity.NotifikasiActivity;
import io.github.aerhakim.lombamobile.activity.UangJimpitanActivity;
import io.github.aerhakim.lombamobile.activity.UangSampahActivity;


public class HomeFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageView notif;
    FirebaseUser user;
    TextView nama, showmore;
    GridLayout uangsampah, uangjimpitan;
    String userId;

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
        notif = view.findViewById(R.id.notif);
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

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
        return view;
    }
}