package io.github.aerhakim.lombamobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import io.github.aerhakim.lombamobile.R;

public class OtpActivity extends AppCompatActivity {
    TextView tvNOHP, tvTimer, tvVerif;
    EditText edOTP;
    LinearLayout lltimer;
    FirebaseAuth fAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        tvNOHP = findViewById(R.id.nohp);
        tvTimer = findViewById(R.id.tvTimer);
        tvVerif = findViewById(R.id.tvVerif);
        lltimer = findViewById(R.id.lltimer);
        edOTP = findViewById(R.id.edOTP);
        fAuth = FirebaseAuth.getInstance();
        tvNOHP.setText(getIntent().getStringExtra("nohp"));
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("00:" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                lltimer.setVisibility(View.VISIBLE);
            }

        }.start();

        tvVerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "arhakim.info@gmail.com";
                String password = "12345678";
                String pin = edOTP.getText().toString().trim();;


                if(TextUtils.isEmpty(pin)){
                    edOTP.setError("Kode OTP Wajib OTP!");
                    return;
                }
                if(pin.length() < 6){
                    edOTP.setError("Kode OTP Harus 6 Angka!");
                    return;
                }
                if(pin.length() > 6){
                    edOTP.setError("Kode OTP Harus 6 Angka!");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                            finishAffinity();
                        }else {
                        }
                    }
                });
            }
        });
        sendCode();
    }

    public void sendCode() {
        String phoneNumber = getIntent().getStringExtra("nohp");
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {
                        //isi jika berhasil
                    }
                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        //isi jika gagal
                    }
                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        //isi jika code berhasil dikirim
                    }
                };

        if (phoneNumber.length() > 7) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    verificationCallbacks);

        }else {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(mIntent);
        finish();
        finishAffinity();
    }

}