package com.example.islam.iec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class ProjectsActivity extends AppCompatActivity {

    private RecyclerView projectsRecyclerView;
    private ProjectsAdapter projectsAdapter;
    private RecyclerView.LayoutManager projectsLayoutManager;
    ArrayList<Project> projectsList;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        projectsRecyclerView = (RecyclerView) findViewById(R.id.projects_rec_view);
        if (projectsRecyclerView != null) {
            projectsRecyclerView.setHasFixedSize(true);
        }

        projectsList = new ArrayList<>();
        projectsList.add(new Project("", "The great and powerful", "someURL"));
        projectsList.add(new Project("My image", "The second project", "someURL"));
        projectsList.add(new Project("", "Vote for me", "someURL"));


        // Use linear layout manager
        Log.d("IEC", "onCreate: Printed");
        projectsLayoutManager = new LinearLayoutManager(this);
        projectsRecyclerView.setLayoutManager(projectsLayoutManager);

        // specify an adapter (See also next example)
        projectsAdapter = new ProjectsAdapter(this, projectsList);
        projectsRecyclerView.setAdapter(projectsAdapter);

        prefManager = new PrefManager(this);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
        this.onBackPressed();

    }
        return true;
    }
}
