package com.example.certamen1app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.certamen1app.helper.BD;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declarar firebaseAuth
    private FirebaseAuth mAuth;
    private SQLiteDatabase bd;
    private Cursor c;

    ListView lista;
    private List<String> nombres;
    private List<String> raza;
    private List<String> edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Buttons
        findViewById(R.id.buttonLogout).setOnClickListener(this);
        findViewById(R.id.btnHome).setOnClickListener(this);
        findViewById(R.id.btnMaps).setOnClickListener(this);
        findViewById(R.id.btnForm).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        lista = (ListView) findViewById(R.id.lista2);
        final BD bdmascotas = new BD(getApplicationContext());
        c = bdmascotas.cursor();
        nombres = new ArrayList<String>();
        raza = new ArrayList<String>();
        edad = new ArrayList<String>();
        if (c.moveToFirst()) {

            do {
                nombres.add(c.getString(1));
                raza.add(c.getString(2));
                edad.add(c.getString(3));

            } while (c.moveToNext());


            MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, nombres, raza, edad);
            lista.setAdapter(myAdapter);

        }
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
        }else if(i == R.id.btnForm){
            Intent formActivity = new Intent(MainActivity.this, FormActivity.class);
            startActivity(formActivity);
        }else if(i == R.id.btnHome){
            Intent homeActivity = new Intent(MainActivity.this, MainActivity.class);
            startActivity(homeActivity);
        }else if(i == R.id.btnMaps){
            Intent mapsActivity = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(mapsActivity);
        }
    }



}

