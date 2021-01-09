package com.kuldeepFirebase.AbhiFirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserNotes extends AppCompatActivity implements UserInterface{

    private TextView txtUsertxt;
    private Toolbar toolbar;
    private FloatingActionButton btnFloating;

    private ArrayList<Notes> allNotes;
    private DatabaseReference databaseReference;

    private DatabaseReference databaseReferenceForUserName;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notes);

        recyclerView = findViewById(R.id.add_all_notes);

        allNotes = new ArrayList<>();
        pd= new ProgressDialog(this);

        toolbar = findViewById(R.id.toolbar);

        String uid = MainActivity.currentUser.getUid();

        UserInfo userInfo = new UserInfo();

//        String uName = getIntent().getStringExtra("username");
//
        toolbar.setTitle("User Notes");
        toolbar.setTitleTextColor(Color.WHITE);

        txtUsertxt = findViewById(R.id.txtUsertxt);
        btnFloating = findViewById(R.id.btnFloating);


//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference("USERNOTES").child(uid);

//        databaseReferenceForUserName = FirebaseDatabase.getInstance().getReference("USERS").child(uid);
//        toolbar.setTitle(databaseReferenceForUserName.+ " Notes");

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//        readAllNotes();

      btnFloating.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent i1 = new Intent(UserNotes.this,AddNotes.class);
              startActivity(i1);

          }
      });

    }

    @Override
    protected void onStart() {
        super.onStart();

        readAllNotes();
    }

    private void readAllNotes() {

        allNotes.clear();
        pd.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pd.dismiss();

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                      Notes notes = dataSnapshot.getValue(Notes.class);
                      allNotes.add(notes);
                }

                NotesAdapter notesAdapter =  new NotesAdapter(UserNotes.this,allNotes);
                recyclerView.setAdapter(notesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UserNotes.this, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public void updateUserNoteInterface(Notes notes) {
        databaseReference.child(notes.noteID).setValue(notes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(UserNotes.this, "Notes updated successfully!", Toast.LENGTH_SHORT).show();
                    readAllNotes();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserNotes.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteUserNoteInterface(Notes notes) {
        databaseReference.child(notes.noteID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(UserNotes.this, "deleted Successfully!", Toast.LENGTH_SHORT).show();
                    readAllNotes();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserNotes.this, "error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}