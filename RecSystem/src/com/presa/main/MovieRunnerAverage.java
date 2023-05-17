package com.presa.main;

import java.util.ArrayList;
/**
 * @author zenWai
 */
public class MovieRunnerAverage {
    public void printAverageRatings() {
        SecondRatings sr = new SecondRatings();

        ArrayList<Rating> movieAvgRatings = sr.getAverageRatings(12);
        //sort ratings of movies from less to higher 0 to 10
        //print avgRating Title
        movieAvgRatings.stream()
                .sorted()
                .forEach(Rating -> {
                    String title = sr.getTitle(Rating.getItem());
                    System.out.println(Rating.getValue() + " " + title);
                });
    }

    public void printAverageRatingOneMovie() {
        String TITLE = "Vacation";
        SecondRatings sr = new SecondRatings();
        ArrayList<Rating> movieAvgRatings = sr.getAverageRatings(3);
        movieAvgRatings.stream()
                .filter(r -> sr.getTitle(r.getItem()).equals(TITLE))
                .findFirst()
                .ifPresentOrElse(
                        rating -> {
                            //if a Rating/filter is found
                            System.out.println("Average rating for title: " + TITLE + " " + rating.getValue());
                        },
                        () -> {
                            // not found
                            System.out.println("No rating found for title: " + TITLE);
                        }
                );
    }

    public static void main(String[] args) {
        MovieRunnerAverage mra = new MovieRunnerAverage();
        mra.printAverageRatings();
        mra.printAverageRatingOneMovie();
    }

}
