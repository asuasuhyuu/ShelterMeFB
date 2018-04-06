package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Spinner spinnerSearchShelterGender;
    Spinner spinnerSearchShelterAgeRange;
    EditText editTextSearchShelterName;
    Button buttonSearchGender;
    Button buttonSearchAgeRange;
    Button buttonSearchShelterName;
    ListView listViewShelterSearch;
    List<Shelter> shelterList;
    EditText editTextSearchCharacteristic;
    Button buttonSearchCharacteristic;
    EditText editTextSearchAddress;
    Button buttonSearchAddress;

    DatabaseReference databaseShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle bundle = getIntent().getExtras();
        final Person person = (Person) bundle.getSerializable("person");


        databaseShelter = FirebaseDatabase.getInstance().getReference("shelters");

        spinnerSearchShelterGender = (Spinner) findViewById(R.id.spinnerSearchShelterGender);
        spinnerSearchShelterAgeRange = (Spinner) findViewById(R.id.spinnerSearchShelterAgeRange);
        editTextSearchShelterName = (EditText) findViewById(R.id.editTextSearchShelterName);
        buttonSearchGender = (Button) findViewById(R.id.buttonSearchGender);
        buttonSearchAgeRange = (Button) findViewById(R.id.buttonSearchAgeRange);
        buttonSearchShelterName = (Button) findViewById(R.id.buttonSearchShelterName);
        listViewShelterSearch = (ListView) findViewById(R.id.listViewShelterSearch);
        editTextSearchCharacteristic = (EditText) findViewById(R.id.editTextSearchCharacteristic);
        editTextSearchAddress = (EditText) findViewById(R.id.editTextSearchAddress);
        buttonSearchCharacteristic = (Button) findViewById(R.id.buttonSearchCharacteristic);
        buttonSearchAddress = (Button) findViewById(R.id.buttonSearchAddress);

        shelterList = new ArrayList<>();

        buttonSearchGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchGender();
            }
        });

        buttonSearchAgeRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAgeRange();
            }
        });

        buttonSearchShelterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchShelterName();
            }
        });

        buttonSearchCharacteristic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCharacteristic();
            }
        });

        buttonSearchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAddress();
            }
        });

        listViewShelterSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter s = (Shelter) listViewShelterSearch.getItemAtPosition(i);
                Intent intent = new Intent(SearchActivity.this, ShelterDetailViewActivity.class);
                intent.putExtra("shelter", s);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });

    }

    private void searchCharacteristic() {
        databaseShelter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String characteristics  = editTextSearchCharacteristic.getText().toString().trim();
                shelterList.clear();
                List<Shelter> allShelters = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    allShelters.add(snapshot.getValue(Shelter.class));
                }
                shelterList.addAll(Shelter.searchCharacteristic(allShelters, characteristics));
                ShelterList adapter = new ShelterList(SearchActivity.this, shelterList);
                listViewShelterSearch.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void searchAddress() {
        databaseShelter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address  = editTextSearchAddress.getText().toString().trim();
                shelterList.clear();
                List<Shelter> allShelters = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    allShelters.add(snapshot.getValue(Shelter.class));
                }
                shelterList.addAll(Shelter.searchAddress(allShelters, address));
                ShelterList adapter = new ShelterList(SearchActivity.this, shelterList);
                listViewShelterSearch.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void searchGender() {
        // gives you female, male
        databaseShelter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gender = spinnerSearchShelterGender.getSelectedItem().toString();
                shelterList.clear();

                List<Shelter> allShelters = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    allShelters.add(snapshot.getValue(Shelter.class));
                }

                shelterList.addAll(Shelter.searchGender(allShelters, gender));
                ShelterList adapter = new ShelterList(SearchActivity.this, shelterList);
                listViewShelterSearch.setAdapter(adapter);
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
                String ageRange = spinnerSearchShelterAgeRange.getSelectedItem().toString();
                shelterList.clear();
                List<Shelter> allShelters = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Shelter shelter = snapshot.getValue(Shelter.class);
                    // shelter.restrictions
                    allShelters.add(snapshot.getValue(Shelter.class));

                }
                shelterList.addAll(Shelter.searchAgeRange(allShelters, ageRange));
                ShelterList adapter = new ShelterList(SearchActivity.this, shelterList);
                listViewShelterSearch.setAdapter(adapter);
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
                String shelterName = editTextSearchShelterName.getText().toString().trim();
                shelterList.clear();
                List<Shelter> allShelters = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Shelter shelter = snapshot.getValue(Shelter.class);
                    // shelter.restrictions
                    //if (shelter.getShelterName().contains(shelterName)) {
                        // do whatever you wanna do
                        //shelterList.add(shelter);
                    allShelters.add(snapshot.getValue(Shelter.class));

                }
                shelterList.addAll(Shelter.searchShelterName(allShelters, shelterName));
                ShelterList adapter = new ShelterList(SearchActivity.this, shelterList);
                listViewShelterSearch.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
