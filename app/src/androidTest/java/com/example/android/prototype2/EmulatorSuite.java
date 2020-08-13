package com.example.android.prototype2;

import androidx.room.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmulatorSuite {

    public void emulatorSettings() {
        // [START rtdb_emulator_connect]
        // 10.0.2.2 is the special IP address to connect to the 'localhost' of
        // the host computer from an Android emulator.
        // In almost all cases the ns (namespace) is your project ID.
        FirebaseDatabase database = FirebaseDatabase.getInstance("http://10.0.2.2:9000?prototype2-27746");
        // [END rtdb_emulator_connect]

    }

    public void flushRealtimeDatabase(FirebaseDatabase database) {
        // [START rtdb_emulator_flush]
        // With a DatabaseReference, write null to clear the database.
        database.getReference().setValue(null);
        // [END rtdb_emulator_flush]

    }


}