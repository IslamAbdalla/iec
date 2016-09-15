package com.example.islam.iec;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestEvents extends Fragment {

    private RecyclerView latestEventsRecyclerView;
    private RecyclerView.Adapter latestEventsAdapter;
    private RecyclerView.LayoutManager latestEventsLayoutManager;


    public LatestEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latest_events, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // ------------- End of Recycler ActivityRecycler Activity

        latestEventsRecyclerView = (RecyclerView) getView().findViewById(R.id.latest_events_rec_view);
        ArrayList<Event> latestEvents = new ArrayList<>();
//        ArrayList<String> oldLatestEvents = new ArrayList<>();

//        oldLatestEvents.add("Startup Weekend Khartoum");
//        oldLatestEvents.add("Startup Weekend Medani");
//        oldLatestEvents.add("Startup Grind");

        latestEvents.add(new Event("Startup Weekend Khartoum",
                                    new Location(""),
                                    "Khartoum, Spark city",
                                    new Date(),
                                    "No description",
                                    R.drawable.startup_weekend_khartoum,
                                    true
                                    ));

        latestEvents.add(new Event("Separator"));

        latestEvents.add(new Event("Startup Weekend Medani",
                                    new Location(""),
                                    "Medani, Hantoob",
                                    new Date(),
                                    "No description",
                                    R.drawable.startup_weekend_khartoum,
                                    false
                                    ));

        latestEvents.add(new Event("Startup Grind",
                                    new Location(""),
                                    "Bahri, Cooper",
                                    new Date(),
                                    "No description",
                                    R.drawable.startup_weekend_khartoum,
                                    false
                                    ));

        latestEventsRecyclerView.setHasFixedSize(true);

        // Use linear layout manager
        latestEventsLayoutManager = new LinearLayoutManager(getActivity());
        latestEventsRecyclerView.setLayoutManager(latestEventsLayoutManager);

        // specify an adapter
        latestEventsAdapter = new LatestEventsAdapter(latestEvents);
        latestEventsRecyclerView.setAdapter(latestEventsAdapter);


        // ------------- End of Recycler Activity
    }

}
