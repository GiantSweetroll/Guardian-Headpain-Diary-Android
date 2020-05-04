package com.gardyanakbar.guardianheadpaindiary.interfaces;

import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;

public interface HistoryListener
{
    public void refreshHistory(PatientData patientData);
}
