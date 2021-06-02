package com.example.workoutapp.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workoutapp.LoginRegisterActivity;
import com.example.workoutapp.R;
import com.example.workoutapp.UserController;
import com.example.workoutapp.UserSingleton;
import com.example.workoutapp.ui.chat.ChatListActivity;
import com.example.workoutapp.ui.usermanage.SharedPreferencesController;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private static String emailNotif;


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.requireActivity().setTitle("Perfil");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setShowHideAnimationEnabled(false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

        updateList(root);
        return root;

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button2).setOnClickListener(view1 -> {
            Context context = view1.getContext();
            logOut(context);
            Intent intent = new Intent(context, LoginRegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        view.findViewById(R.id.appCompatButton).setOnClickListener(view12 -> {
            Context context = view12.getContext();
            Intent intent = new Intent(context, SettingsActivity.class);
            context.startActivity(intent);
        });

        view.findViewById(R.id.button).setOnClickListener(v -> NavHostFragment.findNavController(ProfileFragment.this)
                .navigate(R.id.action_navigation_profile_to_favorites_fragment));

        view.findViewById(R.id.appCompatButton3).setOnClickListener(view13 -> {
            Context context = view13.getContext();
            Intent intent = new Intent(context, ChatListActivity.class);
            context.startActivity(intent);
        });
      
        view.findViewById(R.id.my_activities).setOnClickListener(view14 -> NavHostFragment.findNavController(ProfileFragment.this)
                .navigate(R.id.action_navigation_profile_to_my_activities_fragment));

        view.findViewById(R.id.covid_gencat_button).setOnClickListener(v -> {
            String url = "https://web.gencat.cat/es/activem/restriccions-territorials/catalunya/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(Intent.createChooser(intent, "Browse with"));
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    private void updateList(View root) {
        TextView username = root.findViewById(R.id.textView);
        TextView email = root.findViewById(R.id.textView2);
        TextView favorites = root.findViewById(R.id.textView3);
        TextView events = root.findViewById(R.id.textView5);

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

            @Override
            public void onResponseProfile(ArrayList<String> ret) {
                email.append(ret.get(0));
                favorites.append(ret.get(1));
                events.append(ret.get(2));
                emailNotif = ret.get(3);
            }
        });
    }

    public void logOut(Context ctx){
        SharedPreferencesController pref_ctrl = new SharedPreferencesController(ctx);

        String user_act = pref_ctrl.loadUserAct();
        UserSingleton userSingleton = UserSingleton.getInstance();
        userSingleton.destroy();


        pref_ctrl.deletePreferences(user_act);
    }

    public static Boolean getEmailNotif(){
        return emailNotif.equals("true");
    }

    public static void changeEmailNotif(){
        if(emailNotif.equals("true")) emailNotif = "false";
        else emailNotif = "true";
    }
}