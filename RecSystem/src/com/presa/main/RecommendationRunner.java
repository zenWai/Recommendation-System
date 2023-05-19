package com.presa.main;

import com.presa.movie.MovieDatabase;
import com.presa.rater.Rater;
import com.presa.rater.RaterDatabase;
import com.presa.rating.FourthRatings;
import com.presa.rating.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class RecommendationRunner implements Recommender {

    public ArrayList<String> getItemsToRate(){
        FourthRatings fourthRatings = new FourthRatings();
        int MINIMALRATERS = 18;
        ArrayList<Rating> movieAvgRatings = fourthRatings.getAverageRatings(MINIMALRATERS);

        return movieAvgRatings.stream()
                .sorted(Collections.reverseOrder())
                .limit(10)
                .map(Rating::getItem)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fourthRatings = new FourthRatings();

        int numSimilarRaters = 35;
        int minimalRaters = 25;

        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);

        // If the size of similarRatings is less than 15 then reducing minimalRaters
        while (similarRatings.size() < 20 && minimalRaters > 0) {
            minimalRaters--;
            similarRatings = fourthRatings.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        }

        Rater user = RaterDatabase.getRater(webRaterID);

        ArrayList<String> top20MovieIds = similarRatings.stream()
                .filter(rating -> !user.hasRating(rating.getItem()))
                .sorted(Collections.reverseOrder())
                .limit(20)
                .map(Rating::getItem)
                .collect(Collectors.toCollection(ArrayList::new));


        // CSS zebra striping
        System.out.println("<style> "
                + "table {table-layout: auto; margin: 0 auto; border-collapse: collapse;} "
                + "th, td {border: 1px solid black; padding: 5px; text-align: center; white-space: nowrap; font-size: 1.2em;} "
                + "tr:nth-child(even) {background-color: #f2f2f2;} "
                + "th {background-color: #4CAF50; color: white; font-size: 1.5em; font-weight: bold;} "
                + ".posterImg {transition: transform .2s; width: 128px; height: 85px; object-fit: contain;}"
                + ".posterImg:hover {position: relative; z-index: 1; transform: scale(3.5);}"
                + "</style>");

        System.out.println("<table>");
        System.out.println("<caption style='caption-side: top; text-align: center; font-size: 1.7em; font-weight: bold;'>These are the top 20 recommended movies for you.</caption>");
        System.out.println("<tr><th>Movie Title</th><th>Year</th><th>Poster</th><th>Genres</th><th>Directors</th><th>Country</th><th>Minutes</th></tr>");

        for (String movieId : top20MovieIds) {
            String title = MovieDatabase.getTitle(movieId);
            int year = MovieDatabase.getYear(movieId);
            String genres = MovieDatabase.getGenres(movieId);
            String poster = MovieDatabase.getPoster(movieId);
            int minutes = MovieDatabase.getMinutes(movieId);
            String country = MovieDatabase.getCountry(movieId);
            String director = MovieDatabase.getDirector(movieId);

            //when poster not available loading https://www.movienewz.com/img/films/poster-holder.jpg
            System.out.println("<tr>"
                    + "<td>" + title + "</td>"
                    + "<td>" + year + "</td>"
                    + "<td><img class='posterImg' src='" + poster + "' onerror='this.src=\"https://www.movienewz.com/img/films/poster-holder.jpg\";'></td>"
                    + "<td>" + genres + "</td>"
                    + "<td>" + director + "</td>"
                    + "<td>" + country + "</td>"
                    + "<td>" + minutes + "</td>"
                    + "</tr>"
            );
        }

        // End HTML
        System.out.println("</table>");
    }

}
