package com.example.synthesizer;

import static org.junit.jupiter.api.Assertions.*;

class VFSineWaveTest {
	public static void main(String[] args) {
		hearASineWaveGeneratedByDefaultVF();
		hearASineWaveGeneratedByCustomVF();
	}
	
	static void hearASineWaveGeneratedByDefaultVF() {
		VFSineWave vfSineWave = new VFSineWave(true);
		AudioClip audioClip = vfSineWave.getClip();
		PlayAudioClip.play(audioClip,"default VF",1);
	}
	
	static void hearASineWaveGeneratedByCustomVF() {
		VFSineWave vfSineWave = new VFSineWave(50, 10000,true);
		AudioClip audioClip = vfSineWave.getClip();
		PlayAudioClip.play(audioClip,"custom VF",1);
	}
}