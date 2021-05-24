package com.tutoriales.simplemvvmroom.model.databases;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tutoriales.simplemvvmroom.model.entities.Note;
import com.tutoriales.simplemvvmroom.model.interfaces.NoteDao;

public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance;

    public abstract NoteDao noteDao();

    //synchronized para que solo se acceda a esta clase desde diferentes hilos a la vez
    public static synchronized AppDataBase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"NoteDataBase")
                            .fallbackToDestructiveMigration() //desactivar migraciones
                            .addCallback(roomCallBack) //agregando el callback a usarse
                            .build();
        }

        return instance;
    }

    //callback para realizar algo cuando la base de datos se cree, solo se llama 1 vez
    private static RoomDatabase.Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //asynckTask para llenar la base de datos
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private PopulateDbAsyncTask(AppDataBase dataBase) {
            noteDao = dataBase.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Note note;

            note= new Note();
            note.setTitle("title 1");
            note.setDescription("description 1");
            note.setPriority(1);
            noteDao.insert(note);

            new Note();
            note.setTitle("title 2");
            note.setDescription("description 2");
            note.setPriority(2);
            noteDao.insert(note);
            return null;
        }
    }
}
