package com.example.islam.iec;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by islam on 9/12/16.
 */
public class LatestEventsAdapter extends RecyclerView.Adapter<LatestEventsAdapter.ViewHolder> {
    private ArrayList<Event> eventsList;
    final private int UPCOMING = 1, PAST = 0, SEPARATOR = 2;
    private Context context;
    private PrefManager prefManager;


    // Provide a reference to the view for each data item
    // Complex data may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtLocation;
        public TextView txtDate;
        public ImageView imgEvent;
        public RelativeLayout rootLayout;
        public Button bookButton;

        public ViewHolder(View v) {
            super(v);

            // View can be event, past_event, or a separator.
            // In the separator case, the following variables will be null.
            txtTitle = (TextView) v.findViewById(R.id.event_title);
            txtLocation = (TextView) v.findViewById(R.id.event_location);
            txtDate = (TextView) v.findViewById(R.id.event_date);
            imgEvent = (ImageView) v.findViewById(R.id.event_pic);
            rootLayout = (RelativeLayout) v.findViewById(R.id.root_layout);
            bookButton = (Button) v.findViewById(R.id.btn_book);

        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public LatestEventsAdapter(Context context, ArrayList<Event> inEventsList) {
        this.context = context;
        eventsList = inEventsList;
        prefManager = new PrefManager(context);
    }

    // Provide a data updater function
    public void updateDataSet(ArrayList<Event> inEventList) {
        eventsList = inEventList;
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
        if(eventsList.get(position).isSeparator())
            return SEPARATOR;
        else if(eventsList.get(position).getUpcoming())
            return UPCOMING;
        else
            return PAST;
    }

    // Replace the content of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final LatestEventsAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Event event = eventsList.get(position);

        if(!event.isSeparator()) {
            holder.txtTitle.setText(event.getName());
            holder.txtLocation.setText(event.getLocation());
            holder.txtDate.setText((CharSequence) event.getDate().toString());

            Log.i("IEC", "onBindViewHolder: Image URL: " + event.getImage());
            Picasso.with(context)
                    .load("https://scontent.xx.fbcdn.net/v/t1.0-9/14358705_715127548627927_376893474631163672_n.jpg?oh=3452f21fa868e991606207fe0bc8ad23&oe=5866434F")
                    //.resize(holder.imgEvent.getMeasuredWidth(),360)
                    .fit()
                    .centerCrop()
                    .into(holder.imgEvent);
            if (position == 2) {
                holder.imgEvent.setImageResource(R.drawable.startup_weekend_medani);
            Picasso.with(context)
                    .load("https://scontent-arn2-1.xx.fbcdn.net/t31.0-8/13047850_812313515567579_4736785202866688398_o.jpg")
                    //.resize(holder.imgEvent.getMeasuredWidth(),360)
                    .fit()
                    .centerCrop()
                    .into(holder.imgEvent);

            }

            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EventActivity.class);
                    intent.putExtra("event", event);
                    v.getContext().startActivity(intent);
                }
            });
            if (holder.bookButton != null) {
                holder.bookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("IEC", "bookEvent: loggedIn: " + prefManager.isLoggedIn());
                        if (prefManager.isLoggedIn()) {
                            new WebDownloaderTask((Activity) context, WebDownloaderTask.BOOK).execute(event.getId());
                        } else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setMessage("Are you sure you are logged in?");
                            alertDialogBuilder.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, Login.class);
                                    ((Activity) context).startActivityForResult(intent, 0);

                                }
                            });

                            alertDialogBuilder.setNegativeButton("Sign up", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, SignUp.class);
                                    ((Activity) context).startActivityForResult(intent, 1);
                                }
                            });
                            alertDialogBuilder.show();
                        }

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }


    public void clearEvents(){
        Log.i("IEC", "clearEvents: Cleared!");
        int listSize = eventsList.size();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                eventsList.remove(0);
            }

            this.notifyItemRangeRemoved(0, listSize);
        }

    }

}
