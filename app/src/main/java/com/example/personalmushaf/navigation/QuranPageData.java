package com.example.personalmushaf.navigation;

public class QuranPageData {
    private static final QuranPageData ourInstance = new QuranPageData();

    public int[] JuzPageNumbers = {2, 29, 57, 85, 113, 141, 169, 197, 225, 253, 281,
            309, 337, 365, 393, 421, 449, 477, 505, 533,
            559, 587, 613, 641, 667, 697, 727, 757, 787, 819};

    public int[][] JuzContentPageNumbers = {
            {}, //1

            {}, //2

            {}, //3

            {}, //4

            {}, //5

            {}, //6

            {}, //7

            {}, //8

            {}, //9

            {}, //10

            {}, //11

            {}, //12

            {}, //13

            {}, //14

            {}, //15

            {}, //16

            {}, //17

            {}, //18

            {}, //19

            {}, //20

            {}, //21

            {}, //22

            {}, //23

            {}, //24

            {}, //25

            {}, //26

            {}, //27

            {}, //28

            {}, //29

            {}, //30

    };

    public static QuranPageData getInstance() {
        return ourInstance;
    }

    private QuranPageData() {
    }
}
