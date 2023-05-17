package com.presa.main;

import lib.csv.CSVRecord;
import lib.duke.*;

import java.io.IOException;
import java.util.*;
/**
 * @author zenWai
 */
public class FirstRatings {
    private final String DATAPATH = "data/";
    public ArrayList<Movie> loadMovies(String filename) {
        FileResource file = new FileResource(DATAPATH+filename);

        ArrayList<Movie> movies = new ArrayList<>();
        for( CSVRecord currRow : file.getCSVParser() ) {
            movies.add(
                    new Movie(
                            currRow.get("id"),
                            currRow.get("title"),
                            currRow.get("year"),
                            currRow.get("genre"),
                            currRow.get("director"),
                            currRow.get("country"),
                            currRow.get("poster"),
                            Integer.parseInt(currRow.get("minutes"))
                            ));
        }
        return movies;
    }

    public int getHowManyMoviesWithGenre(ArrayList<Movie> movies, String genre) {
        int counter = 0 ;
        for(Movie m : movies) {
            if(m.getGenres().contains(genre)) {
                counter++;
            }
        }
        return counter;
    }

    public int getHowManyMoviesLongerThan(ArrayList<Movie> movies, int checkMinutes) {
        int counter = 0 ;
        for(Movie m : movies) {
            if(m.getMinutes() > checkMinutes) {
                counter++;
            }
        }
        return counter;
    }

    public HashMap<String, Integer> getDirectorCounts(ArrayList<Movie> movies) {
        HashMap<String, Integer> directorCounts = new HashMap<>();

        for(Movie m : movies) {
            String directors = m.getDirector();
            String[] directorsArray = directors.split(",");

            for(String director : directorsArray) {
                director = director.trim();
                if(directorCounts.containsKey(director)) {
                    directorCounts.put(director, directorCounts.get(director)+1);
                } else {
                    directorCounts.put(director, 1);
                }
            }

        }
        return directorCounts;
    }

    public void printMaxMoviesDirector(HashMap<String, Integer> directorCounts) {
        int maxMovies = 0;
        for (int count : directorCounts.values()) {
            if (count > maxMovies) {
                maxMovies = count;
            }
        }

        List<String> directorsWithMaxMovies = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : directorCounts.entrySet()) {
            if (entry.getValue() == maxMovies) {
                directorsWithMaxMovies.add(entry.getKey());
            }
        }

        System.out.println("The greatest number of movies directed by a director is: " + maxMovies);
        String directorsNames = String.join(", ", directorsWithMaxMovies);
        System.out.println("The director(s) who has/have achieved this feat is/are: " + directorsNames);
    }

    public ArrayList<Rater> loadRaters(String filename) {
        FileResource file = new FileResource(DATAPATH+filename);
        HashMap<String, Rater> raterMap = new HashMap<>();
        for (CSVRecord currRow : file.getCSVParser()) {
            String raterId = currRow.get("rater_id");

            Rater rater;
            if (raterMap.containsKey(raterId)) {
                rater = raterMap.get(raterId);
            } else {
                rater = new Rater(raterId);
                raterMap.put(raterId, rater);
            }

            rater.addRating(
                    currRow.get("movie_id"),
                    Double.parseDouble(currRow.get("rating"))
            );
        }

        return new ArrayList<>(raterMap.values());
    }

    public void printRatersInfo(ArrayList<Rater> raters) {
        System.out.println("Total number of raters: " + raters.size());

        for (Rater rater : raters) {
            System.out.println("Rater ID: " + rater.getID() + ", Number of ratings: " + rater.numRatings());
            ArrayList<String> ratedMovies = rater.getItemsRated();

            for (String itemId : ratedMovies) {
                System.out.println("Movie ID: " + itemId + ", Rating: " + rater.getRating(itemId));
            }
        }
    }

    public void printNumRatingsForRater(ArrayList<Rater> raters, String raterId) {
        for (Rater rater : raters) {
            if (rater.getID().equals(raterId)) {
                System.out.println("Rater ID: " + rater.getID() + ", Number of ratings: " + rater.numRatings());
                return;
            }
        }
        System.out.println("No rater found with ID: " + raterId);
    }

    public void printRatersWithMaxRatings(ArrayList<Rater> raters) {
        int maxRatings = 0;
        ArrayList<String> ratersWithMaxRatings = new ArrayList<>();

        for (Rater rater : raters) {
            int numRatings = rater.numRatings();
            if (numRatings > maxRatings) {
                maxRatings = numRatings;
                ratersWithMaxRatings.clear();
                ratersWithMaxRatings.add(rater.getID());
            } else if (numRatings == maxRatings) {
                ratersWithMaxRatings.add(rater.getID());
            }
        }

        System.out.println("The greatest number of ratings by a rater: " + maxRatings);
        String ratersIds = String.join(", ", ratersWithMaxRatings);
        System.out.println("Rater(s) with maximum number of ratings, id(s): " + ratersIds);
    }

    public int getMovieRatingCountFromRaters(ArrayList<Rater> raters, String movieId) {
        int counter = 0;
        for (Rater rater : raters) {
            if (rater.hasRating(movieId)) {
                counter++;
            }
        }
        return counter;
    }

    public void printNumberOfRatedMovies(ArrayList<Rater> raters) {
        //no uniques
        Set<String> movies = new HashSet<>();
        for (Rater rater : raters) {
            ArrayList<String> ratedMovies = rater.getItemsRated();
            movies.addAll(ratedMovies);
        }
        System.out.println(raters.size() + " Raters have rated this number of movies: " + movies.size());
    }

    public static void main(String[] args) {
        FirstRatings fr = new FirstRatings();

        //////////////////
        // MOVIES
        final String FILENAMEMOVIES = "ratedmoviesfull.csv";
        final String GENRE = "Comedy";
        final int MINUTESTOCHECK = 150;
        ArrayList<Movie> movies;
        movies = fr.loadMovies(FILENAMEMOVIES);
        System.out.println();

        int howManyGenre =
                fr.getHowManyMoviesWithGenre(
                        movies,
                        GENRE
                );
        System.out.println("There are " +
                howManyGenre +
                " movies with genre " +
                GENRE);
        System.out.println();

        int howManyLongerThan =
                fr.getHowManyMoviesLongerThan(
                        movies,
                        MINUTESTOCHECK
                );
        System.out.println("There are " +
                howManyLongerThan +
                " movies that are longer than " +
                MINUTESTOCHECK +
                " minutes");
        System.out.println();

        HashMap<String,Integer> directorsCount =
                fr.getDirectorCounts(movies);
        fr.printMaxMoviesDirector(directorsCount);
        System.out.println();

        //////////////////////////////
        // RATERS

        String FILENAMERATERS = "ratings.csv";
        ArrayList<Rater> raters = fr.loadRaters(FILENAMERATERS);
        //fr.printRatersInfo(raters);

        fr.printNumRatingsForRater(raters, "193");
        System.out.println();

        fr.printRatersWithMaxRatings(raters);
        System.out.println();

        String MOVIEID = "1798709";
        int counter = fr.getMovieRatingCountFromRaters(raters, MOVIEID);
        System.out.println("Movie " + MOVIEID + " has " + counter + " ratings.");
        System.out.println();

        fr.printNumberOfRatedMovies(raters);

    }
}
