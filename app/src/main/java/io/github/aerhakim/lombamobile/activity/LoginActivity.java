package io.github.aerhakim.lombamobile.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import io.github.aerhakim.lombamobile.R;


public class LoginActivity extends AppCompatActivity {
    EditText edNoHP,edKataSandi;
    TextView tvLogin,tvLupaSandi, tvSignup;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edNoHP = findViewById(R.id.edNoHP);
        edKataSandi = findViewById(R.id.edPassword);
        fAuth = FirebaseAuth.getInstance();
        tvLogin = findViewById(R.id.tvLogin);
        tvSignup = findViewById(R.id.tvSignup);
        tvLupaSandi = findViewById(R.id.tvLupaSandi);

        //Cek apakah user sudah login atau belum, kalau sudah langsung redirect ke mainactivity
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = "arhakim.info@gmail.com";
                String nohp = edNoHP.getText().toString().trim();;
                String password = edKataSandi.getText().toString().trim();;


                if(TextUtils.isEmpty(nohp)){
                    edNoHP.setError("Nomor HP wajib diisi!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    edKataSandi.setError("Kata sandi wajib diisi!");
                    return;
                }




                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                            finishAffinity();
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                finish();
                finishAffinity();
            }
        });

        tvLupaSandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgetPasswordActivity.class));
                finish();
                finishAffinity();
            }
        });
        cekKoneksi();
    }

    public void cekKoneksi () {
        if(isNetworkAvailable()) {
            //Keknya ga ush di isi, dibiarin aja
        } else {
            MaterialDialog mDialog = new MaterialDialog.Builder(LoginActivity.this)
                    .setTitle("Tidak Ada Koneksi")
                    .setMessage("Ada Gangguan Dengan Internet Anda, Silahkan Menggunakan Jaringan WIFI atau Coba Lagi.")
                    .setCancelable(false)
                    .setPositiveButton("Connect",  new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setNegativeButton("Retry",  new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .build();
            // Show Dialog
            mDialog.show();


        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    @Override
    public void onBackPressed() {
        LoginActivity.super.onBackPressed();
    }

}
