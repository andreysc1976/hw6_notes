package ru.a_party.hw6_notes;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null) Note.generateDemoNotes();
        setContentView(R.layout.activity_main_m);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openStr, R.string.closeStr);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_dehaze_24, this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.itemAbout){
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerNoteList,AboutFragment.newInstance())
                        .commit();
            } else if (item.getItemId()==R.id.itemExit){
                finish();
            }
            return false;
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerNoteList,NoteListFragment.newInstance())
                .commit();


    }



}