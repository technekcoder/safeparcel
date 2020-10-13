package com.inopetech.safeparcel.userinteface;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.inopetech.safeparcel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inopetech.safeparcel.MainActivity;
import com.inopetech.safeparcel.R;
import com.inopetech.safeparcel.model.registerinfo;

import androidx.annotation.NonNull;

public class resetpassword extends AppCompatActivity {
    private Button btnreset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);
        inputEmail = (EditText) findViewById(R.id.email);
        btnreset = (Button) findViewById(R.id.btnresetpassword);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                finish();
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(resetpassword.this, "Check email for password reset instruction ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(resetpassword.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
