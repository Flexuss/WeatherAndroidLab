package com.example.dmitry.weatherandroidlab.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.weatherandroidlab.R;
import com.example.dmitry.weatherandroidlab.activity.CityInfoActivity;
import com.example.dmitry.weatherandroidlab.entities.City;
import com.example.dmitry.weatherandroidlab.providers.CitiesProvider;

import java.util.List;

/**
 * Created by Dmitry on 17.11.2016.
 */

public class RecyclerCitiesAdapter extends RecyclerView.Adapter<RecyclerCitiesAdapter.CitiesViewHolder> {
    List<City> mCities;
    Context context;

    public RecyclerCitiesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_item,
                parent,
                false
        );
        return new CitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitiesViewHolder holder, int position) {
        final City city = mCities.get(position);

        holder.nameCity.setText(city.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CityInfoActivity.class);
                intent.putExtra("name", city.getName());
//                intent.putExtra("degrees", city.getDegrees);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        mCities= CitiesProvider.getInstance(context).getContacts();
        return mCities.size();
    }

    public class CitiesViewHolder extends RecyclerView.ViewHolder{
        TextView nameCity;

        public CitiesViewHolder(View itemView) {
            super(itemView);
            nameCity = (TextView) itemView.findViewById(R.id.tv_name_city);
        }
    }
}
