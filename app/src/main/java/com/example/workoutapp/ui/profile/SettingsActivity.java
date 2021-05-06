package com.example.workoutapp.ui.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.workoutapp.LoginRegisterActivity;
import com.example.workoutapp.MainActivity;
import com.example.workoutapp.R;
import com.example.workoutapp.UserController;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }



    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            PreferenceManager.setDefaultValues(getContext(), R.xml.settings_preferences, true);
            setPreferencesFromResource(R.xml.settings_preferences, rootKey);

            Preference button = getPreferenceManager().findPreference(getString(R.string.delete_button));
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
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
                                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onResponseProfile(ArrayList<String> ret) {

                                        }
                                    });
                                    ProfileFragment pf = new ProfileFragment();
                                    pf.logOut(getContext());
                                    Context context = getContext();
                                    Intent intent = new Intent(context, LoginRegisterActivity.class);
                                    context.startActivity(intent);
                                }
                            });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
            });
        }
    }
}