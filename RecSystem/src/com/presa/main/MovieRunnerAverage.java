package com.presa.main;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        SecondRatings sr = new SecondRatings();

        System.out.println(sr.getMovieSize());
        System.out.println(sr.getRaterSize());
    }

    public static void main(String[] args) {
        MovieRunnerAverage mra = new MovieRunnerAverage();
        mra.printAverageRatings();
    }

}
