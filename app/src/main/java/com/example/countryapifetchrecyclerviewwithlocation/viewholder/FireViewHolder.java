package com.example.countryapifetchrecyclerviewwithlocation.viewholder;

import android.app.Application;

import com.example.countryapifetchrecyclerviewwithlocation.database.FirebaseRepo;
import com.example.countryapifetchrecyclerviewwithlocation.helper.CommonFunctions;
import com.example.countryapifetchrecyclerviewwithlocation.model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class FireViewHolder extends AndroidViewModel {

    FirebaseRepo db =new FirebaseRepo();
    //instance Variable
    private static FireViewHolder instance;
    //mutable Live Data
    public MutableLiveData<List<Country>> viewModelLiveData;
    public List<Country> countries = new ArrayList<>();

    public static FireViewHolder getInstance(Application application) {
        if(instance == null){
            instance = new FireViewHolder(application);
        }
        return instance;
    }
    public FireViewHolder(@NonNull  Application application) {
        super(application);
        this.viewModelLiveData =this.db.countryMutableLiveData;
    }

    public void addData(Country country){
        this.db.addFav(country);
    }

    public void deleteData(Country country){
        this.db.deleteFav(country);
    }

    public List<Country> getAll(){


        CommonFunctions functions = new CommonFunctions();
        countries = this.db.getExistingList();

        functions.LogCatD("Check--->","Array after removing duplicates: "
                + countries.size());
        /*
         * convert array to list and then add all
         * elements to LinkedHashSet. LinkedHashSet
         * will automatically remove all duplicate elements.
         */
        LinkedHashSet<Country> lhSetColors =
                new LinkedHashSet<Country>(countries);

        //create array from the LinkedHashSet
        Country[] newArray = lhSetColors.toArray(new Country[ lhSetColors.size() ]);
        functions.LogCatD("Check--->","Array after removing duplicates: "
                + newArray.length);
        return this.db.getExistingList();
    }
}
