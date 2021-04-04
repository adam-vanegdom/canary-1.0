package com.meetkumarpatel.canary.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> pm1Data;
    private MutableLiveData<String> pm25Data;
    private MutableLiveData<String> pm10Data;
    private MutableLiveData<String> vocData;
    private MutableLiveData<String> gasData;
    private MutableLiveData<String> temperatureData;
    private MutableLiveData<String> humidityData;
    private MutableLiveData<String> pmAqiData;
    private MutableLiveData<String> gasAqiData;

    private final String pmUnit = " µg/m3";
    private final String vocUnit = "";
    private final String gasUnit = " ppb";
    private final String temperatureUnit = " °C";
    private final String humidityUnit = " %";
    private final String aqiUnit = "";

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

        pm1Data = new MutableLiveData<>();
        pm25Data = new MutableLiveData<>();
        pm10Data = new MutableLiveData<>();
        vocData = new MutableLiveData<>();
        gasData = new MutableLiveData<>();
        temperatureData = new MutableLiveData<>();
        humidityData = new MutableLiveData<>();
        pmAqiData = new MutableLiveData<>();
        gasAqiData = new MutableLiveData<>();

        pm1Data.setValue("0" + pmUnit);
        pm25Data.setValue("0" + pmUnit);
        pm10Data.setValue("0" + pmUnit);
        vocData.setValue("0" + vocUnit);
        gasData.setValue("0" + gasUnit);
        temperatureData.setValue("0" + temperatureUnit);
        humidityData.setValue("0" + humidityUnit);
        pmAqiData.setValue("0" + aqiUnit);
        gasAqiData.setValue("0" + aqiUnit);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getPm1Data() {
        return pm1Data;
    }

    public LiveData<String> getPm25Data() {
        return pm25Data;
    }

    public LiveData<String> getPm10Data() {
        return pm10Data;
    }

    public LiveData<String> getVocData() {
        return vocData;
    }

    public LiveData<String> getGasData() {
        return gasData;
    }

    public LiveData<String> getTemperatureData() {
        return temperatureData;
    }

    public LiveData<String> getHumidityData() {
        return humidityData;
    }

    public LiveData<String> getPmAqiData() {
        return pmAqiData;
    }

    public LiveData<String> getGasAqiData() {
        return gasAqiData;
    }

    public void setPm1Data(String pm1Data) {
        this.pm1Data.postValue(pm1Data + pmUnit);
    }

    public void setPm25Data(String pm25Data) {
        this.pm25Data.postValue(pm25Data + pmUnit);
    }

    public void setPm10Data(String pm10Data) {
        this.pm10Data.postValue(pm10Data + pmUnit);
    }

    public void setVocData(String vocData) {
        this.vocData.postValue(vocData + vocUnit);
    }

    public void setGasData(String gasData) {
        this.gasData.postValue(gasData + gasUnit);
    }

    public void setTemperatureData(String temperatureData) {
        this.temperatureData.postValue(temperatureData + temperatureUnit);
    }

    public void setHumidityData(String humidityData) {
        this.humidityData.postValue(humidityData + humidityUnit);
    }

    public void setPmAqiData(String pmAqiData) {
        this.pmAqiData.postValue(pmAqiData + aqiUnit);
    }

    public void setGasAqiData(String gasAqiData) {
        this.gasAqiData.postValue(gasAqiData + aqiUnit);
    }
}