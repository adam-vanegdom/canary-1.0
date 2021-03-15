package com.meetkumarpatel.canary.managers;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class CloudManager {
    public void initialize(Context context){
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(context);
            Log.i("CanaryCloud", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("CanaryCloud", "Could not initialize Amplify", error);
        }
    }
}
