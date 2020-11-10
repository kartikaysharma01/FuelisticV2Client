package com.example.fuelisticv2client.fuelisticv2client.UI.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.fuelisticv2client.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlaceOrder extends AppCompatActivity {

    TextInputEditText deliveryDate;
    Spinner deliveryMode;
    String fuelType;
    TextInputLayout orderQuantity;
    final Calendar myCalendar = Calendar.getInstance();
    long minDate  = myCalendar.getTimeInMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_place_order);

        //Hooks
        deliveryDate= (TextInputEditText) findViewById(R.id.deliveryDate);
        fuelType = "High Speed Diesel";
        orderQuantity= findViewById(R.id.order_quantity);

        // Spinner element
        deliveryMode = (Spinner) findViewById(R.id.deliveryMode);

        spinnerClass();


    }

    private void spinnerClass() {
        // Spinner click listener

        // Spinner Drop down elements for delivery mode
        List<String> deliveryMode_categories = new ArrayList<String>();
        deliveryMode_categories.add("Generator");
        deliveryMode_categories.add("Oil Can");
        deliveryMode_categories.add("Drum");
        deliveryMode_categories.add("Machine");
        deliveryMode_categories.add("Non-Mobile Engine");
        deliveryMode_categories.add("Other");

        // Creating adapter for spinner
        ArrayAdapter<String> deliveryMode_dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deliveryMode_categories);

        // Drop down layout style - list view with radio button
        deliveryMode_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        deliveryMode.setAdapter(deliveryMode_dataAdapter);
    }



    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    public void selectDate(View view) {
        DatePickerDialog dpd = new DatePickerDialog(PlaceOrder.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        dpd.getDatePicker().setMinDate(minDate);
        dpd.show();
    } ;

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

//        myCalendar.getTimeInMillis();
        deliveryDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void callSelectAddress(View view) {
        String _deliveryDate = deliveryDate.getText().toString();
        String _orderQuantity = orderQuantity.getEditText().getText().toString();
        String _deliveryMode = deliveryMode.getSelectedItem().toString();

        Intent intent = new Intent(getApplicationContext(), PlaceOrder2nd.class);
        //intent.putExtra("fuelType",_fuelType );
        intent.putExtra("deliveryDate", _deliveryDate);
        intent.putExtra("orderQuantity", _orderQuantity);
        intent.putExtra("deliveryMode", _deliveryMode);
        startActivity(intent);
        finish();
    }
    
}