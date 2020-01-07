package com.android.naskh15;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.FileUtils;

public class ClassicNaskh15MushafMetadata extends MushafMetadata {

    public ClassicNaskh15MushafMetadata() {
        directoryName = "classic_naskh_15_line";
        databasePath = FileUtils.ASSETSDIRECTORY + "/" + directoryName + "/databases/ayahinfo_classicnaskh15line.db";
        name = "Classic 15 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.classic_naskh_15_line_preview1;
        previewDrawableIDs[1] = R.drawable.classic_naskh_15_line_preview2;
    }
}
