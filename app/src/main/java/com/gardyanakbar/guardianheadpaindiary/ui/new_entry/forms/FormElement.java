package com.gardyanakbar.guardianheadpaindiary.ui.new_entry.forms;

import android.text.Html;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.gardyanakbar.guardianheadpaindiary.interfaces.GUIFunctions;
import com.gardyanakbar.guardianheadpaindiary.interfaces.LanguageListener;
import com.gardyanakbar.guardianheadpaindiary.methods.Methods;

public abstract class FormElement<T> extends Fragment implements GUIFunctions, LanguageListener
{
    private TextView labName;
    private boolean required;
    private ScrollView scroll;
    private String name;

    protected View view;

    //Constructor
    public FormElement(boolean required)
    {
        this.required = required;
//        this.setFormTitle(name);
    }

    //Protected Methods
    /**
     * Set the name of the form.
     * @param name - name of the form.
     */
    protected void setName(String name)
    {
        this.name = name;
    }

    //Public Methods
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
            this.labName.setText(HtmlCompat.fromHtml(Methods.createTextWithRequiredIdentifier(title), Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
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

    /**
     * Get the name of the form.
     * @return a string of the name of the form.
     */
    public String getName()
    {
        return this.name;
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