package com.example.synthesizer;

import javax.sound.sampled.Clip;

public class GlobalSoundManager {
	public static Clip globalClip = null;
	private static int frequency = 400;
	private static int volume = 100;
	
	// set default audioComponent to prevent NullException
	private static AudioComponentWidgetBase currentActiveAudioComponent = new SineWaveWidget();
	
	public static int getVolume()
	{
		return volume;
	}
	
	public static int getFrequency()
	{
		return frequency;
	}
	
	public static AudioComponentWidgetBase getCurrentActiveAudioComponent()
	{
		return currentActiveAudioComponent;
	}
	public static void updateFrequency(int newFrequency)
	{
		frequency = newFrequency;
		currentActiveAudioComponent.updateFrequencyText();
	}
	
	public static void updateVolume(int newVolume)
	{
		volume = newVolume;
		currentActiveAudioComponent.updateVolumeText();
	}
	
	public static void updateCurrentActiveAudioComponent(AudioComponentWidgetBase input)
	{
		currentActiveAudioComponent = input;
	}
}
