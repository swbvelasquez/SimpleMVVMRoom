package com.tutoriales.simplemvvmroom.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tutoriales.simplemvvmroom.R;
import com.tutoriales.simplemvvmroom.model.entities.Note;
import com.tutoriales.simplemvvmroom.viewmodel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private FloatingActionButton fbaAdd;
    private RecyclerView rvNotes;
    private NoteRecyclerAdapter rvNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        boolean result;
        if(itemId==R.id.itmDelete){
            deleteAllNotes();
            result=true;
        }else{
            result=super.onOptionsItemSelected(item);
        }
        return result;
    }

    private void deleteAllNotes(){
        noteViewModel.deleteAllNotes();
    }

    private void setView(){

        //nueva forma de invocar activitys for result
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_OK){
                            String title = result.getData().getStringExtra("note_title");
                            String description = result.getData().getStringExtra("note_description");
                            int priority = result.getData().getIntExtra("note_priority",0);

                            Note note = new Note();
                            note.setTitle(title);
                            note.setDescription(description);
                            note.setPriority(priority);
                            noteViewModel.insert(note);
                        }
                    }
                }
        );

        fbaAdd = findViewById(R.id.fabAdd);
        fbaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                launcher.launch(intent);
            }
        });

        //configurando el recycler
        rvNotes = findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        rvNoteAdapter = new NoteRecyclerAdapter();
        rvNotes.setAdapter(rvNoteAdapter);

        //animacion para el recycler
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //si mueve a la derecha o izquierda, borra la nota
                if(direction==ItemTouchHelper.LEFT || direction==ItemTouchHelper.RIGHT){
                    noteViewModel.delete(rvNoteAdapter.getNote(viewHolder.getAdapterPosition()));
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(rvNotes);

        //configurando el view model
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> noteList) {
                //update ui
                rvNoteAdapter.setNotes(noteList);
            }
        });
    }
}