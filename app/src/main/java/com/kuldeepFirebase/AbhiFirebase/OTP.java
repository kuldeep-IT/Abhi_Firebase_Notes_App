package com.kuldeepFirebase.AbhiFirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    private EditText edtMobileNumber;
    private Button btnOTP;
    private String mobileNumber="";
    public FirebaseAuth mAuth;

    private ProgressDialog pd;


//    #2 step
    PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        mAuth = FirebaseAuth.getInstance();

        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        btnOTP = findViewById(R.id.btnVerify);

        pd = new ProgressDialog(this);
        pd.setMessage("please wait...");


//        #2 step-2
        onVerificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                pd.dismiss();
                Toast.makeText(OTP.this, "success!", Toast.LENGTH_SHORT).show();
                signInwithMobile(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(OTP.this, "Failed! "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileNumber ="+91"+ edtMobileNumber.getText().toString();

                if (!mobileNumber.equalsIgnoreCase("") )
                {
                    verifyMobileNumber(mobileNumber);
                }
                else {
                    Toast.makeText(OTP.this, "please enter valid mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    #1 step
    private void verifyMobileNumber(String mobileNumber)
    {

        pd.show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNumber,60, TimeUnit.SECONDS,this,onVerificationStateChangedCallbacks);
    }

    private void signInwithMobile(PhoneAuthCredential phoneAuthCredential)
    {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    pd.dismiss();

                    Toast.makeText(OTP.this, "Success nd goto SingUp", Toast.LENGTH_SHORT).show();

                    FirebaseUser currentUser = task.getResult().getUser();
                    String uid = currentUser.getUid();

                    Intent i1 =  new Intent(OTP.this,MainActivity.class);
                    i1.putExtra("MOBILE",mobileNumber);
                    i1.putExtra("UID",uid);
                    startActivity(i1);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(OTP.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}