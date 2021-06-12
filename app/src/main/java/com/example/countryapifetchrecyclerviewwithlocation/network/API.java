package com.example.countryapifetchrecyclerviewwithlocation.network;

import com.example.countryapifetchrecyclerviewwithlocation.model.Country;
import com.example.countryapifetchrecyclerviewwithlocation.model.FlagImages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    String BASE_URL = "https://restcountries.eu/";
    String BASE_URL_IMAGE = "https://www.countryflags.io/";

    @GET("./rest/v2/all")
    Call<List<Country>> getAllCountries();

    @GET("./{code}/flat/64.png")
    Call<String> flagCountries(@Path("code") String countryName);

    @GET("./rest/v2/alpha/ca")
    Call<Country> currCountry();

//    @GET("./rest/v2/alpha/{code}}")
//    Call<Country> currCountry(@Path("code") String countryName);


}
