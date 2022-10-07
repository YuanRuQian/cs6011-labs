package com.example.synthesizer;

// TODO: auto clamp across the project
// TODO: use the helper method across the project
public class ShortClampingHelper {
	public static short getClampedShort(double input)
	{
		if(input > Short.MAX_VALUE)
		{
			return Short.MAX_VALUE;
		} else if (input < Short.MIN_VALUE) {
			return Short.MIN_VALUE;
		}
		return (short) input;
	}
}
