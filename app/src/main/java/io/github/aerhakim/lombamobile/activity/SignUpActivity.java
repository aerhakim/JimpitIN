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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.activity.MainActivity;


public class SignUpActivity extends AppCompatActivity {
    EditText mPhone, edFullName, edNIK, edPassword;
    TextView mLogin, mRegister;
    CountryCodePicker ccp;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        refreshLayout = findViewById(R.id.swipe_refresh_layout_signup);
        mPhone      = findViewById(R.id.edNoHP);
        edFullName      = findViewById(R.id.edFullName);
        edNIK      = findViewById(R.id.edNIK);
        edPassword      = findViewById(R.id.edPassword);
        mLogin      = findViewById(R.id.tvLogin);
        mRegister      = findViewById(R.id.tvSignUp);
        ccp = findViewById(R.id.ccp);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = edNIK.getText().toString().trim();
                String katasandi = edPassword.getText().toString().trim();
                String nama = edFullName.getText().toString();
                String phone = mPhone.getText().toString().trim();
                String NoHP = ccp.getSelectedCountryCodeWithPlus() + phone;

                if(TextUtils.isEmpty(nama)){
                    edFullName.setError("Nama wajib diisi!");
                    return;
                }
                if(TextUtils.isEmpty(nik)){
                    edNIK.setError("NIK wajib diisi!");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    mPhone.setError("Nomer HP wajib diisi!");
                    return;
                }
                if(TextUtils.isEmpty(katasandi)){
                    edPassword.setError("Kata sandi wajib diisi!");
                    return;
                }

                if(katasandi.length() < 6){
                    edPassword.setError("Kata Sandi Wajib >6 Karakter!");
                    return;
                }
                Intent mIntent = new Intent(SignUpActivity.this, OtpActivity.class);
                mIntent.putExtra("nohp",NoHP);
                startActivity(mIntent);
                finish();
                finishAffinity();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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
            MaterialDialog mDialog = new MaterialDialog.Builder(SignUpActivity.this)
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
        Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mIntent);
        finish();
        finishAffinity();
    }
}
