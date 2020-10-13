package com.inopetech.safeparcel.userinteface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.app.ProgressDialog;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.inopetech.safeparcel.util.Config;
/*import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.PlayGamesAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
*/

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;


public class registerpage extends AppCompatActivity {
    private static final String USERNAME_KEY = "FULLNAME";
    private static final String USER_EMAIL_KEY = "EMAIL";
    private static final String USER_PHONENUMBER = "PHONENUMBER";
    private static final String USER_COUNTY = "COUNTY";
    private static final String USER_PHYSICALADDRESS = "PHYSICALADDRESS";
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String TAG = "safeparcellog";
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
    String radiodefaultusage = "";
    SharedPreferences.Editor editor;
    Context context;
    ListView listViewcountry, listViewcounty;
    RadioButton radiosender;
    RadioButton radioreceiver;
    EditText txtfirstname, txtsurname, txtphonenumber, txtphonenumber2, txtemailaddress, txtphysicaladdress,
            txtpassword, txtconfirmpassword;
    TextView countrypicked, countypicked;
    String[] country;
    String[] county;
    private Button mReg;
    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics firebaseAnalytics;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
        firebaseAuth = FirebaseAuth.getInstance();
        initwidgets();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);


        Button mReg = (Button) findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        if (firebaseAuth.getCurrentUser() != null) {

        }

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = txtfirstname.getText().toString();
                String phonenumber = txtphonenumber.getText().toString();
                String surname = txtsurname.getText().toString();
                String email = txtemailaddress.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();
                String cpassword = txtconfirmpassword.getText().toString().trim();
                String phonenumber2 = txtphonenumber2.getText().toString().trim();
                String country = "KENYA";
                String county = "NAKURU";
                String physicaladdress = txtphysicaladdress.getText().toString().trim();

                if (TextUtils.isEmpty(firstname)) {
                    txtfirstname.setText("");
                    txtfirstname.requestFocus();
                    return;
                } else if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()) {
                    Toast.makeText(registerpage.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(email)) {
                    txtemailaddress.setText("");
                    txtemailaddress.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(surname)) {
                    txtsurname.setText("");
                    txtsurname.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(getApplicationContext(), "please retype password", Toast.LENGTH_SHORT).show();
                    txtconfirmpassword.setText("");
                    txtconfirmpassword.requestFocus();

                    return;
                } else if (TextUtils.isEmpty(phonenumber)) {
                    txtphonenumber.setText("");
                    txtphonenumber.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "please enter password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short,enter mimimum of 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(registerpage.this, "Password Not matching", Toast.LENGTH_SHORT).show();
                }
                progressDialog.setMessage("Registering..");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(registerpage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(registerpage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
/*
                                if (!task.isSuccessful()) {
                                    //THIS IS FOR STORING AUTHENTICATED USER'S DATA
                                    Toast.makeText(registerpage.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();


                                } else {
                                    FirebaseUser userreg1= FirebaseAuth.getInstance().getCurrentUser();
                                    databaseRef= FirebaseDatabase.getInstance().getReference("UserData").child(userreg1.getUid());
                                    String firstname = txtfirstname.getText().toString();
                                    String phonenumber = txtphonenumber.getText().toString();
                                    String surname = txtsurname.getText().toString();
                                    String email = txtemailaddress.getText().toString().trim();
                                    String password = txtpassword.getText().toString().trim();
                                    String cpassword = txtconfirmpassword.getText().toString().trim();
                                    String phonenumber2 = txtphonenumber2.getText().toString().trim();
                                    String country = txtcountry.getText().toString().trim();
                                    String county = txtcounty.getText().toString().trim();
                                    String physicaladdress = txtphysicaladdress.getText().toString().trim();
                                    String radiodefaultusage;

                                    if(radiosender.isChecked()) {
                                        radiodefaultusage = "sender";
                                    }
                                    else {
                                        radiodefaultusage = "receiver";
                                    }
                                    registerinfo Register = new registerinfo(firstname,surname,phonenumber,phonenumber2,email,physicaladdress,radiodefaultusage,county,country);
                                    FirebaseUser cuser5 = FirebaseAuth.getInstance().getCurrentUser();
                                    FirebaseDatabase.getInstance().getReference("Users").child(cuser5.getUid())
                                            .setValue(Register).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(registerpage.this, "registered", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(registerpage.this, "failed", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
*/
                                //saveRegistrationInformation();
                                String email = txtemailaddress.getText().toString().trim();

                                progressDialog.setMessage("Registration successful" + email);
                                progressDialog.hide();


                            }
                        });

            }

        });
    }

    private void initwidgets() {
        country = getResources().getStringArray(R.array.COUNTRY);
        county = getResources().getStringArray(R.array.COUNTY);
        Log.w(TAG, "" + country + "" + county);
        txtfirstname = (EditText) findViewById(R.id.txt_firstname);
        txtsurname = (EditText) findViewById(R.id.txt_surname);
        txtphonenumber = (EditText) findViewById(R.id.txt_phonenumber);
        txtphonenumber2 = (EditText) findViewById(R.id.txt_phonenumber2);
        txtemailaddress = (EditText) findViewById(R.id.txt_emailaddress);
        txtphysicaladdress = (EditText) findViewById(R.id.txt_physicaladdress);
        radiosender = (RadioButton) findViewById(R.id.radio_sender);
        radioreceiver = (RadioButton) findViewById(R.id.radio_receiver);
        listViewcountry = (ListView) findViewById(R.id.listview_country);
        listViewcounty = (ListView) findViewById(R.id.listview_county);
        txtpassword = (EditText) findViewById(R.id.txt_password);
        txtconfirmpassword = (EditText) findViewById(R.id.txt_confirm_password);
        ArrayAdapter<String> adaptercountry = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country);
        listViewcountry.setAdapter(adaptercountry);
        ArrayAdapter<String> adaptercounty = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, county);
        listViewcounty.setAdapter(adaptercounty);

        listViewcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countrypicked = (TextView) view;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        listViewcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countypicked = (TextView) view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void updateUI(FirebaseUser account) {

        if (account != null) {
            Toast.makeText(this, "U Signed In successfully", Toast.LENGTH_LONG).show();
            sendpersonalinfo();
            saveRegistrationInformation();
            nextActivity(MainActivity.class);
        } else {
            Toast.makeText(this, "U Didnt sign in", Toast.LENGTH_LONG).show();
        }


    }

    private void sendpersonalinfo() {
        FirebaseUser userreg1 = FirebaseAuth.getInstance().getCurrentUser();
        databaseRef = FirebaseDatabase.getInstance().getReference("UserData").child(userreg1.getUid());
        String firstname = txtfirstname.getText().toString();
        String phonenumber = txtphonenumber.getText().toString();
        String surname = txtsurname.getText().toString();
        String email = txtemailaddress.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();
        String cpassword = txtconfirmpassword.getText().toString().trim();
        String phonenumber2 = txtphonenumber2.getText().toString().trim();
        String country = "KENYA";
        String county = countypicked.getText().toString().trim();
        String physicaladdress = txtphysicaladdress.getText().toString().trim();
        String radiodefaultusage;

        if (radiosender.isChecked()) {
            radiodefaultusage = "sender";
        } else {
            radiodefaultusage = "receiver";
        }
        registerinfo Register = new registerinfo(firstname, surname, phonenumber, phonenumber2, email, physicaladdress, radiodefaultusage, county, country);
        FirebaseUser cuser5 = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Users").child(cuser5.getUid())
                .setValue(Register).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(registerpage.this, "registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(registerpage.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void saveRegistrationInformation() {
        String firstname = txtfirstname.getText().toString();
        String phonenumber = txtphonenumber.getText().toString();
        String surname = txtsurname.getText().toString();
        String email = txtemailaddress.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();
        String cpassword = txtconfirmpassword.getText().toString().trim();
        String phonenumber2 = txtphonenumber2.getText().toString().trim();
        String country = countrypicked.getText().toString().trim();
        String county = countypicked.getText().toString().trim();
        String physicaladdress = txtphysicaladdress.getText().toString().trim();
        if (radiosender.isChecked()) {
            radiodefaultusage = "sender";
        } else {
            radiodefaultusage = "receiver";
        }
        if (!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phonenumber) && !TextUtils.isEmpty(physicaladdress)) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            Log.d("TAG", "WEDRFG11111111111" + firstname);
            sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(USERNAME_KEY, firstname + surname);
            editor.putString(USER_EMAIL_KEY, email);
            editor.putString(USER_PHONENUMBER, phonenumber);
            editor.putString(USER_PHYSICALADDRESS, physicaladdress);
            editor.apply();
            Toast.makeText(registerpage.this, "data saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(registerpage.this, "please fill in all fields", Toast.LENGTH_SHORT).show();

        }
    }

    private void nextActivity(Class nextClass) {
        overridePendingTransition(0, 0);
        startActivity(new Intent(context, nextClass));
        overridePendingTransition(0, 0);
        finish();
    }

}



