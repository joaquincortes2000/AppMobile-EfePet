package com.example.certamen1app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "LoginActivity";
    private EditText etEmail;
    private EditText etPass;

    //Declarar FirebaseAuth
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //EditText
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        //Buttons
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.buttonRegistro).setOnClickListener(this);
        findViewById(R.id.buttonAnonimo).setOnClickListener(this);


        //Inicializar firebaseAuth
        mAuth = FirebaseAuth.getInstance();

    }//Fin onCreate



    @Override
    protected void onStart() {
        super.onStart();
        //Verificar si el usuario mantiene sesión activa y actualizar UI según corresponda
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

    }//Fin onStart

    private boolean validarUsuario(){
        boolean valido = true;

        String email = etEmail.getText().toString();
        //El uso de TextUtils siempre retornará un valor, el .isEmpty() puede generar NullPointerException en caso de que el String sea null
        if (TextUtils.isEmpty(email)){
            etEmail.setError("Campo obligatorio");
            valido = false;
        } else {
            etEmail.setError(null);
        }

        String pass = etPass.getText().toString();
        if (TextUtils.isEmpty(pass)){
            etPass.setError("Campo obligatorio");
            valido = false;
        }else{
            etPass.setError(null);
        }

        return valido;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "Algó pasó", Toast.LENGTH_SHORT).show();
        }
    }

    private void iniciarSesion(String email, String pass){
        if (!validarUsuario()){
            return;
        }

        // Inicio del LogIn
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void signInAnonymously() {

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.buttonAnonimo){
            signInAnonymously();
        }else if(i == R.id.buttonLogin){
            iniciarSesion(etEmail.getText().toString(), etPass.getText().toString());
        }else if(i == R.id.buttonRegistro){
            Intent registroIntent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(registroIntent);
        }
    }


}
