package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.methods.FileOperation;
import com.gardyanakbar.guardianheadpaindiary.methods.PainDataOperation;

import java.util.ArrayList;
import java.util.List;

import giantsweetroll.date.Date;

public class GraphFragment extends Fragment implements GUIFunctions
{

    //Fields
    private GraphViewModel graphViewModel;
    public static GraphSettingsFragment graphSettings;
    public Graph graph;
    private Button btnSettings, btnExport, btnSwitch, btnRefresh;
    public HorizontalScrollView scroll;
    private boolean graphReversed;

    //Private Methods
    private void initGraph()
    {
        try
        {
            this.scroll.removeView(this.graph);
        }
        catch(NullPointerException ex){}

        this.graphReversed = false;

        String category = GraphFragment.graphSettings.getGraphCategory();
        Date dateFrom = GraphFragment.graphSettings.getDateFrom();
        Date dateTo = GraphFragment.graphSettings.getDateTo();

        List<PainEntryData> list = null;
        try
        {
            list = FileOperation.getListOfEntries(Globals.activePatient.getID(), dateFrom, dateTo);
        }
        catch(NullPointerException ex)
        {
            list = new ArrayList<PainEntryData>();
        }

        //TODO: Create recent medication filter for graph
//        if (Globals.GRAPH_FILTER_PANEL.isRecentMedicationSelected())
//        {
//            list = PainDataOperation.filter(list, PainDataIdentifier.RECENT_MEDICATION, Globals.GRAPH_FILTER_PANEL.getRecentMedicationFilter());
//        }

        if (GraphFragment.graphSettings.isDisplayEmptyDataSelected())
        {
            list = PainDataOperation.insertEmptyData(list, dateFrom, dateTo);
        }

        if (category.equals(this.getString(R.string.graph_settings_category_entries_vs_date)) || category.equals(""))
        {
            this.graph = new LineGraph(this.getContext(), PainDataOperation.getAmountOfEntriesVSDate(list));
            this.graph.setXAxisName(this.getString(R.string.day_text));
            this.graph.setYAxisName(this.getString(R.string.amount_of_entries_text));
        }
        else if (category.equals(this.getString(R.string.graph_settings_category_intensity_vs_episode)))
        {
            this.graph = new LineGraph(this.getContext(), PainDataOperation.getIntensityVSTime(list));
            this.graph.setXAxisName(this.getString(R.string.episode_text));
            this.graph.setYAxisName(this.getString(R.string.intensity_text));
        }
        else if (category.equals(this.getString(R.string.graph_settings_category_duration_vs_episode)))
        {
            this.graph = new LineGraph(this.getContext(), PainDataOperation.getDurationVSTime(list));
            this.graph.setXAxisName(this.getString(R.string.episode_text));
            this.graph.setYAxisName(this.getString(R.string.duration_text));
        }
        else if (category.equals(this.getString(R.string.graph_settings_category_avg_intensity_vs_date)))
        {
            this.graph = new LineGraph(this.getContext(), PainDataOperation.getAverageIntensityVSDate(list));
            this.graph.setXAxisName(this.getString(R.string.day_text));
            this.graph.setYAxisName(this.getString(R.string.average_intensity_text));
        }
        else if (category.equals(this.getString(R.string.graph_settings_category_avg_duration_vs_date)))
        {
            this.graph = new LineGraph(this.getContext(), PainDataOperation.getAverageDurationVSDate(list));
            this.graph.setXAxisName(this.getString(R.string.day_text));
            this.graph.setYAxisName(this.getString(R.string.average_duration_text));
        }
        else if (category.equals(this.getString(R.string.graph_settings_category_avg_intensity_vs_month)))
        {
            this.graph = new LineGraph(this.getContext(), PainDataOperation.getAverageIntensityVSMonth(list));
            this.graph.setXAxisName(this.getString(R.string.month_text));
            this.graph.setYAxisName(this.getString(R.string.average_intensity_text));
        }
        else if (category.equals(this.getString(R.string.graph_settings_category_avg_duration_vs_month)))
        {
            this.graph = new LineGraph(this.getContext(), PainDataOperation.getAverageDurationVSMonth(list));
            this.graph.setXAxisName(this.getString(R.string.month_text));
            this.graph.setYAxisName(this.getString(R.string.average_duration_text));
        }
        else if (category.equals(this.getString(R.string.graph_settings_category_pain_kind_vs_amount)))
        {
            this.graph = new BarGraph(this.getContext(), PainDataOperation.getNumberOfDifferentPainKind(list));
            this.graph.setXAxisName(this.getString(R.string.kinds_of_headpains_text));
            this.graph.setYAxisName(this.getString(R.string.amount_text));
        }
        else if (category.equals(this.getString(R.string.trigger_text)))
        {
            this.graph = new BarGraph(this.getContext(), PainDataOperation.getAmountOfActivity(list));
            this.graph.setXAxisName(this.getString(R.string.trigger_text));
            this.graph.setYAxisName(this.getString(R.string.amount_text));
        }
        this.graph.displayDataValues(GraphFragment.graphSettings.isShowDataValuesSelected());
        this.graph.displayDataPoint(GraphFragment.graphSettings.isDisplayDataPointsSelected());

        //Add to scroll
        HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(HorizontalScrollView.LayoutParams.MATCH_PARENT, HorizontalScrollView.LayoutParams.MATCH_PARENT);
        this.graph.setLayoutParams(params);
        this.scroll.addView(this.graph);
    }

    //Overridden Methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Initialization
        graphViewModel = ViewModelProviders.of(this).get(GraphViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graph, container, false);
//        final TextView textView = root.findViewById(R.id.text_graph);
//        graphViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        this.graphSettings = new GraphSettingsFragment();
        this.btnSettings = (Button)root.findViewById(R.id.graphButtonSetting);
        this.btnExport = (Button)root.findViewById(R.id.graphButtonExport);
        this.btnSwitch = (Button)root.findViewById(R.id.graphButtonSwitch);
        this.btnRefresh = (Button)root.findViewById(R.id.graphButtonRefresh);
        this.scroll = (HorizontalScrollView)root.findViewById(R.id.graphScroll);
        this.initGraph();

        //Properties
        this.btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.graphFragmentContainer, GraphFragment.graphSettings);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        this.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                initGraph();
            }
        });

        return root;
    }


    @Override
    public void resetDefaults() {}

    @Override
    public void refresh()
    {
        this.initGraph();
    }
}