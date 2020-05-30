package com.gardyanakbar.guardianheadpaindiary.ui.table;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.methods.FileOperation;
import com.gardyanakbar.guardianheadpaindiary.methods.PainDataOperation;

import java.util.ArrayList;
import java.util.List;

import giantsweetroll.date.Date;

public class TableFragment extends Fragment
{
    //Fields
    private static final String TAG = "TableFragment";
    private TableViewModel tableViewModel;
    private Button btnSelectAll, btnDeselectAll, btnDelete, btnExport, btnSettings, btnRefresh;
    private RecyclerView recView;
    private TableSettingsFragment settings;

    //Constructor
    public TableFragment()
    {
        super();
        this.settings = new TableSettingsFragment();
    }

    //Private Methods
    private PainEntryAdapter getRecyclerViewAdapter()
    {
        Log.d(TAG, "getRecyclerViewAdapter: called");
        Date dateFrom = this.settings.getDateFrom();
        Log.d(TAG, "getRecyclerViewAdapter: Get entry from date: " + dateFrom.toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
        Date dateTo = this.settings.getDateTo();
        Log.d(TAG, "getRecyclerViewAdapter: Get entry to date: " + dateTo.toString(Date.DAY, Date.MONTH, Date.YEAR, "-"));
        String filterType = this.settings.getFilterCategory();
        Log.d(TAG, "getRecyclerViewAdapter: Filter category: " + filterType);
        String keyword = this.settings.getFilterKeyword();
        Log.d(TAG, "getRecyclerViewAdapter: Filter keyword: " + keyword);

        List<PainEntryData> entries = new ArrayList<PainEntryData>();
        try
        {
            entries.addAll(FileOperation.getListOfEntries(Globals.activePatient.getID(), dateFrom, dateTo));
        }
        catch(NullPointerException ex){}
        entries = PainDataOperation.getFilteredData(this.getContext(), filterType, keyword, entries);

        return new PainEntryAdapter(entries);
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView: TableFragment view created");
        //Initialization
        tableViewModel = ViewModelProviders.of(this).get(TableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_table, container, false);
        this.btnSelectAll = (Button)root.findViewById(R.id.btnSelectAll);
        this.btnDeselectAll = root.findViewById(R.id.btnDeselectAll);
        this.btnDelete = root.findViewById(R.id.btnDelete);
        this.btnExport = root.findViewById(R.id.btnExport);
        this.btnSettings = root.findViewById(R.id.btnSettings);
        this.btnRefresh = root.findViewById(R.id.btnRefresh);
        this.recView = root.findViewById(R.id.tableEntriesListView);

        //Properties
        this.recView.setAdapter(this.getRecyclerViewAdapter());
        this.btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.tableParentLayout, settings);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        this.btnRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                recView.setAdapter(getRecyclerViewAdapter());
                getView().invalidate();
            }
        });

        return root;
    }
}
