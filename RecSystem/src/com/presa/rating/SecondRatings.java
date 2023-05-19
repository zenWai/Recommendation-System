package com.presa.rating;

import com.presa.movie.Movie;
import com.presa.rater.Rater;

import java.util.*;
/**
 * @author zenWai
 */
public class SecondRatings {
    private final ArrayList<Movie> myMovies;
    private final ArrayList<Rater> myRaters;
    private final FirstRatings fr = new FirstRatings();
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String movieFile, String ratingsFile) {

        myMovies = fr.loadMovies(movieFile);
        myRaters = fr.loadRaters(ratingsFile);
    }

    public int getMovieSize() {
        return myMovies.size();
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
        ArrayList<Rating> averageRatings = new ArrayList<>();
        for (Movie m : myMovies) {
            double average = getAverageByID(m.getID(), minimalRaters);
            if (average > 0) {
                averageRatings.add(new Rating(m.getID(), average));
            }
        }
        return averageRatings;
    }

    public String getTitle(String id) {
        for(Movie m : myMovies) {
            if(m.getID().equals(id)) {
                return m.getTitle();
            }
        }
        return "The searched movie id was now found";
    }

    public String getId(String title) {
        return myMovies.stream()
                .filter(m -> m.getTitle().equals(title))
                .map(Movie::getID)
                .findFirst()
                .orElse("Movie " + title + " was not found");
    }


}
