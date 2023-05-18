package com.presa.main;

import java.util.ArrayList;

/**
 * @author zenWai
 */
public class AllFilters implements Filter {
    ArrayList<Filter> filters;
    
    public AllFilters() {
        filters = new ArrayList<>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(String id) {
        for(Filter f : filters) {
            if (! f.satisfies(id)) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "AllFilters{" +
                "filters=" + filters +
                '}';
    }
}
