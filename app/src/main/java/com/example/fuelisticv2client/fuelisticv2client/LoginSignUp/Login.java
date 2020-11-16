package com.example.fuelisticv2client.fuelisticv2client.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.example.fuelisticv2client.fuelisticv2client.Model.UserModel;
import com.example.fuelisticv2client.fuelisticv2client.UI.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCES);
    private UserModel userModel = new UserModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        progressbar = findViewById(R.id.login_progress_bar);
        password = findViewById(R.id.login_password);

    }

    //allow login
    public void letUserLogIn(View view) {
        //validate phone number & password
        if (!isConnected(this)) {
            showCustomDialog();
        }

        if (!validateCredentials()) {
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        ////get data
        String _phoneNo = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        //Remove first zero if entered!
        if (_phoneNo.charAt(0) == '0') {
            _phoneNo = _phoneNo.substring(1);
        }

        final String _completePhoneNo = "+" + countryCodePicker.getFullNumber() + _phoneNo;

        //Database
        Query checkUser = userRef.orderByChild("phoneNo").equalTo(_completePhoneNo);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = snapshot.child(_completePhoneNo).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullName = snapshot.child(_completePhoneNo).child("fullName").getValue(String.class);
                        String _email = snapshot.child(_completePhoneNo).child("email").getValue(String.class);
                        String _phoneNo = snapshot.child(_completePhoneNo).child("phoneNo").getValue(String.class);
                        String _dateOfBirth = snapshot.child(_completePhoneNo).child("dateOfBirth").getValue(String.class);
                        String _password = snapshot.child(_completePhoneNo).child("password").getValue(String.class);
                        String _gender = snapshot.child(_completePhoneNo).child("gender").getValue(String.class);
                        String _username = snapshot.child(_completePhoneNo).child("username").getValue(String.class);
                        String _address = snapshot.child(_completePhoneNo).child("address").getValue(String.class);
                        String _totalOrderQuantity = snapshot.child(_completePhoneNo).child("totalOrderQuantity").getValue(String.class);

                        //create a Session
                        userModel.setPhoneNo(_phoneNo);
                        userModel.setFullName(_fullName);
                        userModel.setUsername(_username);
                        userModel.setAddress(_address);
                        userModel.setEmail(_email);
                        userModel.setDateOfBirth(_dateOfBirth);
                        userModel.setPassword(_password);
                        userModel.setGender(_gender);
                        userModel.setTotalOrderQuantity(_totalOrderQuantity);

                        progressbar.setVisibility(View.GONE);
                        gotoHomeActivity(userModel);


                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "User does not exist!", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, ""+error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    private void gotoHomeActivity(UserModel userModel) {

        Common.currentUser = userModel;
        startActivity(new Intent(Login.this, HomeActivity.class));
        finish();

//        FirebaseInstanceId.getInstance()
//                .getInstanceId()
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        Common.currentUser = userModel;
//                        startActivity(new Intent(Login.this, HomeActivity.class));
//                        finish();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                Common.currentUser = userModel;
////                Common.updateToken(Login.this, task.getResult().getToken());
//                startActivity(new Intent(Login.this, HomeActivity.class));
//                finish();
//            }
//
//        });
    }


    //check internet connection
    private boolean isConnected(Login login) {

        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            return true;
        } else {
            return false;
        }

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No internet connection")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), AppStartupScreen.class));
                    finish();
                });
    }

    private boolean validateCredentials() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();

        String _password = password.getEditText().getText().toString().trim();


        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else {
            if (_password.isEmpty()) {
                password.setError("Field can not be empty");
                return false;
            }
            return true;

        }
    }


    public void callSignUpScreen(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp.class));
        finish();
    }


}