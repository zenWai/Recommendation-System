package com.presa.main;

/**
 * @author zenWai
 */
public class YearAfterFilter implements Filter {
	private int myYear;
	
	public YearAfterFilter(int year) {
		myYear = year;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= myYear;
	}

	@Override
	public String toString() {
		return "YearAfterFilter{" +
				"myYear=" + myYear +
				'}';
	}
}
