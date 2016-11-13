package com.example.islam.iec;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by islam on 10/20/16.
 */
public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {
    private ArrayList<Event.Project> Projects;
    private Context context;
    private PrefManager prefManager;

    public ProjectsAdapter(Context mContext, ArrayList<Event.Project> mProjects) {
        Projects = mProjects;
        context = mContext;
        prefManager = new PrefManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String eventID = ((ProjectsActivity) context).eventID;
        final PrefManager.ProjectState state = prefManager.getVoted(eventID);

        // Voting app
        if (state == PrefManager.ProjectState.NOTYET) {
            Log.d("ProjectsAdapter", "onBindViewHolder: Not yet");
//            holder.voteButton.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.voteButton.setBackground(context.getResources().getDrawable(R.drawable.outline_button));
            holder.voteButton.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.voteButton.setText("Vote");
        }
        if (state == PrefManager.ProjectState.VOTED) {
            Log.d("ProjectsAdapter", "onBindViewHolder: voted");
//            holder.voteButton.setBackgroundColor(context.getResources().getColor(R.color.textGray));
            holder.voteButton.setBackground(context.getResources().getDrawable(R.drawable.rounded_greyed_button));
            holder.voteButton.setTextColor(context.getResources().getColor(R.color.white));
            holder.voteButton.setText("Voted");
        }
        if (state == PrefManager.ProjectState.UNKNOWN) {
            Log.d("ProjectsAdapter", "onBindViewHolder: unknown");
//            holder.voteButton.setBackgroundColor(context.getResources().getColor(R.color.textGray));
            holder.voteButton.setBackground(context.getResources().getDrawable(R.drawable.rounded_greyed_button));
            holder.voteButton.setTextColor(context.getResources().getColor(R.color.white));
            holder.voteButton.setText("Vote");

        }

        final Event.Project project = Projects.get(position);
        holder.projectName.setText(project.getName());
        final String url = project.getLink();
        if (!project.getImage().isEmpty()) {

            Picasso.with(context)
//                    .load("http://design.ubuntu.com/wp-content/uploads/ubuntu-logo32.png")
                    .load(project.getImage())
                    //.resize(holder.imgEvent.getMeasuredWidth(),360)
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });
        holder.voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefManager.isLoggedIn()){
                    Log.d("ProjectsAdapter", "onClick: state is " + state.toString());
                    switch (state) {
                        case NOTYET:
                            {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setMessage("You can only vote for ONE project. Do you want to vote for " + project.getName() + "?");
                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                            new WebDownloaderTask((ProjectsActivity) context, WebDownloaderTask.VOTE).execute(eventID, project.getId());
                                    Toast.makeText(context, "Connecting to the server", Toast.LENGTH_SHORT).show();


                                }
                            });

                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialogBuilder.show();
                            }
                            break;

                        case VOTED:
                        {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setMessage("You have already voted in this event.");
                            alertDialogBuilder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            alertDialogBuilder.show();
                        }
                            break;
                        case UNKNOWN:
                            new WebDownloaderTask((ProjectsActivity) context, WebDownloaderTask.VOTED).execute(eventID);
                            Toast.makeText(context, "Trying to reach the server.", Toast.LENGTH_SHORT).show();

                            break;
                    }

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return Projects.size();
    }


    public void clearProjects(){
        Log.i("IEC", "clearEvents: Cleared!");
        int listSize = Projects.size();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                Projects.remove(0);
            }

            this.notifyItemRangeRemoved(0, listSize);
        }

    }

    // Provide a data updater function
    public void updateDataSet(ArrayList<Event.Project> inProjects) {
        Projects = inProjects;
    }

    public void addProject(Event.Project project) {
        Projects.add(0, project);
        notifyItemInserted(0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView projectName;
        public ImageView imageView;
        public Button voteButton;
        public LinearLayout rootLayout;

        public ViewHolder(View v) {
            super(v);
            projectName = (TextView) v.findViewById(R.id.project_name);
            imageView = (ImageView) v.findViewById(R.id.project_image);
            rootLayout = (LinearLayout) v.findViewById(R.id.project_root_layout);
            voteButton = (Button) v.findViewById(R.id.btn_project_vote);
        }

    }


}
