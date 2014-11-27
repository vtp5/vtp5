package vtp5.txttest;

import java.util.Random;
import java.util.Scanner;

import vtp5.logic.TestFile;

public class Test extends Importer {

	public Test(TestFile test) {
		super(test);
		// TODO Auto-generated constructor stub
	}

	public static void test() {

		int score = 0;

		while (true) {

			Random rand = new Random();
			int r = rand.nextInt((/* max - min */(i / 2) - 0) + 1) + 0;
			// r --;
			// Test
			System.out.println(q.get(r));

			Scanner s = new Scanner(System.in);

			String ans = s.nextLine();

			if (ans.contains("add_money")) {

				String[] add = ans.split(" ");

				int ammount = Integer.parseInt(add[1]);
				if (ammount > 40000) {
					ammount = 40000;
					System.out.println("Max is 40000");
				}

				score = score + ammount;
				System.out.println("           score: " + score);
			} else if (ans.contains(a.get(r)) /* ans.equalsIgnoreCase(a.get(0)) */) {
				System.out.println("correct");
				score++;
				System.out.println("           score: " + score);
			}

			else {
				System.out.println("wrong. Correct is: " + a.get(r));
				System.out.println("           score: " + score);
			}
		}
	}

}
