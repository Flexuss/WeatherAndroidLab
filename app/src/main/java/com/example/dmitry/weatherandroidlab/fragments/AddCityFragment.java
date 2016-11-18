package com.example.dmitry.weatherandroidlab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dmitry.weatherandroidlab.R;
import com.example.dmitry.weatherandroidlab.activity.MainActivity;
import com.example.dmitry.weatherandroidlab.entities.City;
import com.example.dmitry.weatherandroidlab.providers.CitiesProvider;

/**
 * Created by Dmitry on 17.11.2016.
 */

public class AddCityFragment extends DialogFragment {
   private EditText etCityName;
   private Button btnAdd;
   private Button btnClose;
    private MyFragment myFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Add City");
        View view = inflater.inflate(R.layout.my_dialog, null);
        etCityName = (EditText) view.findViewById(R.id.et_name_city);
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        btnClose = (Button) view.findViewById(R.id.btn_close);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCityName.getText().toString();

                etCityName.setText("");
                myFragment = new MyFragment().newInstance(name);
                myFragment.startTask(name);

//                if(myFragment.getResult()) {
                if(MainActivity.result==1){
                    CitiesProvider.getInstance(getActivity()).addCity(new City(
                            name));
                }
                MainActivity.result=1;
                dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
