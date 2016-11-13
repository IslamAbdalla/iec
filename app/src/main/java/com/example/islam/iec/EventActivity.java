package com.example.islam.iec;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by islam on 9/15/16.
 */
public class EventActivity extends AppCompatActivity {
    public Event event;
    public TextView title;
    public TextView location;
    public TextView date;
    public TextView details;
    public TextView projectsLabel;
    public ImageView image;
    public Button bookButton;
    private PrefManager prefManager;
    public LinearLayout voteForProjectLayout;

    static final int SIGN_IN_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        setSupportActionBar((Toolbar) findViewById(R.id.anim_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_white_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        setTitle(" ");
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        title = (TextView) findViewById(R.id.event_ac_title);
        location = (TextView) findViewById(R.id.event_ac_loc);
        date = (TextView) findViewById(R.id.event_ac_date);
        details = (TextView) findViewById(R.id.event_ac_details);
        projectsLabel = (TextView) findViewById(R.id.vote_for_projects_label);
        image = (ImageView) findViewById(R.id.header);
        bookButton = (Button) findViewById(R.id.btn_book);
        voteForProjectLayout = (LinearLayout) findViewById(R.id.vote_for_projects_layout);
        prefManager = new PrefManager(this);

        // Get event details
        Bundle data = getIntent().getExtras();
        event = data.getParcelable("event");
        if (event != null ) {
            Log.i("IEC", "onCreate: Event.name: " +  event.getName());
            title.setText(event.getName());
            location.setText(event.getLocationText());
            date.setText(event.getDate());
            details.setText(Html.fromHtml(event.getDescription()));

            Picasso.with(this)
                    .load(event.getImage())
                    //.resize(holder.imgEvent.getMeasuredWidth(),360)
                    .fit()
                    .centerCrop()
                    .into(image);


        }



        // To display title only when collapsed
        if (appBarLayout != null && event != null) {
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if(scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if(scrollRange + verticalOffset == 0) {
                        if (collapsingToolbarLayout != null) {
                            collapsingToolbarLayout.setTitle(event.getName());
                        }
                        isShow = true;
                    } else if(isShow) {
                        if (collapsingToolbarLayout != null) {
                            collapsingToolbarLayout.setTitle(" ");
                        }
                        isShow = false;
                    }

                }
            });
        }

        // Book button
        updateBookButton();

        // Show or hide vote for projects
        if(event.projects.isEmpty()){
            voteForProjectLayout.setVisibility(View.GONE);
        } else {
            voteForProjectLayout.setVisibility(View.VISIBLE);
        }
    }

    public void openProjects(View view) {

        Intent intent = new Intent(this, ProjectsActivity.class);
        Log.d("IEC", "openProjects: Event ID " + event.getId());
        intent.putExtra("eventID", event.getId() );
        intent.putExtra("event", event);
        startActivityForResult(intent,0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @NonNull
    private Boolean isEventBooked (String eventID) {

        Gson gson = new Gson();
        String json = prefManager.getTicketsList();
        ArrayList<EventTicket> eventTickets = gson.fromJson(json, new TypeToken<ArrayList<EventTicket>>(){}.getType());
        for (int i = 0; i < eventTickets.size(); i++) {
            EventTicket ticket = eventTickets.get(i);
            if (ticket.getEventID().equals(eventID))
                return true;
        }
        return false;
    }

    public void updateBookButton(){

        if (!event.getUpcoming()){
            bookButton.setBackground(getResources().getDrawable(R.drawable.rounded_greyed_button));
            bookButton.setText(R.string.book_now);
            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(EventActivity.this, "This event has already finished.", Toast.LENGTH_LONG).show();
                }
            });

        } else if (prefManager.isLoggedIn()){
            if (isEventBooked(event.getId())){
                bookButton.setBackground(getResources().getDrawable(R.drawable.rounded_greyed_button));
                bookButton.setText(R.string.booked);
                bookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EventActivity.this, "You have already booked this event.", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                bookButton.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                bookButton.setText(R.string.book_now);
                bookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new WebDownloaderTask(EventActivity.this, WebDownloaderTask.BOOK).execute(event.getId());
                        Toast.makeText(EventActivity.this, "Connecting..", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } else {
            bookButton.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                bookButton.setText(R.string.book_now);
            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EventActivity.this);
                    alertDialogBuilder.setMessage("Please log in to continue.");
                    alertDialogBuilder.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(EventActivity.this, Login.class);
                            EventActivity.this.startActivityForResult(intent, SIGN_IN_CODE);

                        }
                    });

                    alertDialogBuilder.setNegativeButton("Sign up", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(EventActivity.this, SignUp.class);
                            EventActivity.this.startActivityForResult(intent, 1);
                        }
                    });
                    alertDialogBuilder.show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateBookButton();
        if (requestCode == SIGN_IN_CODE && resultCode == RESULT_OK) {
            Log.d("EventActivity", "onActivityResult: Result ok");
            new WebDownloaderTask(EventActivity.this, WebDownloaderTask.UPDATE_TICKETS).execute();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void openLocation(View view) {
        double latitude = event.getLat();
        double longitude = event.getLng();
        String label = event.getLocationText();
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void shareEvent(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, event.getName()+"\n"
                +event.getLocationText()+"\n"
                +event.getDate()
                +"\nClick here for more info\nhttps://www.facebook.com/IECpage/"
        );
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }
}
