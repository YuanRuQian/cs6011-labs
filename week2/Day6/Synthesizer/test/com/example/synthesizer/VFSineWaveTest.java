package com.example.synthesizer;

class VFSineWaveTest {
	public static void main(String[] args) {
		hearASineWaveGeneratedByDefaultVF();
		hearASineWaveGeneratedByCustomVF();
	}
	
	static void hearASineWaveGeneratedByDefaultVF() {
		LinearRamp linearRamp = new LinearRamp(50, 2000);
		VFSineWave vfSineWave = new VFSineWave();
		vfSineWave.connectInput(linearRamp);
		PlayAudioClip.play(vfSineWave.getClip(),"default VF",1);
	}
	
	static void hearASineWaveGeneratedByCustomVF() {
		LinearRamp linearRamp = new LinearRamp(50, 10000);
		VFSineWave vfSineWave = new VFSineWave();
		vfSineWave.connectInput(linearRamp);
		PlayAudioClip.play(vfSineWave.getClip(),"default VF",1);
	}
}