package com.example.synthesizer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioClipTest {
	
	@Test
	public void testAudioClip() {
		AudioClip audioClip = new AudioClip();
		int dataLength = Short.MAX_VALUE - Short.MIN_VALUE + 1;
		ArrayList<Short> testData = new ArrayList<>(dataLength);
		int index = 0;
		for (short data = Short.MIN_VALUE; data <= Short.MAX_VALUE; data++, index++) {
			testData.add(data);
			audioClip.setSample(index, data);
			assertEquals((int) testData.get(index), audioClip.getSample(index));
			if (data == Short.MAX_VALUE) {
				// Java short overflow prevention: shortMax + 1 == shortMin
				break;
			}
		}
		
		AudioClip copy = new AudioClip(audioClip);
		for (; index >= 0; index--) {
			assertEquals((int) testData.get(index), copy.getSample(index));
		}
	}
	
}