package com.example.workoutapp.ui.calendar;

import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.Chat;
import com.example.workoutapp.Organizer;
import com.example.workoutapp.R;
import com.example.workoutapp.Review;
import com.example.workoutapp.UserActivityController;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CalendarFragment extends Fragment {

    CompactCalendarView calendar;
    RecyclerView activityListView;
    TextView emptyView;
    TextView monthText;
    CalendarAdapter adapter;
    List<Activitat> activitatsUsuari;
    DateFormatSymbols mesesEnEsp;
    Date selectedDate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requireActivity().setTitle("Calendario");
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

        mesesEnEsp = DateFormatSymbols.getInstance(new Locale("es", "ar"));
        mesesEnEsp.setMonths(new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"});
        mesesEnEsp.setShortMonths(new String[]{"Ene", "Feb", "Mar", "May", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"});

        selectedDate = Calendar.getInstance().getTime();

        calendar = root.findViewById(R.id.calendar);
        activityListView = root.findViewById(R.id.calendarActivity);
        emptyView = root.findViewById(R.id.empty_view);
        monthText = root.findViewById(R.id.month_text);

        adapter = CalendarAdapter.getInstance(root.getContext(), new ArrayList<>());
        activityListView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        activityListView.setAdapter(adapter);

        calendarIni();
        updateList();


        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectedDate = dateClicked;
                displayActivitiesByDate();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String a = new SimpleDateFormat("MMMM - yyyy", mesesEnEsp).format(firstDayOfNewMonth);
                monthText.setText(a);
                selectedDate = firstDayOfNewMonth;
                displayActivitiesByDate();
            }
        });

        return root;
    }

    private void calendarIni() {

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setUseThreeLetterAbbreviation(false);
        calendar.shouldScrollMonth(true);
        calendar.shouldDrawIndicatorsBelowSelectedDays(true);
        calendar.setCurrentDate(Calendar.getInstance().getTime());
        calendar.shouldDrawIndicatorsBelowSelectedDays(true);
        calendar.setDayColumnNames(new String[]{"L", "M", "X", "J", "V", "S", "D"});

        calendar.setCurrentDayBackgroundColor(Color.rgb(0xac, 0xb4, 0xdb));
        calendar.setCurrentDayIndicatorStyle(CompactCalendarView.FILL_LARGE_INDICATOR);

        calendar.setCurrentSelectedDayBackgroundColor(Color.argb(81,110,180,255));
        calendar.setCurrentSelectedDayIndicatorStyle(CompactCalendarView.FILL_LARGE_INDICATOR);

        //calendar.setEventIndicatorStyle(CompactCalendarView.SMALL_INDICATOR);
        calendar.setEventIndicatorStyle(CompactCalendarView.FILL_LARGE_INDICATOR);

        String a = new SimpleDateFormat("MMMM - yyyy", mesesEnEsp).format(Calendar.getInstance().getTime());
        monthText.setText(a);
    }

    //date tiene la hora 00:00
    private void displayActivitiesByDate() {

        if(activitatsUsuari == null) return;

        ArrayList<Activitat> listaAux = new ArrayList<>();

        Calendar dateToBeCompared = Calendar.getInstance();
        dateToBeCompared.setTime(selectedDate);

        dateToBeCompared.set(Calendar.HOUR_OF_DAY, 0);
        dateToBeCompared.set(Calendar.MINUTE, 0);
        dateToBeCompared.set(Calendar.SECOND, 0);

        for (Activitat act : activitatsUsuari) {

            Calendar dateAux = Calendar.getInstance();
            dateAux.setTimeInMillis(act.getTimestamp() * 1000L); //time in ms
            dateAux.set(Calendar.HOUR_OF_DAY, 0);
            dateAux.set(Calendar.MINUTE, 0);
            dateAux.set(Calendar.SECOND, 0);

            if (dateAux.equals(dateToBeCompared)) listaAux.add(act);
        }

        if (listaAux.isEmpty()) {
            activityListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter.setActivitatsUsuari(listaAux);
            activityListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void setUpEvents() {
        calendar.removeAllEvents();
        for (Activitat act : activitatsUsuari) {
            Event event = new Event(Color.argb(192, 80, 128, 255), act.getTimestamp() * 1000L);
            calendar.addEvent(event);
        }
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
                activitatsUsuari = new ArrayList<Activitat>();

                for(Activitat act : ret)
                    if(act.getTimestamp() * 1000L >= Calendar.getInstance().getTimeInMillis())
                        activitatsUsuari.add(act);

                setUpEvents();
                displayActivitiesByDate();
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

    @Override
    public void onResume() {
       super.onResume();
       displayActivitiesByDate();
       updateList();
    }
}
