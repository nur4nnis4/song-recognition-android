package com.example.asus.songrecognition;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private IdentifyFragment identifyFragment;
    private HistoryFragment historyFragment;
    private AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainNav =  findViewById(R.id.main_nav);
        mainFrame = findViewById(R.id.main_frame);
        identifyFragment = new IdentifyFragment();
        historyFragment = new HistoryFragment();
        aboutFragment =  new AboutFragment();

        setFragment(identifyFragment);
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_identify:
                        setFragment(identifyFragment);
                        return true;
                    case R.id.nav_history:
                        setFragment(historyFragment);
                        return true;
                    case R.id.nav_about:
                        setFragment(aboutFragment);
                        return true;
                    default:
                        return false;

                }
            }
        });
    }

    private void setFragment( Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
