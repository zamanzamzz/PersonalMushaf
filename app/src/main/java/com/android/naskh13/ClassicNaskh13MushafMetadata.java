package com.android.naskh13;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.FileUtils;

public class ClassicNaskh13MushafMetadata extends ModernNaskh13MushafMetadata {

    public ClassicNaskh13MushafMetadata() {
        directoryName = "classic_naskh_13_line";
        databasePath = FileUtils.ASSETSDIRECTORY + "/" + directoryName + "/databases/ayahinfo_classicnaskh13line.db";
        name = "Classic 13 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        /*previewDrawableIDs[0] = R.drawable.modern_naskh_13_line_preview1;
        previewDrawableIDs[1] = R.drawable.modern_naskh_13_line_preview2;*/
    }
}
