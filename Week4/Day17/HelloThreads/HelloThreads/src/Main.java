import java.util.ArrayList;

public class Main {
	
	// What happens? Do all the threads run in order?
	// Every thread print in order from 1 to 100
	// But all the threads do not in order
	
	// Run your program a couple of times - does the same thread always print the 1st hello? The last hello?
	// No
	public static void sayHello() {
		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(new MyThread()));
		}
		threads.forEach(Thread::start);
		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		});
	}
	
	
	private static int answer = 0;
	
	// when numThreads = 1 it works cuz the thread does not reset answer
	// when numThreads > 1 the threads reset answer to 0, so the result loses some numbers
	
	// when set maxValue to 100 the results tends to be more stable when the numThreads increases
	// like when numThreads = 2, 3, etc., we usually get different results each time
	// but when numThreads grows to 8, 9, 10, the results usually stay stable
	public static void badSum() {
		int numThreads = 10;
		int maxValue = 40000;
		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			final int finalI = i;
			threads.add(new Thread(() -> {
				answer = 0;
				int start = finalI * maxValue / numThreads;
				int end = Math.min((finalI + 1) * maxValue / numThreads, maxValue);
				for (int j = start; j < end; j++) {
					answer += j;
				}
			}));
		}
		threads.forEach(Thread::start);
		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		});
		System.out.println("");
		System.out.println("computed answer: " + answer);
		System.out.println("expected answer: " + (maxValue * (maxValue - 1) / 2));
	}
	
	public static void main(String[] args) {
		sayHello();
		badSum();
	}
}