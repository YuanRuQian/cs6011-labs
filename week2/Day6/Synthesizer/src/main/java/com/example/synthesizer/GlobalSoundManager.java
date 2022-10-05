package com.example.synthesizer;

import javax.sound.sampled.Clip;

public class GlobalSoundManager {
	public static Clip globalClip;
	
	GlobalSoundManager()
	{
		this.globalClip = null;
	}
}
