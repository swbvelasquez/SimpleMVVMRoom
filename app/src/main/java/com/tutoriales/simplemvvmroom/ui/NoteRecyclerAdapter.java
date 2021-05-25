package com.tutoriales.simplemvvmroom.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tutoriales.simplemvvmroom.R;
import com.tutoriales.simplemvvmroom.model.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder> {

    private List<Note> noteList;

    public NoteRecyclerAdapter() {
        noteList=new ArrayList<>();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note_cardview,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerAdapter.NoteViewHolder holder, int position) {
        holder.bindViewHolder(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvDescription,tvPriority;

        public NoteViewHolder(View itemView){
            super(itemView);
            setView();
        }

        public void bindViewHolder(Note note){
            if(note!=null){
                tvPriority.setText(( String.valueOf(note.getPriority())));
                tvTitle.setText(note.getTitle());
                tvDescription.setText(note.getDescription());
            }
        }

        private void setView(){
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
