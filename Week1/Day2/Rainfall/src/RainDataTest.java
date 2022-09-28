import java.io.FileNotFoundException;

class RainDataTest {
	
	@org.junit.jupiter.api.Test
	void testAtlanta() throws FileNotFoundException {
		String atlantaUrl = RainData.getCurrentDirectory() + "/src/Atlanta.txt";
		RainData atlanta = new RainData(atlantaUrl);
		atlanta.readFile();
		atlanta.outputResults();
	}
	
	@org.junit.jupiter.api.Test
	void testMacon() throws FileNotFoundException {
		String atlantaUrl = RainData.getCurrentDirectory() + "/src/Macon.txt";
		RainData atlanta = new RainData(atlantaUrl);
		atlanta.readFile();
		atlanta.outputResults();
	}
}