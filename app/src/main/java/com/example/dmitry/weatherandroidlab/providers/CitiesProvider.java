package com.example.dmitry.weatherandroidlab.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.dmitry.weatherandroidlab.entities.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 17.11.2016.
 */

public class CitiesProvider {
    public static final String CITY_PREFERENCES = "CitiesList";
    public static final String PREFERENCES_NAME = "CityName";

    private static CitiesProvider sInstance;
    private Context context;

    public static CitiesProvider getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new CitiesProvider(context.getApplicationContext());
        }
        return sInstance;
    }

    public CitiesProvider(Context context) {
        this.context = context;
    }

    public List<City> getContacts() {
        SharedPreferences preferences = context.getSharedPreferences(CITY_PREFERENCES, Context.MODE_PRIVATE);
        if (preferences.contains(PREFERENCES_NAME)) {
            Gson gson = new Gson();
            String jsonText = preferences.getString(PREFERENCES_NAME, "");
            Type listType = new TypeToken<List<City>>() {
            }.getType();
            List<City> cities = gson.fromJson(jsonText, listType);
            return cities;
        } else {
            List<City> cities = new ArrayList<>();
            saveCities(cities);
            return cities;
        }
    }

    public void saveCities(List<City> cities) {
        SharedPreferences preferences = context.getSharedPreferences(CITY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<City>>() {
        }.getType();
        String jsonText = gson.toJson(cities, listType);
        editor.putString(PREFERENCES_NAME, jsonText);
        editor.commit();
    }


    public void addCity(City city) {
        List<City> cities = getContacts();
        cities.add(city);
        saveCities(cities);
    }

}
