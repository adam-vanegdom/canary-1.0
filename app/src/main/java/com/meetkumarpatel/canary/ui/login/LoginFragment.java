package com.meetkumarpatel.canary.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.meetkumarpatel.canary.R;
import com.meetkumarpatel.canary.managers.ApiManager;
import com.meetkumarpatel.canary.managers.AuthManager;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        final TextView textView = root.findViewById(R.id.text_login);
        final Button signUpButton = root.findViewById(R.id.signUp);
        final EditText userInput = root.findViewById(R.id.username);
        final EditText passInput = root.findViewById(R.id.password);

        loginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final AuthManager authManager = new AuthManager();
        final ApiManager apiManager = new ApiManager();
        authManager.initialize();


        if (authManager.signedIn.get() == true) {
            Log.i("AmplifyQuickstart", "SIGNED IN");
        }
        else{
            Log.i("AmplifyQuickstart", "NOT SIGNED IN");
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userInput.getText().toString();
                String password = passInput.getText().toString();

                authManager.logIn(username, password);

                if(authManager.signedIn.get() == true){
                    apiManager.createUser(username, username);
                }
            }
        });

        return root;
    }
}