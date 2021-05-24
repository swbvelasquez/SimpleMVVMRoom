package com.tutoriales.simplemvvmroom.model.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tutoriales.simplemvvmroom.model.entities.Note;

import java.util.List;

public interface INoteRepository {
    void insert(Note note);
    void update(Note note);
    void delete(Note note);
    void deleteAllNotes();
    LiveData<List<Note>> getAllNotes();
}
