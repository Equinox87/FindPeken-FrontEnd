package com.example.findpeken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.findpeken.fragment.AboutFragment;
import com.example.findpeken.fragment.MapsFragment;
import com.example.findpeken.fragment.PasarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    MenuItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigation = findViewById(R.id.bottomNavigationView);
        choosefragment();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.menuhome:
                        Intent main= new Intent(Home.this, MainActivity.class);
                        startActivity(main);
                        break;
                    case R.id.menupasar:
                        fragment = new PasarFragment();
                        break;
                    case R.id.menumaps:
                        fragment = new MapsFragment();
                        break;
                    case R.id.menuabout:
                        fragment = new AboutFragment();
                        break;
                }
                return getFragmentPage(fragment);
            }
        });
    }

    private void choosefragment() {
        try {
            String key= getIntent().getStringExtra("key");
            if(key.equals("1")){
                Fragment fragmentmap= new MapsFragment();
                String  mapid= getIntent().getStringExtra("mapid");
                Bundle bundle= new Bundle();
                bundle.putString("mapid",mapid);
                if (mapid != null && !mapid.equals("")) {
                    fragmentmap.setArguments(bundle);
                }
               item= bottomNavigation.getMenu().findItem(R.id.menumaps).setChecked(true);
                getFragmentPage(fragmentmap);
            }else if(key.equals("0")){
                Fragment fragmentpasar= new PasarFragment();
                item= bottomNavigation.getMenu().findItem(R.id.menupasar).setChecked(true);
                getFragmentPage(fragmentpasar);
            }


        }catch (Exception e){

        }

    }

    public boolean getFragmentPage(Fragment fragment){
        Log.d("HOME Activity", "getFragmentPage: NOPE ");
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containermenu, fragment)
                    .commit();
            return true;
        }
        return false;

    }

}
