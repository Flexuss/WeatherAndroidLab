package com.example.dmitry.weatherandroidlab.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dmitry.weatherandroidlab.R;
import com.example.dmitry.weatherandroidlab.fragments.MyFragment;
import com.example.dmitry.weatherandroidlab.interfaces.TaskInterface;

/**
 * Created by Dmitry on 17.11.2016.
 */

public class CityInfoActivity extends AppCompatActivity implements TaskInterface {

    private TextView tvNameCity;
    public TextView tvDegrees;
    private ProgressBar progressBar;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);

        tvNameCity = (TextView) findViewById(R.id.tv_name_city);
        tvDegrees = (TextView) findViewById(R.id.tv_degrees);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if(savedInstanceState!=null){
            tvNameCity.setText(savedInstanceState.getString("name"));
            tvDegrees.setText(savedInstanceState.getString("degrees"));
        }
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        if(getFragment().isRunning()){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
        }



//        RetrofitAsyncTask retrofitAsyncTask = new RetrofitAsyncTask(CityInfoActivity.this, name);
//        retrofitAsyncTask.execute();
//        String degrees = intent.getStringExtra("degrees");
        tvNameCity.setText(name);
//        tvDegrees.setText(degrees);

    }

    @Override
    public void onTaskStart() {
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpgrade(int i) {
        progressBar.setProgress(i);
    }

    @Override
    public void onFinish(String s) {
        progressBar.setVisibility(View.GONE);
        if(s!=null){
            tvDegrees.setText(s);
        }
    }

    @Override
    public void onTaskCancel() {
        progressBar.setVisibility(View.GONE);
    }
    private MyFragment getFragment(){
        MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentByTag(MyFragment.class.getName());
        if(fragment == null){
            fragment = new MyFragment().newInstance(name);
//            fragment=new MyFragment();
//            fragment.startTask(name);
            getSupportFragmentManager().beginTransaction().add(fragment, MyFragment.class.getName()).commit();
        }
        return fragment;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("city", tvNameCity.getText().toString());
        outState.putString("degrees", tvDegrees.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
