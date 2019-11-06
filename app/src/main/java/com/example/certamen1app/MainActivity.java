package com.example.certamen1app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declarar firebaseAuth
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Buttons
        findViewById(R.id.buttonLogout).setOnClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.navigation_home:

                        break;
                    case R.id.navigation_dashboard:

                        break;
                    case R.id.navigation_notifications:

                        break;
                }

                return true;
            }
        });

    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent i = new Intent(MainActivity.this,MainActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.buttonLogout){
            signOut();
        }
    }
}
