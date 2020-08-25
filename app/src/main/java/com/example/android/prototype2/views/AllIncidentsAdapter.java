package com.example.android.prototype2.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.R;
import com.example.android.prototype2.helperClass.AllIncidentsModel;

import java.util.ArrayList;
import java.util.List;

public class AllIncidentsAdapter extends RecyclerView.Adapter<AllIncidentsAdapter.ViewHolder> implements Filterable {
    //Declare a list using the AllIncidentsModel
    private final List<AllIncidentsModel> incidentList;
    private final List<AllIncidentsModel> incidentListFull;

    //Variables
    private final Context context;


    //Constructor
    public AllIncidentsAdapter(Context context, List<AllIncidentsModel> incidentList) {
        this.context = context;
        this.incidentList = incidentList;
        incidentListFull = new ArrayList<>(incidentList);

    }


    @NonNull
    @Override
    //If viewHolder does  not exist create one by inflating the user_details_view
    public AllIncidentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.all_incidents_card, parent, false)

        );
    }

    @Override
    //Data binded to the view for display within the recycler
    public void onBindViewHolder(@NonNull AllIncidentsAdapter.ViewHolder holder, int position) {
        //Retrieve the incident stored at the position and set the values in the text views
        final AllIncidentsModel incident = incidentList.get(position);
        holder.textViewName.setText(String.format("Player Name: %s", incident.getName()));
        holder.textViewCoachName.setText(String.format("Coach: %s", incident.getCoachName()));
        holder.textViewDate.setText(String.format("Date: %s", incident.getDate()));
        holder.textViewRedFlag.setText(incident.getRed_FLag_Test());
        holder.textViewObservable.setText(incident.getObservable_Signs_Test());
        holder.textViewSymptoms.setText(String.format("Symptoms: %s", incident.getSymptoms()));
        holder.textViewMemory.setText(incident.getMemory_Question());
        holder.textViewReport.setText(String.format("Report: %s", incident.getReports()));

    }


    @Override
    //Return the amount of players
    public int getItemCount() {
        return this.incidentList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    //Filter method to search the incidents for the search entry. An example here of an Asynchronous task as it runs in background
    private final Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AllIncidentsModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(incidentListFull);

            } else {
                //Trim removes empty spaces
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (AllIncidentsModel incident : incidentListFull) {
                    if (incident.getName().toLowerCase().contains(filterPattern) || incident.getName() == null) {
                        filteredList.add(incident);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Publish the results as a list
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            incidentList.clear();
            incidentList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    //ViewHolder wraps the view passed to it so RecyclerView can deal with it
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewCoachName;
        private final TextView textViewDate;
        private final TextView textViewRedFlag;
        private final TextView textViewObservable;
        private final TextView textViewSymptoms;
        private final TextView textViewMemory;
        private final TextView textViewReport;


        //Initialise the variables to their corresponding views
        public ViewHolder(@NonNull View view) {
            super(view);
            this.textViewName = view.findViewById(R.id.all_incidents_playerName);
            this.textViewCoachName = view.findViewById(R.id.all_incidents_coachName);
            this.textViewDate = view.findViewById(R.id.allincidents_Date);
            this.textViewRedFlag = view.findViewById(R.id.all_incidents_RedFlag);
            this.textViewObservable = view.findViewById(R.id.all_incidents_Obs);
            this.textViewSymptoms = view.findViewById(R.id.all_incidents_Symptoms);
            this.textViewMemory = view.findViewById(R.id.all_incidents_Memory);
            this.textViewReport = view.findViewById(R.id.all_incidents_Report);

        }
    }
}

