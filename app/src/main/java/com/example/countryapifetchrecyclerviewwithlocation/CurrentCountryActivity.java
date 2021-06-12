package com.example.countryapifetchrecyclerviewwithlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.countryapifetchrecyclerviewwithlocation.adapter.RecyclerViewAdapter;
import com.example.countryapifetchrecyclerviewwithlocation.databinding.ActivityCurrentCountryBinding;
import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.helper.LocationHelper;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;
import com.example.countryapifetchrecyclerviewwithlocation.network.RetrofitClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class CurrentCountryActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getCanonicalName();
    CommonFunctions functions = new CommonFunctions();

    private Country currentCountry = new Country();
    String currentCountryCode = "";
    private LocationHelper locationHelper;
    private Location lastLocation;
    private LocationCallback locationCallback;
    private LatLng currentLocation;
    ActivityCurrentCountryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCurrentCountryBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.currentLocation = new LatLng(this.getIntent().getDoubleExtra("EXTRA_LAT", 0.0),
                this.getIntent().getDoubleExtra("EXTRA_LNG", 0.0));

        this.locationHelper = LocationHelper.getInstance();
        this.locationHelper.checkPermissions(this);

        if (this.locationHelper.locationPermissionGranted) {
            this.initiateLocationListener();
        }
    }

    private void initiateLocationListener() {
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location loc : locationResult.getLocations()) {
                    String code = locationHelper.getCode(getApplicationContext(),loc );
                    setCurrDetails(code);

                    Log.e(TAG, "onLocationResult: update location " + loc.toString());
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this, this.locationCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationHelper.stopLocationUpdates(this, this.locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationHelper.requestLocationUpdates(this, this.locationCallback);
    }

    private void setCurrDetails(String code) {
        Call<Country> curr = RetrofitClient.getInstance().getApi().currCountry();
        try {
            curr.enqueue(new Callback<Country>() {
                @Override
                public void onResponse(Call<Country> call, Response<Country> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        currentCountry = response.body();
                        binding.CurrCunName.setText(currentCountry.getCountryName());
                        String code = currentCountry.getCountryCode();
                        String capital = currentCountry.getCountryCapital();
                        String population = currentCountry.getCountryPopulation();
                        String sendText = "Capital is - " + capital
                                + "\n" + "Country code is - " + code +
                                "\n" + "Population is - " + population;
                        String flagImageURL = "https://www.countryflags.io/" + code + "/flat/64.png";
                        Glide.with(getApplicationContext())
                                .load(flagImageURL)
                                .placeholder(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_media_next))
                                .into(binding.imageView);
                        binding.info.setText(sendText);
                    }
                }

                @Override
                public void onFailure(Call<Country> call, Throwable t) {

                }
            });
        } catch (Exception ex) {

        }
    }
}