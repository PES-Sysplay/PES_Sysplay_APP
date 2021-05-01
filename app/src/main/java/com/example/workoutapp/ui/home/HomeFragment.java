package com.example.workoutapp.ui.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityListAdapter adapter;
    private Boolean advancedSearch = false;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerview);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setShowHideAnimationEnabled(false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

        updateList(root);
        updateType(root);

        adapter = ActivityListAdapter.getInstance(root.getContext(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.toolbar_seach_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setIconifiedByDefault(false);

        menu.findItem(R.id.advanced_search).setVisible(advancedSearch); //hides and shows the advanced search button

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(transformQueryFormat(newText, "", ""));
                return false;
            }
        });

        //sets up listeners to detect when to hide and show the advanced search button
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                advancedSearch = true;
                getActivity().invalidateOptionsMenu();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                advancedSearch = false;
                getActivity().invalidateOptionsMenu();
                return true;
            }
        });
    }

    private void updateList(View root) {
        ActivityController dc = new ActivityController(getContext());

        dc.getActivitats(new ActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseJoinedOrLeft(String message) {
            }

            @Override
            public void onResponseActivity(ArrayList<Activitat> ret) {
                adapter.setList(ret);
                Log.d("STATE", ret.get(0).getDate_time());
                // do things
            }

            @Override
            public void onResponseType(ArrayList<String> ret) {
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.advanced_search) {
            showAdvancedSearchDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAdvancedSearchDialog() {
        LayoutInflater linf = LayoutInflater.from(getContext());
        final View inflator = linf.inflate(R.layout.advanced_search_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle("Advanced Search");
        dialog.setView(inflator);

        final EditText title = inflator.findViewById(R.id.advanced_title);
        final EditText organization = inflator.findViewById(R.id.advanced_org);
        final Spinner sport = inflator.findViewById(R.id.advanced_sport);

        ArrayList<String> types = adapter.getActivity_id_list();
        types.add(0, "Any sport");
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, types);
        sport.setAdapter(stringAdapter);

        dialog.setPositiveButton("Search!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String sTitle = title.getText().toString();
                String sOrg = organization.getText().toString();
                String sSport = sport.getSelectedItem().toString();

                if (sSport.equals("Any sport")) sSport = "";

                adapter.getFilter().filter(transformQueryFormat(sTitle, sOrg, sSport));
                //searchView.setIconified(true);
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                searchView.setQuery("", true);
                searchView.clearFocus();
                dialog.cancel();
                //searchView.setIconified(true);
            }
        });

        dialog.show();
    }

    private String transformQueryFormat(String title, String org, String sport) {
        //String sport es el nombre, no el codigo

        return "StartTitle" + title + "EndTitle" +
                "StartOrganization" + org + "EndOrganization" +
                "StartSport" + sport + "EndSport";
    }

    private void updateType(View root) {
        ActivityController dc = new ActivityController(getContext());

        dc.getActivityTypes(new ActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseJoinedOrLeft(String message) {
            }

            @Override
            public void onResponseActivity(ArrayList<Activitat> ret) {
            }

            @Override
            public void onResponseType(ArrayList<String> ret) {
                adapter.setActivity_id_list(ret);
            }

        });
    }

}