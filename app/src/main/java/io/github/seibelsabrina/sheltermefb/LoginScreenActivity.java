package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

public class LoginScreenActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLoginScreenLogin;
    Button buttonLoginScreenCancel;
    DatabaseReference databasePeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        databasePeople = FirebaseDatabase.getInstance().getReference("people");

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLoginScreenLogin = (Button) findViewById(R.id.buttonLoginScreenLogin);
        buttonLoginScreenCancel = (Button) findViewById(R.id.buttonLoginScreenCancel);


        buttonLoginScreenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            databasePeople.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean isFound = false;
                    for (DataSnapshot personSnapshot: dataSnapshot.getChildren()) {
                        if (!isFound) {
                            Person person = personSnapshot.getValue(Person.class);
                            if (checkValidCredentials(person)) {
                                Toast.makeText(LoginScreenActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                isFound = true;
                            }
                        }
                    }
                    Toast.makeText(LoginScreenActivity.this, "Invalid Login", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            }
        });

        buttonLoginScreenCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginScreenActivity.this, "Cancelling login attempt", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkValidCredentials(Person person) {
        if (person.getUsername().equals(editTextUsername.getText().toString())
                && person.getPassword().equals(editTextPassword.getText().toString())) {

                // user is correctly logged in and is an admin
                if (person.getMode().equals("Admin")) {
                    //Toast.makeText(LoginScreenActivity.this, "SUCCESS ADMIN", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginScreenActivity.this, MainAdminActivity.class);
                    intent.putExtra("person", person);
                    startActivity(intent);
                    return true;

                } else {
                    Toast.makeText(LoginScreenActivity.this, "success user", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginScreenActivity.this, MainUserActivity.class);
                    intent.putExtra("person", person);
                    startActivity(intent);
                    return true;
                }
        } else {
            //Toast.makeText(LoginScreenActivity.this, "incorrect username or password", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
