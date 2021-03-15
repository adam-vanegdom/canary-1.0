package com.meetkumarpatel.canary.managers;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;

import java.util.UUID;

public class ApiManager {
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
}
