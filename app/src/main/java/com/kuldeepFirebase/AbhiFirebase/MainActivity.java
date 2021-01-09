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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity   {

    private EditText edtEmail, edtUserName, edtPass;
    private Button btnSinUp, btnLogin,btnForgotpass,btnMainOTP;
    public FirebaseAuth mAuth;

    private DatabaseReference databaseUser;

    private ProgressDialog pd;
    public static FirebaseUser currentUser;

   public  String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtUserName = findViewById(R.id.edtUserName);

//        username = edtUserName.getText().toString();
//        Intent i5= new Intent(MainActivity.this,UserNotes.class);
//        i5.putExtra("username",username);
//        startActivity(i5);

        btnSinUp = findViewById(R.id.btnSingUp);
        btnLogin = findViewById(R.id.btnLoginMain);
        btnForgotpass = findViewById(R.id.btnForgetPass);
        btnMainOTP = findViewById(R.id.btnMainOtp);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        databaseUser = FirebaseDatabase.getInstance().getReference("USERS");

        if (currentUser != null)
        {
            Intent i1= new Intent(MainActivity.this,UserNotes.class);
            startActivity(i1);

            finish();
        }

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Please wait...");



        btnSinUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singUp();
                pd.show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i1);
            }
        });

        btnForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this,ForgotPassWord.class);
                startActivity(i1);
            }
        });

        btnMainOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this,OTP.class);
                startActivity(i1);
            }
        });
//
//        if (getIntent().hasExtra("MOBILE"))
//        {
//            edtPass.setVisibility(View.GONE);
//        }
//        else
//        {
//            edtPass.setVisibility(View.VISIBLE);
//
//        }
    }

    private void singUp() {
        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        pd.dismiss();

                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Successfull sing Up", Toast.LENGTH_SHORT).show();

//                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            String uid = currentUser.getUid();



                            UserInfo userInfo = new UserInfo(edtUserName.getText().toString(),
                                    edtEmail.getText().toString(),"");

                            databaseUser.child(uid).setValue(userInfo);


//                            FirebaseDatabase.getInstance().getReference().
//                                    child("my_user").
//                                    child(task.getResult().getUser().getUid()).
//                                    child("username").setValue(edtUserName.getText().toString());


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, task.toString()+"error!", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }


}