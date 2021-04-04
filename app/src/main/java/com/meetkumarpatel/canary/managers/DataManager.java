package com.meetkumarpatel.canary.managers;

import android.hardware.Sensor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amplifyframework.datastore.generated.model.SensorReading;
import com.amplifyframework.datastore.generated.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static User currentUser;
    private static ArrayList<SensorReading> sensorData;

    public static void setCurrentUser(User user){
        currentUser = user;
    }
    public static User getCurrentUser(){
        return currentUser;
    }

    public static void addSensorData( ArrayList<SensorReading> reading) { sensorData = reading;}
    public static ArrayList<SensorReading> getSensorData () { return sensorData; }
}
