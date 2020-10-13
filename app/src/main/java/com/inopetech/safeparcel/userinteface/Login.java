package com.inopetech.safeparcel.userinteface;

import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.KeyStore;
import java.util.regex.Pattern;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.inopetech.safeparcel.MainActivity;
import com.inopetech.safeparcel.R;
import com.inopetech.safeparcel.util.Config;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "safeparcellog";
    private static final String USERNAME_KEY = "FULLNAME";
    private static final String USER_EMAIL_KEY = "EMAIL";
    private static final String USER_PHONENUMBER = "PHONENUMBER";
    private static final String USER_COUNTY = "COUNTY";
    private static final String USER_PHYSICALADDRESS = "PHYSICALADDRESS";
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final int RC_SIGN_IN = 1001;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefsEditor;
    SharedPreferences.Editor editor;
    Context context;
    ;
    GoogleSignInClient googleSignInClient;
    private EditText userName, userPassword, constatus;
    private Button mlogin, btnLinkSignup;
    private TextView registerscreen;
    private TextView resetscreen;
    private KeyStore keyStore;
    private FirebaseAnalytics firebaseanalytics;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();
        context = this;
        initalizeWidgets();

        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(Login.this, "User is logged in", Toast.LENGTH_LONG).show();

            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        firebaseanalytics = FirebaseAnalytics.getInstance(this);
        final EditText userName = (EditText) findViewById(R.id.txtemail);
        final EditText userpassword1 = (EditText) findViewById(R.id.txtpassword);
        //final EditText constatus=(EditText)findViewById( R.id.connectionstatus);
        final Button mlogin = (Button) findViewById(R.id.btnLogin);
        progressDialog1 = new ProgressDialog(this);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), registerpage.class);
                startActivity(i);
            }
        });

        // Listening to registerinfo new account link
        TextView resetscreen = (TextView) findViewById(R.id.forgot_your_password);
        resetscreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), resetpassword.class);
                startActivity(i);
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                String email1 = userName.getText().toString();
                final String password1 = userpassword1.getText().toString();

                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!EMAIL_ADDRESS_PATTERN.matcher(email1).matches()) {
                    Toast.makeText(Login.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                    FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

                    /*if (!fingerprintManager.isHardwareDetected()) {
                        Toast.makeText(getApplicationContext(), "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show();
                    }
                    if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Please enable the fingerprint permission", Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions(Login.this.getApplicationContext(), new String[]{Manifest.permission.USE_FINGERPRINT}, FingerprintHandler.FINGERPRINT_PERMISSION);

                    }

                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                        Toast.makeText(getApplicationContext(), "Your Device has no registered Fingerprints! Please registerinfo atleast one in your Device settings", Toast.LENGTH_LONG).show();
                    }
                    return;*/
                } else {

                    progressDialog1.setMessage("Loggin in...");
                    progressDialog1.show();

                    //authenticate user
                    final String email = userName.getText().toString();
                    final String password = userpassword1.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(Login.this, "LoginUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            Toast.makeText(Login.this, "password too short", Toast.LENGTH_LONG).show();

                                        } else if (email.isEmpty()) {
                                            Toast.makeText(Login.this, "email required", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(Login.this, " failed to log in", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(Login.this, "WELCOME" + email, Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        updateUI(user);


                                    }
                                }
                            });
                }
            }
        });
    }

    private void initalizeWidgets() {
    }

    private void updateUI(FirebaseUser account) {

        if (account != null) {
            Toast.makeText(this, "U Signed In successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "U Didnt signed in", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...

        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                showToastMessage("Google Sign in Succeeded");
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                showToastMessage("Google Sign in Failed " + e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Log.d(TAG, "signInWithCredential:success: currentUser: " + user.getEmail());

                            showToastMessage("Firebase Authentication Succeeded ");
                            launchMainActivity(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            showToastMessage("Firebase Authentication failed:" + task.getException());
                        }
                    }
                });
    }

    private void showToastMessage(String message) {
        Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
    }

    private void launchMainActivity(FirebaseUser user) {
        if (user != null) {
            MainActivity.startActivity(this, user.getDisplayName());
            finish();
        }
    }

}