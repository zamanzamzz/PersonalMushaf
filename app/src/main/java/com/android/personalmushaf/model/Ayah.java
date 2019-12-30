package com.android.personalmushaf.model;

import androidx.annotation.Nullable;

import java.util.List;

public class Ayah {
    private String key;
    private List<AyahBounds> ayahBounds;


    public Ayah(String key, List<AyahBounds> ayahBounds) {
        this.ayahBounds = ayahBounds;
        this.key = key;
    }

    public List<AyahBounds> getAyahBounds() {
        return ayahBounds;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj.getClass() == Ayah.class) {
            Ayah ayah = (Ayah) obj;
            return this.key.equals(ayah.key);
        }
        return false;
    }
}
