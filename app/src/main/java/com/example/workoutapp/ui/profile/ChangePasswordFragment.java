package com.example.workoutapp.ui.profile;

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
import androidx.navigation.fragment.NavHostFragment;

import com.example.workoutapp.MainActivity;
import com.example.workoutapp.R;
import com.example.workoutapp.UserController;

public class ChangePasswordFragment extends Fragment {

    AppCompatButton changepw_bt;
    EditText oldpw_et, newpw_et, newpw2_et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_resetpassword, container, false);
        UserController userController = new UserController(getContext());

        oldpw_et = root.findViewById(R.id.old_password);
        newpw_et = root.findViewById(R.id.new_password);
        newpw2_et = root.findViewById(R.id.new_password2);
        changepw_bt = root.findViewById(R.id.actualizarcontraseña);

        changepw_bt.setOnClickListener(v -> {
            String oldpass = oldpw_et.getText().toString();
            String newpass = newpw_et.getText().toString();
            String newpass2 = newpw2_et.getText().toString();

            Boolean isCorrect = checkCorrectFields(oldpass, newpass, newpass2, root);

            if(isCorrect) {

                userController.changePassword(oldpass, newpass, new UserController.VolleyResponseListener(){
                @Override
                public void onError(String message) {
                    Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String message) {
                    Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });

                NavHostFragment.findNavController(ChangePasswordFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        return root;
    }

    private boolean checkCorrectFields(String oldpass, String pass, String pass2, View root) {


        if (oldpass.matches("") || pass.matches("") || pass.matches("")){
            Toast.makeText(root.getContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();

            return false;
        } else if (!pass.equals(pass2)) {

            Toast.makeText(root.getContext(), "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            newpw2_et.getText().clear();

            return false;
        }
        return true;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}