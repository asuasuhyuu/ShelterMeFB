package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapSearchActivity extends AppCompatActivity {

    Spinner spinnerMapGender;
    Spinner spinnerMapAge;
    EditText editTextMapName;
    Button buttonMapName;
    Button buttonMapGender;
    Button buttonMapAge;

    DatabaseReference databaseShelter;

    List<Shelter> shelterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);



        databaseShelter = FirebaseDatabase.getInstance().getReference("shelters");

        spinnerMapGender = (Spinner) findViewById(R.id.spinnerMapGender);
        spinnerMapAge = (Spinner) findViewById(R.id.spinnerMapAge);
        editTextMapName = (EditText) findViewById(R.id.editTextMapName);
        buttonMapName = (Button) findViewById(R.id.buttonMapName);
        buttonMapGender = (Button) findViewById(R.id.buttonMapGender);
        buttonMapAge = (Button) findViewById(R.id.buttonMapAge);

        shelterList = new ArrayList<Shelter>();


        buttonMapGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchGender();
            }
        });

        buttonMapAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAgeRange();
            }
        });

        buttonMapName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShelterName();
            }
        });

    }


    private void searchGender() {
        // gives you female, male
        databaseShelter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gender = spinnerMapGender.getSelectedItem().toString();
                String gender2;
                if (gender.equals("Female")) {
                    gender2 = "Women";
                } else {
                    gender2 = "Men";
                }
                shelterList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shelter shelter = snapshot.getValue(Shelter.class);
                    // shelter.restrictions
                    if (shelter.getRestrictions().contains(gender) ||
                            shelter.getRestrictions().contains(gender2)) {
                        shelterList.add(shelter);
                    }
                }
                Intent intent = new Intent(MapSearchActivity.this, MapsActivity.class);
                intent.putExtra("ShelterList", (Serializable) shelterList);
                startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void searchAgeRange() {
        // gives you Anyone, Children, Family, Young Adults, Newborn
        databaseShelter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ageRange = spinnerMapAge.getSelectedItem().toString();
                shelterList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shelter shelter = snapshot.getValue(Shelter.class);
                    // shelter.restrictions
                    if (shelter.getRestrictions().contains(ageRange)) {
                        shelterList.add(shelter);
                    }
                }
                Intent intent = new Intent(MapSearchActivity.this, MapsActivity.class);
                intent.putExtra("ShelterList", (Serializable) shelterList);
                startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void searchShelterName() {
        // literally anything
        databaseShelter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String shelterName = editTextMapName.getText().toString().trim();
                shelterList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shelter shelter = snapshot.getValue(Shelter.class);
                    // shelter.restrictions
                    if (shelter.getShelterName().contains(shelterName)) {
                        // do whatever you wanna do
                        shelterList.add(shelter);
                    }
                }
                Intent intent = new Intent(MapSearchActivity.this, MapsActivity.class);
                intent.putExtra("ShelterList", (Serializable) shelterList);
                startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}


