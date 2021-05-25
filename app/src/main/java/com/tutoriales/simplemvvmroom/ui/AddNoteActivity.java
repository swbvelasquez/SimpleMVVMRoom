package com.tutoriales.simplemvvmroom.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.tutoriales.simplemvvmroom.R;

public class AddNoteActivity extends AppCompatActivity {

    private EditText etTitle,etDescription;
    private NumberPicker npPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        boolean result;
        if(itemId==R.id.itmSave){
            saveNote();
            result=true;
        }else{
            result=super.onOptionsItemSelected(item);
        }
        return result;
    }

    private void saveNote(){
        Intent intent = new Intent();
        intent.putExtra("note_title",etTitle.getText().toString());
        intent.putExtra("note_description",etDescription.getText().toString());
        intent.putExtra("note_priority",npPriority.getValue());
        setResult(RESULT_OK,intent);
        finish();
    }

    private void setView(){
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        npPriority = findViewById(R.id.npPriority);
        npPriority.setMinValue(1);
        npPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
    }
}