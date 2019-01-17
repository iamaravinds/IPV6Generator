package main;

import java.math.BigInteger;
import java.util.Scanner;

public class UtilClass {

	public String adder(String value) {
		String trimmer = "";
		String str = "";
		BigInteger added, one, result;

		trimmer = value.replaceAll("[-+.^:,]", "");

//		System.out.println("Trim \t\t\t:" + trimmer);

		one = new BigInteger("1", 16);
		added = new BigInteger(trimmer, 16);
		result = added.add(one);
		str = result.toString(16);

//		System.out.println("Bigger value \t\t:" + added);
//		System.out.println("Bigger added \t\t:" + str);

		return str;
	}

	public String patternMaker(String value) {

		StringBuilder temp = new StringBuilder(value);
		StringBuilder fornow = temp.reverse();
		int j = 0;
		for (int i = 1; i < temp.length() - 1; i++) {

			if (i % 4 == 0) {
				fornow.insert(i + j, ':');
				j++;
			}
		}
		fornow.reverse();
		if (value.length() % 4 == 0) {
//			System.out.println(fornow.substring(1));
			return fornow.substring(1);
		}

		return fornow.toString();
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String res;
		System.out.print("Enter the IP \t\t:");
		String value = scan.next();
		value = value.toLowerCase();
		UtilClass u = new UtilClass();
		res = u.adder(value);
		res = u.patternMaker(res);
		System.out.println("Result \t\t\t:" + res);
		scan.close();
	}

	public String callNextValue(String ip) {

		int initiallength = ip.length();
		int finallength = 0;
		StringBuilder ipAppender = new StringBuilder();
		Scanner scan = new Scanner(System.in);
		String res;
		UtilClass u = new UtilClass();
		res = u.adder(ip);
		finallength = res.length();
		int dif = initiallength - finallength;
		for (int i = 0; i < dif - res.length(); i++)
			ipAppender.append("0");

//		System.out.println("ipAppender :" + ipAppender);
		ipAppender.append(res);
//		System.out.println("after appending :" + ipAppender);
		res = u.patternMaker(ipAppender.toString());

//		System.out.println("Result \t\t\t:" + res);

		scan.close();

		return res;
	}
}
// ffff:ffff:ffff:ffff
