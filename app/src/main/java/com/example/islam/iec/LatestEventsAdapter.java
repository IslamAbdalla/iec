package com.example.islam.iec;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by islam on 9/12/16.
 */
public class LatestEventsAdapter extends RecyclerView.Adapter<LatestEventsAdapter.ViewHolder> {
    //private ArrayList<String> mDataset;
    private ArrayList<Event> eventsList;
    final private int UPCOMING = 1, PAST = 0, SEPARATOR = 2;

    // Provide a reference to the view for each data item
    // Complex data may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtLocation;
        public TextView txtDate;
        public ImageView imgEvent;
        public RelativeLayout rootLayout;


        public ViewHolder(View v) {
            super(v);

            // View can be event, past_event, or a separator.
            // In the separator case, the following variables will be null.
            txtTitle = (TextView) v.findViewById(R.id.event_title);
            txtLocation = (TextView) v.findViewById(R.id.event_location);
            txtDate = (TextView) v.findViewById(R.id.event_date);
            imgEvent = (ImageView) v.findViewById(R.id.event_pic);
            rootLayout = (RelativeLayout) v.findViewById(R.id.root_layout);
        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public LatestEventsAdapter(ArrayList<Event> inEventsList) {
        eventsList = inEventsList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LatestEventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        // Create a new view
        if(UPCOMING == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_event, parent, false);
        } else if(PAST == viewType){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_past_event, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_events_separator, parent, false);
        }
        // set the view's size, margins, padding and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(eventsList.get(position).isSeparator()){
            return SEPARATOR;
        } else if(eventsList.get(position).getUpcoming()) return UPCOMING;
        else return PAST;
    }

    // Replace the content of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final LatestEventsAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Event event = eventsList.get(position);

        if(!event.isSeparator()) {
            //final String name = mDataset.get(position);
            holder.txtTitle.setText(event.getTitle());
            holder.txtLocation.setText(event.getLocationText());
            holder.txtDate.setText((CharSequence) event.getDate().toString());

            if (position == 2) {
                holder.imgEvent.setImageResource(R.drawable.startup_weekend_medani);

            }

            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EventActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

}
