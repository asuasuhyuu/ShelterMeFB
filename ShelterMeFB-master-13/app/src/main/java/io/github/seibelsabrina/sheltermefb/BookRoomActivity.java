package io.github.seibelsabrina.sheltermefb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookRoomActivity extends AppCompatActivity {
    DatabaseReference databaseShelters = FirebaseDatabase.getInstance().getReference("shelters");
    DatabaseReference databasePeople = FirebaseDatabase.getInstance().getReference("people");

    TextView textViewBookRoomVacancies;
    TextView textViewBookRoomEnterBeds;
    EditText editTextBookRoomBed;
    Button buttonBookRoomBed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);

        Bundle bundle = getIntent().getExtras();
        final Shelter s = (Shelter) bundle.getSerializable("shelter");
        final Person person = (Person) bundle.getSerializable("person");

        textViewBookRoomVacancies = (TextView) findViewById(R.id.textViewBookRoomVacancies);
        textViewBookRoomVacancies.setText("Beds Available: " + s.getVacancies().toString());
        textViewBookRoomEnterBeds = (TextView) findViewById(R.id.textViewBookRoomEnterBeds);
        editTextBookRoomBed = (EditText) findViewById(R.id.editTextBookRoomBed);
        buttonBookRoomBed = (Button) findViewById(R.id.buttonBookRoomBed);



//        textViewBookRoomAptVacancies.setText("Apartments Available: " + s.getApartmentVacancies().toString());
//        textViewBookRoomFamVacancies.setText("Family Rooms Available: " + s.getFamilyVacancies().toString());
//        textViewBookRoomSingleVacancies.setText("Single Spaces Available: " + s.getSingleVacancies().toString());

//        textViewBookRoomEnterFam = (TextView) findViewById(R.id.textViewBookRoomEnterFam);
//        textViewBookRoomEnterSingle = (TextView) findViewById(R.id.textViewBookRoomEnterSingle);
//        textViewBookRoomEnterApt = (TextView) findViewById(R.id.textViewBookRoomEnterApt);

//        editTextBookRoomFamRoom = (EditText) findViewById(R.id.editTextBookRoomFamRoom);
//        editTextBookRoomSingleRoom = (EditText) findViewById(R.id.editTextBookRoomSingleRoom);
//        editTextBookRoomApt = (EditText) findViewById(R.id.editTextBookRoomApt);

//        buttonBookRoomFamRoom = (Button) findViewById(R.id.buttonBookRoomFamRoom);
//        buttonBookRoomSingleRoom = (Button) findViewById(R.id.buttonBookRoomSingleRoom);
//        buttonBookRoomApt = (Button) findViewById(R.id.buttonBookRoomApt);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        buttonBookRoomBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vacanciesEntered = editTextBookRoomBed.getText().toString().trim();
                if (!TextUtils.isEmpty(vacanciesEntered)) {
                    final Integer vacanciesWanted = Integer.parseInt(vacanciesEntered);
                    if (s.getVacancies() >= vacanciesWanted) {
                        if (person.getReservation().length() == 0) {
                            databaseShelters.child(s.getShelterName()).child("vacancies").setValue(s.getVacancies() - vacanciesWanted);
                            databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
                            databasePeople.child(person.getUsername()).child("numReservations").setValue(vacanciesWanted);
                            Toast.makeText(BookRoomActivity.this, "Beds Booked!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
                            startActivity(intent);
                        } else {
                            builder.setMessage("You currently have spaces booked at another shelter. You cannot occupy spaces at multiple shelters. Would you like to remove your reservations at that shelter?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // update old shelter's vacancies
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

                                            // update new shelter's vacancies
                                            DatabaseReference ref2 = databaseShelters.child(s.getShelterName()).child("vacancies");
                                            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Integer vac = dataSnapshot.getValue(Integer.class);
                                                    databaseShelters.child(s.getShelterName()).child("vacancies").setValue(vac - vacanciesWanted);
                                                    databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
                                                    databasePeople.child(person.getUsername()).child("numReservations").setValue(vacanciesWanted);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            Toast.makeText(BookRoomActivity.this, "Beds Booked!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
                                            intent.putExtra("person", person);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    } else {
                        Toast.makeText(BookRoomActivity.this, "Not Enough Vacancies", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(BookRoomActivity.this, "Enter # of Family Rooms Desired", Toast.LENGTH_LONG).show();
                }
            }
        });

//        buttonBookRoomFamRoom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String famVacanciesEntered = editTextBookRoomFamRoom.getText().toString().trim();
//                if (!TextUtils.isEmpty(famVacanciesEntered)) {
//                    final Integer famVacanciesWanted = Integer.parseInt(famVacanciesEntered);
//                    if (s.getFamilyVacancies() >= famVacanciesWanted) {
//                        if (person.getReservation().length() == 0) {
//                            databaseShelters.child(s.getShelterName()).child("familyVacancies").setValue(s.getFamilyVacancies() - famVacanciesWanted);
//                            databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
//                            databasePeople.child(person.getUsername()).child("numReservations").setValue(famVacanciesWanted);
//                            Toast.makeText(BookRoomActivity.this, "Family Rooms Booked!", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
//                            startActivity(intent);
//                        } else {
//                            builder.setMessage("You currently have spaces booked at another shelter. You cannot occupy spaces at multiple shelters. Would you like to remove your reservations at that shelter?")
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            // update old shelter's vacancies
//                                            DatabaseReference ref = databaseShelters.child(person.getReservation()).child("familyVacancies");
//                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    Integer vac = dataSnapshot.getValue(Integer.class);
//                                                    databaseShelters.child(person.getReservation()).child("familyVacancies").setValue(vac + person.getNumReservations());
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//                                            // update new shelter's vacancies
//                                            DatabaseReference ref2 = databaseShelters.child(s.getShelterName()).child("familyVacancies");
//                                            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    Integer vac = dataSnapshot.getValue(Integer.class);
//                                                    databaseShelters.child(s.getShelterName()).child("familyVacancies").setValue(vac - famVacanciesWanted);
//                                                    databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
//                                                    databasePeople.child(person.getUsername()).child("numReservations").setValue(famVacanciesWanted);
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            Toast.makeText(BookRoomActivity.this, "Family Rooms Booked!", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
//                                            intent.putExtra("person", person);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                            AlertDialog alert = builder.create();
//                            alert.show();
//                        }
//                    } else {
//                        Toast.makeText(BookRoomActivity.this, "Not Enough Vacancies", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(BookRoomActivity.this, "Enter # of Family Rooms Desired", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        buttonBookRoomSingleRoom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String singleVacanciesEntered = editTextBookRoomSingleRoom.getText().toString().trim();
//                if (!TextUtils.isEmpty(singleVacanciesEntered)) {
//                    final Integer singleVacanciesWanted = Integer.parseInt(singleVacanciesEntered);
//                    if (s.getSingleVacancies() >= singleVacanciesWanted) {
//                        if (person.getReservation().length() == 0) {
//                            databaseShelters.child(s.getShelterName()).child("singleVacancies").setValue(s.getSingleVacancies() - singleVacanciesWanted);
//                            databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
//                            Toast.makeText(BookRoomActivity.this, "Single Spaces Booked!", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
//                            startActivity(intent);
//                        } else {
//                            builder.setMessage("You currently have spaces booked at another shelter. You cannot occupy spaces at multiple shelters. Would you like to remove your reservations at that shelter?")
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            // update old shelter's vacancies
//                                            DatabaseReference ref = databaseShelters.child(person.getReservation()).child("singleVacancies");
//                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    Integer vac = dataSnapshot.getValue(Integer.class);
//                                                    databaseShelters.child(person.getReservation()).child("singleVacancies").setValue(vac + person.getNumReservations());
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//                                            // update new shelter's vacancies
//                                            DatabaseReference ref2 = databaseShelters.child(s.getShelterName()).child("singleVacancies");
//                                            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    Integer vac = dataSnapshot.getValue(Integer.class);
//                                                    databaseShelters.child(s.getShelterName()).child("singleVacancies").setValue(vac - singleVacanciesWanted);
//                                                    databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
//                                                    databasePeople.child(person.getUsername()).child("numReservations").setValue(singleVacanciesWanted);
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            Toast.makeText(BookRoomActivity.this, "Single Beds Booked!", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
//                                            intent.putExtra("person", person);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                            AlertDialog alert = builder.create();
//                            alert.show();
//                        }
//                    } else {
//                        Toast.makeText(BookRoomActivity.this, "Not Enough Vacancies", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(BookRoomActivity.this, "Enter # of Single Spaces Desired", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        buttonBookRoomApt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String aptVacanciesEntered = editTextBookRoomApt.getText().toString().trim();
//                if (!TextUtils.isEmpty(aptVacanciesEntered)) {
//                    final Integer aptVacanciesWanted = Integer.parseInt(aptVacanciesEntered);
//                    if (s.getApartmentVacancies() >= aptVacanciesWanted) {
//                        if (person.getReservation().length() == 0) {
//                            databaseShelters.child(s.getShelterName()).child("apartmentVacancies").setValue(s.getApartmentVacancies() - aptVacanciesWanted);
//                            databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
//                            Toast.makeText(BookRoomActivity.this, "Apartments Booked!", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
//                            startActivity(intent);
//                        } else {
//                            builder.setMessage("You currently have spaces booked at another shelter. You cannot occupy spaces at multiple shelters. Would you like to remove your reservations at that shelter?")
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            // update old shelter's vacancies
//                                            DatabaseReference ref = databaseShelters.child(person.getReservation()).child("familyVacancies");
//                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    Integer vac = dataSnapshot.getValue(Integer.class);
//                                                    databaseShelters.child(person.getReservation()).child("apartmentVacancies").setValue(vac + person.getNumReservations());
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//                                            // update new shelter's vacancies
//                                            DatabaseReference ref2 = databaseShelters.child(s.getShelterName()).child("apartmentVacancies");
//                                            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                    Integer vac = dataSnapshot.getValue(Integer.class);
//                                                    databaseShelters.child(s.getShelterName()).child("apartmentVacancies").setValue(vac - aptVacanciesWanted);
//                                                    databasePeople.child(person.getUsername()).child("reservation").setValue(s.getShelterName());
//                                                    databasePeople.child(person.getUsername()).child("numReservations").setValue(aptVacanciesWanted);
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            Toast.makeText(BookRoomActivity.this, "Apartments Booked!", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(BookRoomActivity.this, MainUserActivity.class);
//                                            intent.putExtra("person", person);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                            AlertDialog alert = builder.create();
//                            alert.show();
//                        }
//                    } else {
//                        Toast.makeText(BookRoomActivity.this, "Not Enough Vacancies", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(BookRoomActivity.this, "Enter # of Apartments Desired", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }
}
