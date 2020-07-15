package com.ammanz.personalmushaf.util;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.ammanz.personalmushaf.quranpage.QuranPageAdapter;

public class GlyphsHighlighter {
    private ViewPager2 pager;
    private QuranPageAdapter pagerAdapter;
    private Thread highlightGlyphsThread;
    private int currentGlyphIndex;
    private boolean isGlyphForward;

    public GlyphsHighlighter(ViewPager2 pager, QuranPageAdapter pagerAdapter) {
        this.pager = pager;
        this.pagerAdapter = pagerAdapter;
        currentGlyphIndex = 0;
        isGlyphForward = true;
    }

    public void setPagerAdapter(QuranPageAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    public void setPager(ViewPager2 pager) {
        this.pager = pager;
    }

    public void startHighlight() {
        highlightGlyphsThread = new Thread(() -> {
            try {
                int position = pager.getCurrentItem();
                int numOfGlyphs = pagerAdapter.getNumOfGlyphs(position);
                if (isGlyphForward)
                    for (; currentGlyphIndex < numOfGlyphs; currentGlyphIndex++) {
                        pagerAdapter.highlightGlyph(position, currentGlyphIndex);
                        Thread.sleep(100);
                    }
                else
                    for (; currentGlyphIndex >= 0; currentGlyphIndex--) {
                        pagerAdapter.highlightGlyph(position, currentGlyphIndex);
                        Thread.sleep(100);
                    }
                pagerAdapter.highlightGlyph(position, -1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        highlightGlyphsThread.start();
    }

    public void stopHighlight() {
        if (highlightGlyphsThread != null && !highlightGlyphsThread.getState().equals(Thread.State.TERMINATED) && !highlightGlyphsThread.isInterrupted())
            highlightGlyphsThread.interrupt();
    }

    public void pausePlayHighlight() {
        if (highlightGlyphsThread == null || highlightGlyphsThread.isInterrupted() || highlightGlyphsThread.getState().equals(Thread.State.TERMINATED))
            startHighlight();
        else if (highlightGlyphsThread.isAlive())
            highlightGlyphsThread.interrupt();
    }

    public void forwardHighlight() {
        if (highlightGlyphsThread != null && highlightGlyphsThread.isAlive()) {
            if (isGlyphForward)
                return;
            highlightGlyphsThread.interrupt();
        }
        isGlyphForward = true;
        startHighlight();
    }

    public void reverseHighlight() {
        if (highlightGlyphsThread != null && highlightGlyphsThread.isAlive()) {
            if (!isGlyphForward)
                return;
            highlightGlyphsThread.interrupt();
        }
        isGlyphForward = false;
        startHighlight();
    }


}
