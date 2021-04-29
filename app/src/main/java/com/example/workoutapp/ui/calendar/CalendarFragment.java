package com.example.workoutapp.ui.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutapp.Activitat;
import com.example.workoutapp.ActivityController;
import com.example.workoutapp.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarFragment extends Fragment {

    CompactCalendarView calendar;
    RecyclerView activityListView;
    TextView emptyView;
    CalendarAdapter adapter;
    List<Activitat> activitatsUsuari;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = (CompactCalendarView) root.findViewById(R.id.calendar);
        activityListView = (RecyclerView) root.findViewById(R.id.calendarActivity);
        emptyView = (TextView) root.findViewById(R.id.empty_view);

        adapter = CalendarAdapter.getInstance(root.getContext(), new ArrayList<>());
        activityListView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        activityListView.setAdapter(adapter);

        calendarIni();
        updateList(root);

        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                displayActivitiesByDate(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //TODO cambiar (y poner) el texto del mes
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


        calendar.setCurrentDayBackgroundColor(Color.CYAN);
        calendar.setCurrentDayIndicatorStyle(CompactCalendarView.FILL_LARGE_INDICATOR);

        calendar.setCurrentSelectedDayBackgroundColor(Color.MAGENTA);
        calendar.setCurrentSelectedDayIndicatorStyle(CompactCalendarView.FILL_LARGE_INDICATOR);

        calendar.setEventIndicatorStyle(CompactCalendarView.SMALL_INDICATOR);
    }

    //date tiene la hora 00:00
    private void displayActivitiesByDate(Date date){
        ArrayList<Activitat> listaAux = new ArrayList<>();

        Calendar dateToBeCompared = Calendar.getInstance();
        dateToBeCompared.setTime(date);

        dateToBeCompared.set(Calendar.HOUR_OF_DAY, 0);
        dateToBeCompared.set(Calendar.MINUTE, 0);
        dateToBeCompared.set(Calendar.SECOND, 0);

        for (Activitat act: activitatsUsuari) {

            Calendar dateAux = Calendar.getInstance();
            dateAux.setTimeInMillis(act.getTimestamp()*1000L); //time in ms
            dateAux.set(Calendar.HOUR_OF_DAY, 0);
            dateAux.set(Calendar.MINUTE, 0);
            dateAux.set(Calendar.SECOND, 0);

            if(dateAux.equals(dateToBeCompared)) listaAux.add(act);
        }

        if (listaAux.isEmpty()) {
            activityListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            adapter.setActivitatsUsuari(listaAux);
            activityListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void setUpEvents() {
        for(Activitat act: activitatsUsuari){
            //TODO cambiar los colores
            Event event = new Event(Color.GREEN, act.getTimestamp()*1000L);
            calendar.addEvent(event);
        }
    }

    //TODO pedir las actividades del usuario, no todas las de la api
    private void updateList(View root) {
        ActivityController dc = new ActivityController(getContext());

        dc.getActivitats(new ActivityController.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseActivity(ArrayList<Activitat> ret) {

                /*just testing here, keep scrolling
                Activitat aux = ret.get(0);
                ret.add(aux);
                ret.add(aux);
                ret.add(aux);
                ret.add(aux);
                ret.add(aux);*/


                activitatsUsuari = ret;
                setUpEvents();
                displayActivitiesByDate(Calendar.getInstance().getTime());
            }

            @Override
            public void onResponseType(ArrayList<String> ret) {

            }

        });
    }


}
