package com.presa.main;

import java.util.AbstractMap;
import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

/**
 * @author zenWai
 */
public class ThirdRatings {
    private final ArrayList<Rater> myRaters;
    private final FirstRatings fr = new FirstRatings();

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsFile) {
        myRaters = fr.loadRaters(ratingsFile);
    }

    public int getRaterSize() {
        return myRaters.size();
    }

    private double getAverageByID(String movieId, int minimalRaters) {
        int howManyRatings =
                fr.getMovieRatingCountFromRaters(myRaters, movieId);

        if(howManyRatings >= minimalRaters) {
            double sumRatings = 0.0;

            for(Rater rater : myRaters) {
                if (rater.hasRating(movieId)) {
                    double rating = rater.getRating(movieId);
                    if (rating >= 0) {
                        sumRatings += rating;
                    }
                }
            }

            return sumRatings / howManyRatings;
        }
        return 0.0;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {

        return MovieDatabase.filterBy(new TrueFilter()).stream()
                //map each movieId to mapSimpleEntry, key is movieId value - average rating
                .map(movieId -> new AbstractMap.SimpleEntry<>(movieId, getAverageByID(movieId, minimalRaters)))
                //filter where avg rating not > 0
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> new Rating(entry.getKey(), entry.getValue()))
                .collect(toCollection(ArrayList::new));
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {

        return MovieDatabase.filterBy(filterCriteria).stream()
                .map(movieId -> new AbstractMap.SimpleEntry<>(movieId, getAverageByID(movieId, minimalRaters)))
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> new Rating(entry.getKey(), entry.getValue()))
                .collect(toCollection(ArrayList::new));
    }

}
