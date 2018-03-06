package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShelterActivity extends AppCompatActivity {
    EditText editTextShelterName;
    EditText editTextCapacity;
    EditText editTextRestrictions;
    EditText editTextLongitude;
    EditText editTextLatitude;
    EditText editTextAddress;
    EditText editTextShelterNotes;
    EditText editTextPhoneNumber;
    Button buttonAddShelterCancel;
    Button buttonAddShelterAdd;

    DatabaseReference databaseShelter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shelter);

        databaseShelter = FirebaseDatabase.getInstance().getReference("shelters");

        editTextShelterName = (EditText) findViewById(R.id.editTextShelterName);
        editTextCapacity = (EditText) findViewById(R.id.editTextCapacity);
        editTextRestrictions = (EditText) findViewById(R.id.editTextRestrictions);
        editTextLongitude = (EditText) findViewById(R.id.editTextLongitude);
        editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextShelterNotes = (EditText) findViewById(R.id.editTextShelterNotes);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        buttonAddShelterCancel = (Button) findViewById(R.id.buttonAddShelterCancel);
        buttonAddShelterAdd = (Button) findViewById(R.id.buttonAddShelterAdd);

        buttonAddShelterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddShelterActivity.this, MainAdminActivity.class);
                startActivity(intent);
            }
        });

        buttonAddShelterAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addShelter();
            }
        });
    }


    private void addShelter() {
        String shelterName = editTextShelterName.getText().toString().trim();
        String capacity = editTextCapacity.getText().toString().trim();
        String restrictions = editTextRestrictions.getText().toString().trim();
        String longitude = editTextLongitude.getText().toString().trim();
        String latitude = editTextLatitude.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String shelterNotes = editTextShelterNotes.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        if ((!TextUtils.isEmpty(shelterName))
                && (!TextUtils.isEmpty(shelterName))
                && (!TextUtils.isEmpty(restrictions))
                && (!TextUtils.isEmpty(address))
                && (!TextUtils.isEmpty(shelterNotes))
                && (!TextUtils.isEmpty(phoneNumber))
                && (!TextUtils.isEmpty(capacity))
                && (!TextUtils.isEmpty(longitude))
                && (!TextUtils.isEmpty(latitude))) {

            Float numlongitude = Float.parseFloat(longitude);
            Float numlatitude =  Float.parseFloat(latitude);
            String id = databaseShelter.push().getKey();
            Shelter shelter = new Shelter(shelterName, capacity, restrictions,
                    numlongitude, numlatitude, address, shelterNotes, phoneNumber);

            databaseShelter.child(id).setValue(shelter);
            Toast.makeText(this, "Shelter Added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddShelterActivity.this, MainAdminActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(AddShelterActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
        }
    }
}
