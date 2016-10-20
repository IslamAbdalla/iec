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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by islam on 10/20/16.
 */
public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {
    private ArrayList<Project> Projects;
    private Context context;

    public ProjectsAdapter(Context mContext, ArrayList<Project> mProjects) {
        Projects = mProjects;
        context = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Project project = Projects.get(position);
        holder.projectName.setText(project.getName());
        final String url = project.getUrl();
        if (!project.getImageURL().isEmpty()) {

            Picasso.with(context)
                    .load("http://design.ubuntu.com/wp-content/uploads/ubuntu-logo32.png")
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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("You can only vote for ONE project. Do you want to vote for "+ project.getName()+"?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(context, "Voted successfully", Toast.LENGTH_SHORT).show();


                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
    public void updateDataSet(ArrayList<Project> inProjects) {
        Projects = inProjects;
    }

    public void addProject(Project project) {
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
