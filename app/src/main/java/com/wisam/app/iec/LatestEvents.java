package com.wisam.app.iec;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestEvents extends Fragment {

    private RecyclerView latestEventsRecyclerView;
    private LatestEventsAdapter latestEventsAdapter;
    private RecyclerView.LayoutManager latestEventsLayoutManager;
    private ArrayList<Event> latestEventsList;
    private PrefManager prefManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    public LatestEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        new WebDownloaderTask(this, WebDownloaderTask.GET_EVENTS).execute("http://www.test.com/index.html");
        return inflater.inflate(R.layout.fragment_latest_events, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        latestEventsRecyclerView = (RecyclerView) getView().findViewById(R.id.latest_events_rec_view);
        latestEventsRecyclerView.setHasFixedSize(true);
        latestEventsList = new ArrayList<>();

        prefManager = new PrefManager(getContext());

        Gson gson = new Gson();
        String json = prefManager.getEventsList();
        latestEventsList = gson.fromJson(json, new TypeToken<ArrayList<Event>>(){}.getType());

//        latestEventsList.add(new Event("Startup Weekend Khartoum",
//                                    "53",
//                                    "Khartoum, Spark city",
//                                    "Khartoum, Spark city",
//                                    "2 Oct 2016",
//                                    "No description",
//                                    "",
//                                    true
//                                    ));
//
//        latestEventsList.add(new Event("Startup Weekend Khartoum",
//                                    "55",
//                                    "Khartoum, Spark city",
//                                    "Khartoum, Spark city",
//                                    "2 Oct 2016",
//                                    "No description",
//                                    "",
//                                    true
//                                    ));
//
//        latestEventsList.add(new Event("Startup Weekend Khartoum",
//                                    "56",
//                                    "Khartoum, Spark city",
//                                    "Khartoum, Spark city",
//                                    "2 Oct 2016",
//                                    "No description",
//                                    "",
//                                    true
//                                    ));
//        latestEventsList.add(new Event("Separator"));
//
//        latestEventsList.add(new Event("Startup Weekend Medani",
//                                    "55",
//                                    "Medani, Hantoob",
//                                    "Medani, Hantoob",
//                                    "2 Oct 2016",
//                                    "No description",
//                                    "",
//                                    false
//                                    ));
//
//        latestEventsList.add(new Event("Startup Grind",
//                                    "58",
//                                    "Bahri, Cooper",
//                                    "Bahri, Cooper",
//                                    "2 Oct 2016",
//                                    "No description",
//                                    "",
//                                    false
//                                    ));
//
//        latestEventsList.add(new Event("Startup Grind",
//                                    "58",
//                                    "Bahri, Cooper",
//                                    "Bahri, Cooper",
//                                    "2 Oct 2016",
//                                    "No description",
//                                    "",
//                                    false
//                                    ));
//
//        latestEventsList.add(new Event("Startup Grind",
//                                    "58",
//                                    "Bahri, Cooper",
//                                    "Bahri, Cooper",
//                                    "2 Oct 2016",
//                                    "No description",
//                                    "",
//                                    false
//                                    ));

        // Use linear layout manager
        latestEventsLayoutManager = new LinearLayoutManager(getActivity());
        latestEventsRecyclerView.setLayoutManager(latestEventsLayoutManager);

        // specify an adapter
        latestEventsAdapter = new LatestEventsAdapter(getActivity(), latestEventsList);
        latestEventsRecyclerView.setAdapter(latestEventsAdapter);

        // ======== Swipe Refresh ============= //
        setupSwipeRefresh();
    }

    public void setEvents(ArrayList<Event> latestEvents) {
        Log.i("IEC", "setEvents: Set");
        latestEventsAdapter.updateDataSet(latestEvents);
        latestEventsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        Log.d("Main", "onRefresh: stopped Refresh animation");

    }

    public void clearEvents(){
        Log.i("IEC", "clearEvents: Cleared first called");
        latestEventsAdapter.clearEvents();
    }

    public void redrawEvents(){
        latestEventsRecyclerView.setAdapter(null);
        latestEventsRecyclerView.setLayoutManager(null);
        latestEventsRecyclerView.setAdapter(latestEventsAdapter);
        latestEventsRecyclerView.setLayoutManager(latestEventsLayoutManager);
        latestEventsAdapter.notifyDataSetChanged();
    }

    public void setBooked(String eventID) {
        Log.i("IEC", "setBooked:");
        int position = getEventPosition(eventID);
        Log.i("IEC", "setBooked: Position: " + position);
        if (position != -1){
            View view = latestEventsLayoutManager.findViewByPosition(position);
            Button bookButton = (Button) view.findViewById(R.id.btn_book);
            if (bookButton != null) {
                bookButton.setBackgroundColor(getActivity().getResources().getColor(R.color.textGray));
                bookButton.setText("Booked");
                bookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Event is already booked", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


    private int getEventPosition(String eventID) {
        Log.i("IEC", "getEventPosition: eventID: " + eventID);

        Gson gson = new Gson();
        String json = prefManager.getEventsList();
        latestEventsList = gson.fromJson(json, new TypeToken<ArrayList<Event>>(){}.getType());

        for (int i = 0; i < latestEventsList.size(); i++) {
            Event event = latestEventsList.get(i);
        Log.i("IEC", "getEventPosition: ID: " + event.getId());
            if (event.getId().equals(eventID))
                return i;
        }
        return -1;
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    Log.d("Main", "onRefresh: called");
                    new WebDownloaderTask(LatestEvents.this, WebDownloaderTask.GET_EVENTS).execute("http://www.test.com/index.html");
                }
            });
        }
    }


}
