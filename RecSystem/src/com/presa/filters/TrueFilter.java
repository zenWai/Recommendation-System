package com.presa.filters;

/**
 * @author zenWai
 */
public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
