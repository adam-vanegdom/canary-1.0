package com.meetkumarpatel.canary.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.meetkumarpatel.canary.R;
import com.meetkumarpatel.canary.managers.ApiManager;
import com.meetkumarpatel.canary.managers.AuthManager;

import java.util.UUID;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private AuthManager authManager;
    private ApiManager apiManager;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        this.authManager = new AuthManager();
        this.apiManager = new ApiManager(getContext());

        final Button loginButton = root.findViewById(R.id.loginButton);
        final Button signUpButton = root.findViewById(R.id.newSignUp);
        final EditText userLogin = root.findViewById(R.id.userLogin);
        final EditText passLogin = root.findViewById(R.id.passLogin);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                nav.navigate(R.id.navigation_signup);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userLogin.getText().toString();
                String password = passLogin.getText().toString();
                authManager.login(username, password);
                apiManager.getUser(username);

                NavController nav = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                nav.navigate(R.id.navigation_dashboard);
            }
        });

        return root;
    }
}