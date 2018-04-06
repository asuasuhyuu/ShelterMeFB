package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonMainLogin;
    Button buttonMainRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMainLogin = (Button) findViewById(R.id.buttonMainLogin);
        buttonMainRegister = (Button) findViewById(R.id.buttonMainRegister);

        buttonMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginScreenActivity.class);
                startActivity(intent);
            }
        });

        buttonMainRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterScreenActivity.class);
                startActivity(intent);
            }
        });

    }
}
