package com.presa.main;

public class MinutesFilter implements Filter {
    private int myMinMinutes;
    private int myMaxMinutes;
    public MinutesFilter(int minMinutes, int maxMinutes) {
        myMinMinutes=minMinutes;
        myMaxMinutes=maxMinutes;
    }
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= myMinMinutes &&
                MovieDatabase.getMinutes(id) <= myMaxMinutes;
    }

    @Override
    public String toString() {
        return "MinutesFilter{" +
                "myMinMinutes=" + myMinMinutes +
                ", myMaxMinutes=" + myMaxMinutes +
                '}';
    }
}
