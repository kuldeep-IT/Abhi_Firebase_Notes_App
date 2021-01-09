package com.kuldeepFirebase.AbhiFirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassWord extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnForgot;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_word);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtForgotMail);
        btnForgot = findViewById(R.id.btnForgot);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtEmail.getText().toString().trim()))
                {
                    Toast.makeText(ForgotPassWord.this, "fill email", Toast.LENGTH_SHORT).show();
                }

                else {

                    mAuth.sendPasswordResetEmail(edtEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(ForgotPassWord.this, "Link sent to your email!", Toast.LENGTH_SHORT).show();

                                    Intent i1=new Intent(ForgotPassWord.this, MainActivity.class);
                                    startActivity(i1);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ForgotPassWord.this, "error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}