package mymath;

/**
 * Trigonometric functions: sine, cosine, tangent, their reciprocals
 * (csc, sec, cot), inverses, hyperbolic forms and their inverses,
 * plus angle conversions. All angles are in radians unless stated otherwise.
 */
public class trigonometry {

	public static void main(String[] args) {}

	//Returns the cosine of a value in radians
	public static double cos(double x) {
		return Math.cos(x);
	} 

	//Returns the arc cosine of a value;
	public static double acos(double x) {
		return Math.acos(x);
	}

	//Return secant of x
	public static double sec(double x) {
		return 1/Math.cos(x);
	} 

	//Returns the hyperbolic cosine of a value.
	public static double cosh(double x) {
		return Math.cosh(x);
	} 

	//Return inverse hyperbolic cosine of x
	public static double acosh(double x) {
		if (x < 1) throw new IllegalArgumentException("x must be >= 1");
		return Math.log(x + Math.sqrt(x*x - 1));
	}

	//Returns the sine of a value
	public static double sin(double x) {
		return Math.sin(x);
	}

	//Returns the arc sine of a value;
	public static double asin(double x) {
		return Math.asin(x);
	}

	//Return the cosecant of x
	public static double csc(double x) {
		return 1/Math.sin(x);
	}

	//Returns the hyperbolic sine of a value.
	public static double sinh(double x) {
		return Math.sinh(x);
	}

	//Return inverse hyperbolic sine of x
	public static double asinh(double x) {
		return Math.log(x + Math.sqrt(x*x + 1));
	}

	//return the tangent of a value
	public static double tan(double x) {
		return Math.tan(x);
	}

	//Returns the arc tangent of a value;
	public static double atan(double x) {
		return Math.atan(x);
	}

	//Return cotangent
	public static double cot(double x) {
		return 1/Math.tan(x);
	}
	//Return the hyperbolic tangent of a value
	public static double tanh(double x) {
		return Math.tanh(x);
	}

	//return inverse hyperbolic tangent of x
	public static double atanh(double x) {
		if (x <= -1 || x >= 1) throw new IllegalArgumentException("x must be in (-1, 1)");
		return 0.5 * Math.log((1 + x) / (1 - x));
	}



	//Returns the angle theta from the conversion of 
	//rectangular coordinates (x, y) 
	//to polar coordinates (r, theta).
	public static double atan2(double x, double y) {
		return Math.atan2(x,y);
	}

	//Converts from degree to radians
	public static double degtorad(double x) {
		return x*Math.PI/180;
	}

	//Converts from radian to degree
	public static double radtodeg(double x) {
		return x*180/Math.PI;
	}

	//Returns sqrt(x^2 +y^2) without intermediate overflow or underflow
	public static double hypot(double a, double b) {
		return Math.hypot(a, b);
	}
}
