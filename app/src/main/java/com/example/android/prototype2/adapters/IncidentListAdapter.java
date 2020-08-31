package com.example.android.prototype2.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.R;
import com.example.android.prototype2.helperClass.PlayerIncidentsModel;

import java.util.ArrayList;
import java.util.List;

//This class is an adapter for the Recyclerview
public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.ViewHolder> {

    //Variables
    private final Context context;
    //Declare a list using the PlayerIncidentsModel class
    private final List<PlayerIncidentsModel> incidentList;
    private final List<PlayerIncidentsModel> incidentListFull;


    //Tag for printing log details
    private final String TAG = getClass().getSimpleName();

    //Constructor
    public IncidentListAdapter(Context context, List<PlayerIncidentsModel> incidentsList) {
        this.context = context;
        this.incidentList = incidentsList;
        incidentListFull = new ArrayList<>(incidentsList);
    }

    @NonNull
    @Override
    //If viewHolder does  not exist create one by inflating the user_details_view
    public IncidentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.incident_item, parent, false)
        );
    }

    @Override
    //onBind fills the incident with the data, and updates as the user scrolls down the recycler
    public void onBindViewHolder(@NonNull IncidentListAdapter.ViewHolder holder, final int position) {
        //Retrieve the incident stored at the position and set the values in the text views
        final PlayerIncidentsModel incidents = incidentList.get(position);
        holder.coachName.setText(String.format("Coach: %s", incidents.getCoachName()));
        holder.textViewRedFlag.setText(incidents.getRed_FLag_Test());
        holder.textViewObs.setText(incidents.getObservable_Signs_Test());
        holder.textViewMemory.setText(incidents.getMemory_Question());
        holder.textViewSymptoms.setText(String.format("Symptoms: %s", incidents.getSymptoms()));
        holder.textViewIncidentReport.setText(String.format("Report: %s", incidents.getReports()));
        holder.date.setText(String.format("Date: %s", incidents.getDate()));
        holder.incidentPlayerName.setText(String.format("Player: %s", incidents.getName()));

    }

    @Override
    //Return the amount of players
    public int getItemCount() {
        return this.incidentList.size();
    }


    //ViewHolder wraps the view passed to it so RecyclerView can deal with it
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewRedFlag;
        private final TextView textViewObs;
        private final TextView textViewMemory;
        private final TextView textViewSymptoms;
        private final TextView textViewIncidentReport;
        private final TextView date;
        private final TextView coachName;
        private final TextView incidentPlayerName;
        private final View parentView;


        //Initialise the variables to their corresponding views
        public ViewHolder(@NonNull View view) {
            super(view);
            this.parentView = view;
            this.incidentPlayerName = view.findViewById(R.id.incident_player_name);
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

