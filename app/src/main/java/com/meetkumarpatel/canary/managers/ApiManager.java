package com.meetkumarpatel.canary.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.amplifyframework.api.ApiOperation;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.SensorReading;
import com.amplifyframework.datastore.generated.model.User;
import com.meetkumarpatel.canary.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ApiManager {
    private Context context;

    public ApiManager(Context context){
        this.context = context;
    }

    public UUID createUser(String user, String email){
        UUID userID = UUID.randomUUID();

        User newUser = User.builder()
                .username(user)
                .email("canary.devteam@gmail.com")
                .id(userID.toString()).build();

        Amplify.API.mutate(
                ModelMutation.create(newUser),
                response -> Log.i("MyAmplifyApp", "Added User with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

        return userID;
    }

    public void getUser(String username){
        Amplify.API.query(
                ModelQuery.list(User.class, User.USERNAME.contains(username)),
                response -> {
                    for (User users : response.getData()) {
                        DataManager.setCurrentUser(users);
                        Log.i("MyAmplifyApp", users.getUsername() + " " + users.getId());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        try {
            //set time in mili
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createReading (double aqi, double voc){
        UUID readingID = UUID.randomUUID();
        Date now = new Date();
        SensorReading newReading = SensorReading.builder()
                .readingId(readingID.toString())
                .userId(DataManager.getCurrentUser().getId())
                .createdAt(new Date().toString())
                .aqi(aqi)
                .voc(voc)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newReading),
                response -> Log.i("MyAmplifyApp", "Added User with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

        try {
            //set time in mili
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getSensorData(String userID){
        Amplify.API.query(
                ModelQuery.list(SensorReading.class, SensorReading.USER_ID.contains(userID)),
                response -> {
                    ArrayList<SensorReading> list = new ArrayList<SensorReading>();
                    for (SensorReading data : response.getData()) {
                        list.add(data);
                        Log.i("MyAmplifyApp", "AQI:" + data.getAqi() + " VOC:" + data.getVoc());
                    }
                    DataManager.addSensorData(list);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        try {
            //set time in mili
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
