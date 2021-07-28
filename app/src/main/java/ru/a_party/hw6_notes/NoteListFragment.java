package ru.a_party.hw6_notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class NoteListFragment extends Fragment {

    private boolean isLandscape = false;

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
        initView(view);
        setHasOptionsMenu(true);
    }



    private void initView(View view) {
        LinearLayout linearLayout = (LinearLayout)view;

        ArrayList<Note> notes = Note.getNotes();
        for (int i=0;i<notes.size();i++){
            TextView textView = new TextView(getContext());
            textView.setText(notes.get(i).getName());
            textView.setTextSize(24);
            linearLayout.addView(textView);


            final int finalI=i;
            textView.setOnClickListener(v -> showNote(notes.get(finalI)));


        }

    }

    private void showNote(Note note) {
        if (isLandscape) {
            showNoteLand(note);
        } else {
            showNotePort(note);
        }
    }


    private void showNoteLand(Note note){
        Fragment noteFragment = NoteFullFragment.newInstance(note);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.landscapeContainer,noteFragment)
                .commit();
    }

    private void showNotePort(Note note){
        Fragment noteFragment = NoteFullFragment.newInstance(note);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerNoteList,noteFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.itemMenuClear){
            Toast.makeText(getActivity().getBaseContext(),"Тут должно что то очиститься",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}