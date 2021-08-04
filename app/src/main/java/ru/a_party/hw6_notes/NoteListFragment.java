package ru.a_party.hw6_notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class NoteListFragment extends Fragment {

    private boolean isLandscape = false;
    private RecyclerView recyclerView;
    private NoteListAdapter noteListAdapter;

    public static NoteListFragment newInstance() {
        return new NoteListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE;
        recyclerView = view.findViewById(R.id.note_recyler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        noteListAdapter = new NoteListAdapter();
        recyclerView.setAdapter(noteListAdapter);
        noteListAdapter.setListener(position -> showNote(Note.getNotes().get(position)));



    }

    private void showNote(Note note) {
        if (isLandscape) {
            showNoteLand(note);
        } else {
            showNotePort(note);
        }
    }


    private void showNoteLand(Note note){
        NoteFullFragment noteFragment = NoteFullFragment.newInstance(note);
        noteFragment.setUpdater(() -> noteListAdapter.notifyDataSetChanged());
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.landscapeContainer, noteFragment)
                        .commit();
    }

    private void showNotePort(Note note){
        NoteFullFragment noteFragment = NoteFullFragment.newInstance(note);
        noteFragment.setUpdater(() -> noteListAdapter.notifyDataSetChanged());
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerNoteList,noteFragment)
                .addToBackStack(null)
                .commit();
    }

    interface NoteUpdater{
        void update();
    }
}