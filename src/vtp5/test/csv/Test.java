package vtp5.test.csv;

import java.util.Random;
import java.util.Scanner;

/*VTP5 Copyright (C) 2014-2015  Abdel Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Test extends Importer {

	public static void test() {

		int score = 0;

		while (true) {

			Random rand = new Random();
			int r = rand.nextInt((/* max - min */(i / 2) - 0) + 1) + 0;
			// r --;
			// Test
			System.out.println(q.get(r));

			@SuppressWarnings("resource")
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
