package com.example.synthesizer;

import static org.junit.jupiter.api.Assertions.*;

class VFSquareWaveTest {
	public static void main(String[] args) {
		hearASquareWaveGeneratedByDefaultVF();
		hearASquareWaveGeneratedByCustomVF();
	}
	
	static void hearASquareWaveGeneratedByDefaultVF() {
		VFSquareWave vfSquareWave = new VFSquareWave(true);
		AudioClip audioClip = vfSquareWave.getClip();
		PlayAudioClip.play(audioClip,"default VF",1);
	}
	
	static void hearASquareWaveGeneratedByCustomVF() {
		VFSquareWave vfSquareWave = new VFSquareWave(50, 10000,true);
		AudioClip audioClip = vfSquareWave.getClip();
		PlayAudioClip.play(audioClip,"custom VF",1);
	}
}