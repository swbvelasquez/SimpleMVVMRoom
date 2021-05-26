package com.tutoriales.simplemvvmroom.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tutoriales.simplemvvmroom.R;
import com.tutoriales.simplemvvmroom.model.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerAdapter extends ListAdapter<Note,NoteRecyclerAdapter.NoteViewHolder> {
    private OnItemClickListener listener;

    public NoteRecyclerAdapter() {
        super(diffCallback);
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
        holder.bindViewHolder(getItem(position));
    }

    public Note getNote(int position){
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION && listener!=null) {
                listener.onItemClick(getItem(position));
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    private static final DiffUtil.ItemCallback<Note> diffCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {

            boolean result=false;

            if(oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getDescription().equals(newItem.getDescription())
                && oldItem.getPriority() == newItem.getPriority()){
                result=true;
            }

            return result;
        }
    };
}
