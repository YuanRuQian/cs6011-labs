public class Fraction {
	private long numerator;
	private long denominator;
	
	private long GCD(long a, long b) {
		long gcd = a;
		long reminder = b;
		while (reminder != 0) {
			long temp = reminder;
			reminder = gcd % reminder;
			gcd = temp;
		}
		return gcd;
	}
	
	private void reduce() {
		int sign = numerator * denominator < 0 ? -1 : 1;
		long gcd = GCD(numerator, denominator);
		numerator = sign * Math.abs(numerator / gcd);
		denominator = Math.abs(denominator / gcd);
	}
	
	public Fraction() {
		numerator = 0;
		denominator = 1;
		reduce();
	}
	
	public Fraction(long n, long d) {
		numerator = n;
		denominator = d;
		reduce();
	}
	
	public Fraction plus(Fraction rhs) {
		long newNumerator = numerator * rhs.denominator + denominator * rhs.numerator;
		long newDenominator = denominator * rhs.denominator;
		return new Fraction(newNumerator, newDenominator);
	}
	
	public Fraction minus(Fraction rhs) {
		long newNumerator = numerator * rhs.denominator - denominator * rhs.numerator;
		long newDenominator = denominator * rhs.denominator;
		return new Fraction(newNumerator, newDenominator);
	}
	
	public Fraction times(Fraction rhs) {
		long newNumerator = numerator * rhs.numerator;
		long newDenominator = denominator * rhs.denominator;
		return new Fraction(newNumerator, newDenominator);
	}
	
	public Fraction dividedBy(Fraction rhs) {
		long newNumerator = numerator * rhs.denominator;
		long newDenominator = denominator * rhs.numerator;
		return new Fraction(newNumerator, newDenominator);
	}
	
	public Fraction reciprocal() {
		return new Fraction(denominator, numerator);
	}
	
	public String toString() {
		String sign = numerator * denominator < 0 ? "-" : "";
		return sign + Math.abs(numerator) + "/" + Math.abs(denominator);
	}
	
	public double toDouble() {
		return (double) numerator / denominator;
	}
	
}
