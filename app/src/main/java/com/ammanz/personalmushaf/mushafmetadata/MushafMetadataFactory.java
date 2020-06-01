package com.ammanz.personalmushaf.mushafmetadata;

import com.ammanz.madani15.ClassicMadani15MushafMetadata;
import com.ammanz.naskh13.ModernNaskh13MushafMetadata;
import com.ammanz.naskh15.ClassicNaskh15MushafMetadata;
import com.ammanz.personalmushaf.QuranSettings;

public class MushafMetadataFactory {
    public static int[][] mushafStructure = {{QuranSettings.MODERN_NASKH_13_LINE}, {QuranSettings.CLASSIC_NASKH_15_LINE, QuranSettings.CLASSIC_MADANI_15_LINE}};

    public static MushafMetadata getMushafMetadata(int mushaf) {
        switch (mushaf) {
            case QuranSettings.CLASSIC_MADANI_15_LINE:
                return new ClassicMadani15MushafMetadata();
            case QuranSettings.MODERN_NASKH_13_LINE:
                return new ModernNaskh13MushafMetadata();
            case QuranSettings.CLASSIC_NASKH_15_LINE:
                return new ClassicNaskh15MushafMetadata();
            default:
                return null;
        }
    }
}
