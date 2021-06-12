package com.example.countryapifetchrecyclerviewwithlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.countryapifetchrecyclerviewwithlocation.adapter.FavAdapter;
import com.example.countryapifetchrecyclerviewwithlocation.adapter.RecyclerViewAdapter;
import com.example.countryapifetchrecyclerviewwithlocation.databinding.ActivityFavoriteListBinding;
import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;
import com.example.countryapifetchrecyclerviewwithlocation.viewholder.FavViewHolder;
import com.example.countryapifetchrecyclerviewwithlocation.viewholder.FireViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getCanonicalName();
    CommonFunctions functions = new CommonFunctions();

    ActivityFavoriteListBinding binding;

    FireViewHolder fireViewHolder;
    List<Country> countryList = new ArrayList<>();
    FavAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityFavoriteListBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.fireViewHolder = FireViewHolder.getInstance(this.getApplication());
//        this.fireViewHolder.getAll();

        this.fireViewHolder = FireViewHolder.getInstance(this.getApplication());
        this.fireViewHolder.viewModelLiveData.observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
               countryList = countries;
                viewAdapter = new FavAdapter( countryList, getApplicationContext());
                binding.favRecycle.setAdapter(viewAdapter);
                binding.favRecycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });
        countryList = this.fireViewHolder.getAll();
    }
}