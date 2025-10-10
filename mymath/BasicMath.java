package mymath;

//fundamental scalar operations — exponentiation, 
//logarithms, absolute values, and simple number 
//theory operations.
public class BasicMath {

	public static void main(String[] args) {}

	// General power function
	public static double pow(double base, double exponent) {
		// handle zero base
		if (base == 0 && exponent > 0) return 0;
		if (base == 0 && exponent == 0) return 1; // convention
		if (base == 0) throw new ArithmeticException("0 raised to negative power");

		// integer exponent optimization
		if (exponent == (int) exponent) {
			return powerInt(base, (int) exponent);
		}

		// for real exponents, use exp and log
		return exp(exponent * log(base));
	}

	// helper for integer exponents
	private static double powerInt(double base, int exponent) {
		if (exponent == 0) return 1;
		if (exponent < 0) return 1.0 / powerInt(base, -exponent);

		double result = 1;
		for (int i = 0; i < exponent; i++) {
			result *= base;
		}
		return result;
	}

	//Exponentiel function
	public static double exp(double x) {
		double sum = 1.0; // first term
		double term = 1.0;
		int n = 1;

		while (abs(term) > 1e-15) {
			term *= x / n;
			sum += term;
			n++;
		}

		return sum;
	}

	//Absolute value function 
	public static double abs(double value) {
		if(value <0) 
			return value*(-1);
		else
			return value;
	}

	//Returns the first floating-point argument with the 
	//sign of the second floating-point argument.

	public static double copySigne(double magnitude,double sign) {
		return Math.copySign(magnitude,sign);
	}

	//Returns the natural logarithm of x
	public static double log(double x) {
		if (x <= 0) throw new IllegalArgumentException("x must be positive non zero number");

		double y = 1; // initial guess
		double epsilon = 1e-15;

		while (true) {
			double expY = exp(y);
			double yNext = y - 1 + x / expY;
			if (abs(yNext - y) < epsilon) break;
			y = yNext;
		}
		return y;
	}

	
	//Return the base y logarithm of x
	public static double glog(double x, double y) {
		if (x <= 0) throw new IllegalArgumentException("x must be positive");
	    if (y <= 0) throw new IllegalArgumentException("the base must be positive");
		if(y == 1) throw new IllegalArgumentException("the base must be different from 1");	
		return Math.log(x)/Math.log(y);
	}

	
	//Returns the sum of its arguments
	public static double addExact(int x,int y) {
		return Math.addExact(x,y);
	}


	//Return the cube root of a double value
	public static double cbrt(double x) {
		return Math.cbrt(x);
	}

	//Return the square root of a double value
	public static double sqrt(double x){	
		return Math.sqrt(x);
	}



	//Returns the smallest (closest to negative infinity) 
	//double value that is greater than or equal to the 
	//argument and is equal to a mathematical integer.
	public static double ceil(double x) {
		return Math.ceil(x);
	}


	//Returns the value of x rounded down to its nearest integer
	public static double floor(double a) {
		return Math.floor(a);
	}

	//Returns the division between x and y rounded down
	public static int floorDiv(int a, int b) {
		return Math.floorDiv(a,b);
	}

	//Returns the remainder of a division between x and y where the result of the division was rounded down
	public static int floorMod(int a ,int b) {
		return Math.floorMod(a,b);
	}

	//Returns the unbiased exponent used in x
	public static int getExponent(int a) {
		return Math.getExponent(a);
	}


	//Computes the remainder operation on x and y as prescribed by the IEEE 754 standard
	public static double IEEEremainder(double x, double y) {
		return Math.IEEEremainder(x, y);
	}

	//return a+1
	public static double incrementExact(int a) {
		return Math.incrementExact(a);
	}

	//Returns the argument decremented by one, 
	//throwing an exception if the result overflows an integer.
	public static int decrementExact(int x) {
		return Math.decrementExact(x);
	}
	
}
