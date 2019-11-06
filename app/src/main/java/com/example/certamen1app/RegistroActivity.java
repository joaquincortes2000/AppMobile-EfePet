package com.example.certamen1app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "RegistroActivity";
    private EditText etEmail;
    private EditText etPass;
    private EditText etPass2;

    //Declarar firebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //EditText
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etPass2 = findViewById(R.id.etPass2);

        //Buttons
        findViewById(R.id.buttonCrearCuenta).setOnClickListener(this);
        findViewById(R.id.buttonVerificar).setOnClickListener(this);

        //Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

    }//Cierre onCreate


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void crearCuenta(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validarForm()) {
            return;
        }

        // Inicio método Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistroActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            findViewById(R.id.buttonVerificar).setEnabled(!user.isEmailVerified());
        }
    }


    private void enviarVerificacion() {
        // Disable button
        findViewById(R.id.buttonVerificar).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        findViewById(R.id.buttonVerificar).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegistroActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validarForm() {
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
        String pass2 = etPass2.getText().toString();
        if (TextUtils.isEmpty(pass)){
            etPass.setError("Campo obligatorio");
            valido = false;
        }else if(pass.equals(pass2)){
            etPass.setError(null);
        } else {
            etPass2.setError("Las contraseñas no coinciden");
        }

        return valido;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.buttonCrearCuenta){
            crearCuenta(etEmail.getText().toString(), etPass.getText().toString());
        }else if(i == R.id.buttonVerificar){
            enviarVerificacion();
        }
    }
}
