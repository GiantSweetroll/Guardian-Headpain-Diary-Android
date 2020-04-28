package com.gardyanakbar.guardianheadpaindiary.methods;

import com.gardyanakbar.guardianheadpaindiary.constants.Constants;

public class Methods
{
    public static final String createTextWithRequiredIdentifier(String text)
    {
        return "<html>" + text + "<font color='red'>" + Constants.REQUIRED_IDENTIFIER + "</font></html>";
    }
}
