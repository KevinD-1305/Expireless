package com.example.foodwasteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText email_editText, password_editTxt;
    private Button signIn_Btn, register_Btn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        email_editText = findViewById(R.id.email_editTxt);
        password_editTxt = findViewById(R.id.password_editTxt);
        signIn_Btn = findViewById(R.id.signIn_btn);
        register_Btn = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progressBar);

        signIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()) return;
                inProgress((true));
                mAuth.signInWithEmailAndPassword(email_editText.getText().toString(),
                        password_editTxt.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignIn.this,"Sign In Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignIn.this, Fridge.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish(); return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignIn.this, "Sign In failed." + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()) return;
                inProgress((true));
                mAuth.createUserWithEmailAndPassword(email_editText.getText().toString(),
                        password_editTxt.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignIn.this,"User Registered Successfully",Toast.LENGTH_LONG).show();
                                inProgress(false);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignIn.this, "Registration Failed." + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void inProgress(boolean x) {
        if(x) {
            progressBar.setVisibility(View.VISIBLE);
            signIn_Btn.setEnabled(false);
            register_Btn.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            signIn_Btn.setEnabled(true);
            register_Btn.setEnabled(true);
        }
    }
    private boolean isEmpty() {
        if(TextUtils.isEmpty(email_editText.getText().toString())){
            return true;
        }
        if(TextUtils.isEmpty(password_editTxt.getText().toString())){
            return true;
        }
        return false;
    }
}
