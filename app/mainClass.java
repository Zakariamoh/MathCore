package app;

import java.util.Scanner;
import java.lang.reflect.Method;
import mymath.*;

public class mainClass {

	public static void main(String[] args) {
        // Instantiate a Scanner object to read user input from the standard input stream (console).
        Scanner s = new Scanner(System.in);

        // Infinite loop to continuously prompt the user for input until an explicit exit condition is met.
        while (true) {
            // Print a prompt symbol to indicate readiness to receive input.
            System.out.print("> ");

            // Read the entire line entered by the user, and remove leading and trailing whitespace.
            String input = s.nextLine().trim();

            // If the input is empty (user presses Enter without typing),it continues.
            if (input.isEmpty()) continue;

            // Attempt to interpret and execute the input as a function call.
            try {
                // Print a result prefix to differentiate output from user input.
                System.out.print("[R] ");
                
                // Delegate the input string to the callFunction method which performs reflection-based execution.
                callFunction(input);

            // Catch any exception thrown during function invocation, parsing, or reflection, 
            // and report it as a user-friendly error message.
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }

	/**
	 * Dynamically invokesa function in classes of mymath package based on user input.
	 * 
	 * @param input the string entered by the user in the format "functionName(arg1,arg2,...)"
	 * @throws Exception if any reflection or invocation errors occur
	 */
	public static void callFunction(String input) throws Exception {

	    // Validate the input format: it must contain parentheses and end with ')'
	    if (!input.contains("(") || !input.endsWith(")"))
	        throw new IllegalArgumentException("Format must be name(arg1,arg2,...)");

	    // Extract the function name from input (everything before '(')
	    String funcName = input.substring(0, input.indexOf('(')).trim();

	    // Extract the arguments part from input (everything inside the parentheses)
	    String argPart = input.substring(input.indexOf('(') + 1, input.length() - 1).trim();

	    // Split the arguments string into individual arguments, or create empty array if no args
	    String[] parts = argPart.isEmpty() ? new String[0] : splitArgs(argPart);

	    // Initialize arrays to store argument values and their corresponding types
	    Object[] args = new Object[parts.length];
	    Class<?>[] types = new Class<?>[parts.length];

	    // Parse each argument from string to numeric value (int or double)
	    for (int i = 0; i < parts.length; i++) {
	        args[i] = parseNumeric(parts[i]);
	        types[i] = toPrimitive(args[i].getClass());
	    }

	    // Convert any int[] arguments to double[] to maintain compatibility with methods expecting double[]
	    for (int i = 0; i < args.length; i++) {
	        if (args[i] instanceof int[]) {
	            int[] ints = (int[]) args[i];
	            double[] doubles = new double[ints.length];
	            for (int j = 0; j < ints.length; j++) doubles[j] = ints[j];
	            args[i] = doubles;
	            types[i] = double[].class; // Update type for reflection
	        }
	    }

	    boolean found = false;
	    
	    for (Class<?> cls : FUNCTION_CLASSES) {
	        try {
	            // Try with original argument types
	            Method m = cls.getMethod(funcName, types);
	            Object result = m.invoke(null, args);
	            if (m.getReturnType() != void.class) System.out.println(result);
	            found = true;
	            break;
	        } catch (NoSuchMethodException e) {
	            // Retry with int -> double conversion if any integer argument exists
	            Object[] newArgs = new Object[args.length];
	            Class<?>[] newTypes = new Class<?>[args.length];
	            boolean retried = false;

	            for (int i = 0; i < args.length; i++) {
	                Object a = args[i];
	                if (a instanceof Integer) {
	                    newArgs[i] = ((Integer) a).doubleValue();
	                    newTypes[i] = double.class;
	                    retried = true;
	                } else {
	                    newArgs[i] = a;
	                    newTypes[i] = types[i];
	                }
	            }

	            if (retried) {
	                try {
	                    Method m2 = cls.getMethod(funcName, newTypes);
	                    Object result = m2.invoke(null, newArgs);
	                    if (m2.getReturnType() != void.class) System.out.println(result);
	                    found = true;
	                    break;
	                } catch (NoSuchMethodException ignored) {}
	            }
	        }
	    }

	    if (!found) {
	        System.out.println("error: no matching function " + funcName +
	                " with " + args.length + " argument(s) in any class");
	    }
	}

	// Classes where functions can be found
	private static final Class<?>[] FUNCTION_CLASSES = {
	    mainClass.class,  // main class
	    //Classes in my mymath package
	    ArrayMath.class,
	    BasicMath.class,
	    finance.class,
	    statistic.class,
	    trigonometry.class,  
	};

	/**
	 * Splits a string of arguments into individual components while respecting nested structures.
	 * 
	 * This method correctly handles:
	 *  - Commas separating top-level arguments
	 *  - Nested arrays denoted by square brackets [ ]
	 *  - Nested parentheses ( )
	 * 
	 * Example:
	 *   Input: "1, 2, [3,4], func(5,6)"
	 *   Output: ["1", "2", "[3,4]", "func(5,6)"]
	 * 
	 * @param s the raw arguments string extracted from user input
	 * @return an array of strings, each representing one argument
	 */
	private static String[] splitArgs(String s) {

	    // List to accumulate each argument after parsing
	    java.util.List<String> parts = new java.util.ArrayList<>();

	    // StringBuilder to construct each argument progressively
	    StringBuilder cur = new StringBuilder();

	    // Counters to track nesting levels
	    int depthSq = 0;   // square brackets nesting level
	    int depthPar = 0;  // parentheses nesting level

	    // Iterate over each character in the input string
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);

	        // Handle opening square bracket: increment depth, include in current argument
	        if (c == '[') { 
	            depthSq++; 
	            cur.append(c); 
	        }
	        // Handle closing square bracket: decrement depth, include in current argument
	        else if (c == ']') { 
	            depthSq--; 
	            cur.append(c); 
	        }
	        // Handle opening parenthesis: increment depth, include in current argument
	        else if (c == '(') { 
	            depthPar++; 
	            cur.append(c); 
	        }
	        // Handle closing parenthesis: decrement depth, include in current argument
	        else if (c == ')') { 
	            depthPar--; 
	            cur.append(c); 
	        }
	        // Handle comma: only split if at top-level (not inside brackets or parentheses)
	        else if (c == ',' && depthSq == 0 && depthPar == 0) {
	            parts.add(cur.toString().trim());  // add completed argument
	            cur.setLength(0);                  // reset builder for next argument
	        } 
	        // All other characters: append to current argument
	        else {
	            cur.append(c);
	        }
	    }

	    // Add the last argument if present
	    if (cur.length() > 0) parts.add(cur.toString().trim());

	    // Convert list to array and return
	    return parts.toArray(new String[0]);
	}


	/**
	 * Converts a wrapper class type to its corresponding primitive type.
	 * 
	 * This is useful for reflection when methods expect primitive arguments 
	 * (e.g., int, double) instead of their boxed counterparts (Integer, Double).
	 * 
	 * @param cls the Class object representing the type to convert
	 * @return the corresponding primitive Class if cls is Integer or Double,
	 *         otherwise returns the original Class object unchanged
	 */
	private static Class<?> toPrimitive(Class<?> cls) {
	    if (cls == Integer.class) return int.class;  // Convert Integer to int
	    if (cls == Double.class) return double.class; // Convert Double to double
	    return cls;  // Return as-is for all other types
	}


	/**
	 * Parses a numeric string or numeric array representation into the corresponding Java object.
	 * 
	 * Handles two cases:
	 * 1. Array notation: "[1,2,3]" or "[1.0,2.5,3]"
	 *    - Returns int[] if all elements are integers.
	 *    - Returns double[] if at least one element contains a decimal point.
	 *    - Returns an empty double[] for "[]".
	 * 2. Single number:
	 *    - Returns Integer if the string represents a whole number.
	 *    - Returns Double if the string represents a decimal number.
	 * 
	 * @param s the string representing a number or numeric array
	 * @return an Object which is either Integer, Double, int[], or double[]
	 * @throws IllegalArgumentException if the input is not a valid numeric string or array
	 */
	private static Object parseNumeric(String s) {
	    s = s.trim();

	    // --- Case 1: numeric array like [1,2,3] ---
	    if (s.startsWith("[") && s.endsWith("]")) {
	        String inner = s.substring(1, s.length() - 1).trim();
	        if (inner.isEmpty()) return new double[0]; // empty array []

	        String[] elems = inner.split("\\s*,\\s*");

	        boolean isDouble = false;
	        for (String elem : elems) {
	            if (elem.contains(".")) {
	                isDouble = true;
	                break;
	            }
	        }

	        if (isDouble) {
	            double[] arr = new double[elems.length];
	            for (int i = 0; i < elems.length; i++) {
	                arr[i] = Double.parseDouble(elems[i]);
	            }
	            return arr;
	        } else {
	            int[] arr = new int[elems.length];
	            for (int i = 0; i < elems.length; i++) {
	                arr[i] = Integer.parseInt(elems[i]);
	            }
	            return arr;
	        }
	    }

	    // --- Case 2: single number ---
	    if (s.matches("-?\\d+")) return Integer.parseInt(s);  // pure int
	    if (s.matches("-?\\d+(\\.\\d+)?")) return Double.parseDouble(s);

	    throw new IllegalArgumentException("Invalid number: " + s);
	}

} 