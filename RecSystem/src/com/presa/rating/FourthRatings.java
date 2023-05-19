package com.presa.rating;

import com.presa.filters.Filter;
import com.presa.filters.TrueFilter;
import com.presa.movie.MovieDatabase;
import com.presa.rater.Rater;
import com.presa.rater.RaterDatabase;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

/**
 * @author zenWai
 */
public class FourthRatings {
    private double getAverageByID(String movieId, int minimalRaters) {
        ArrayList<Rater> myRaters = RaterDatabase.getRaters();
        double sumRatings = 0;
        int howManyRaters = 0;
        for( Rater rater : myRaters ) {
            if (rater.hasRating(movieId)) {
                double rating = rater.getRating(movieId);
                if (rating >= 0) {
                    howManyRaters++;
                    sumRatings += rating;
                }
            }
        }
        return howManyRaters >= minimalRaters ? sumRatings/howManyRaters : 0.0;
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

    private double dotProduct (Rater me, Rater r) {
        double product = 0;
        ArrayList<String> moviesRatedByRater = r.getItemsRated();
        ArrayList<String> moviesRatedByMe = me.getItemsRated();
        for(String id : moviesRatedByRater) {
            if(moviesRatedByMe.contains(id)) {
                product = product + (me.getRating(id) - 5) * (r.getRating(id)-5);
            }
        }
        return product;
    }


    private ArrayList<Rating> getSimilarities(String id) {
        Rater me = RaterDatabase.getRater(id);

        return RaterDatabase.getRaters().stream()
                .filter(r -> !r.getID().equals(id))
                .map(r -> new AbstractMap.SimpleEntry<>(r, dotProduct(me, r)))
                .filter(entry -> entry.getValue() >= 0)
                .map(entry -> new Rating(entry.getKey().getID(), entry.getValue()))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> similarities = getSimilarities(id);

        if (similarities.size() < numSimilarRaters)
            return new ArrayList<>();

        similarities = new ArrayList<>(similarities.subList(0, numSimilarRaters));

        Map<String, ArrayList<Double>> movieRatings = new HashMap<>();
        for (Rating rating : similarities) {
            Rater rater = RaterDatabase.getRater(rating.getItem());
            MovieDatabase.filterBy(new TrueFilter()).stream()
                    .filter(rater::hasRating)
                    .forEach(movieID -> {
                        if (!movieRatings.containsKey(movieID))
                            movieRatings.put(movieID, new ArrayList<>());
                        movieRatings.get(movieID).add(rating.getValue() * rater.getRating(movieID));
                    });
        }

        return movieRatings.entrySet().stream()
                .filter(e -> e.getValue().size() >= minimalRaters)
                .map(e -> new Rating(e.getKey(), e.getValue().stream().mapToDouble(Double::doubleValue).sum() / e.getValue().size()))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> similarities = getSimilarities(id);

        if (similarities.size() < numSimilarRaters)
            return new ArrayList<>(); // Return an empty list if there are not enough similar raters

        similarities = new ArrayList<>(similarities.subList(0, numSimilarRaters));

        Map<String, ArrayList<Double>> movieRatings = new HashMap<>();
        for (Rating rating : similarities) {
            Rater rater = RaterDatabase.getRater(rating.getItem());
            MovieDatabase.filterBy(filterCriteria).stream()
                    .filter(rater::hasRating)
                    .forEach(movieID -> {
                        if (!movieRatings.containsKey(movieID))
                            movieRatings.put(movieID, new ArrayList<>());
                        movieRatings.get(movieID).add(rating.getValue() * rater.getRating(movieID));
                    });
        }

        return movieRatings.entrySet().stream()
                .filter(e -> e.getValue().size() >= minimalRaters)
                .map(e -> new Rating(e.getKey(), e.getValue().stream().mapToDouble(Double::doubleValue).sum() / e.getValue().size()))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
