package com.example.workoutapp.ui.myactivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OldTabFragment extends Fragment {

    private RecyclerView recyclerViewOld;
    private OldActAdapter adapter;
    private Boolean advancedSearch = false;
    private SearchView searchView;
    int intents = 0, poss = -33;

    ViewGroup root;
    ArrayList<Activitat> oldActivities;
    TextView emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_act_old, container, false);

        recyclerViewOld = root.findViewById(R.id.recyclerviewold);

        emptyView = root.findViewById(R.id.empty_view);

        updateType(root);

        adapter = OldActAdapter.getInstance(root.getContext(), new ArrayList<>());
        recyclerViewOld.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewOld.setAdapter(adapter);

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

    private String transformQueryFormat(String title, String org, String sport) {
        //String sport es el nombre, no el codigo

        return "StartTitle" + title + "EndTitle" +
                "StartOrganization" + org + "EndOrganization" +
                "StartSport" + sport + "EndSport";
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        UserActivityController uc = new UserActivityController(getContext());

        uc.getJoinedActivities(new UserActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String message) {

            }

            @Override
            public void onResponseFavorites(ArrayList<Activitat> ret) {

            }

            @Override
            public void onResponseFav() {

            }

            @Override
            public void onResponseJoinedActivites(ArrayList<Activitat> ret) {
                oldActivities = ret;
                displayOldAct();
            }

            @Override
            public void onResponseReviewList(ArrayList<Review> ret) {

            }

            @Override
            public void onResponseOrganizationList(ArrayList<Organizer> ret) {

            }

            @Override
            public void onResponseChat(ArrayList<Chat> ret) {

            }

            @Override
            public void onResponseReportReview() {

            }
        });
    }

    public void updateType(View root) {
        ActivityController dc = new ActivityController(getContext());

        dc.getActivityTypes(new ActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseActivity(ArrayList<Activitat> ret) {
            }

            @Override
            public void onResponseType(ArrayList<String> ret) {
                adapter.setActivity_id_list(ret);
            }

            @Override
            public void onResponseJoinActivity() {

            }

        });
    }

    private void displayOldAct() {
        ArrayList<Activitat> oldAux = new ArrayList<>();

        if (oldActivities != null){
            for (Activitat act : oldActivities) {
                if (act.isOld()) oldAux.add(act);
            }
            if (oldAux.isEmpty()) {
                recyclerViewOld.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
            else {
                adapter.setList(oldAux);
                recyclerViewOld.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        }
    }
}
