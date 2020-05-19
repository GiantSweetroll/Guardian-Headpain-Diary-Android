package com.gardyanakbar.guardianheadpaindiary.methods;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.gardyanakbar.guardianheadpaindiary.R;
import com.gardyanakbar.guardianheadpaindiary.constants.Constants;
import com.gardyanakbar.guardianheadpaindiary.constants.Globals;
import com.gardyanakbar.guardianheadpaindiary.constants.ImageConstants;
import com.gardyanakbar.guardianheadpaindiary.constants.PainLocationConstants;
import com.gardyanakbar.guardianheadpaindiary.constants.XMLIdentifier;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PainEntryData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.PatientData;
import com.gardyanakbar.guardianheadpaindiary.datadrivers.Settings;
import com.gardyanakbar.guardianheadpaindiary.ui.puzzle_image.ImagePiece;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import giantsweetroll.xml.dom.XMLManager;

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

    /**
     * Returns the text content of the XML Element with the given tag name (key). If multiple elements of the same tag name exists,
     * it will return the first one to occur.
     *
     * @param doc
     * @param key
     * @return String
     */
    public static String getElementTextContent(Document doc, String key)			//Get text content of element from the selected document (only takes text content from the first NodeList)
    {
        try
        {
            return XMLManager.getElement(doc.getElementsByTagName(key), 0).getTextContent();
        }
        catch(NullPointerException ex)
        {
            return "";
        }
    }

    /**
     * Returns the text contents of the given list of Elements in a List object.
     *
     * @param elements
     * @return List<String>
     */
    public static List<String> getTextContentsFromElements(List<Element> elements)
    {
        List<String> list = new ArrayList<String>();

        for (Element element : elements)
        {
            list.add(element.getTextContent());
        }

        return list;
    }

    //Preset Pain Locations
    /**
     * Checks if the pain location is a part of the preset pain locations.
     * @param painLoc
     * @return boolean
     */
    public static boolean isPresetPainLocation(String painLoc)
    {
        if (painLoc.equals(PainLocationConstants.EYES_AND_FOREHEAD) ||
                painLoc.equals(PainLocationConstants.FACE_LEFT_AND_HEAD) ||
                painLoc.equals(PainLocationConstants.FACE_RIGHT_AND_HEAD) ||
                painLoc.equals(PainLocationConstants.HEAD_BACK) ||
                painLoc.equals(PainLocationConstants.HEAD_FRONT) ||
                painLoc.equals(PainLocationConstants.HEAD_FULL))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Converts preset pain location ID to the provided language.
     * @param painLoc
     * @return String of the pain location provided by the language.
     */
    public static String convertPresetPainLocationToLanguage(String painLoc)
    {
        String str = painLoc;
        if (painLoc.equals(PainLocationConstants.EYES_AND_FOREHEAD))
        {
            str = Resources.getSystem().getString(R.string.pain_location_eye_and_forehead_text);
        }
        else if (painLoc.equals(PainLocationConstants.FACE_LEFT_AND_HEAD))
        {
            str = Resources.getSystem().getString(R.string.pain_location_face_left_and_head_text);
        }
        else if (painLoc.equals(PainLocationConstants.FACE_RIGHT_AND_HEAD))
        {
            str = Resources.getSystem().getString(R.string.pain_location_face_right_and_head_text);
        }
        else if (painLoc.equals(PainLocationConstants.HEAD_FULL))
        {
            str = Resources.getSystem().getString(R.string.pain_location_head_all_text);
        }
        else if (painLoc.equals(PainLocationConstants.HEAD_BACK))
        {
            str = Resources.getSystem().getString(R.string.pain_location_head_back_text);
        }
        else if (painLoc.equals(PainLocationConstants.HEAD_FRONT))
        {
            str = Resources.getSystem().getString(R.string.pain_location_head_front_text);
        }
        return str;
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
    public static List<ImagePiece> getCustomPainLocationList(Context context)
    {
        List<ImagePiece> images = new ArrayList<>();

        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan1, "dpn1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan2, "dpn2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan3, "dpn3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan4, "dpn4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang1, "blkg1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang2, "blkg2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang3, "blkg3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang4, "blkg4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan5, "dpn5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan6, "dpn6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan7, "dpn7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan8, "dpn8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang5, "blkg5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang6, "blkg6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang7, "blkg7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang8, "blkg8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan9, "dpn9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan10, "dpn10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan11, "dpn11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan12, "dpn12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang9, "blkg9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang10, "blkg10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang11, "blkg11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang12, "blkg12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan13, "dpn13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan14, "dpn14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan15, "dpn15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan16, "dpn16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang13, "blkg13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang14, "blkg14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang15, "blkg15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang16, "blkg16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan17, "dpn17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan18, "dpn18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan19, "dpn19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukadepan20, "dpn20", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang17, "blkg17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang18, "blkg18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang19, "blkg19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukabelakang20, "blkg20", false));

        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri1, "kir1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri2, "kir2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri3, "kir3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri4, "kir4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan1, "kan1", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan2, "kan2", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan3, "kan3", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan4, "kan4", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri5, "kir5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri6, "kir6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri7, "kir7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri8, "kir8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan5, "kan5", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan6, "kan6", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan7, "kan7", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan8, "kan8", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri9, "kir9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri10, "kir10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri11, "kir11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri12, "kir12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan9, "kan9", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan10, "kan10", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan11, "kan11", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan12, "kan12", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri13, "kir13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri14, "kir14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri15, "kir15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri16, "kir16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan13, "kan13", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan14, "kan14", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan15, "kan15", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan16, "kan16", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri17, "kir17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri18, "kir18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri19, "kir19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakiri20, "kir20", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan17, "kan17", false));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan18, "kan18", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan19, "kan19", true));
        images.add(new ImagePiece(context, R.drawable.pain_loc_custom_mukakanan20, "kan20", false));

        return images;
    }

    //Time operations
    /**
     * Converts seconds to minutes. Any remainders will be lost.
     * @param sec
     * @return int
     */
    public static int secondsToMinutes(int sec)
    {
        return sec/60;
    }
    /**
     * Converts seconds to hours. Any remainders will be lost.
     * @param sec
     * @return int
     */
    public static int secondsToHours(int sec)
    {
        return sec/3600;
    }
    /**
     * Converts seconds to days. Any remainders will be lost.
     * @param sec
     * @return int
     */
    public static int secondsToDays(long sec)
    {
        return Integer.parseInt(Long.toString(sec/86400));
    }

    /**
     * Converts minutes to seconds.
     * @param min
     * @return int
     */
    public static int minutesToSeconds(int min)
    {
        return min*60;
    }

    /**
     * Converts hours to seconds.
     * @param hours
     * @return int
     */
    public static int hoursToSeconds(int hours)
    {
        return hours*3600;
    }

    /**
     * Converts days to seconds.
     * @param days
     * @return long
     */
    public static long daysToSeconds(int days)
    {
        return days*86400;
    }

    //ID Conversions
    /**
     * converts the default pain kind from the provided language into its ID.
     * @param text
     * @return String of the ID
     */
    public static String convertPainKindLanguageToID(String text)
    {
        if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_painkind_default_pulsating_text)))
        {
            return XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING;
        }
        else if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_painkind_default_radiating_text)))
        {
            return XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING;
        }
        else if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_painkind_default_throbbing_text)))
        {
            return XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING;
        }
        else if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_painkind_default_tight_band_text)))
        {
            return XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND;
        }
        else
        {
            return text;
        }
    }

    /**
     * converts the default pain kind ID to the one provided by the language.
     * @param id
     * @return String of the pain kind provided by the language.
     */
    public static String convertPainKindIDToLanguage(String id)
    {
        if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_PULSATING))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_painkind_default_pulsating_text);
        }
        else if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_RADIATING))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_painkind_default_radiating_text);
        }
        else if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_THROBBING))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_painkind_default_throbbing_text);
        }
        else if (id.equals(XMLIdentifier.DEFAULT_PAIN_KIND_TIGHT_BAND))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_painkind_default_tight_band_text);
        }
        else if (id.equals(XMLIdentifier.OTHER_TEXT))
        {
            return Resources.getSystem().getString(R.string.other_text);
        }
        else
        {
            return id;
        }
    }
    /**
     * converts the default trigger from the provided language into its ID.
     * @param text
     * @return String of the ID
     */
    public static String convertTriggerLanguageToID(String text)
    {
        if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_trigger_default_sunlight_text)))
        {
            return XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT;
        }
        else if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_trigger_default_diet_text)))
        {
            return XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP;
        }
        else if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_trigger_default_physical_activity_text)))
        {
            return XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY;
        }
        else if (text.equals(Resources.getSystem().getString(R.string.entry_log_form_trigger_default_stressor_text)))
        {
            return XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR;
        }
        else
        {
            return text;
        }
    }
    /**
     * converts the default trigger ID to the one provided by the language.
     * @param id
     * @return String of the trigger provided by the language.
     */
    public static String convertTriggerIDToLanguage(String id)
    {
        if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_SUNLIGHT))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_trigger_default_sunlight_text);
        }
        else if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_IMPROPER_DIET_SLEEP))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_trigger_default_diet_text);
        }
        else if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_PHYSICAL_ACTIVITY))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_trigger_default_physical_activity_text);
        }
        else if (id.equals(XMLIdentifier.DEFAULT_TRIGGERS_STRESSOR))
        {
            return Resources.getSystem().getString(R.string.entry_log_form_trigger_default_stressor_text);
        }
        else
        {
            return id;
        }
    }

    //Pain Entry Operations
    /**
     * Generates a string for the path to the folder that will be storing the specified entry.
     * @param patient - the PatientData object
     * @param entry - the PainEntryData object.
     * @return the string of the folder path.
     */
    public static String generatePainDataFolderPathName(PatientData patient, PainEntryData entry)
    {
        return Globals.settings.getDataMap().get(Settings.DATABASE_PATH) + File.separator +
                patient.getID() + File.separator +
                Integer.toString(entry.getDate().getYear()) + File.separator +
                Integer.toString(entry.getDate().getMonth()) + File.separator +
                Integer.toString(entry.getDate().getDay()) + File.separator;
    }
    /**
     * Generates a string for the path to the folder that will be storing the specified entry. The file name and extension is also given.
     * @param patient - the PatientData object
     * @param entry - the PainEntryData object.
     * @return the string of the folder path (with the file name and extension).
     */
    public static String generatePainDataFilePathName(PatientData patient, PainEntryData entry)
    {
        return 	generatePainDataFolderPathName(patient, entry) + File.separator +
                entry.getTimeHour() + "-" +
                entry.getTimeMinutes()/* + "-" +
				entry.getDataMap().get(PainDataIdentifier.TIME_SECONDS)*/ +
                Constants.PAIN_DATA_ENTRY_FILE_EXTENSION;
    }

    /**
     * Refreshes the History global variables.
     * @param patient - the PatientData object
     */
    public static void refreshHistories(PatientData patient)
    {
        Globals.HISTORY_MEDICINE_COMPLAINT.refresh(patient);
        Globals.HISTORY_RECENT_MEDICATION.refresh(patient);
        Globals.HISTORY_PAIN_KIND.refresh(patient);
        Globals.HISTORY_TRIGGER.refresh(patient);
    }

    //Other Operations
    /**
     * Check if the string element exists in the list. Uses linear search.
     * @param list
     * @param element
     * @param ignoreCase
     * @return boolean
     */
    public static boolean elementExists(List<String> list, String element, boolean ignoreCase)
    {
        for (String item : list)
        {
            if (ignoreCase)
            {
                if (item.equalsIgnoreCase(element))
                {
                    return true;
                }
            }
            else
            {
                if (item.equals(element))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Deletes all instances of the selected element in the list if found. Uses linear search.
     * Once an instance is deleted, the iteration repeats from the very beginning.
     * @param list
     * @param element
     * @param ignoreCase
     */
    public static void deleteElement(List<String> list, String element, boolean ignoreCase)
    {
        for (int i=0; i<list.size(); i++)
        {
            if (ignoreCase)
            {
                if (list.get(i).equalsIgnoreCase(element))
                {
                    list.remove(i);
                    i=-1;
                    continue;
                }
            }
            else
            {
                if (list.get(i).equals(element))
                {
                    list.remove(i);
                    i=-1;
                    continue;
                }
            }
        }
    }

    /**
     * Removes duplicates within the List of String.
     * @param list
     */
    public static void removeDuplicatesFromStringList(List<String> list)
    {
        for (int i=0; i<list.size()-1; i++)
        {
            for (int a=i+1; a<list.size(); a++)
            {
                if (list.get(i).equals(list.get(a)))
                {
                    list.remove(a);
                    a=i;
                }
            }
        }
    }

    /**
     * Get the string path to the entries database (where all entries are stored)
     * @param context
     * @return the string of the absolute path
     */
    public static String getDatabasePath(Context context)
    {
        return context.getFilesDir() + File.separator + "data" + File.separator + "database" + File.separator;
    }

    /**
     * Get the string path to the patients database (where user patient data are stored)
     * @param context
     * @return the string of the absolute path
     */
    public static String getPatientsDatabasePath(Context context)
    {
        return context.getFilesDir() + File.separator + "data" + File.separator + "users" + File.separator;
    }

    /**
     * Get the string path to the settings folder (where the app's settings are stored)
     * @param context
     * @return the string of the absolute path of the folder
     */
    public static String getSettingsFolderPath(Context context)
    {
        return context.getFilesDir() + File.separator + "data" + File.separator + "settings" + File.separator;
    }

    /**
     * compares two instances of time by comparing the values of their hour and minute.
     * @param hour1
     * @param min1
     * @param hour2
     * @param min2
     * @return returns true if they both have the same exact values.
     */
    public static boolean isSameTime(int hour1, int min1, int hour2, int min2)
    {
        return hour1==hour2 && min1==min2;
    }
}
