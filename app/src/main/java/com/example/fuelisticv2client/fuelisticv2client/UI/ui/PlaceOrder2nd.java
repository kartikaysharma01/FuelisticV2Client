package com.example.fuelisticv2client.fuelisticv2client.UI.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelisticv2client.R;
import com.example.fuelisticv2client.fuelisticv2client.Callback.ILoadTimeFromFirebaseListener;
import com.example.fuelisticv2client.fuelisticv2client.Common.Common;
import com.example.fuelisticv2client.fuelisticv2client.Model.Order;
import com.example.fuelisticv2client.fuelisticv2client.UI.HomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public class PlaceOrder2nd extends AppCompatActivity implements ILoadTimeFromFirebaseListener {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;

    EditText edit_address;
    EditText edit_comment;
    TextView text_address;
    TextView place_order_total_price;
    RadioButton rdb_home_add, rdb_other_add, rdb_use_current_add, rdb_cod, rdb_brainTree;

    double totalPrice = 1;

    String deliveryDate, deliveryMode, fuelType, orderQuantity;
    ILoadTimeFromFirebaseListener listener;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order2nd);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listener = this;

        //get data from prev screen
        //Get all the data from Intent
        deliveryDate = getIntent().getStringExtra("deliveryDate");
        deliveryMode = getIntent().getStringExtra("deliveryMode");
//        fuelType = getIntent().getStringExtra("fuelType");
        orderQuantity = getIntent().getStringExtra("orderQuantity");


        place_order_total_price = (TextView) findViewById(R.id.place_order_total_price);
        edit_address = findViewById(R.id.edit_address);
        edit_comment = findViewById(R.id.edit_comment);
        text_address = findViewById(R.id.text_address_detail);
        rdb_home_add = findViewById(R.id.rdb_home_add);
        rdb_other_add = findViewById(R.id.rdb_other_add);
        rdb_use_current_add = findViewById(R.id.rdb_use_current_add);
        rdb_cod = findViewById(R.id.rdb_cod);
        rdb_brainTree = findViewById(R.id.rdb_braintree);


        //Data
        edit_address.setText(Common.currentUser.getAddress());
        totalPrice = Math.round(totalPrice * (Common.diesel_price) * Integer.parseInt(orderQuantity) );
        place_order_total_price.setText("Rs. "+ totalPrice );


        //Event
        rdb_home_add.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                edit_address.setText(Common.currentUser.getAddress());
                text_address.setVisibility(View.GONE);
            }
        });

        rdb_other_add.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                edit_address.setText(" "); //clear
                edit_address.setHint("Enter your address..");
                text_address.setVisibility(View.GONE);
            }
        });

        rdb_use_current_add.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                fusedLocationProviderClient.getLastLocation()
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PlaceOrder2nd.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                text_address.setVisibility(View.GONE);
                            }
                        })
                        .addOnCompleteListener(task -> {
                            final String coordinates = new StringBuilder()
                                    .append(task.getResult().getLatitude())
                                    .append("/")
                                    .append(task.getResult().getLongitude()).toString();

                            Single<String> singleAddress = Single.just(getAddressFromLatLng(task.getResult().getLatitude(),
                                    task.getResult().getLongitude()));

                            Disposable disposable = singleAddress.subscribeWith(new DisposableSingleObserver<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    edit_address.setText(s);
                                    //text_address.setText(s);
                                    text_address.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    edit_address.setText(coordinates);
                                    text_address.setText(e.getMessage());
                                    text_address.setVisibility(View.VISIBLE);
                                }
                            });


                        });
            }
        });

        initLocation();

    }

    private String getAddressFromLatLng(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String result = "";
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder(address.getAddressLine(0));
                result = sb.toString();
            } else {
                result = "Address not found!!";
            }

        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fusedLocationProviderClient != null)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());


    }

    @Override
    public void onStop(){
        if(fusedLocationProviderClient!=null)
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        compositeDisposable.clear();
        super.onStop();
    }

    @SuppressLint("MissingPermission")
    private void initLocation() {
        buildLocationRequest();
        buildLocationCallback();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    private void buildLocationCallback() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                currentLocation = locationResult.getLastLocation();
            }
        };

    }

    private void buildLocationRequest() {
        locationRequest= new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);

    }

    public void place_Order(View view) {
        if(rdb_cod.isChecked())
            paymentCOD(edit_address.getText().toString(), edit_comment.getText().toString());
    }

    private void paymentCOD(String address, String comment) {
        double finalPrice = totalPrice ;
        Order order = new Order();

        order.setUserName(Common.currentUser.getUsername());
        order.setUserPhone(Common.currentUser.getPhoneNo());
        order.setShippingAddress(address);
        order.setComment(comment);

        if(currentLocation != null){
            order.setLat(currentLocation.getLatitude());
            order.setLng(currentLocation.getLongitude());
        }
        else{
            order.setLng(-0.1f);
            order.setLat(-0.1f);
        }

        order.setTotalPayment(totalPrice);
        order.setDeliveryCharge(0);  //later
        order.setFinalPayment(finalPrice);
        order.setCod(true);
        order.setTransactionId("Cash On Delivery");
        order.setFuelType(fuelType);
        order.setQuantity(orderQuantity);
        order.setDeliveryDate(deliveryDate);
        order.setDeliveryMode(deliveryMode);
        order.setOrderStatus(0);

        syncLocalTimeWithGlobalTime(order);

    }

    private void syncLocalTimeWithGlobalTime(Order order) {
        final DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                long offset = snapshot.getValue(Long.class);
                long estimatedServerTimeInMS = System.currentTimeMillis()+offset;

                SimpleDateFormat sdf = new SimpleDateFormat("MM DD. YYYY HH:mm");
                Date resultDate = new Date(estimatedServerTimeInMS);

                listener.onLoadTimeSuccess(order, estimatedServerTimeInMS);
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                listener.onLoadTimeFailed(error.getMessage());
            }
        });
    }

    private void writeOrderToFirebase(Order order) {
        FirebaseDatabase.getInstance()
                .getReference(Common.ORDER_REF)
                .child(Common.createOrderNumber())
                .setValue(order)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PlaceOrder2nd.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(task -> {
            Toast.makeText(this, "Order Placed Successfully !!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                });
    }

    @Override
    public void onLoadTimeSuccess(Order order, long estimateTimeInMs) {
        order.setOrderDate(estimateTimeInMs);
        writeOrderToFirebase(order);
    }

    @Override
    public void onLoadTimeFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}