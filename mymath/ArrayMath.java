package mymath;

public class ArrayMath {

	public static void main(String[] args) {}
  

	/**
	 * Computes the sum of all values in the specified array
	 * @param values the array of numerical values (non-null and non-empty)
	 * @return the sum of of all values in the array
	 */
	public static double sum(double[] values) {
		// --- Input validation ---
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Input array must be non-null and non-empty.");
		// --- Computes The Sum Of all Values --- 
		double sum=0;
		for ( double i : values) sum += i;
		return sum;
	}

	/**
	 * Computes the product of all values in the specified array
	 * @param values the array of numerical values (non-null and non-empty)
	 * @return the product of all values in the array
	 */
	public static double product(double[] values) {
		// --- Input validation ---
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Input array must be non-null and non-empty.");
		// --- Computes the Product Of All Values ---
		double product=1;
		for ( double i : values) product *= i;
		return product;
	}

}
