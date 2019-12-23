package com.android.naskh13;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.QuranStrategy;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;

public class Naskh13QuranStrategy implements QuranStrategy {

    private MushafMetadata mushafMetadata;

    public Naskh13QuranStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public int pageNumberToDualPagerPosition(int pageNumber) {
        if (pageNumber % 2 == 0)
            return pageNumber / 2 - 1;
        else
            return (pageNumber - 1) / 2 - 1;
    }

    public int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return dualPagerPosition * 2 + 2;
    }

    public int pageNumberToSinglePagerPosition(int pageNumber) {
        return pageNumber - 2;
    }

    public int singlePagerPositionToPageNumber(int position) {
        return position + 2;
    }

    public int minPage() {
        return 2;
    }

    public int maxPage() {
        return 848;
    }

    public int getPageNumberFromPagerPosition(int position) {
        return position + 1;
    }

    public int getNumOfSinglePages() {
        return 847;
    }

    public int getNumOfDualPages() {
        return Naskh13NavigationData.naskh13LineDualPageSets.length;
    }

    public String getPagePath(int pageNumber) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    public PageData getPageData(int pageNumber) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][0] + ".png";
    }

    public String getRightPagePath(int dualPagerPosition) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][1] + ".png";
    }

    public PageData getLeftPageData(int dualPagerPosition) {
        return new PageData(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][0], mushafMetadata.getDatabasePath());
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return new PageData(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][1], mushafMetadata.getDatabasePath());
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        return dualPagerPosition == 423;
    }
}
