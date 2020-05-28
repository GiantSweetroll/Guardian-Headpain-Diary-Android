package com.gardyanakbar.guardianheadpaindiary.ui.table;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gardyanakbar.guardianheadpaindiary.R;

public class TableFragment extends Fragment
{
    //Fields
    private TableViewModel tableViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Initialization
        tableViewModel = ViewModelProviders.of(this).get(TableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_table, container, false);

        //Add graph panel

        return root;
    }
}
