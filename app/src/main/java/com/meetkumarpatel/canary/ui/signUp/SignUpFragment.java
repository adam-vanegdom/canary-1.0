package com.meetkumarpatel.canary.ui.signUp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.meetkumarpatel.canary.R;
import com.meetkumarpatel.canary.managers.ApiManager;
import com.meetkumarpatel.canary.managers.AuthManager;

public class SignUpFragment extends Fragment {

    private SignUpViewModel signUpViewModel;
    private AuthManager authManager;
    private ApiManager apiManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signUpViewModel =
                new ViewModelProvider(this).get(SignUpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        final Button signUpButton = root.findViewById(R.id.signUp);
        final EditText userInput = root.findViewById(R.id.username);
        final EditText passInput = root.findViewById(R.id.password);
        final EditText passConfirm = root.findViewById(R.id.passwordConfirm);
        final TextView error = root.findViewById(R.id.error);

        this.authManager = new AuthManager();
        this.apiManager = new ApiManager(getContext());
        this.authManager.initialize();


        if (this.authManager.signedIn.get() == true) {
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
                String passwordConfirm = passConfirm.getText().toString();

                boolean validPassword = authManager.verifyPassword(password, passwordConfirm, error);

                if (validPassword){
                        authManager.createNewUser(username, password, error);
                        apiManager.createUser(username, password);
                        try {
                            //set time in mili
                            Thread.sleep(3000);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        NavController nav = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        nav.navigate(R.id.navigation_login);
                }
            }
        });

        return root;
    }
}