package com.gardyanakbar.guardianheadpaindiary.methods;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.ImageConstants;
import com.gardyanakbar.guardianheadpaindiary.constants.PainLocationConstants;

import java.net.URL;
import java.util.LinkedHashMap;

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

    public static final LinkedHashMap<String, LinkedHashMap<Integer, String>> generatePainLocationsTextIDMap(Context context)
    {
        LinkedHashMap<String, LinkedHashMap<Integer, String>> map = new LinkedHashMap<>();

        LinkedHashMap<Integer, String> subMap = new LinkedHashMap<>();

        subMap.put(ImageConstants.PAIN_LOCATION_EYES_AND_FOREHEAD, PainLocationConstants.EYES_AND_FOREHEAD);
        map.put(context.getString(R.string.pain_location_eye_and_forehead_text), subMap);

        subMap = new LinkedHashMap<>();
        subMap.put(ImageConstants.PAIN_LOCATION_FACE_LEFT_AND_HEAD, PainLocationConstants.FACE_LEFT_AND_HEAD);
        map.put(context.getString(R.string.pain_location_face_left_and_head_text), subMap);

        subMap = new LinkedHashMap<>();
        subMap.put(ImageConstants.PAIN_LOCATION_FACE_RIGHT_AND_HEAD, PainLocationConstants.FACE_RIGHT_AND_HEAD);
        map.put(context.getString(R.string.pain_location_face_right_and_head_text), subMap);

        subMap = new LinkedHashMap<>();
        subMap.put(ImageConstants.PAIN_LOCATION_HEAD_FULL, PainLocationConstants.HEAD_FULL);
        map.put(context.getString(R.string.pain_location_head_all_text), subMap);

        subMap = new LinkedHashMap<>();
        subMap.put(ImageConstants.PAIN_LOCATION_HEAD_BACK, PainLocationConstants.HEAD_BACK);
        map.put(context.getString(R.string.pain_location_head_back_text), subMap);

        subMap = new LinkedHashMap<>();
        subMap.put(ImageConstants.PAIN_LOCATION_HEAD_FRONT, PainLocationConstants.HEAD_FRONT);
        map.put(context.getString(R.string.pain_location_head_front_text), subMap);

        return map;
    }

    public static String[] getDefaultPainKinds(Context context)
    {
        String kinds[] = {context.getString(R.string.entry_log_form_painkind_default_throbbing_text),
                            context.getString(R.string.entry_log_form_painkind_default_pulsating_text),
                            context.getString(R.string.entry_log_form_painkind_default_radiating_text),
                            context.getString(R.string.entry_log_form_painkind_default_tight_band_text)};
        return kinds;
    }
    public static String[] getDefaultTrigger(Context context)
    {
        String kinds[] = {context.getString(R.string.entry_log_form_trigger_default_diet_text),
                            context.getString(R.string.entry_log_form_trigger_default_physical_activity_text),
                            context.getString(R.string.entry_log_form_trigger_default_stressor_text),
                            context.getString(R.string.entry_log_form_trigger_default_sunlight_text)};
        return kinds;
    }
}
