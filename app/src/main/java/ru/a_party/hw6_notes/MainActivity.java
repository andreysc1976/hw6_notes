package ru.a_party.hw6_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NoteListFragment noteListFragment = NoteListFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerNoteList,noteListFragment)
                .commit();

        if (savedInstanceState==null){
            //Note.generateDemoNotes();
            FirebaseHelper firebaseHelper = new FirebaseHelper();
            firebaseHelper.setOnDataUpdater(new FirebaseHelper.OnDataUpdater() {
                @Override
                public void onUpdate() {
                    noteListFragment.notifyDataChange();
                }
            });
            firebaseHelper.loadNotes();
        };

    }




}