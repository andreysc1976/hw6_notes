package ru.a_party.hw6_notes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirebaseHelper {
    private static final String COLLECTION_NAME = "notes";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OnDataUpdater mOnDataUpdater;

    public void setOnDataUpdater(OnDataUpdater onDataUpdater){
        mOnDataUpdater=onDataUpdater;
    }

    public void saveNote(Note note){
        db.collection(COLLECTION_NAME)
                .document(note.getUuid().toString())
                .set(note);
    }

    public void loadNotes() {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Note> notes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                notes.add(document.toObject(Note.class));
                            }
                            Note.setNotes(notes);
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
                        if (mOnDataUpdater!=null){
                            mOnDataUpdater.onUpdate();
                        }
                    }
                });
    }

    public void removeNote(Note note) {
        db.collection(COLLECTION_NAME)
                .document(note.getUuid())
                .delete();
    }

    interface OnDataUpdater{
        void onUpdate();
    }

}
