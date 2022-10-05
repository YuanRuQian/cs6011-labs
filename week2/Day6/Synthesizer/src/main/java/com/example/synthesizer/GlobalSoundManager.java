package com.example.synthesizer;

import javax.sound.sampled.Clip;

public class GlobalSoundManager {
	public static Clip globalClip;
	public static int frequency;
	public static AudioClip audioClip;
	GlobalSoundManager()
	{
		this.globalClip = null;
		this.frequency = 440;
		this.audioClip = null;
	}
	
	public static void updateFrequency(int newFrequency)
	{
		frequency = newFrequency;
	}
	
	public static  void updateAudioClip(AudioClip newAudioClip)
	{
		audioClip = newAudioClip;
	}
	
	public static void playAudio()
	{
		globalClip.start();
	}
	
	public static void endAudio()
	{
		globalClip.close();
	}
}
