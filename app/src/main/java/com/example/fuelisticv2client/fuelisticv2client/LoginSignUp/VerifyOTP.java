package com.example.fuelisticv2client.fuelisticv2client.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.example.fuelisticv2client.fuelisticv2client.Model.UserModel;
import com.example.fuelisticv2client.fuelisticv2client.UI.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class VerifyOTP extends AppCompatActivity {

    PinView pinFromUser;
    String fullName, phoneNo, email, username, password,  dateOfBirth, gender,address, whatToDO;
    TextView otpDescriptionText;
    String codeBySystem;

    private AlertDialog dialog;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_verify_o_t_p);

        //hooks
        pinFromUser = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_description_text);

        //Get all the data from Intent
        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        dateOfBirth = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");
        address = getIntent().getStringExtra("address");
//        whatToDO = getIntent().getStringExtra("whatToDO");

        otpDescriptionText.setText("Enter One Time Password Sent On " + phoneNo);

        sendVerificationCodeToUser(phoneNo);
        
    }

    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,// Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }



            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    public void callLoginScreenFromSIgnUp(View view) {
        String code = pinFromUser.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);

        }


    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        storeNewUserData();

                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void storeNewUserData() {

        userRef = FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCES);
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        UserModel userModel= new UserModel();
        userModel.setPhoneNo(phoneNo);
        userModel.setFullName(fullName);
        userModel.setUsername(username);
        userModel.setAddress(address);
        userModel.setEmail(email);
        userModel.setDateOfBirth(dateOfBirth);
        userModel.setPassword(password);
        userModel.setGender(gender);
        userModel.setTotalOrderQuantity("0");

        dialog.show();
        userRef.child(phoneNo).setValue(userModel)
                .addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(VerifyOTP.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        dialog.dismiss();
                        Toast.makeText(VerifyOTP.this, "Congratulations!! Registration Complete." , Toast.LENGTH_SHORT).show();
                        gotoHomeActivity(userModel);
                    }
                });
    }

    private void gotoHomeActivity(UserModel userModel) {

        Common.currentUser = userModel;
        startActivity(new Intent(VerifyOTP.this, HomeActivity.class));
        finish();

//        FirebaseInstanceId.getInstance()
//                .getInstanceId()
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(VerifyOTP.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        Common.currentUser = userModel;
//                        startActivity(new Intent(VerifyOTP.this, HomeActivity.class));
//                        finish();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                Common.currentUser = userModel;
//                Common.updateToken(VerifyOTP.this, task.getResult().getToken());
//                startActivity(new Intent(VerifyOTP.this, HomeActivity.class));
//                finish();
//            }
//
//        });

    }
}