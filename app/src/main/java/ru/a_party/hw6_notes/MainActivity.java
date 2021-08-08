package ru.a_party.hw6_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    public static GoogleSignInOptions gso=null;

    private static final int RC_SIGN_IN=45542432;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        if (account == null){
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        setContentView(R.layout.activity_main);

        NoteListFragment noteListFragment = NoteListFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerNoteList,noteListFragment)
                .commit();

        if (savedInstanceState==null){
            //Note.generateDemoNotes();
            FirebaseHelper firebaseHelper = new FirebaseHelper();
            firebaseHelper.setOnDataUpdater(() -> noteListFragment.notifyDataChange());
            firebaseHelper.loadNotes();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        GoogleSignInAccount account = completedTask.getResult();
        //в теории теперь можно из акка взять например мыло, и использовать как префикс
        //для хранения заметок, и таким образом заметки станут видны только для конкретного
        //пользователя google
    }
}