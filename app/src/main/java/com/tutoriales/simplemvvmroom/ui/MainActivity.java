package com.tutoriales.simplemvvmroom.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tutoriales.simplemvvmroom.R;
import com.tutoriales.simplemvvmroom.model.entities.Note;
import com.tutoriales.simplemvvmroom.viewmodel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setView(){


        RecyclerView rvNotes = findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        NoteRecyclerAdapter noteAdapter = new NoteRecyclerAdapter();
        rvNotes.setAdapter(noteAdapter);

        //configurando el view model
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> noteList) {
                //update ui
                noteAdapter.setNotes(noteList);
            }
        });
    }
}