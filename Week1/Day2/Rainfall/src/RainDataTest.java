import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

class RainDataTest {
	@Test
	void testAtlanta() throws FileNotFoundException {
		String atlantaUrl = RainData.getCurrentDirectory() + "/src/Atlanta.txt";
		RainData atlanta = new RainData(atlantaUrl);
		atlanta.readFile();
		atlanta.outputResults();
	}
	
	@Test
	void testMacon() throws FileNotFoundException {
		String atlantaUrl = RainData.getCurrentDirectory() + "/src/Macon.txt";
		RainData atlanta = new RainData(atlantaUrl);
		atlanta.readFile();
		atlanta.outputResults();
	}
}