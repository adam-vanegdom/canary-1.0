package com.meetkumarpatel.canary.managers;

import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.android.play.core.tasks.Tasks.await;

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

    private String signUp(String email, String password){
        final AtomicReference<String> response = new AtomicReference<>();
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), "canary.devteam@gmail.com")
                .build();
        Amplify.Auth.signUp(email, password, options,
                result -> {
                    Log.i("AuthQuickStart", "Result: " + result.toString());

                },
                error ->  {
                    Log.e("AuthQuickStart", "Sign up failed", error);
                }
        );
        return response.get();
    }

    public void login(String username, String password){
        Amplify.Auth.signIn(
                username,
                password,
                result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }

    public boolean verifyPassword(String pass1, String pass2, TextView error){
        boolean status = false;

        Log.i("MyAmplifyApp", pass1 +" " +pass2);

        if (!(pass1.equals(pass2))){
            error.setText("Passwords do not match");
        }
        else if(pass1.length() < 8){
            error.setText("Password must be at least 8 characters");
        }
        else {
            status = true;
        }

        return status;
    }

    public boolean createNewUser(String username, String password, TextView error) {
        try{
            String resp = signUp(username, password);
            error.setText(resp);
            return false;
        }
        catch (Exception e){
            error.setText("Account already exists, please login instead");
            return false;
        }
    }
}


