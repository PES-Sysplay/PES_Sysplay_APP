package com.example.workoutapp.ui.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
    AppCompatButton delete;
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                UserController userController = new UserController(getContext());
                String checked;
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

            }
        });

        push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spc.setPush();
                if(!isChecked){
                    disablePush(spc);
                }
                else{
                    favs.setEnabled(true);
                    joins.setEnabled(true);
                }
            }
        });

        favs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spc.setAlertFavs();
                favs.setChecked(isChecked);
            }
        });

        joins.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spc.setAlertJoins();
                joins.setChecked(isChecked);
            }
        });
    }

    private void deleteUser(){
        UserController userController = new UserController(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Seguro que quiere eliminar este usuario?");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userController.deleteUser(new UserController.VolleyResponseListener() {
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
                                context.startActivity(intent);
                            }

                            @Override
                            public void onResponseProfile(ArrayList<String> ret) {

                            }

                        });
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
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