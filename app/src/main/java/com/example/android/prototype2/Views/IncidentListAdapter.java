package com.example.android.prototype2.Views;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.Model.PlayerIncidentsModel;
import com.example.android.prototype2.R;

import java.util.List;

//This class is an adapter for the Recyclerview
public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.ViewHolder> {

    //Variables
    private Context context;
    //Declare a list using the UserHelperClass
    private List<PlayerIncidentsModel> incidentList;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();

    //Constructor
    public IncidentListAdapter(Context context, List<PlayerIncidentsModel> incidentsList) {
        this.context = context;
        this.incidentList = incidentsList;
    }

    @NonNull
    @Override
    //If viewHolder does  not exist create one by inflating the user_details_view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.incident_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        //Retrieve the incident stored at the position
        final PlayerIncidentsModel incidents = incidentList.get(position);
        holder.coachName.setText(String.format("Coach: %s" , incidents.getCoachName()));
        holder.textViewRedFlag.setText(incidents.getRed_FLag_Test());
        holder.textViewObs.setText(incidents.getObservable_Signs_Test());
        holder.textViewMemory.setText(incidents.getMemory_Question());
        holder.textViewSymptoms.setText(incidents.getSymptoms());
        holder.textViewIncidentReport.setText("Report: " + incidents.getReports());
        holder.date.setText("Date: " + incidents.getDate());
         holder.incidentPlayerName.setText("Player: "+incidents.getName());
        Log.d(TAG, "SIZE--"+incidentList.size());
    }

    @Override
    //Return the amount of players
    public int getItemCount() {
        return this.incidentList.size();
    }



    //ViewHolder wraps the view passed to it so RecyclerView can deal with it
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewRedFlag;
        private TextView textViewObs;
        private TextView textViewMemory;
        private TextView textViewSymptoms;
        private TextView textViewIncidentReport;
        private TextView date;
        private TextView coachName;
       private TextView incidentPlayerName;
        private View parentView;


        //Initialise the variables to their corresponding views
        public ViewHolder(@NonNull View view) {
            super(view);
            this.parentView = view;
           this.incidentPlayerName=view.findViewById(R.id.incident_player_name);
            this.textViewRedFlag = view.findViewById(R.id.red_flag_diagnosis);
            this.textViewObs = view.findViewById(R.id.obs_diagnosis);
            this.textViewMemory = view.findViewById(R.id.memory_test_diagnosis);
            this.textViewSymptoms = view.findViewById(R.id.symptoms_diagnosis);
            this.textViewIncidentReport = view.findViewById(R.id.incident_report_result);
            this.date = view.findViewById(R.id.player_view_date);
            this.coachName = view.findViewById(R.id.player_view_coachname);
        }
    }
}

