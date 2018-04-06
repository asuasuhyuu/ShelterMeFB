package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAdminActivity extends AppCompatActivity {

    Button buttonAddShelter;
    Button buttonMainAdminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        buttonAddShelter = (Button) findViewById(R.id.buttonAddShelterAdd);
        buttonMainAdminLogout = (Button) findViewById(R.id.buttonMainAdminLogout);

        buttonAddShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAdminActivity.this, AddShelterActivity.class);
                startActivity(intent);
            }
        });

        buttonMainAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
