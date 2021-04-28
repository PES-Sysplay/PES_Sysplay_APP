package com.example.workoutapp.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutapp.LoginRegisterActivity;
import com.example.workoutapp.R;
import com.example.workoutapp.UserController;
import com.example.workoutapp.UserSingleton;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);
        TextView username = root.findViewById(R.id.textView);
        TextView email = root.findViewById(R.id.textView2);

        username.append(UserSingleton.getInstance().getUsername());

        UserController userController = new UserController(getContext());
        userController.getProfile(new UserController.VolleyResponseListener() {
            @Override
        public void onError(String message) {
            Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String message) {
            email.append(message);
        }
    });




        return root;


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.appCompatButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ChangePasswordActivity.class);
                context.startActivity(intent);
            }
        });

        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                logOut(context);
                Intent intent = new Intent(context, LoginRegisterActivity.class);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    public void logOut(Context ctx){
        SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);

        String user_act = pref_ctrl.loadUserAct();

        pref_ctrl.deletePreferences(user_act);
    }

}