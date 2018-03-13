package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainUserActivity extends AppCompatActivity {

    Button buttonUserShelterList;
    Button buttonMainUserLogout;
    Button buttonUserShelterSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        buttonUserShelterList = (Button) findViewById(R.id.buttonUserShelterList);
        buttonMainUserLogout = (Button) findViewById(R.id.buttonMainUserLogout);
        buttonUserShelterSearch = (Button) findViewById(R.id.buttonUserShelterSearch);
        buttonUserShelterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainUserActivity.this, ShelterListActivity.class);
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
                startActivity(intent);
            }
        });
    }


}
