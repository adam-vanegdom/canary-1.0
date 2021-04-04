package com.meetkumarpatel.canary.ui.history;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.datastore.generated.model.SensorReading;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.meetkumarpatel.canary.R;
import com.meetkumarpatel.canary.managers.ApiManager;
import com.meetkumarpatel.canary.managers.DataManager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLEngine;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private LineChart aqiChart;
    private LineChart vocChart;
    private ApiManager apiManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        apiManager = new ApiManager(getContext());
        aqiChart = (LineChart) root.findViewById(R.id.aqiChart);
        vocChart = (LineChart) root.findViewById(R.id.vocChart);

        OnStart();

        return root;
    }

    public void OnStart(){
        apiManager.getSensorData(DataManager.getCurrentUser().getId());

        ArrayList<SensorReading> resp = DataManager.getSensorData();
        ArrayList<SensorReading> list = sortData(resp);

        List<Entry> aqiEntries = new ArrayList<Entry>();
        List<Entry> vocEntries = new ArrayList<Entry>();
        String[] dates = new String[list.size()];

        int i = 0;
        for (SensorReading data : list){
            aqiEntries.add(new Entry(i, data.getAqi().floatValue()));
            vocEntries.add(new Entry(i, data.getVoc().floatValue()));
            i++;
        }
        formatPlot(aqiChart, 100f);
        formatPlot(vocChart, 100f);

        LineDataSet aqiDataSet = new LineDataSet(aqiEntries, "AQI");
        LineDataSet vocDataSet = new LineDataSet(vocEntries, "VOC");
        aqiDataSet.setValueTextSize(15f);
        vocDataSet.setValueTextSize(15f);

        LineData aqiData = new LineData(aqiDataSet);
        LineData vocData = new LineData(vocDataSet);

        aqiChart.setData(aqiData);
        aqiChart.invalidate(); // refresh

        vocChart.setData(vocData);
        vocChart.invalidate(); // refresh
    }

    public void formatPlot (LineChart chart, float limit){
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        YAxis rAxis = chart.getAxisRight();
        rAxis.setEnabled(false);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setTextSize(15f);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(false);

        Description description = chart.getDescription();
        description.setEnabled(false);

        LimitLine ll = new LimitLine(limit); // set where the line should be drawn
        ll.setLineColor(Color.MAGENTA);
        ll.setLabel("Safe Threshold");
        ll.setLineWidth(1f);

        yAxis.addLimitLine(ll);
    }

    public ArrayList<SensorReading> sortData(ArrayList<SensorReading> list){
        Collections.sort(list, new Comparator<SensorReading>() {
            @Override
            public int compare(SensorReading o1, SensorReading o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        return list;
    }
}