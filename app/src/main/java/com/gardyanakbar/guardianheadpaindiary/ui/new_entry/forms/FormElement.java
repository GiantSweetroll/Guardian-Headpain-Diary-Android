package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.text.Html;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

public abstract class FormElement<T> extends Fragment implements GUIFunctions, LanguageListener
{
    private TextView labName;
    private boolean required;
    private ScrollView scroll;

    //Constructor
    public FormElement(boolean required)
    {
        this.required = required;
//        this.setFormTitle(name);
    }

    //Methods
    public TextView getFormTitleLabel()
    {
        return this.labName;
    }
    public void setFormTitleLabel(TextView tv)
    {
        this.labName = tv;
    }
    public String getFormTitle()
    {
        return (String)this.labName.getText();
    }
    public void setFormTitle(String title)
    {
        if (this.required)
        {
            this.labName.setText(Html.fromHtml(Methods.createTextWithRequiredIdentifier(title)), TextView.BufferType.SPANNABLE);
        }
        else
        {
            this.labName.setText(title);
        }
    }
    public void setRequired(boolean required)
    {
        this.required = required;
    }
    public boolean isRequired()
    {
        return this.required;
    }

    public void setScroll(ScrollView scroll)
    {
        this.scroll = scroll;
    }

    public ScrollView getScroll()
    {
        return this.scroll;
    }

    //Overridden Methods
    @Override
    public void resetDefaults()
    {
        this.scroll.scrollTo(0, 0); //Set the scroll bar to go back to the top
    }

    //Abstract Methods
    public abstract T getData();
    public abstract void setData(T data);
    public abstract boolean allFilled();
}