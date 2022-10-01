import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FractionTest {
	@Test
	public void testPlus() {
		Fraction f1 = new Fraction(2, 7);
		Fraction f2 = new Fraction(3, 8);
		Fraction f3 = f1.plus(f2);
		assertEquals(f3.toString(), "37/56");
	}
	
	@Test
	public void testMinus() {
		Fraction f1 = new Fraction(1, 5);
		Fraction f2 = new Fraction(1, 3);
		Fraction f3 = f1.minus(f2);
		assertEquals(f3.toString(), "-2/15");
	}
	
	@Test
	public void testTimes() {
		Fraction f1 = new Fraction(-3, -5);
		Fraction f2 = new Fraction(21, 9);
		Fraction f3 = f1.times(f2);
		assertEquals(f3.toString(), "7/5");
	}
	
	@Test
	public void testDividedBy() {
		Fraction f1 = new Fraction(8, -5);
		Fraction f2 = new Fraction(-22, 7);
		Fraction f3 = f1.dividedBy(f2);
		assertEquals(f3.toString(), "28/55");
	}
	
	@Test
	public void testReciprocal() {
		Fraction f1 = new Fraction(42, -24);
		Fraction f2 = f1.reciprocal();
		assertEquals(f2.toString(), "-4/7");
	}
	
	@Test
	public void testTestToString() {
		Fraction f1 = new Fraction(42, -24);
		assertEquals(f1.toString(), "-7/4");
		
		Fraction f2 = new Fraction(-42, -24);
		assertEquals(f2.toString(), "7/4");
	}
	
	@Test
	public void testToDouble() {
		Fraction f1 = new Fraction(42, -24);
		assertEquals(f1.toDouble(), (double) 42 / -24);
		
		Fraction f2 = new Fraction(-42, -24);
		assertEquals(f2.toDouble(), (double) -42 / -24);
	}
	
	@Test
	public void testDividedByZero() {
		try {
			Fraction f1 = new Fraction(10, 0);
		} catch (ArithmeticException e) {
			assertEquals(e.getMessage(), "Failed: fraction divided by zero");
		}
		
		try {
			Fraction f2 = new Fraction(0, 10);
			Fraction f3 = f2.reciprocal();
		} catch (ArithmeticException e) {
			assertEquals(e.getMessage(), "Failed: fraction divided by zero");
		}
	}
	
}