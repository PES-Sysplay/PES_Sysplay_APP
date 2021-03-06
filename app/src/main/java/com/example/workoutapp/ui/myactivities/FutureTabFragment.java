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

public class FutureTabFragment extends Fragment {

    private RecyclerView recyclerViewFut;
    private FutureActAdapter adapter;
    private Boolean advancedSearch = false;

    ViewGroup root;
    ArrayList<Activitat> futActivities;
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
        root = (ViewGroup) inflater.inflate(R.layout.fragment_act_future, container, false);

        recyclerViewFut = root.findViewById(R.id.recyclerviewfut);

        emptyView = root.findViewById(R.id.empty_view);

        updateType(root);

        adapter = FutureActAdapter.getInstance(root.getContext(), new ArrayList<>());
        recyclerViewFut.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewFut.setAdapter(adapter);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.toolbar_seach_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
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
            public void onResponseJoinedActivities(ArrayList<Activitat> ret) {
                futActivities = ret;
                displayFutAct();
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

    private void displayFutAct() {
        ArrayList<Activitat> futAux = new ArrayList<>();

        if (futActivities != null){
            for (Activitat act : futActivities) {
                if (!act.isOld()) futAux.add(act);
            }
        }

        if (futAux.isEmpty()) {
            recyclerViewFut.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter.setList(futAux);
            recyclerViewFut.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}
