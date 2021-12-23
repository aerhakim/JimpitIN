package io.github.aerhakim.lombamobile.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;
import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.activity.AgendaActivity;
import io.github.aerhakim.lombamobile.activity.LoginActivity;
import io.github.aerhakim.lombamobile.activity.MainActivity;
import io.github.aerhakim.lombamobile.activity.UbahPasswordActivity;
import io.github.aerhakim.lombamobile.activity.UserProfileActivity;

public class ProfileFragment extends Fragment {
    public static Context context;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView Nama, NOhp;
    GridLayout editprofile, edabout;
    CardView editpassword, logout;
    FirebaseUser user;
    ImageView profileImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_profile, container, false);
        context = getActivity().getApplicationContext();
        profileImage = view.findViewById(R.id.profileImageView);
        fAuth = FirebaseAuth.getInstance();
        editpassword = view.findViewById(R.id.cv2);
        Nama = view.findViewById(R.id.tvnama);
        edabout = view.findViewById(R.id.about);
        NOhp = view.findViewById(R.id.tvnotelp);
        editprofile = view.findViewById(R.id.editprofile);
        logout = view.findViewById(R.id.logout);
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        edabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog passwordResetDialog = new MaterialDialog.Builder((Activity) v.getContext())
                        .setTitle("About Developer")
//                        .setView(resetPassword)
                        .setMessage("Silahkan mengunjungi repository project ini untuk melihat lebih detail mengenai project, credit, para developer dan informasi penting lainnya.", TextAlignment.START)
                        .setCancelable(false)
                        .setPositiveButton("Visit",  new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String url = ("https://github.com/aerhakim/LombaMobile");
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Batal",  new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
                // Show Dialog
                passwordResetDialog.show();

            }
        });
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ava)
                        .error(R.drawable.ava);
                Glide.with(getActivity().getApplicationContext()).load(uri).apply(options).into(profileImage);

            }
        });

        DocumentReference documentReference = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    Nama.setText(documentSnapshot.getString("fName"));
                    NOhp.setText(documentSnapshot.getString("phone"));

                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), UserProfileActivity.class);
                view.getContext().startActivity(mIntent);
            }
        });
        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetPassword = new EditText(v.getContext());
                final MaterialDialog passwordResetDialog = new MaterialDialog.Builder((Activity) v.getContext())
                                    .setTitle("Ubah Kata Sandi")
//                        .setView(resetPassword)
                        .setMessage("Ingin Mengubah Kata Sandi? Klik Button Ubah Untuk Menuju Halaman Ubah Kata Sandi!", TextAlignment.START)
                        .setCancelable(false)
                        .setPositiveButton("Ubah",  new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Intent mIntent = new Intent(getActivity().getApplicationContext(), UbahPasswordActivity.class);
                                startActivity(mIntent);
                                getActivity().finish();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Batal",  new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Toast.makeText(getActivity(), "Password Batal Diubah!", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
                // Show Dialog
                passwordResetDialog.show();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                getActivity().finish();

            }
        });
        return  view;

    }
}