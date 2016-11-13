package com.example.islam.iec;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProjectsActivity extends AppCompatActivity {

    private RecyclerView projectsRecyclerView;
    private ProjectsAdapter projectsAdapter;
    private RecyclerView.LayoutManager projectsLayoutManager;
    ArrayList<Event.Project> projectsList;
    public PrefManager prefManager;
    public String eventID;
    public Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle data = getIntent().getExtras();
        eventID = data.getString("eventID");
        event = (Event) data.getParcelable("event");
        Log.d("IEC", "onCreate: Event ID "+ eventID );
//        Log.d("IEC", "onCreate: project name "+ event.projects.get(1).getName() );

        projectsRecyclerView = (RecyclerView) findViewById(R.id.projects_rec_view);
        if (projectsRecyclerView != null) {
            projectsRecyclerView.setHasFixedSize(true);
        }

        projectsList = new ArrayList<>();
        for (int index = 0; index < event.projects.size(); index++) {
            projectsList.add(event.projects.get(index));
        }
//        projectsList.add(new Project("My image", "The second project", "someURL", "52"));
//        projectsList.add(new Project("", "Vote for me", "someURL","81"));



        // Use linear layout manager
        Log.d("IEC", "onCreate: Printed");
        projectsLayoutManager = new LinearLayoutManager(this);
        projectsRecyclerView.setLayoutManager(projectsLayoutManager);

        // specify an adapter (See also next example)
        projectsAdapter = new ProjectsAdapter(this, projectsList);
        projectsRecyclerView.setAdapter(projectsAdapter);

        prefManager = new PrefManager(this);


        if (prefManager.isLoggedIn())  {
            new WebDownloaderTask(this, WebDownloaderTask.VOTED).execute(eventID);
        }


    }
    public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
        this.onBackPressed();

    }
        return true;
    }

    public void redrawProjects(){
        projectsRecyclerView.setAdapter(null);
        projectsRecyclerView.setLayoutManager(null);
        projectsRecyclerView.setAdapter(projectsAdapter);
        projectsRecyclerView.setLayoutManager(projectsLayoutManager);
        projectsAdapter.notifyDataSetChanged();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (prefManager.isLoggedIn())  {
            new WebDownloaderTask(this, WebDownloaderTask.VOTED).execute(eventID);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
