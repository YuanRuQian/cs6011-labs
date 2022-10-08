package com.example.synthesizer;

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
