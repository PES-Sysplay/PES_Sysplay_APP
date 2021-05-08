package com.example.workoutapp.ui.usermanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.LoginRegisterActivity;
import com.example.workoutapp.MainActivity;
import com.example.workoutapp.R;
import com.example.workoutapp.UserController;
import com.example.workoutapp.UserSingleton;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.ArrayList;

public class LoginTabFragment extends Fragment {

    AppCompatButton login_bt;
    EditText username_et, password_et;
    GoogleSignInButton google_bt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        UserController userController = new UserController(getContext());

        username_et = root.findViewById(R.id.log_username);
        password_et = root.findViewById(R.id.log_password);
        login_bt = root.findViewById(R.id.loginbutton);
        google_bt = root.findViewById(R.id.signingoogle);

        login_bt.setOnClickListener(v -> {
            String username = username_et.getText().toString();
            String password = password_et.getText().toString();

            userController.logIn(username, password, new UserController.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String message) {
                    provaActivitat(root.getContext());
                }
            });
        });

        google_bt.setOnClickListener(v -> {
            ((LoginRegisterActivity)getActivity()).signIn();
        });

        return root;
    }


    private void provaActivitat(Context ctx) {
        ActivityController activityController = new ActivityController(ctx);

        activityController.dummyCall(new ActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseActivity(ArrayList<Activitat> ret) {
                //haha lol
            }

            @Override
            public void onResponseType(ArrayList<String> ret) {
                if (ret.size() != 0){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
}
