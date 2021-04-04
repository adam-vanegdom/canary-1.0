package com.meetkumarpatel.canary.ui.dashboard;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amplifyframework.datastore.generated.model.User;
import com.meetkumarpatel.canary.MainActivity;
import com.meetkumarpatel.canary.R;
import com.meetkumarpatel.canary.managers.ApiManager;
import com.meetkumarpatel.canary.managers.DataManager;

import java.util.Random;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ApiManager apiManager;

    private int aqiReading = 0;
    private int vocReading = 0;
    private ProgressBar aqiProgress;
    private TextView aqiText;
    private TextView alert;
    private Button readingButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        aqiProgress = root.findViewById(R.id.aqiProgress);
        aqiText = root.findViewById(R.id.aqiText);
        readingButton = root.findViewById(R.id.readingButton);
        apiManager = new ApiManager(getContext());
        alert = root.findViewById(R.id.alert);

        updateAQI();

        readingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                aqiReading = rand.nextInt(150);
                vocReading = rand.nextInt(500);
                apiManager.createReading((double)aqiReading, (double)vocReading);
                updateAQI();
            }
        });

        return root;
    }

    private void updateAQI(){
        User currUser = DataManager.getCurrentUser();
        int prog = (int)((aqiReading));

        if (aqiReading > 100){
            alert.setTextColor(Color.parseColor("#da4453"));
            alert.setText("Air Quality: Poor \n Move to clean air");
        }
        else if (aqiReading > 80){
            alert.setTextColor(Color.parseColor("#ffce54"));
            alert.setText("Air Quality: Moderate \nConsider moving to clean air");
        }
        else if (aqiReading > 0){
            alert.setTextColor(Color.parseColor("#8cc152"));
            alert.setText("Air Quality: Good \n Breathe happy");
        }
        else{
            alert.setText("");
        }
        aqiProgress.setProgress(prog);
        aqiText.setText(String.format("AQI: %2s", aqiReading));
    }
}