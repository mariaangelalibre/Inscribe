package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotestakerActivity extends AppCompatActivity {
    EditText editText_Title, editText_notes;
    ImageView ImageView_Save;
    Notes notes;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notestaker);

        ImageView_Save = findViewById(R.id.ImageView_Save);
        editText_Title = findViewById(R.id.editText_Title);
        editText_notes = findViewById(R.id.editText_notes);

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_Title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNote = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        ImageView_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_Title.getText().toString();
                String description = editText_notes.getText().toString();

                if(description.isEmpty()){
                    Toast.makeText(NotestakerActivity.this, "Please add a note", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if (!isOldNote){
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}