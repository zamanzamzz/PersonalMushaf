package com.android.madani15;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.QuranStrategy;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;

public class Madani15QuranStrategy implements QuranStrategy {

    private MushafMetadata mushafMetadata;

    public Madani15QuranStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public int pageNumberToDualPagerPosition(int pageNumber) {
        if (pageNumber % 2 == 0)
            return pageNumber / 2 - 1;
        else
            return (pageNumber - 1) / 2;
    }

    public int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return dualPagerPosition *2 + 1;
    }

    public int pageNumberToSinglePagerPosition(int pageNumber) {
        return pageNumber - 1;
    }

    public int singlePagerPositionToPageNumber(int position) {
        return position + 1;
    }

    public int minPage() {
        return 1;
    }

    public int maxPage() {
        return 604;
    }

    public int getPageNumberFromPagerPosition(int position) {
        return position;
    }

    public int getNumOfSinglePages() {
        return 604;
    }

    public int getNumOfDualPages() {
        return Madani15NavigationData.madani15LineDualPageSets.length;
    }

    public String getPagePath(int pageNumber) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    public PageData getPageData(int pageNumber) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][0] + ".png";
    }

    public String getRightPagePath(int dualPagerPosition) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][1] + ".png";
    }


    public PageData getLeftPageData(int dualPagerPosition) {
        return new PageData(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][0], mushafMetadata.getDatabasePath());
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return new PageData(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][1], mushafMetadata.getDatabasePath());
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        return false;
    }
}
