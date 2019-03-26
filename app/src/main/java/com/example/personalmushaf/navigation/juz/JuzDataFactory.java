package com.example.personalmushaf.navigation.juz;

import com.example.personalmushaf.navigation.ruku.Ruku;

import java.util.ArrayList;
import java.util.List;

public class JuzDataFactory {

  public static List<Juz> makeJuz() {

    List<Ruku> rukus = new ArrayList<>();
    List<Juz> juzzes = new ArrayList<>();

    for (int i = 1; i < 11; i++) {
        Ruku currentRuku = new Ruku(i, i);
        rukus.add(currentRuku);
    }

    for (int i = 1; i < 31; i++) {
        String title = Integer.toString(i);
        Juz currentJuz = new Juz(title, rukus,i);
        juzzes.add(currentJuz);
    }

    return juzzes;
  }

}

