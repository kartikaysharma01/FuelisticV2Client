package com.example.fuelisticv2client.fuelisticv2client.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.fuelisticv2client.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {


    //variables for getting data
    TextInputLayout fullName, username, email, password, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.signUp_full_name);
        username = findViewById(R.id.signUp_username);
        email = findViewById(R.id.signUp_email);
        password = findViewById(R.id.signUp_password);
        address = findViewById(R.id.signUp_address);
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    public void callNextSignUpScreen(View view) {
        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword() | !validateAddress() ) {
            return;
        }

        String _fullName = fullName.getEditText().getText().toString();
        String _username = username.getEditText().getText().toString();
        String _email = email.getEditText().getText().toString();
        String _password = password.getEditText().getText().toString();
        String _address = address.getEditText().getText().toString();

        Intent intent = new Intent(getApplicationContext(), SignUp2ndScreen.class);
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);
        intent.putExtra("address", _address);

        startActivity(intent);
        finish();
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress() {
        String val = address.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            address.setError("Field can not be empty");
            return false;
        } else {
            address.setError(null);
            address.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No White spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "\\A\\w{1,20}\\z";        //BUG HERE

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Password is too large!");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain white spaces!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

}