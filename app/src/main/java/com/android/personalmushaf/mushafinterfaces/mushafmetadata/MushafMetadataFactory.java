package com.android.personalmushaf.mushafinterfaces.mushafmetadata;

import com.android.madani15.Madani15MushafMetadata;
import com.android.naskh13.Naskh13MushafMetadata;
import com.android.personalmushaf.QuranSettings;

public class MushafMetadataFactory {
    public static int[][] mushafStructure = {{QuranSettings.NASKH13LINE}, {QuranSettings.MADANI15LINE}};

    public static MushafMetadata getMushafMetadata(int mushaf) {
        switch (mushaf) {
            case QuranSettings.MADANI15LINE:
                return new Madani15MushafMetadata();
            case QuranSettings.NASKH13LINE:
                return new Naskh13MushafMetadata();
            default:
                return null;
        }
    }
}
