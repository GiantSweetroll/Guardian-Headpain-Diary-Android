package com.gardyanakbar.guardianheadpaindiary.ui.graph;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.methods.PainDataOperation;

public class GraphFragment extends Fragment
{

    //Fields
    private GraphViewModel graphViewModel;
    public static GraphPanelFragment graphPanel;
    public static GraphSettingsFragment graphSettings;

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
        this.graphPanel = new GraphPanelFragment(this);
        this.graphSettings = new GraphSettingsFragment(this);

        //Add graph panel
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.graphFragmentContainer, GraphFragment.graphPanel);
        transaction.addToBackStack(null);
        transaction.commit();

        return root;
    }
}