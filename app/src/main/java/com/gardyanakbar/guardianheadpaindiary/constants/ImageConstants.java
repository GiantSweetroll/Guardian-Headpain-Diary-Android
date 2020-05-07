package com.gardyanakbar.guardianheadpaindiary.constants;

import com.gardyanakbar.guardianheadpaindiary.R;

import java.net.URL;

public class ImageConstants
{
    //Pain Locations New
    public static final int PAIN_LOCATION_HEAD_FULL = R.drawable.pain_loc_preset_1;
    public static final int PAIN_LOCATION_FACE_LEFT_AND_HEAD = R.drawable.pain_loc_preset_2;
    public static final int PAIN_LOCATION_FACE_RIGHT_AND_HEAD = R.drawable.pain_loc_preset_3;
    public static final int PAIN_LOCATION_EYES_AND_FOREHEAD = R.drawable.pain_loc_preset_4;
    public static final int PAIN_LOCATION_HEAD_FRONT = R.drawable.pain_loc_preset_5;
    public static final int PAIN_LOCATION_HEAD_BACK = R.drawable.pain_loc_preset_6;
    public static final URL PAIN_LOCATION_CUSTOM = ImageConstants.class.getResource("/pain_location/tap-nojudul.png");

    //Supporters
    public static final URL FKUI = ImageConstants.class.getResource("/logo_icon/logo-fkui.png");
    public static final URL RSCM = ImageConstants.class.getResource("/logo_icon/logo-rscm.png");
    public static final URL MEDICAL_MEDIA = ImageConstants.class.getResource("/logo_icon/logo-medimedi.png");

    //Button Images
    public static final URL NEW_ENTRY = ImageConstants.class.getResource("/logo_icon/icon-frontpage-newentry.png");
    public static final URL VIEW_GRAPH = ImageConstants.class.getResource("/logo_icon/icon-frontpage-viewgraph.png");
    public static final URL VIEW_TABLE = ImageConstants.class.getResource("/logo_icon/icon-frontpage-viewtable.png");

    //Other Images
    public static final URL LOGO = ImageConstants.class.getResource("/logo_icon/logo-guardian.png");
    public static final URL LOGO_MINI = ImageConstants.class.getResource("/logo_icon/logo-simple-blue-bg.png");
    public static final URL LOGO_MINI_DARK = ImageConstants.class.getResource("/logo_icon/logo-simple-dark.png");
}
