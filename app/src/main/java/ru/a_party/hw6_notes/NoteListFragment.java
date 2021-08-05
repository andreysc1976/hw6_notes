package ru.a_party.hw6_notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;


import java.util.Date;
import java.util.UUID;


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
        setHasOptionsMenu(true);
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
        //noteListAdapter.setListener(position -> showNote(Note.getNotes().get(position)));
        noteListAdapter.setListener(position -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view, Gravity.CENTER);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case (R.id.itemPopUpEdit):
                            showNote(Note.getNotes().get(position));
                            break;
                        case(R.id.itemPopUpDelete):
                            Note.deleteByIndex(position);
                            noteListAdapter.notifyDataSetChanged();
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        });



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
                .addToBackStack(null)
                .replace(R.id.fragmentContainerNoteList,noteFragment)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.addNoteMenuItem):
                Note note = new Note(UUID.randomUUID(),"",new Date(),"");
                showNote(note);
                break;
        }
        return true;
    }

    interface NoteUpdater{
        void update();
    }
}