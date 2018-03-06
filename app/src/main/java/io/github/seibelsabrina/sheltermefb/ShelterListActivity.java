package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ShelterListActivity extends AppCompatActivity {
    DatabaseReference databaseShelters;
    ListView listViewShelters;
    List<Shelter> shelterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

        databaseShelters = FirebaseDatabase.getInstance().getReference("shelters");
        listViewShelters = (ListView) findViewById(R.id.listViewShelters);
        shelterList = new ArrayList<>();

        listViewShelters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter s = (Shelter) listViewShelters.getItemAtPosition(i);
                Intent intent = new Intent(ShelterListActivity.this, ShelterDetailViewActivity.class);
                intent.putExtra("shelter", s);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelterList.clear();
                for (DataSnapshot shelterSnapshot: dataSnapshot.getChildren()) {
                    Shelter shelter = shelterSnapshot.getValue(Shelter.class);
                    shelterList.add(shelter);
                }
                ShelterList adapter = new ShelterList(ShelterListActivity.this, shelterList);
                listViewShelters.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
