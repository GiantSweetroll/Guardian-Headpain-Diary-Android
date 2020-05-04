package com.gardyanakbar.guardianheadpaindiary.methods;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;

public class Methods
{
    public static final String createTextWithRequiredIdentifier(String text)
    {
        return "<html>" + text + "<font color='red'>" + Constants.REQUIRED_IDENTIFIER + "</font></html>";
    }

    public static String frameStringWithDashes(String text)
    {
        return "-- " + text + " --";
    }

    public static String getTextData(TextView tf)
    {
        return tf.getText().toString().trim();
    }

    public static String[] getDefaultPainKinds(Context context)
    {
        String kinds[] = {context.getString(R.string.entry_log_form_painkind_default_throbbing_text),
                            context.getString(R.string.entry_log_form_painkind_default_pulsating_text),
                            context.getString(R.string.entry_log_form_painkind_default_radiating_text),
                            context.getString(R.string.entry_log_form_painkind_default_tight_band_text)};
        return kinds;
    }
}
