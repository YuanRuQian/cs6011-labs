package com.example.synthesizer;

class VFSquareWaveTest {
	public static void main(String[] args) {
		hearASquareWaveGeneratedByDefaultVF();
		hearASquareWaveGeneratedByCustomVF();
	}
	
	static void hearASquareWaveGeneratedByDefaultVF() {
		LinearRamp linearRamp = new LinearRamp(50, 2000, true);
		VFSquareWave VFSquareWave = new VFSquareWave();
		VFSquareWave.connectInput(linearRamp);
		PlayAudioClip.play(VFSquareWave.getClip(),"default VF",1);
	}
	
	static void hearASquareWaveGeneratedByCustomVF() {
		LinearRamp linearRamp = new LinearRamp(50, 10000, true);
		VFSquareWave VFSquareWave = new VFSquareWave();
		VFSquareWave.connectInput(linearRamp);
		PlayAudioClip.play(VFSquareWave.getClip(),"default VF",1);
	}
}