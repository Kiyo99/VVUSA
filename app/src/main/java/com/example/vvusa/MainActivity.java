package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    int backButtonCount=0;

    BottomNavigationView navigationView;
    @Override
    public void onBackPressed() {

        if(backButtonCount>= 1)
        {
            finishAffinity();
            finish();
        }else {
            Toast.makeText(this, "Press the back button once again to exit the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//      setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.appbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.app_bar_search)
                {
                    // do something
                }
                else if(item.getItemId()== R.id.nav_settings)
                {
                    Intent kio = new Intent(MainActivity.this, SettingsActivity.class);;
                    startActivity(kio);
                }
                else if(item.getItemId()== R.id.profile){
                    //do something
                }
                else if(item.getItemId()== R.id.help){
                    //do something
                }
                else if(item.getItemId()== R.id.about){
                    Intent kio = new Intent(MainActivity.this, about.class);;
                    startActivity(kio);
                }
                else if(item.getItemId()== R.id.Logout){
                    FirebaseAuth.getInstance().signOut();
                    Intent kio = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(kio);
                    finish();

                }
                return false;
            }
        });




        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){

                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_hostel:
                        fragment = new HostelFragment();
                        break;

                    case R.id.nav_caf:
                        fragment = new CafFragment();
                        break;

                    case R.id.nav_workstudy:
                        fragment = new WorkstudyFragment();
                        break;

                    case R.id.nav_market:
                        fragment = new CampusMarketFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                return true;
            }
        });
    }
}