package com.gardyanakbar.guardianheadpaindiary.constants;

import com.gardyanakbar.guardianheadpaindiary.datadrivers.GraphSettings;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.History;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.Settings;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.TableSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public final class Globals
{
    //Histories
    public static final History HISTORY_RECENT_MEDICATION = new History(Constants.HISTORY_RECENT_MEDICATION_NAME, new PatientData());
    public static final History HISTORY_MEDICINE_COMPLAINT = new History(Constants.HISTORY_MEDICINE_COMPLAINT_NAME, new PatientData());
    public static final History HISTORY_PAIN_KIND = new History(Constants.HISTORY_PAIN_KIND_NAME, new PatientData());
    public static final History HISTORY_TRIGGER = new History(Constants.HISTORY_TRIGGER_NAME, new PatientData());

    //Settings
    public static Settings settings;
    public static PatientData activePatient;
    public static TableSettings tableSettings;
    public static GraphSettings graphSettings;

    public static PainEntryData activeEntry;
    public static boolean isNewEntry;

    public static BottomNavigationView bottomNavigationView;
}
