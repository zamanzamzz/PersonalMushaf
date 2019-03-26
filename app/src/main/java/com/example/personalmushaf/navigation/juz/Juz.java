package com.example.personalmushaf.navigation.juz;

import com.example.personalmushaf.navigation.ruku.Ruku;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;


public class Juz extends ExpandableGroup<Ruku> {

    private int juzNumber;

    public Juz(String title, List<Ruku> items, int juzNumber) {
        super(title, items);
        this.juzNumber = juzNumber;
    }

    public int getJuzNumber() {
        return juzNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Juz)) return false;

        Juz juz = (Juz) o;

        return getJuzNumber() == juz.getJuzNumber();

    }

    @Override
    public int hashCode() {
        return getJuzNumber();
    }
}
