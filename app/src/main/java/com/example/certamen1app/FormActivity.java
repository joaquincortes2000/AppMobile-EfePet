package com.example.certamen1app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.certamen1app.helper.BD;
import com.example.certamen1app.model.mascota;


public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase bd;
    EditText etId;
    EditText etNombre;
    EditText etRaza;
    EditText etEdad;
    private String nombre, raza, edad, id;
    Button btnAgregar, btnModificar, btnEliminar, btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        findViewById(R.id.btnHome).setOnClickListener(this);
        findViewById(R.id.btnMaps).setOnClickListener(this);
        findViewById(R.id.btnForm).setOnClickListener(this);

        final BD bdmascotas = new BD(getApplicationContext());
        bd = bdmascotas.getWritableDatabase();

        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etRaza);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = etNombre.getText().toString();
                raza = etRaza.getText().toString();
                edad = etEdad.getText().toString();

                mascota mascota = new mascota();
                mascota.setNombre(nombre);
                mascota.setRaza(raza);
                mascota.setEdad(edad);

                ContentValues values = new ContentValues();

                values.put(BD.DatosTabla.COLUMNA_NOMBRE, mascota.getNombre());
                values.put(BD.DatosTabla.COLUMNA_RAZA, mascota.getRaza());
                values.put(BD.DatosTabla.COLUMNA_EDAD, mascota.getEdad());

                long newRowId = bd.insert(BD.DatosTabla.NOMBRE_TABLA, BD.DatosTabla.COLUMNA_ID, values);
                Toast.makeText(FormActivity.this,"Se guardó la mascota: "+ newRowId,Toast.LENGTH_SHORT).show();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mascota mascota = new mascota();
                bdmascotas.buscarMascota(mascota, etId.getText().toString());
                etNombre.setText(mascota.getNombre());
                etRaza.setText(mascota.getRaza());
                etEdad.setText(mascota.getEdad());
                Toast.makeText(FormActivity.this,"Mascota encontrada exitosamente",Toast.LENGTH_SHORT).show();


            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdmascotas.editarMascota(etId.getText().toString(), etNombre.getText().toString(), etRaza.getText().toString(), etEdad.getText().toString());
                Toast.makeText(FormActivity.this,"Mascota actualizada exitosamente", Toast.LENGTH_SHORT).show();

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdmascotas.eliminarMascota(etId.getText().toString());
                Toast.makeText(FormActivity.this,"Mascota eliminada exitosamente", Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.btnForm){
            Intent formActivity = new Intent(FormActivity.this, FormActivity.class);
            startActivity(formActivity);
        }else if(i == R.id.btnHome){
            finish();
        }else if(i == R.id.btnMaps){
            Intent mapsActivity = new Intent(FormActivity.this, MapsActivity.class);
            startActivity(mapsActivity);
        }
    }


}
