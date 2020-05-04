package com.gardyanakbar.guardianheadpaindiary.methods;

import android.widget.TextView;

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
}
