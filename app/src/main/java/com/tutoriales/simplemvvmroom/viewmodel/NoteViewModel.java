package com.tutoriales.simplemvvmroom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tutoriales.simplemvvmroom.model.entities.Note;
import com.tutoriales.simplemvvmroom.model.interfaces.INoteRepository;
import com.tutoriales.simplemvvmroom.model.repositories.NoteRepository;

import java.util.List;

//Extiende de AndroidViewModel para poder pasarle el contexto de la app
public class NoteViewModel extends AndroidViewModel {

    private INoteRepository repository;
    private LiveData<List<Note>> noteList;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        noteList = repository.getAllNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes(){
        return noteList;
    }
}
