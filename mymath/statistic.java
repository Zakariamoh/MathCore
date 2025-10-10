package mymath;


public class statistic {

	public static void main(String[] args) {}

	/**
	 * Returns the maximum (largest) value in the specified array.
	 *
	 * @param values the array of numerical values (non-null and non-empty)
	 * @return the maximum value found in the array
	 * @throws IllegalArgumentException if the array is null or empty
	 */
	public static double max(double[] values) {
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Input array must be non-null and non-empty.");

		double maxValue = values[0];
		for (double v : values)
			if (v > maxValue) maxValue = v;

		return maxValue;
	}

	/**
	 * Returns the minimum (smallest) value in the specified array.
	 *
	 * @param values the array of numerical values (non-null and non-empty)
	 * @return the minimum value found in the array
	 * @throws IllegalArgumentException if the array is null or empty
	 */
	public static double min(double[] values) {
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Input array must be non-null and non-empty.");

		double minValue = values[0];
		for (double v : values)
			if (v < minValue) minValue = v;

		return minValue;
	}

	/**
	 * Computes the mid-range of a dataset, defined as the arithmetic mean
	 * of its minimum and maximum values:
	 * 
	 * <pre>
	 *     midRange = (min + max) / 2
	 * </pre>
	 *
	 * @param values an array of numerical values (non-null and non-empty)
	 * @return the mid-range value of the dataset
	 * @throws IllegalArgumentException if the array is null or empty
	 */
	public static double midRange(double[] values) {
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Input array must be non-null and non-empty.");

		return (max(values) + min(values)) / 2.0;
	}

	/**
	 * Computes the median of a dataset, which is the value separating
	 * the higher half from the lower half of the sorted data.
	 * 
	 * <p>If the dataset contains an odd number of elements, the median
	 * is the middle element. If it contains an even number of elements,
	 * the median is the arithmetic mean of the two middle elements.
	 *
	 * @param values the array of numerical values (non-null and non-empty)
	 * @return the median value of the dataset
	 * @throws IllegalArgumentException if the array is null or empty
	 */
	public static double median(double[] values) {
	    // --- Input validation ---
	    if (values == null || values.length == 0)
	        throw new IllegalArgumentException("Input array must be non-null and non-empty.");

	    // --- Make a copy and sort it (non-destructive) ---
	    double[] sortedValues = values.clone();
	    java.util.Arrays.sort(sortedValues);

	    int n = sortedValues.length;
	    if (n % 2 == 1) {
	        // Odd number of elements → middle element
	        return sortedValues[n / 2];
	    } else {
	        // Even number of elements → average of two middle elements
	        return (sortedValues[n / 2 - 1] + sortedValues[n / 2]) / 2.0;
	    }
	}


	/**
	 * Computes the weighted mean of all elements in the specified quartile range
	 * of the given array.
	 * 
	 * <p>Both {@code lowerQuartile} and {@code upperQuartile} must be expressed as
	 * real numbers in the closed interval [0, 1], where:
	 * <ul>
	 *   <li>0 corresponds to the minimum (0th percentile)</li>
	 *   <li>0.25 corresponds to the first quartile (Q1)</li>
	 *   <li>0.5 corresponds to the median (Q2)</li>
	 *   <li>0.75 corresponds to the third quartile (Q3)</li>
	 *   <li>1 corresponds to the maximum (100th percentile)</li>
	 * </ul>
	 * 
	 * <p>The function first clones and sorts the input array to preserve the
	 * original order. It then computes the indices corresponding to the requested
	 * quartile bounds and returns the weighted mean of the values within that interval.
	 * 
	 * @param values the array of numerical values (non-null and non-empty)
	 * @param lowerQuartile the lower quartile bound (inclusive), between 0 and 1
	 * @param upperQuartile the upper quartile bound (exclusive), between 0 and 1
	 * @return the mean of the elements within the specified quartile range
	 * @throws IllegalArgumentException if quartile bounds are invalid or equal
	 */

	public static double wmean(double[] values, double[] weights, double lowerQuartile, double upperQuartile) {
		// --- Validation ---
		if (values == null || weights == null)
			throw new IllegalArgumentException("Input arrays must be non-null.");
		if (values.length == 0)
			throw new IllegalArgumentException("Values array cannot be empty.");
		if (values.length != weights.length)
			throw new IllegalArgumentException("Values and weights must have the same length.");
		if (lowerQuartile < 0 || upperQuartile > 1 || lowerQuartile > upperQuartile)
			throw new IllegalArgumentException("Quartile bounds must satisfy 0 ≤ lower ≤ upper ≤ 1.");

		int n = values.length;

		// --- Sort values with their weights (non-destructive) ---
		double[][] pairs = new double[n][2];
		for (int i = 0; i < n; i++) {
			pairs[i][0] = values[i];
			pairs[i][1] = weights[i];
		}
		java.util.Arrays.sort(pairs, java.util.Comparator.comparingDouble(a -> a[0]));

		// --- Quartile index range ---
		int lowerIndex = (int) Math.floor(lowerQuartile * n);
		int upperIndex = (int) Math.ceil(upperQuartile * n);
		if (upperIndex > n) upperIndex = n;

		// --- Weighted mean computation ---
		double weightedSum = 0;
		double weightSum = 0;
		for (int i = lowerIndex; i < upperIndex; i++) {
			double value = pairs[i][0];
			double weight = pairs[i][1];
			weightedSum += value * weight;
			weightSum += weight;
		}

		if (weightSum == 0)
			throw new ArithmeticException("Sum of weights is zero.");

		return weightedSum / weightSum;
	}

	/**
	 * Computes the geometric mean of all elements within the specified quartile range
	 * of the given array, using a numerically stable logarithmic formulation.
	 *
	 * <p>Both {@code lowerQuartile} and {@code upperQuartile} must be real numbers in
	 * the closed interval [0, 1], where:
	 * <ul>
	 *   <li>0.00 corresponds to the minimum (0th percentile)</li>
	 *   <li>0.25 corresponds to the first quartile (Q1)</li>
	 *   <li>0.50 corresponds to the median (Q2)</li>
	 *   <li>0.75 corresponds to the third quartile (Q3)</li>
	 *   <li>1.00 corresponds to the maximum (100th percentile)</li>
	 * </ul>
	 *
	 * <p>The method clones and sorts the input array to preserve its original order.
	 * It then determines the subarray corresponding to the specified quartile range
	 * and computes the geometric mean of those elements:
	 *
	 * <pre>
	 *   G = exp( (1 / n) * Σ ln(xᵢ) )
	 * </pre>
	 *
	 * <p>This logarithmic formulation prevents overflow or underflow for large or small
	 * datasets and ensures stable numerical behavior.
	 *
	 * @param values         the array of positive numerical values (non-null and non-empty)
	 * @param lowerQuartile  the lower quartile bound (inclusive), between 0 and 1
	 * @param upperQuartile  the upper quartile bound (exclusive), between 0 and 1
	 * @return the geometric mean of the elements within the specified quartile range
	 * @throws IllegalArgumentException if:
	 *         <ul>
	 *           <li>{@code values} is null or empty,</li>
	 *           <li>any element in the selected range is ≤ 0,</li>
	 *           <li>quartile bounds are outside [0, 1],</li>
	 *           <li>the upper quartile is less than the lower quartile, or</li>
	 *           <li>the specified quartile range contains no data points.</li>
	 *         </ul>
	 */

	public static double geomean(double[] values, double lowerQuartile, double upperQuartile) {
		// --- Input validation ---
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Input array must be non-null and non-empty.");
		if (lowerQuartile < 0 || upperQuartile > 1 || lowerQuartile > upperQuartile)
			throw new IllegalArgumentException("Quartile bounds must satisfy 0 ≤ lower ≤ upper ≤ 1.");

		// --- Sorting (non-destructive) ---
		double[] sortedValues = values.clone();
		java.util.Arrays.sort(sortedValues);

		int n = sortedValues.length;
		int lowerIndex = (int) Math.floor(lowerQuartile * n);
		int upperIndex = (int) Math.ceil(upperQuartile * n);
		if (upperIndex > n) upperIndex = n;

		// --- Log-sum computation for numerical stability ---
		double logSum = 0.0;
		int count = 0;

		for (int i = lowerIndex; i < upperIndex; i++) {
			double v = sortedValues[i];
			if (v <= 0)
				throw new IllegalArgumentException("Geometric mean is undefined for zero or negative values.");
			logSum += Math.log(v);
			count++;
		}

		if (count == 0)
			throw new IllegalArgumentException("Quartile range contains no data points.");

		// --- Return geometric mean ---
		return Math.exp(logSum / count);
	}


	/**
	 * Computes the harmonic mean of all elements within the specified quartile range
	 * of the given array.
	 *
	 * <p>Both {@code lowerQuartile} and {@code upperQuartile} must be real numbers in
	 * the closed interval [0, 1], where:
	 * <ul>
	 *   <li>0.00 corresponds to the minimum (0th percentile)</li>
	 *   <li>0.25 corresponds to the first quartile (Q1)</li>
	 *   <li>0.50 corresponds to the median (Q2)</li>
	 *   <li>0.75 corresponds to the third quartile (Q3)</li>
	 *   <li>1.00 corresponds to the maximum (100th percentile)</li>
	 * </ul>
	 *
	 * <p>The method clones and sorts the input array to preserve its original order.
	 * It then determines the subset of elements falling between the two quartile
	 * boundaries and computes their harmonic mean:
	 *
	 * <pre>
	 *   H = n / Σ (1 / xᵢ)
	 * </pre>
	 *
	 * <p>All selected values must be strictly positive. This constraint follows
	 * from the mathematical definition of the harmonic mean, which is undefined
	 * for zero or negative inputs.
	 *
	 * @param values         the array of positive numerical values (non-null and non-empty)
	 * @param lowerQuartile  the lower quartile bound (inclusive), between 0 and 1
	 * @param upperQuartile  the upper quartile bound (exclusive), between 0 and 1
	 * @return the harmonic mean of the elements within the specified quartile range
	 * @throws IllegalArgumentException if:
	 *         <ul>
	 *           <li>{@code values} is null or empty,</li>
	 *           <li>any selected element ≤ 0,</li>
	 *           <li>quartile bounds are outside [0, 1],</li>
	 *           <li>the upper quartile is less than the lower quartile, or</li>
	 *           <li>the specified quartile range contains no data points.</li>
	 *         </ul>
	 */
	public static double harmean(double[] values, double lowerQuartile, double upperQuartile) {
		// --- Input validation ---
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Input array must be non-null and non-empty.");
		if (lowerQuartile < 0 || upperQuartile > 1 || lowerQuartile > upperQuartile)
			throw new IllegalArgumentException("Quartile bounds must satisfy 0 ≤ lower ≤ upper ≤ 1.");
		if (upperQuartile == lowerQuartile)
			throw new IllegalArgumentException("Quartile range cannot be empty.");

		// --- Sorting (non-destructive) ---
		double[] sortedValues = values.clone();
		java.util.Arrays.sort(sortedValues);

		// --- Quartile index calculation ---
		int n = sortedValues.length;
		int lowerIndex = (int) Math.floor(lowerQuartile * n);
		int upperIndex = (int) Math.ceil(upperQuartile * n);

		// --- Mean computation over quartile range ---
		double inverseSum = 0.0;
		int count = 0;

		for (int i = lowerIndex; i < upperIndex && i < n; i++) {
			double v = sortedValues[i];
			if (v <= 0)
				throw new IllegalArgumentException("Harmonic mean is undefined for non-positive values (found: " + v + ").");
			inverseSum += 1.0 / v;
			count++;
		}

		if (count == 0)
			throw new IllegalArgumentException("Quartile range contains no data points.");

		return count / inverseSum;
	}


	/**
	 * Computes the arithmetic mean of all elements in the specified quartile range
	 * of the given array.
	 * 
	 * <p>Both {@code lowerQuartile} and {@code upperQuartile} must be expressed as
	 * real numbers in the closed interval [0, 1], where:
	 * <ul>
	 *   <li>0 corresponds to the minimum (0th percentile)</li>
	 *   <li>0.25 corresponds to the first quartile (Q1)</li>
	 *   <li>0.5 corresponds to the median (Q2)</li>
	 *   <li>0.75 corresponds to the third quartile (Q3)</li>
	 *   <li>1 corresponds to the maximum (100th percentile)</li>
	 * </ul>
	 * 
	 * <p>The function first clones and sorts the input array to preserve the
	 * original order. It then computes the indices corresponding to the requested
	 * quartile bounds and returns the arithmetic mean of the values within that interval.
	 * 
	 * @param values the array of numerical values (non-null and non-empty)
	 * @param lowerQuartile the lower quartile bound (inclusive), between 0 and 1
	 * @param upperQuartile the upper quartile bound (exclusive), between 0 and 1
	 * @return the mean of the elements within the specified quartile range
	 * @throws IllegalArgumentException if quartile bounds are invalid or equal
	 */
	public static double arithmean(double[] values, double lowerQuartile, double upperQuartile) {
		// --- Input validation ---
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("input array must be non-null and non-empty");
		if (lowerQuartile < 0 || upperQuartile < 0 || lowerQuartile > 1 || upperQuartile > 1)
			throw new IllegalArgumentException("quartile bounds must be within [0, 1]");
		if (upperQuartile < lowerQuartile)
			throw new IllegalArgumentException("upper quartile cannot be less than lower quartile");
		if (upperQuartile == lowerQuartile)
			throw new IllegalArgumentException("quartile range cannot be empty");

		// --- Sorting (non-destructive) ---
		double[] sortedValues = values.clone();
		java.util.Arrays.sort(sortedValues);

		// --- Quartile index calculation ---
		int totalCount = sortedValues.length;
		int lowerIndex = (int) Math.floor(lowerQuartile * values.length);
		int upperIndex = (int) Math.ceil(upperQuartile * values.length);

		// --- Mean computation over quartile range ---
		double cumulativeSum = 0.0;
		int includedCount = 0;

		for (int i = lowerIndex; i < upperIndex && i < totalCount; i++) {
			cumulativeSum += sortedValues[i];
			includedCount++;
		}

		if (includedCount == 0)
			throw new IllegalArgumentException("quartile range contains no data points");

		return cumulativeSum / includedCount;
	}



}
