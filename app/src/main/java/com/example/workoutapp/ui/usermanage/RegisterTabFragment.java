package com.example.workoutapp.ui.usermanage;

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

import com.example.workoutapp.LoginRegisterActivity;
import com.example.workoutapp.R;
import com.example.workoutapp.UserController;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.ArrayList;

public class RegisterTabFragment extends Fragment {

    AppCompatButton register_bt;
    GoogleSignInButton google_bt;
    EditText email_et, username_et, password_et, password_check_et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);


        setLayout(root);

        return root;
    }

    private void setLayout(View root) {
        email_et = root.findViewById(R.id.email);
        username_et = root.findViewById(R.id.username);
        password_et = root.findViewById(R.id.password);
        password_check_et = root.findViewById(R.id.passwordcheck);
        register_bt = root.findViewById(R.id.registerbutton);
        google_bt = root.findViewById(R.id.signupgoogle);
        UserController userController = new UserController(getContext());

        register_bt.setOnClickListener(v -> {
            String emailu = email_et.getText().toString();
            String uname = username_et.getText().toString();
            String pwd = password_et.getText().toString();
            String pwd2 = password_check_et.getText().toString();
            boolean u = checkCorrectFields(emailu, uname, pwd, pwd2, root);

            if (u) {
                userController.register(emailu, uname, pwd, new UserController.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {
                        Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();

                        ((LoginRegisterActivity) getActivity()).setTabLayoutAfterRegister();
                    }

                    @Override
                    public void onResponseProfile(ArrayList<String> ret) {

                    }
                });
            }

        });

        google_bt.setOnClickListener(v -> ((LoginRegisterActivity)getActivity()).signIn());

    }


    private boolean checkCorrectFields(String email, String uname, String pwd, String pwd2, View root) {


        if (email.matches("") || uname.matches("") || pwd.matches("") || pwd2.matches("")){
            Toast.makeText(root.getContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();

            return false;
        } else if (!pwd.equals(pwd2)) {

            Toast.makeText(root.getContext(), "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            password_check_et.getText().clear();

            return false;
        }
        return true;
    }

}
