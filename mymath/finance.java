package mymath;

import java.text.DecimalFormat;

/**
 * Financial helper functions (interest-rate conversions).
 */
public class finance {

	public static void main(String[] args) {
	}

	/**
	 * Converts an annual percentage rate (APR) into its equivalent monthly percentage rate,
	 * assuming compound interest equivalence.
	 *
	 * @param annualInterest the annual interest rate in percent (e.g., 12.0 for 12%)
	 * @return the monthly interest rate in percent, formatted to three decimals
	 */
	public static String tomonthlyrate(double annualInterest) {
	    if (annualInterest < 0)
	        throw new IllegalArgumentException("Annual interest cannot be negative.");

	    final double monthlyFactor = 1.0 / 12.0;
	    final double annualFactor = 1.0 + (annualInterest / 100.0);
	    final double monthlyRate = (Math.pow(annualFactor, monthlyFactor) - 1.0) * 100.0;

	    DecimalFormat formatter = new DecimalFormat("#.000");
	    return formatter.format(monthlyRate);
	}

}
