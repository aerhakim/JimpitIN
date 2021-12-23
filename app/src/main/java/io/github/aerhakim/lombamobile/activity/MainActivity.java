package io.github.aerhakim.lombamobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import io.github.aerhakim.lombamobile.R;
import io.github.aerhakim.lombamobile.api.Config;
import io.github.aerhakim.lombamobile.fragment.HomeFragment;
import io.github.aerhakim.lombamobile.fragment.ProfileFragment;
import io.github.aerhakim.lombamobile.fragment.TransaksiFragment;
import io.github.aerhakim.lombamobile.model.GetToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID = "101";
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cekKoneksi();
        getToken();
        createNotificationChannel();
        chipNavigationBar = findViewById(R.id.navigation);
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.transaksi:
                        fragment = new TransaksiFragment();
                        break;

                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

    public void cekKoneksi () {
        if(isNetworkAvailable()) {
            //Keknya ga ush di isi, dibiarin aja
        } else {
            MaterialDialog mDialog = new MaterialDialog.Builder(MainActivity.this)
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
            Toast.makeText(this,"Koneksi Internet Tidak Ada", Toast.LENGTH_SHORT).show();

        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                //If task is failed then
                if (!task.isSuccessful()) {
                    Log.d(TAG, "onComplete: Failed to get the Token");
                }


                //Token
                String token = task.getResult();
                Log.d(TAG, "onComplete: " + token);

                String tokenfcm = token;
                Call<GetToken> call= Config
                        .getInstance()
                        .getApi()
                        .register(token);
                call.enqueue(new Callback<GetToken>() {
                    @Override
                    public void onResponse(Call<GetToken> call, Response<GetToken> response) {
                        //ga tau mau diisi apa
                    }

                    @Override
                    public void onFailure(Call<GetToken> call, Throwable t) {

                        //onFailure Retrofit di MainActivity Toast Nya tidak diaktifkan,
                        // karena di Mainactivity sudah ada toast yg sama di E-Wallet Fragment
//                                     Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "firebaseNotifChannel";
            String description = "Receve Firebase notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}