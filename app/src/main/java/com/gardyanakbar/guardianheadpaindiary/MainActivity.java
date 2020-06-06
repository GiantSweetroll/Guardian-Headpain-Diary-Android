package com.gardyanakbar.guardianheadpaindiary;

import android.os.Bundle;

import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.GraphSettings;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.Settings;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.TableSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        Globals.activePatient = new PatientData();
        Globals.tableSettings = new TableSettings();
        Globals.graphSettings = new GraphSettings();
        Globals.isNewEntry = true;

        Globals.HISTORY_MEDICINE_COMPLAINT.refresh(Globals.activePatient);
        Globals.HISTORY_PAIN_KIND.refresh(Globals.activePatient);
        Globals.HISTORY_RECENT_MEDICATION.refresh(Globals.activePatient);
        Globals.HISTORY_TRIGGER.refresh(Globals.activePatient);
    }
}
