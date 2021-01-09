package com.kuldeepFirebase.AbhiFirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNotes extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edtTitle,edtThoughts;
    private Button btnSave;
    private DatabaseReference databaseReference;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        edtTitle = findViewById(R.id.edtAddNoteTitle);
        edtThoughts = findViewById(R.id.edtAddNoteThought);
        btnSave = findViewById(R.id.btnAddNoteSave);

        pd=  new ProgressDialog( this);
        pd.setMessage("please wait...");

        databaseReference =FirebaseDatabase.getInstance().getReference("USERNOTES").child(MainActivity.currentUser.getUid());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = edtTitle.getText().toString();
                String thoughts = edtThoughts.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                Calendar calendar = Calendar.getInstance();
                String todaysDate=simpleDateFormat.format(calendar.getTime());

                if (!title.equalsIgnoreCase(""))
                {
                    if (!thoughts.equalsIgnoreCase(""))
                    {
                        pd.show();
//                        Toast.makeText(AddNotes.this, ""+MainActivity.currentUser.getUid(), Toast.LENGTH_SHORT).show();
                        String key = databaseReference.push().getKey();
                        Notes notes = new Notes(title,thoughts,todaysDate,key);

                        databaseReference.child(key).setValue(notes).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                pd.dismiss();

                                if (task.isSuccessful())
                                {
                                    Toast.makeText(AddNotes.this, "Saved!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(AddNotes.this, "failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                    else {
                        Toast.makeText(AddNotes.this, "enter thoughts!", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(AddNotes.this, "enter title!", Toast.LENGTH_SHORT).show();
                }

            }
        });




        toolbar = findViewById(R.id.addToolbar);

        toolbar.setTitle("Add Notes");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });

    }
}