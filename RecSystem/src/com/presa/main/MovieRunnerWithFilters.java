package com.presa.main;

import java.util.ArrayList;

public class MovieRunnerWithFilters {
    private final ThirdRatings thirdRatings;
    public MovieRunnerWithFilters() {
        this.thirdRatings = new ThirdRatings("ratings.csv");
        System.out.println("How many raters: " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("How many movies: " + MovieDatabase.size());
        System.out.println();
    }
    public void printAverageRatings() {
        System.out.println();

        int MINIMALRATERS = 35;
        ArrayList<Rating> movieAvgRatings = thirdRatings.getAverageRatings(MINIMALRATERS);
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

    public void printAverageRatingsByYear() {
        System.out.println();

        YearAfterFilter year = new YearAfterFilter(2000);
        int MINIMALRATERS = 20;
        ArrayList<Rating> movieAvgRatings = thirdRatings.getAverageRatingsByFilter(MINIMALRATERS,year);
        System.out.println("After filter by " + MINIMALRATERS + " and " + year);
        System.out.println("There are " +
                movieAvgRatings.size() +
                " movies that got more than this number of ratings: " +
                MINIMALRATERS
        );
        movieAvgRatings.stream()
                .sorted()
                .forEach(Rating -> {
                    String title = MovieDatabase.getTitle( Rating.getItem() );
                    int movieYear = MovieDatabase.getYear( Rating.getItem() );
                    System.out.println(Rating.getValue() + " " + movieYear + " " + title);
                });
    }

    public void printAverageRatingsByGenre() {
        System.out.println();

        GenreFilter genreFilter = new GenreFilter("Comedy");
        int MINIMALRATERS = 20;
        ArrayList<Rating> movieAvgRatings = thirdRatings.getAverageRatingsByFilter(MINIMALRATERS,genreFilter);
        System.out.println("After filter by " + MINIMALRATERS + " and " + genreFilter);
        System.out.println("There are " +
                movieAvgRatings.size() +
                " movies that got more than this number of ratings: " +
                MINIMALRATERS
        );
        movieAvgRatings.stream()
                .sorted()
                .forEach(Rating -> {
                    String title = MovieDatabase.getTitle( Rating.getItem() );
                    String genre = MovieDatabase.getGenres( Rating.getItem() );
                    System.out.println(Rating.getValue() + " " + title);
                    System.out.println("\t" + genre);
                });
    }

    public void printAverageRatingsByMinutes() {
        System.out.println();

        int minMinutes = 105;
        int maxMinutes = 135;
        MinutesFilter minutesFilter = new MinutesFilter(minMinutes,maxMinutes);
        int MINIMALRATERS = 5;
        ArrayList<Rating> movieAvgRatings = thirdRatings.getAverageRatingsByFilter(MINIMALRATERS,minutesFilter);
        System.out.println("After filter by " + MINIMALRATERS + " and " + minutesFilter);
        System.out.println("There are " +
                movieAvgRatings.size() +
                " movies that got more than this number of ratings: " +
                MINIMALRATERS
        );
        movieAvgRatings.stream()
                .sorted()
                .forEach(Rating -> {
                    String title = MovieDatabase.getTitle( Rating.getItem() );
                    int time = MovieDatabase.getMinutes( Rating.getItem() );
                    System.out.println(Rating.getValue() + " Time: " + time + " " + title);
                });
    }

    public void printAverageRatingsByDirectors() {
        System.out.println();

        DirectorsFilters directorsFilters = new DirectorsFilters(
                "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack"
        );
        int MINIMALRATERS = 4;
        ArrayList<Rating> movieAvgRatings = thirdRatings.getAverageRatingsByFilter(MINIMALRATERS,directorsFilters);
        System.out.println("After filter by " + MINIMALRATERS + " and " + directorsFilters);
        System.out.println("There are " +
                movieAvgRatings.size() +
                " movies that got more than this number of ratings: " +
                MINIMALRATERS
        );
        movieAvgRatings.stream()
                .sorted()
                .forEach(Rating -> {
                    String title = MovieDatabase.getTitle( Rating.getItem() );
                    String directors = MovieDatabase.getDirector( Rating.getItem() );
                    System.out.println(Rating.getValue() + " " + title);
                    System.out.println("\t" + directors);
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
        ArrayList<Rating> movieAvgRatings = thirdRatings.getAverageRatingsByFilter(MINIMALRATERS,allFilters);
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

    public void printAverageRatingsByDirectorsAndMinutes() {
        System.out.println();

        int minMinutes = 90;
        int maxMinutes = 180;
        MinutesFilter minutesFilter = new MinutesFilter(minMinutes,maxMinutes);
        DirectorsFilters directorsFilters = new DirectorsFilters(
                "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"
        );
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(minutesFilter);
        allFilters.addFilter(directorsFilters);
        int MINIMALRATERS = 3;
        ArrayList<Rating> movieAvgRatings = thirdRatings.getAverageRatingsByFilter(MINIMALRATERS,allFilters);
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
                    String directors = MovieDatabase.getDirector( Rating.getItem() );
                    int time = MovieDatabase.getMinutes( Rating.getItem() );
                    System.out.println(Rating.getValue() + " Time: " + time + " " + title);
                    System.out.println("\t" + directors);
                });
    }

    public static void main(String[] args) {
        MovieRunnerWithFilters movieRunnerWithFilters = new MovieRunnerWithFilters();
        movieRunnerWithFilters.printAverageRatings();
        movieRunnerWithFilters.printAverageRatingsByYear();
        movieRunnerWithFilters.printAverageRatingsByGenre();
        movieRunnerWithFilters.printAverageRatingsByMinutes();
        movieRunnerWithFilters.printAverageRatingsByDirectors();
        movieRunnerWithFilters.printAverageRatingsByYearAfterAndGenre();
        movieRunnerWithFilters.printAverageRatingsByDirectorsAndMinutes();
    }
}
