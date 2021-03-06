package com.example.android.prototype2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.prototype2.R;
import com.example.android.prototype2.helperClass.PlayerModel;
import com.example.android.prototype2.views.RedFlagActivity;

import java.util.ArrayList;
import java.util.List;


public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> implements Filterable {
    //Declare a list using the PlayerModel class
    private final List<PlayerModel> playersList;
    private final List<PlayerModel> playerListFull;

    //Variables
    private final Context context;

    private final String TAG = getClass().getSimpleName();
    // private Context context;

    //Constructor
    public PlayerListAdapter(Context context, List<PlayerModel> playersList) {
        this.context = context;
        this.playersList = playersList;
        playerListFull = new ArrayList<>(playersList);

    }

    @NonNull
    @Override
    //If viewHolder does  not exist create one by inflating the user_details_view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.users_card_view, parent, false)

        );
    }

    @Override
    //onBind fills the player list with the data, and updates as the user scrolls down the recycler
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Retrieve the player stored at the position and set the values in the text views
        final PlayerModel player = playersList.get(position);
        //Set the info from the player in the textViews
        holder.textViewName.setText(String.format("Name: %s", player.getName()));
        holder.textViewEmail.setText(String.format("Email: %s", player.getEmail()));
        holder.textViewPhone.setText(String.format("Phone: %s", player.getPhoneNo()));
        holder.textViewEmergencyContact.setText(String.format("Emergency Contact: %s", player.getEmergencyContact()));
        holder.textViewEmergencyContactPhone.setText(String.format("Contact's Phone: %s", player.getContactNumber()));

        // Add an onClickListener to the parentView so when a player is selected launches redFlag activity
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //The intent to launch the activity
                Intent intent = new Intent(context, RedFlagActivity.class);
                intent.putExtra("name1", player.getName());
                intent.putExtra("email1", player.getEmail());
                intent.putExtra("uid", player.getUid());
                context.startActivity(intent);

            }
        });


    }

    @Override
    //Return the amount of players
    public int getItemCount() {
        return this.playersList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    //Filter method to search the players for the search entry. An example here of an Asynchronous task
    private final Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PlayerModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(playerListFull);

            } else {
                //trim -empty spaces removed
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PlayerModel player : playerListFull) {
                    if (player.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(player);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Publish the results as a list
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            playersList.clear();
            playersList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    //ViewHolder wraps the view passed to it so RecyclerView can deal with it
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final SearchView searchView;
        private final TextView textViewName;
        private final TextView textViewEmail;
        private final TextView textViewPhone;
        private final TextView textViewEmergencyContact;
        private final TextView textViewEmergencyContactPhone;
        private final View parentView;


        //Initialise the variables to their corresponding views
        public ViewHolder(@NonNull View view) {
            super(view);
            this.parentView = view;
            this.searchView = view.findViewById(R.id.search_users);
            this.textViewName = view.findViewById(R.id.playerName);
            this.textViewEmail = view.findViewById(R.id.playerEmail);
            this.textViewPhone = view.findViewById(R.id.playerPhoneNo);
            this.textViewEmergencyContact = view.findViewById(R.id.player_EmergencyC);
            this.textViewEmergencyContactPhone = view.findViewById(R.id.player_Contact_Phone);
        }
    }
}
