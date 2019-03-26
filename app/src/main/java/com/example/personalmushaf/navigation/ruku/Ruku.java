package com.example.personalmushaf.navigation.ruku;

import android.os.Parcel;
import android.os.Parcelable;


public class Ruku implements Parcelable {
    protected String title;
    protected int pageNumber;
    protected double length;

    public Ruku(Parcel in) {
        this.title = in.readString();
    }

    public Ruku(int pageNumber, double length) {
        this.pageNumber = pageNumber;
        this.length = length;
        this.title = generateName();
    }

    public String generateName() {
        return Integer.toString(pageNumber);
    }

    public String getName() {
        return title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ruku)) return false;

        Ruku ruku = (Ruku) o;

        return  this.pageNumber == ruku.pageNumber;

    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Ruku> CREATOR = new Parcelable.Creator<Ruku>() {
        @Override
        public Ruku createFromParcel(Parcel in) {
            return new Ruku(in);
        }

        @Override
        public Ruku[] newArray(int size) {
            return new Ruku[size];
        }
    };
}

