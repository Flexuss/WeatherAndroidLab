package com.example.dmitry.weatherandroidlab.interfaces;

/**
 * Created by Dmitry on 17.11.2016.
 */

public interface TaskInterface {
    void onTaskStart();
    void onUpgrade(int i);
    void onFinish(String s);
    void onTaskCancel();
}
