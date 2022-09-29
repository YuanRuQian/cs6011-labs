import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;


public class RainData {
	
	
	private final String fileName;
	private String city;
	private final Map<String, CounterPair> monthRainfallCounter;
	
	private static final String[] sortedMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static final DecimalFormat df2 = new DecimalFormat("#.00");
	
	private void addMonthAmountInfo(String monthYearAmountString) {
		String[] monthYearAmount = monthYearAmountString.split("\\s+");
		String month = monthYearAmount[0];
		Double amount = Double.parseDouble(monthYearAmount[2]);
		Double newAmount = amount + monthRainfallCounter.getOrDefault(month, new CounterPair()).getAmount();
		Integer newCount = 1 + monthRainfallCounter.get(month).getCount();
		monthRainfallCounter.put(month, new CounterPair(newAmount, newCount));
	}
	
	
	public RainData(String fn) {
		fileName = fn;
		city = "";
		monthRainfallCounter = new HashMap<>();
		for (String month : sortedMonth) {
			monthRainfallCounter.put(month, new CounterPair());
		}
	}
	
	
	public void readFile() throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		city = fileReader.nextLine();
		String singleDataLine;
		while (fileReader.hasNextLine()) {
			singleDataLine = fileReader.nextLine();
			addMonthAmountInfo(singleDataLine);
		}
	}
	
	public void computeResults() {
		for (String month : monthRainfallCounter.keySet()) {
			Double amount = monthRainfallCounter.get(month).getAmount();
			Integer count = monthRainfallCounter.get(month).getCount();
			Double average = amount / count;
			monthRainfallCounter.put(month, new CounterPair(average, count));
		}
	}
	
	public String outputSingleMonthResult(String month, String amount) {
		return "The average rainfall amount for " + month + " is " + amount + " inches.";
	}
	
	public String outputCityInfo() {
		return "City: " + city;
	}
	
	public String outputOverallResult() {
		Double sum = 0.0;
		for (String month : sortedMonth) {
			sum += monthRainfallCounter.get(month).getAmount();
		}
		String roundedAmount = df2.format(sum / sortedMonth.length);
		return "The overall average rainfall amount for each month is " + roundedAmount + " inches.";
	}
	
	public void outputResults() throws FileNotFoundException {
		String outputFileName = "rainfall_results.txt";
		PrintWriter pw = new PrintWriter(outputFileName);
		computeResults();
		pw.println(outputCityInfo());
		pw.println(outputOverallResult());
		for (String month : sortedMonth) {
			String roundedAmount = df2.format(monthRainfallCounter.get(month).getAmount());
			String singleMonthResult = outputSingleMonthResult(month, roundedAmount);
			pw.println(singleMonthResult);
		}
		pw.close();
	}
	
	public static String getCurrentDirectory() {
		return System.getProperty("user.dir");
	}
	
	
}
