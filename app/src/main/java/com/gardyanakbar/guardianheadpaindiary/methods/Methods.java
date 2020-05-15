package com.gardyanakbar.guardianheadpaindiary.methods;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.ImageConstants;
import com.gardyanakbar.guardianheadpaindiary.constants.PainLocationConstants;
import com.gardyanakbar.guardianheadpaindiary.ui.puzzle_image.ImagePiece;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    //Custom Pain Locations
    public static List<ImagePiece> getPainLocationBack(Context context)
    {
        List<ImagePiece> images = new ArrayList<>();

        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang1, "blkg1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang2, "blkg2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang3, "blkg3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang4, "blkg4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang5, "blkg5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang6, "blkg6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang7, "blkg7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang8, "blkg8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang9, "blkg9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang10, "blkg10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang11, "blkg11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang12, "blkg12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang13, "blkg13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang14, "blkg14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang15, "blkg15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang16, "blkg16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang17, "blkg17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang18, "blkg18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang19, "blkg19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang20, "blkg20", false));

        return images;
    }
    public static List<ImagePiece> getPainLocationFront(Context context)
    {
        List<ImagePiece> images = new ArrayList<>();

        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan1, "dpn1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan2, "dpn2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan3, "dpn3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan4, "dpn4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan5, "dpn5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan6, "dpn6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan7, "dpn7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan8, "dpn8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan9, "dpn9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan10, "dpn10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan11, "dpn11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan12, "dpn12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan13, "dpn13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan14, "dpn14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan15, "dpn15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan16, "dpn16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan17, "dpn17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan18, "dpn18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan19, "dpn19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan20, "dpn20", false));

        return images;
    }
    public static List<ImagePiece> getPainLocationRight(Context context)
    {
        List<ImagePiece> images = new ArrayList<>();

        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan1, "kan1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan2, "kan2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan3, "kan3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan4, "kan4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan5, "kan5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan6, "kan6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan7, "kan7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan8, "kan8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan9, "kan9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan10, "kan10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan11, "kan11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan12, "kan12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan13, "kan13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan14, "kan14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan15, "kan15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan16, "kan16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan17, "kan17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan18, "kan18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan19, "kan19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan20, "kan20", false));

        return images;
    }
    public static List<ImagePiece> getPainLocationLeft(Context context)
    {
        List<ImagePiece> images = new ArrayList<>();

        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri1, "kir1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri2, "kir2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri3, "kir3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri4, "kir4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri5, "kir5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri6, "kir6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri7, "kir7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri8, "kir8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri9, "kir9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri10, "kir10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri11, "kir11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri12, "kir12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri13, "kir13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri14, "kir14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri15, "kir15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri16, "kir16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri17, "kir17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri18, "kir18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri19, "kir19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri20, "kir20", false));

        return images;
    }
}
