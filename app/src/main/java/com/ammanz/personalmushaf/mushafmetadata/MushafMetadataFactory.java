package com.ammanz.personalmushaf.mushafmetadata;

import com.ammanz.personalmushaf.mushafmetadata.madani15.ClassicMadani15MushafMetadata;
import com.ammanz.personalmushaf.mushafmetadata.naskh13.ModernNaskh13CroppedMushafMetadata;
import com.ammanz.personalmushaf.mushafmetadata.naskh13.ModernNaskh13UncroppedMushafMetadata;
import com.ammanz.personalmushaf.mushafmetadata.naskh15.ClassicNaskh15MushafMetadata;
import com.ammanz.personalmushaf.QuranSettings;

public class MushafMetadataFactory {
    public static int[][] mushafStructure = {{QuranSettings.MODERNNASKH13CROPPED, QuranSettings.MODERNNASKH13UNCROPPED}, {QuranSettings.CLASSICNASKH15, QuranSettings.CLASSICMADANI15}};

    public static MushafMetadata getMushafMetadata(int mushaf) {
        switch (mushaf) {
            case QuranSettings.CLASSICMADANI15:
                return new ClassicMadani15MushafMetadata();
            case QuranSettings.MODERNNASKH13CROPPED:
                return new ModernNaskh13CroppedMushafMetadata();
            case QuranSettings.MODERNNASKH13UNCROPPED:
                return new ModernNaskh13UncroppedMushafMetadata();
            case QuranSettings.CLASSICNASKH15:
                return new ClassicNaskh15MushafMetadata();
            default:
                return null;
        }
    }
}
