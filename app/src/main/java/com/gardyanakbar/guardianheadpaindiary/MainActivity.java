package com.gardyanakbar.guardianheadpaindiary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.bottomNavigationView = (BottomNavigationView)findViewById(R.id.nav_view);
//        navView.setBackgroundColor(this.getResources().getColor(R.color.colorButtonBase));    //Set the background color of the navigation bar
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_new_entry, R.id.navigation_graph, R.id.navigation_table)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(Globals.bottomNavigationView, navController);
        Globals.settings = new Settings(this);
    }
}
