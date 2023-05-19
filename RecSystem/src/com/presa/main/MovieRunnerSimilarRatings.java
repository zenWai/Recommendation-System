package com.presa.main;

import com.presa.filters.*;
import com.presa.movie.MovieDatabase;
import com.presa.rater.RaterDatabase;
import com.presa.rating.FourthRatings;
import com.presa.rating.Rating;

import java.util.ArrayList;

public class MovieRunnerSimilarRatings {
    private FourthRatings fourthRatings = new FourthRatings();

    public MovieRunnerSimilarRatings() {
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
    }
    public void printAverageRatings() {
        System.out.println();

        int MINIMALRATERS = 30;
        ArrayList<Rating> movieAvgRatings = fourthRatings.getAverageRatings(MINIMALRATERS);
        System.out.println("After filter by " + MINIMALRATERS);
        System.out.println("There are " +
                movieAvgRatings.size() +
                " movies that got more than this number of ratings: " +
                MINIMALRATERS
        );
        movieAvgRatings.stream()
                .sorted()
                .forEach(Rating -> {
                    String title = MovieDatabase.getTitle( Rating.getItem() );
                    System.out.println(Rating.getValue() + " " + title);
                });
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        System.out.println();

        AllFilters allFilters = new AllFilters();
        YearAfterFilter yearAfterFilter = new YearAfterFilter(1990);
        GenreFilter genreFilter = new GenreFilter("Drama");
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(genreFilter);
        int MINIMALRATERS = 8;
        ArrayList<Rating> movieAvgRatings = fourthRatings.getAverageRatingsByFilter(MINIMALRATERS,allFilters);
        System.out.println("After filter by " + MINIMALRATERS + " and " + allFilters);
        System.out.println("There are " +
                movieAvgRatings.size() +
                " movies that got more than this number of ratings: " +
                MINIMALRATERS
        );
        movieAvgRatings.stream()
                .sorted()
                .forEach(Rating -> {
                    String title = MovieDatabase.getTitle( Rating.getItem() );
                    int year = MovieDatabase.getYear( Rating.getItem() );
                    String genres = MovieDatabase.getGenres( Rating.getItem() );
                    System.out.println(Rating.getValue() + " " + year + " " + title);
                    System.out.println("\t" + genres);
                });
    }

    public void printSimilarRatings() {
        System.out.println("Similar Ratings: ");
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings("71", 20, 5);

        similarRatings.stream()
                .forEach(rating -> {
                    String movieTitle = MovieDatabase.getTitle(rating.getItem());
                    System.out.println("Movie: " + movieTitle + ", Rating: " + rating.getValue());
                });
    }

    public void printSimilarRatingsByGenre() {
        String genre = "Mystery";
        Filter genreFilter = new GenreFilter(genre);
        System.out.println();
        System.out.println("Similar Ratings filted by " + genre);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter("964", 20, 5, genreFilter);

        similarRatings.stream()
                .forEach(rating -> {
                    String movieTitle = MovieDatabase.getTitle(rating.getItem());
                    String movieGenres = MovieDatabase.getGenres(rating.getItem());
                    System.out.println("Movie: " + movieTitle + ", Rating: " + rating.getValue());
                    System.out.println("\t" + movieGenres);
                });
    }

    public void printSimilarRatingsByDirector() {
        String raterId = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";

        DirectorsFilters directorFilter = new DirectorsFilters(directors);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(raterId, numSimilarRaters, minimalRaters, directorFilter);

        System.out.println();
        System.out.println("Similar Ratings filtered by Directors: " + directors);
        similarRatings.stream().forEach(rating -> {
            String movieId = rating.getItem();
            String title = MovieDatabase.getTitle(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);
            System.out.println("Movie: " + title + ", Rating: " + rating.getValue());
            System.out.println("\t" + movieDirectors);
        });
    }

    public void printSimilarRatingsByGenreAndMinutes() {
        FourthRatings fourthRatings = new FourthRatings();
        String raterID = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
        String genre = "Drama";
        int minMinutes = 80;
        int maxMinutes = 160;

        AllFilters genreAndMinutesFilter = new AllFilters();
        genreAndMinutesFilter.addFilter(new GenreFilter(genre));
        genreAndMinutesFilter.addFilter(new MinutesFilter(minMinutes, maxMinutes));

        System.out.println();
        System.out.println("Similar Ratings filtered by: " + genreAndMinutesFilter);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, genreAndMinutesFilter);
        similarRatings.stream().forEach(rating -> {
            String movieID = rating.getItem();
            String title = MovieDatabase.getTitle(movieID);
            String genres = MovieDatabase.getGenres(movieID);
            int minutes = MovieDatabase.getMinutes(movieID);
            System.out.println(title + ", " + minutes + " minutes, Rating: " + rating.getValue());
            System.out.println("\t" + genres);
        });
    }

    public void printSimilarRatingsByYearAfterAndMinutes() {
        String raterId = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
        int year = 1975;
        int minMinutes = 70;
        int maxMinutes = 200;

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(year));
        allFilters.addFilter(new MinutesFilter(minMinutes, maxMinutes));

        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(raterId, numSimilarRaters, minimalRaters, allFilters);

        System.out.println();
        System.out.println("Similar Ratings after Year " + year + " and between " + minMinutes + " and " + maxMinutes + " minutes:");

        similarRatings.stream().forEach(rating -> {
            String movieId = rating.getItem();
            String title = MovieDatabase.getTitle(movieId);
            int movieYear = MovieDatabase.getYear(movieId);
            int movieMinutes = MovieDatabase.getMinutes(movieId);
            System.out.println("Movie: " + title + ", Year: " + movieYear + ", Minutes: " + movieMinutes + ", Rating: " + rating.getValue());
        });
    }

    public static void main(String[] args) {
        MovieRunnerSimilarRatings movieRunnerSRatings = new MovieRunnerSimilarRatings();
       /* movieRunnerSRatings.printAverageRatings();
        movieRunnerSRatings.printAverageRatingsByYearAfterAndGenre();
        movieRunnerSRatings.printSimilarRatings();
        movieRunnerSRatings.printSimilarRatingsByGenre();
        movieRunnerSRatings.printSimilarRatingsByDirector();
        movieRunnerSRatings.printSimilarRatingsByGenreAndMinutes();*/
        movieRunnerSRatings.printSimilarRatingsByYearAfterAndMinutes();
    }
}
