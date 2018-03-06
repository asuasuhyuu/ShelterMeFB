package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterScreenActivity extends AppCompatActivity {

    EditText editTextRegisterName;
    EditText editTextRegisterUsername;
    EditText editTextRegisterPassword;
    Button buttonRegister;
    Spinner spinnerRegisterMode;
    Button buttonRegisterCancel;

    DatabaseReference databasePerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        databasePerson = FirebaseDatabase.getInstance().getReference("people");
        editTextRegisterName = (EditText) findViewById(R.id.editTextRegisterName);
        editTextRegisterUsername = (EditText) findViewById(R.id.editTextRegisterUsername);
        editTextRegisterPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        spinnerRegisterMode = (Spinner)  findViewById(R.id.spinnerRegisterMode);
        buttonRegisterCancel = (Button) findViewById(R.id.buttonRegisterCancel);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        buttonRegisterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterScreenActivity.this, "Cancelling login attempt", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addUser() {
        String name = editTextRegisterName.getText().toString().trim();
        String username = editTextRegisterUsername.getText().toString().trim();
        String password = editTextRegisterPassword.getText().toString().trim();
        String mode = spinnerRegisterMode.getSelectedItem().toString();

        if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(username)) && (!TextUtils.isEmpty(password))) {
            String id = databasePerson.push().getKey();
            Person person = new Person(id, name, username, password, mode);
            databasePerson.child(id).setValue(person);
            Toast.makeText(this, "Account created", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegisterScreenActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
        }
    }

}
