package com.presa.main;

import java.util.*;
/**
 * @author zenWai
 */
/*public class PlainRater implements Rater {
    private final String myID;
    private final ArrayList<Rating> myRatings;

    public PlainRater(String id) {
        myID = id;
        myRatings = new ArrayList<>();
    }

    public void addRating(String item, double rating) {
        myRatings.add(new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        for ( Rating myRating : myRatings ) {
            if ( myRating.getItem().equals(item) ) {
                return true;
            }
        }
        
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        for ( Rating myRating : myRatings ) {
            if ( myRating.getItem().equals(item) ) {
                return myRating.getValue();
            }
        }
        
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<>();
        for ( Rating myRating : myRatings ) {
            list.add(myRating.getItem());
        }
        
        return list;
    }
}*/
