package io.github.seibelsabrina.sheltermefb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainUserActivity extends AppCompatActivity {
    DatabaseReference databaseShelters = FirebaseDatabase.getInstance().getReference("shelters");
    DatabaseReference databasePeople = FirebaseDatabase.getInstance().getReference("people");

    Button buttonUserShelterList;
    Button buttonMainUserLogout;
    Button buttonUserShelterSearch;
    Button buttonUserCancelReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        Bundle bundle = getIntent().getExtras();
        final Person person = (Person) bundle.getSerializable("person");

        buttonUserShelterList = (Button) findViewById(R.id.buttonUserShelterList);
        buttonMainUserLogout = (Button) findViewById(R.id.buttonMainUserLogout);
        buttonUserShelterSearch = (Button) findViewById(R.id.buttonUserShelterSearch);
        buttonUserCancelReservation = (Button) findViewById(R.id.buttonUserCancelReservation);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        buttonUserShelterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainUserActivity.this, ShelterListActivity.class);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });

        buttonMainUserLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonUserShelterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainUserActivity.this, SearchActivity.class);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });

        buttonUserCancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Are you sure you want to cancel your reservations?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference ref = databaseShelters.child(person.getReservation()).child("vacancies");
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Integer vac = dataSnapshot.getValue(Integer.class);
                                        databaseShelters.child(person.getReservation()).child("vacancies").setValue(vac + person.getNumReservations());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                databasePeople.child(person.getUsername()).child("numReservations").setValue(0);
                                databasePeople.child(person.getUsername()).child("reservation").setValue("");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


}
