
import java.util.Scanner;

public class Main {
	public static String getGenerationByAge(int age)
	{
		if(age < 0)
		{
			throw new RuntimeException("");
		}
		if(age >= 80)
		{
			return "greatest generation";
		}
		else if(age >= 60)
		{
			return "boomers";
		}
		else if(age >= 40)
		{
			return "gen X";
		}
		else if(age >= 20)
		{
			return "millennial";
		}
		else
		{
			return "iGen";
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("hello world");
		
		// create 10 random ints
		int tenRandomInts[] = new int[10];
		int randomIntsSum = 0;
		
		System.out.println("Generate 10 random ints from 0 - 100: ");
		for(int i=0; i<tenRandomInts.length; i++)
		{
			tenRandomInts[i] = (int)(Math.random() * 100);
			System.out.println(tenRandomInts[i]);
			randomIntsSum += tenRandomInts[i];
		}
		System.out.println("The sum of 10 random ints is: " + randomIntsSum);
		
		System.out.println("Please enter your name:");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		System.out.println("You entered name: " + name);
		System.out.println("Please enter your age:");
		int age = in.nextInt();
		System.out.println("You entered age: " + age);
		System.out.println("Hi, " + name + ", you are part of the generation of " + getGenerationByAge(age));
	}
}