package com.example.workoutapp.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.workoutapp.LoginRegisterActivity;
import com.example.workoutapp.R;
import com.example.workoutapp.UserController;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    AppCompatButton delete, change_pw;
    Switch email, push, favs, joins;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferencesController spc = new SharedPreferencesController(getContext());

        email = view.findViewById(R.id.email);
        push = view.findViewById(R.id.push);
        favs = view.findViewById(R.id.favs_switch);
        joins = view.findViewById(R.id.joins_switch);

        email.setChecked(ProfileFragment.getEmailNotif());
        push.setChecked(spc.getPush());
        if(!spc.getPush()){
            disablePush(spc);
        }
        else {
            joins.setChecked(spc.getAlertJoins());
            favs.setChecked(spc.getAlertFavs());
        }

        delete = view.findViewById(R.id.eliminar);
        change_pw = view.findViewById(R.id.change);

        change_pw.setOnClickListener(v -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, ChangePasswordActivity.class);
            context.startActivity(intent);
        });

        delete.setOnClickListener(view1 -> deleteUser());

        email.setOnCheckedChangeListener((buttonView, isChecked) -> {

            UserController userController = new UserController(getContext());
            userController.putNofications(isChecked, new UserController.VolleyResponseListener() {
                @Override
                public void onError(String message) {}

                @Override
                public void onResponse(String message) {
                    ProfileFragment.changeEmailNotif();
                    spc.setEmail();
                    email.setChecked(isChecked);
                }

                @Override
                public void onResponseProfile(ArrayList<String> ret) {}
            });

        });

        push.setOnCheckedChangeListener((buttonView, isChecked) -> {
            spc.setPush();
            if(!isChecked){
                disablePush(spc);
            }
            else{
                favs.setEnabled(true);
                joins.setEnabled(true);
            }
        });

        favs.setOnCheckedChangeListener((buttonView, isChecked) -> {
            spc.setAlertFavs();
            favs.setChecked(isChecked);
        });

        joins.setOnCheckedChangeListener((buttonView, isChecked) -> {
            spc.setAlertJoins();
            joins.setChecked(isChecked);
        });
    }

    private void deleteUser(){
        UserController userController = new UserController(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Seguro que quiere eliminar este usuario?");
        builder.setPositiveButton("Confirmar",
                (dialog, which) -> userController.deleteUser(new UserController.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {
                        ProfileFragment pf = new ProfileFragment();
                        pf.logOut(getContext());
                        Context context = getContext();
                        Intent intent = new Intent(context, LoginRegisterActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        assert context != null;
                        context.startActivity(intent);
                    }

                    @Override
                    public void onResponseProfile(ArrayList<String> ret) {

                    }

                }));
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void disablePush(SharedPreferencesController spc){
        favs.setChecked(false);
        favs.setEnabled(false);
        if(spc.getAlertFavs()) spc.setAlertFavs();
        joins.setChecked(false);
        joins.setEnabled(false);
        if(spc.getAlertJoins()) spc.setAlertJoins();
    }
}