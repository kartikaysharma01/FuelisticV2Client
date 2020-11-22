package com.example.fuelisticv2client.fuelisticv2client.LoginSignUp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SettingsSlicesContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fuelisticv2client.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity
{
    private ImageView screenIcon;
    private TextView description, title;
    private TextInputLayout phoneNumberTextField;
    private CountryCodePicker picker;
    private Button nextBtn;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_paassword);

        screenIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        description = findViewById(R.id.forget_password_description);
        phoneNumberTextField = findViewById(R.id.forget_password_phone_number);
        picker = findViewById(R.id.country_code_picker);
        nextBtn = findViewById(R.id.forget_password_next_btn);
        //progressBar = findViewById(R.id.prog)
    }

    //Check internet connection
    private boolean isConnected()
    {
        ConnectivityManager conMan = getSystemService(ConnectivityManager.class);
        //NetworkInfo wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Network currentNetwork = conMan.getActiveNetwork();
        return currentNetwork != null;
    }
    private void showCustomDialog()
    {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setMessage("Connect to network to proceed further")
            .setCancelable(false)
            .setPositiveButton("Connect", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Redirect user to other screen if required
                }
            });
    }

    public void verifyPhone(View v)
    {
        if(!isConnected())
        {
            showCustomDialog();
        }

        //Get fields from user
        String _phoneNumber = Objects.requireNonNull(phoneNumberTextField.getEditText()).toString().trim();
        if(_phoneNumber.charAt(0)=='0')
            phoneNumberTextField.setError("Enetr only phone number");
        final String _completephoneNumber = "+" + picker.getFullNumber() + _phoneNumber;

    }


}