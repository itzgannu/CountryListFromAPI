package com.example.countryapifetchrecyclerviewwithlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.countryapifetchrecyclerviewwithlocation.adapter.RecyclerViewAdapter;
import com.example.countryapifetchrecyclerviewwithlocation.databinding.ActivityMainBinding;
import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;
import com.example.countryapifetchrecyclerviewwithlocation.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getCanonicalName();
    CommonFunctions functions = new CommonFunctions();

    ActivityMainBinding binding;

    private ArrayList<Country> countryListFromAPI = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllCountriesFromAPI();
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view  = this.binding.getRoot();
        setContentView(view);
    }
//
//    private void getFlagsFromAPI(){
//        for(int i = 0; i< countryListFromAPI.size(); i++){
//            Call<String> flagCall = RetrofitClient.getInstance().getApi().flagCountries(countryListFromAPI.get(i).getCountryCode());
//            try{
//                flagCall.enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });
//            }catch (Exception e){
//                functions.LogCatE(TAG, "Caught exception - "+e.getLocalizedMessage());
//            }
//        }
//
//    }

    private void getAllCountriesFromAPI(){
        Call<List<Country>> countryCall = RetrofitClient.getInstance().getApi().getAllCountries();
        try{
            countryCall.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    if(response.code() == 200 && response.body() != null){
                        ArrayList<Country> countryList = (ArrayList<Country>) response.body();
                        countryListFromAPI.clear();
                        countryListFromAPI.addAll(countryList);
                        adapter = new RecyclerViewAdapter(countryListFromAPI, getApplicationContext());
                        binding.mainRecycle.setAdapter(adapter);
                        binding.mainRecycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        //set image
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Log.e(TAG, "onResponse: No response");
                    }
                    call.cancel();
                }
                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    functions.ToastMessageLong(getApplicationContext(),"Failed to fetch data from API");
                }
            });

        }catch (Exception ex){
            Log.e(TAG, "getCategoryList: Exception occurred while getting categories " + ex.getLocalizedMessage());
        }
    }




    //menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_display, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites: {
                functions.ToastMessageLong(this, "Favorite List clicked");
                Intent goToLocationScreen = new Intent(this, FavoriteListActivity.class);
                startActivity(goToLocationScreen);
                break;
            }
            case R.id.currentCountry: {
                functions.ToastMessageLong(this, "Current Country clicked");
                Intent goToLocationScreen = new Intent(this, CurrentCountryActivity.class);
                startActivity(goToLocationScreen);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}