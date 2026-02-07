package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view,parent,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewNotes.setText(note.getText());
        holder.textViewNotes.setBackgroundColor(holder.itemView.getContext().getColor(note.getPriority()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteClickListener != null){
                    onNoteClickListener.onNoteClicked(note);
                    Toast.makeText(holder.itemView.getContext(),"Deleted "+note.getId(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

     class NotesViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNotes;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNotes = itemView.findViewById(R.id.note_text_view);
        }
    }

    interface OnNoteClickListener{
        void onNoteClicked(Note note);
    }
}
