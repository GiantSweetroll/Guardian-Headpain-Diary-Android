package com.gardyanakbar.guardianheadpaindiary.constants;

import android.content.res.Resources;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

import com.gardyanakbar.guardianheadpaindiary.R;

import java.io.File;

public final class Constants
{
    public static final String REQUIRED_IDENTIFIER = "*";

    public static final String HISTORY_FOLDER_PATH = "data" + File.separator + "history" + File.separator;
    public static final String HISTORY_RECENT_MEDICATION_NAME = "recent_medication";
    public static final String HISTORY_MEDICINE_COMPLAINT_NAME = "medicine_complaint";
    public static final String HISTORY_PAIN_KIND_NAME = "pain_kind";
    public static final String HISTORY_TRIGGER_NAME = "trigger";

    public static final String PAIN_DATA_ENTRY_FILE_EXTENSION = ".xml";

    public static final String PAIN_ENTRY_DATE_SEPARATOR = "/";

    public static final String SETTINGS_FILE_NAME = "settings.xml";

    //Font
    public static final int FONT_GENERAL_SIZE = 15;
    public static final int FONT_SIZE_A_BIT_BIGGER = FONT_GENERAL_SIZE + 3;
    public static final Typeface FONT_GENERAL_BOLD = (new Typeface.Builder("gothic.ttf")).build();
    public static final Typeface FONT_GENERAL = (new Typeface.Builder("gothicb.ttf")).build();
}
