package com.example.myapplicationzzzzz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText loginR, passwordR;
    private Button signUp;
    private String email, password;
    private FirebaseAuth firebaseAuth;
    private TextView tvNoAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        loginR = findViewById(R.id.etLogin);
        passwordR = findViewById(R.id.etPassword);
        signUp = findViewById(R.id.btnLogin);
        tvNoAcc = findViewById(R.id.tvNoAcc);

        tvNoAcc.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Auth.class));
        });

        signUp.setOnClickListener(v -> {
            validData();
        });
    }

    private void validData(){
       email = loginR.getText().toString().trim();
       password = passwordR.getText().toString().trim();

       if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           loginR.setError("НЕПРАВИЛЬНО ВВЕДЕНА ПОЧТА");
       }
       else if (TextUtils.isEmpty(password)){
           passwordR.setError("Пароль не должен быть пустым");
       }
       else if (password.length() < 8){
           passwordR.setError("Пароль не должен быть меньше 8 символов");
       }
       else{
           firebaseRegister();
       }
    }

    private void firebaseRegister(){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            Toast.makeText(Register.this, "Успешно зарегались!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Register.this, Auth.class));
        }).addOnFailureListener(e -> {
            Toast.makeText(Register.this, "Ошибка " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}