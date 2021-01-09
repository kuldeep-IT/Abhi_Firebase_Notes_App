package com.kuldeepFirebase.AbhiFirebase;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    private Context context;
    private ArrayList<Notes> dataList = new ArrayList<>();
    String noteId="";
    String noteDate="";
    private UserInterface userInterface;


    public NotesAdapter(Context context, ArrayList<Notes> dataList) {
        this.context=context;
        this.dataList=dataList;

        userInterface = (UserInterface)context;
    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(context).inflate(R.layout.row_recycler_all_notes,parent,false);
        NotesHolder notesHolder = new NotesHolder(view);

        return notesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {

        Notes notes = dataList.get(position);
        String title = notes.getNoteTitle();
        String thoughts = notes.getNoteDesc();
        String date = notes.getNoteDate();

        holder.textTitle.setText(title);
        holder.textThoughts.setText(thoughts);
        holder.textDate.setText(date);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes notes = dataList.get(position);
                showDialog(notes);

            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes notes = dataList.get(position);
                deleteNote(notes);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder
    {

        TextView textTitle;
        TextView textThoughts;
        TextView textDate;
        ImageView imgEdit;
        ImageView imgDelete;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);

             textTitle = (TextView) itemView.findViewById(R.id.textTitle);
             textThoughts = (TextView) itemView.findViewById(R.id.textThoughts);
             textDate = (TextView) itemView.findViewById(R.id.textDate);
             imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
             imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);

        }
    }

    public void showDialog(Notes notes)
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialogue_update_note);
        dialog.show();

        EditText edtTitle = dialog.findViewById(R.id.edtUpdateNoteTitle);
        EditText edtThoughts = dialog.findViewById(R.id.edtUpdateNoteThought);

        edtTitle.setText(notes.getNoteTitle());
        edtThoughts.setText(notes.getNoteDesc());

        Button btnUpdateDialog = dialog.findViewById(R.id.btnUpdateNote);

        btnUpdateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                Calendar calendar = Calendar.getInstance();
                noteDate = simpleDateFormat.format( calendar.getTime());
                noteId = notes.getNoteID();

               String title = edtTitle.getText().toString();
               String thoghts = edtThoughts.getText().toString();

               Notes notes1 = new Notes(title,thoghts,noteDate,noteId);

               userInterface.updateUserNoteInterface(notes1);

               dialog.dismiss();

            }
        });
    }

    public void deleteNote(Notes notes)
    {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.show();

        TextView yes = (TextView) dialog.findViewById(R.id.txt_delete_yes);
        TextView no = (TextView) dialog.findViewById(R.id.txt_delete_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                userInterface.deleteUserNoteInterface(notes);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
