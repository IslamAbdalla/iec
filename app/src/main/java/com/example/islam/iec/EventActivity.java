package com.example.islam.iec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    public ImageView image;

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
        image = (ImageView) findViewById(R.id.header);

        // Get event details
        Bundle data = getIntent().getExtras();
        event = data.getParcelable("event");
        if (event != null ) {
            Log.i("IEC", "onCreate: Event.name: " +  event.getName());
            title.setText(event.getName());
            location.setText(event.getLocation());
            date.setText(event.getDate());
            details.setText(Html.fromHtml(event.getDescription()));

            if (event.getUpcoming())
            Picasso.with(this)
                    .load("https://scontent.xx.fbcdn.net/v/t1.0-9/14358705_715127548627927_376893474631163672_n.jpg?oh=3452f21fa868e991606207fe0bc8ad23&oe=5866434F")
                    //.resize(holder.imgEvent.getMeasuredWidth(),360)
                    .fit()
                    .centerCrop()
                    .into(image);
            else
            Picasso.with(this)
                    .load("https://scontent-arn2-1.xx.fbcdn.net/t31.0-8/13047850_812313515567579_4736785202866688398_o.jpg")
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




    }

    public void openProjects(View view) {

        Intent intent = new Intent(this, ProjectsActivity.class);
        Log.d("IEC", "openProjects: Event ID " + event.getId());
        intent.putExtra("eventID", event.getId() );
        intent.putExtra("event", event);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
