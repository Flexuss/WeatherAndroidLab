package com.example.dmitry.weatherandroidlab.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.dmitry.weatherandroidlab.activity.MainActivity;
import com.example.dmitry.weatherandroidlab.interfaces.OpenWeatherMap;
import com.example.dmitry.weatherandroidlab.interfaces.TaskInterface;
import com.example.dmitry.weatherandroidlab.pojo.Weather;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dmitry on 17.11.2016.
 */

public class MyFragment extends Fragment {
    private TaskInterface taskInterface;
    private MyAsyncTask task;
    private String city;
    private boolean result=true;

    public boolean isRunning(){
        return task!=null;
    }

    @Override
    public void onAttach(Context context) {
        interf(context);
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        interf(activity);
        super.onAttach(activity);
    }

    public void startTask(String city) {
        if (task == null) {
            task = new MyAsyncTask(city);
            task.execute();
        }
    }
    public void stopTask(){
        if(task!=null){
            task.cancel(true);
            task=null;
        }
    }

    private void interf (Context context) {
        if (context instanceof TaskInterface) {
            taskInterface = (TaskInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskInterface = null;
    }
    public static MyFragment newInstance(String city){
        MyFragment myFragment = new MyFragment();
        Bundle arguments = new Bundle();
        arguments.putString("city",city);
        myFragment.setArguments(arguments);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);                                    //чтобы фрагмент не пересоздавался
        city=getArguments().getString("city");
        MyAsyncTask myAsyncTask = new MyAsyncTask(city);
        myAsyncTask.execute();
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, String> {

//        private CityInfoActivity cityInfoActivity;
        private String name;

        public MyAsyncTask( String name) {
//            this.cityInfoActivity = cityInfoActivity;
            this.name = name;
        }

        @Override
        protected void onPreExecute() {
            if (taskInterface != null) {
                taskInterface.onTaskStart();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create())
                    .build();


            OpenWeatherMap openWeatherMap = retrofit.create(OpenWeatherMap.class);

            Call<Weather> weatherCall = openWeatherMap.getWeather(name, OpenWeatherMap.API_KEY);
            String temp = "";
            try {
                Response<Weather> response = weatherCall.execute();
                if (response.errorBody() != null) {                                //обработка ошибки
                    result=false;
                    MainActivity.result=0;
                    return String.valueOf(response.code());
                }
                Weather weather = response.body();
                temp = (String.valueOf((int) (weather.getMain().getTemp() - 273)) + " °С");



            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int randomProgress = 0;
                // Simulate some heavy work.
                while (randomProgress < 100 && !isCancelled()) {
                    randomProgress += 5;
                    publishProgress(randomProgress);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                return null;
            }
            return temp;
        }

        @Override
        protected void onPostExecute(String s) {
            task=null;
            if(taskInterface!=null){
//                cityInfoActivity.tvDegrees.setText(s);
                taskInterface.onFinish(s);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(taskInterface!=null) {
                taskInterface.onUpgrade(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            task=null;
            if(taskInterface!=null){
                taskInterface.onTaskCancel();
            }
        }

    }
    public boolean getResult(){return  result;}

}
