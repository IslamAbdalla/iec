package com.wisam.app.iec;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyEvents extends Fragment {

    private RecyclerView myEventsRecyclerView;
    private EventTicketsAdapter myEventsAdapter;
    private RecyclerView.LayoutManager myEventsLayoutManager;
    public ArrayList<EventTicket> myEventsList;
    private PrefManager prefManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MyEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_events, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myEventsRecyclerView = (RecyclerView) getView().findViewById(R.id.my_events_rec_view);
        myEventsRecyclerView.setHasFixedSize(true);

        myEventsList = new ArrayList<>();

        prefManager = new PrefManager(getContext());
        Gson gson = new Gson();
        String json = prefManager.getTicketsList();

        if (prefManager.isLoggedIn() ){

            myEventsList = gson.fromJson(json, new TypeToken<ArrayList<EventTicket>>(){}.getType());
            if (myEventsList.isEmpty()) {
                showNoTicketsIndicator();
            }
            new WebDownloaderTask(this, WebDownloaderTask.GET_TICKETS).execute("http://www.test.com/index.html");
        } else {
            showNoTicketsIndicator();
        }


        // Use linear layout manager
        myEventsLayoutManager = new LinearLayoutManager(getActivity());
        myEventsRecyclerView.setLayoutManager(myEventsLayoutManager);

        // specify an adapter (See also next example)
        myEventsAdapter = new EventTicketsAdapter(getActivity(), myEventsList);
        myEventsRecyclerView.setAdapter(myEventsAdapter);

        prefManager = new PrefManager(getContext());


        setupSwipeRefresh();

    }

    public void hideNoTicketsIndicator(){
        TextView textView = (TextView) getView().findViewById(R.id.no_tickets_indicator);
        textView.setVisibility(View.INVISIBLE);
    }
    public void showNoTicketsIndicator(){
        Log.d("Tickets", "showNoTicketsIndicator: ");
        TextView textView = (TextView) getView().findViewById(R.id.no_tickets_indicator);
        textView.setVisibility(View.VISIBLE);
    }

    public void setTickets(ArrayList<EventTicket> eventTickets) {
        Log.i("IEC", "setTickets: Set");
        if (eventTickets.isEmpty()) showNoTicketsIndicator();
        else hideNoTicketsIndicator();
        myEventsAdapter.updateDataSet(eventTickets);
        myEventsAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    public void clearTickets(){
        Log.i("IEC", "clearTickets: Cleared first called");
        showNoTicketsIndicator();
        myEventsAdapter.clearTickets();
    }

    public void updateTickets(Boolean isLoggedIn){
        Log.d("IEC", "updateTickets: updateTickets called");

        if (isLoggedIn ){
            new WebDownloaderTask(this, WebDownloaderTask.GET_TICKETS).execute("http://www.test.com/index.html");
        } else {
            clearTickets();
        }
    }

    public void redrawTickets(){

        if (prefManager != null) {
            prefManager = new PrefManager(getContext());
            Gson gson = new Gson();
            String json = prefManager.getTicketsList();

            if (prefManager.isLoggedIn() ){
                myEventsList = gson.fromJson(json, new TypeToken<ArrayList<EventTicket>>(){}.getType());
                if (myEventsList.isEmpty()) {
                    showNoTicketsIndicator();
                } else {
                    setTickets(myEventsList);
                }
            } else {
                showNoTicketsIndicator();
            }

        }
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    Log.d("Tickets", "onRefresh: called");

                    if (prefManager.isLoggedIn() ){

                        String json = prefManager.getTicketsList();
                        Gson gson = new Gson();
                        myEventsList = gson.fromJson(json, new TypeToken<ArrayList<EventTicket>>(){}.getType());
                        if (myEventsList.isEmpty()) {
                            showNoTicketsIndicator();
                        }
                        new WebDownloaderTask(MyEvents.this, WebDownloaderTask.GET_TICKETS).execute("http://www.test.com/index.html");
                    } else {
                        showNoTicketsIndicator();
                    }
//                    swipeRefreshLayout.setRefreshing(false);
//                    Log.d("Tickets", "onRefresh: stopped Refresh animation");
                }
            });
        }
    }


}
