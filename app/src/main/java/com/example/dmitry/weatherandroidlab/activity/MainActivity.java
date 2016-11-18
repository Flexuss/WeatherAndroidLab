package com.example.dmitry.weatherandroidlab.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmitry.weatherandroidlab.R;
import com.example.dmitry.weatherandroidlab.adapters.RecyclerCitiesAdapter;
import com.example.dmitry.weatherandroidlab.fragments.AddCityFragment;

/**
 * Created by Dmitry on 17.11.2016.
 */

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    Button btnAddCity;
    AddCityFragment fragment;
    public TextView tvError;
    public static int result=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv_cities);
        btnAddCity = (Button) findViewById(R.id.btn_add);
        tvError= (TextView) findViewById(R.id.tv_error_message);
        fragment = new AddCityFragment();
//        fragment.newInstance(MainActivity.this);

        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        RecyclerCitiesAdapter adapter = new RecyclerCitiesAdapter(MainActivity.this);
        rv.setAdapter(adapter);

        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }
}
