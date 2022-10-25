public class MyThread implements Runnable {
	private static int totalHelloCount = 0;
	
	@Override
	public void run() {
		for (int helloIndex = 1; helloIndex <= 100; helloIndex++) {
			System.out.print(String.format("Hello: %d from thread %d\t", helloIndex, Thread.currentThread().threadId()));
			totalHelloCount++;
			if (totalHelloCount % 10 == 0) {
				System.out.println("");
			}
		}
	}
}
