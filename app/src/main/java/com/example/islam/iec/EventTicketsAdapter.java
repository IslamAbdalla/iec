package com.example.islam.iec;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by islam on 10/17/16.
 */
public class EventTicketsAdapter extends RecyclerView.Adapter<EventTicketsAdapter.ViewHolder> {
    private ArrayList<EventTicket> eventTickets;
    private Context context;

    public EventTicketsAdapter(Context mContext, ArrayList<EventTicket> mEventTickets) {
        eventTickets = mEventTickets;
        context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final EventTicket ticket = eventTickets.get(position);
        holder.eventName.setText(ticket.getEventName());
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(ticket.getRegCode());
                alertDialogBuilder.setTitle("Registration code");
                alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventTickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView regCode;
        public RelativeLayout rootLayout;

        public ViewHolder(View v) {
            super(v);
            eventName = (TextView) v.findViewById(R.id.ticket_event_name);
            regCode = (TextView) v.findViewById(R.id.ticket_event_reg_code);
            rootLayout = (RelativeLayout) v.findViewById(R.id.ticket_event_root);
        }

    }


}
