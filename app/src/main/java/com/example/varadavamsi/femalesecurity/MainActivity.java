package com.example.varadavamsi.femalesecurity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.varadavamsi.femalesecurity.setnumbers.MyPREFERENCES;
import static com.example.varadavamsi.femalesecurity.setnumbers.number1;
import static com.example.varadavamsi.femalesecurity.setnumbers.number2;
import static com.example.varadavamsi.femalesecurity.setnumbers.number3;
import static com.example.varadavamsi.femalesecurity.setnumbers.number4;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    GPSTracker gps;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
     public  String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Button next = (Button) findViewById(R.id.SetNumbers);
        Button SendLocation = (Button) findViewById(R.id.SendLocation);
        checkLocationPermission();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent_book = new Intent(MainActivity.this,setnumbers.class);
                startActivity(newIntent_book);
            }
        });
        SendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                gps = new GPSTracker(MainActivity.this);
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    getAddress(MainActivity.this,latitude,longitude);
                    System.out.println("asdafkjadlskj");
                    System.out.println(latitude+longitude);
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }
                else{
                    gps.showSettingsAlert();
                }




                System.out.println("ifsound");
                String firstnumber = sharedpreferences.getString(number1,"");
                String secondnumber = sharedpreferences.getString(number2,"");
                String thirdnumber = sharedpreferences.getString(number3,"");
                String fourthnumber = sharedpreferences.getString(number4,"");
                List<String> Phonenumbers = new ArrayList<String>();
                System.out.println(firstnumber+"sdfsd"+secondnumber+"sdkfla"+thirdnumber+fourthnumber);
                Phonenumbers.add(firstnumber);
                Phonenumbers.add(secondnumber);
                Phonenumbers.add(thirdnumber);
                Phonenumbers.add(fourthnumber);

                String toNumbers = "";
                for ( String s : Phonenumbers)
                {
                    toNumbers = toNumbers + s + ";";
                }
                //  message= "Alert Alert!! i am in trouble at 0.0 location ";

                Uri sendSmsTo = Uri.parse("smsto:" + toNumbers);
                Intent intent = new Intent(
                        android.content.Intent.ACTION_SENDTO, sendSmsTo);
                intent.putExtra("sms_body", message);
                startActivity(intent);
            }
        });
    }

    public  void getAddress(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {



                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                Log.d("location", "getAddress:  address" + address);
                Log.d("location", "getAddress:  city" + city);
                Log.d("location", "getAddress:  state" + state);
                Log.d("location", "getAddress:  postalCode" + postalCode);
                Log.d("location", "getAddress:  knownName" + knownName);



                message="Alert Alert!! i am in trouble at"+"Address:"+address+"\n"+"City:"+city+"\n"+"State:"+state+"\n"+"Postalcode:"+postalCode+"\n"+"Knownname:"+knownName;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location permissions")
                        .setMessage("Please give the permission to access location")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}