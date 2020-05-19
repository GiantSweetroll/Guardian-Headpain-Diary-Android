package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gardyanakbar.guardianheadpaindiary.R;

public class CommentsFragment extends FormElement
{
    //Fields
    EditText taComments;

    //Constructor
    public CommentsFragment()
    {
        super(false);
    }

    //Overridden Methods
    @Override
    public void resetDefaults()
    {
        super.resetDefaults();
        this.taComments.setText("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.fragment_entry_log_comments, container, false);

        //Initialization
        this.setScroll((ScrollView)this.view.findViewById(R.id.entryLogCommentsScroll));
        this.setFormTitleLabel((TextView)view.findViewById(R.id.entryLogCommentsLabel));
        this.setFormTitle("");
        this.taComments = (EditText)this.view.findViewById(R.id.entryLogCommentsTextArea);
        this.setName(this.getString(R.string.entry_log_map_button_comments_text));

        return this.view;
    }

    @Override
    public String getData()
    {
        return this.taComments.getText().toString();
    }

    @Override
    public void setData(Object data)
    {
        this.taComments.setText(data.toString());
    }

    @Override
    public boolean allFilled()
    {
        return true;
    }

    @Deprecated
    @Override
    public void refresh() {}

    @Override
    public void revalidateLanguage()
    {
        this.setFormTitle(this.getString(R.string.entry_log_map_button_comments_text));
    }
}
