package com.kuldeepFirebase.AbhiFirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ProgressDialog pd;

    private EditText edtEmail, edtPass;
    private Button btnLogin;
    public static  FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            Intent i1 = new Intent(LoginActivity.this,UserNotes.class);
            startActivity(i1);
        }

        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Please wait...");

      edtEmail = findViewById(R.id.edtLoginEmail);
      edtPass = findViewById(R.id.edtLoginPass);

      btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();
                pd.show();
            }
        });

    }

    private void singIn()
    {
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        pd.dismiss();

                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "sign In successfull", Toast.LENGTH_SHORT).show();

                            Intent i1  =new Intent(LoginActivity.this,UserNotes.class);
                            startActivity(i1);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "error!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}