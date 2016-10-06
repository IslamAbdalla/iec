package com.example.islam.iec;

import android.app.Activity;
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
import android.widget.TextView;

/**
 * Created by islam on 9/15/16.
 */
public class EventActivity extends AppCompatActivity {
    public TextView title;
    public TextView location;
    public TextView date;
    public TextView details;

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

        // Get event details
        Bundle data = getIntent().getExtras();
        final Event event = data.getParcelable("event");
        if (event != null ) {
            Log.i("IEC", "onCreate: Event.name: " +  event.getName());
            title.setText(event.getName());
            location.setText(event.getLocation());
            date.setText(event.getDate());
            details.setText(Html.fromHtml(event.getDescription()));
        }

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
}
