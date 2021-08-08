package ru.a_party.hw6_notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private onItemClickListener mListener;



    public void setListener(@NonNull onItemClickListener onItemClickListener){
        this.mListener=onItemClickListener;
    }



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_note,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.noteName.setText(Note.getNotes().get(position).getName());
        holder.noteDate.setText(Note.getNotes().get(position).getFormatedDate());
    }

    @Override
    public int getItemCount() {
        if (Note.getNotes()==null) return 0;
        return Note.getNotes().size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteName;
        TextView noteDate;

        public NoteViewHolder(@NonNull View itemView) {

            super(itemView);
            noteName = itemView.findViewById(R.id.textViewListNoteName);
            noteDate = itemView.findViewById(R.id.textViewListNoteDateTime);
            itemView.setOnClickListener(v -> mListener.onItemClick(getAdapterPosition()));
        }
    }

    interface onItemClickListener{
        void onItemClick(int position);
    }
}
