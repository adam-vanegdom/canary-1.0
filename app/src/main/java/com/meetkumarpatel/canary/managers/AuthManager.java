package com.meetkumarpatel.canary.managers;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;

import java.util.concurrent.atomic.AtomicBoolean;

public class AuthManager {
    public AtomicBoolean signedIn;

    public void initialize(){
        this.signedIn = new AtomicBoolean(false);

        Amplify.Auth.fetchAuthSession(
                result -> {
                    this.signedIn.set(result.isSignedIn());
                    Log.i("AmplifyQuickstart", result.toString());
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );
    }

    public boolean signUp(String email, String password){
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), "canary.devteam@gmail.com")
                .build();
        Amplify.Auth.signUp(email, password, options,
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );

        return true;
    }

    public void logIn(String username, String password){
        Amplify.Auth.signIn(
                username,
                password,
                result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }
}


