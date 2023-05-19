package com.presa.filters;

import com.presa.movie.MovieDatabase;

import java.util.Arrays;
import java.util.List;

public class DirectorsFilters implements Filter {

    private List<String> directors;

    public DirectorsFilters(String directors) {
        this.directors = Arrays.asList(directors.split(","));
    }

    @Override
    public boolean satisfies(String id) {
        String movieDirectors = MovieDatabase.getDirector(id);
        return directors.stream().anyMatch(movieDirectors::contains);
    }

    @Override
    public String toString() {
        return "DirectorsFilters{" +
                "directors=" + directors +
                '}';
    }
}
