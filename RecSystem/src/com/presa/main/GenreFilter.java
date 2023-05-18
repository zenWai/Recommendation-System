package com.presa.main;

/**
 * @author zenWai
 */
public class GenreFilter implements Filter{
    private String myGenre;

    public GenreFilter(String genre) {
        myGenre=genre;
    }
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).contains(myGenre);
    }

    @Override
    public String toString() {
        return "GenreFilter{" +
                "myGenre='" + myGenre + '\'' +
                '}';
    }
}
