package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.validator.routines.InetAddressValidator;

/*
 * @author:Aravind S
 * 
 * Utility method that generates IPV6 range based on Range and limits
 * Pass either Limit less than 65535 or give start and end index 
 * 
 * */

public class UtilClass2 {

	static String DEFAULT_IP_PREFIX = "2001:0DB8:ABCD:0012:0000:0000:0000:";
	InetAddressValidator validator = InetAddressValidator.getInstance();
	List<String> ipList = new ArrayList<>();

	// Validate an IPv6 address
	public boolean IPValidator(String ip) {

		if (validator.isValidInet6Address(ip))
			return true;
		else
			return false;
	}

	/*
	 * Generated IP Address
	 **/
	public List<String> ipGenerate(int limit) {
		List<String> ipList = new ArrayList<>();
		for (Integer i = 0; i <= limit; i++) {
			StringBuilder tempfill = new StringBuilder(DEFAULT_IP_PREFIX);
			tempfill.append(Integer.toHexString(i));
			ipList.add(tempfill.toString());
			System.out.println(tempfill);

		}
		System.out.println("Total IPS: " + ipList.size());
		return ipList;
	}

	int firstMismatch(String org, String check) {
		int limit = (org.length() > check.length()) ? org.length() : check.length();
		for (int i = 0; i < limit; i++) {
			try {
				if (org.charAt(i) != check.charAt(i)) {
					return i; // If one of the strings end, that's the position
								// they are different, otherwise there is a
								// mismatch. Either way, just return the index
								// of mismatch
				}
			} catch (Exception e) {
				if (org.length() != check.length()) {

					return (i); // Execution comes here only when length of
								// strings is unequal
					// Exception occurs because first string is smaller than
					// the second or vice versa. Say if you use "fred"
					// and"fredd" as org and check
					// respectively, "fred" is smaller than "fredd" so accessing
					// org[4] is not allowed.
					// Hence the exception.
				}

				System.out.println("Problem encountered"); // Some other
															// exception has
															// occured.
				return (-2);
			}
		}
		return (-1); // if they are equal, just return -1

	}

	/*
	 * IP Generator - generated the IP list
	 */
	public List<String> ipGenerate(String startIp, String endIp) {

		Integer limit = 0;
		Integer startIndex = 0, endIndex = 0;
		String startHex = "";
		String endHex = "";
		Integer diff = firstMismatch(startIp, endIp);
		if (diff < 0) {
			System.out.println("Error!");
		} else if (diff == 0) {
			System.out.println("IPs are Equal");
			return null;
		} else {

			startHex = startIp.substring(diff, startIp.length());
			System.out.println("Start value\t" + startHex);
			endHex = endIp.substring(diff, endIp.length());
			System.out.println("End value\t" + endHex);
			startIndex = Integer.parseInt(startHex, 16);
			System.out.println("Start value\t" + startIndex);
			endIndex = Integer.parseInt(endHex, 16);
			System.out.println("End value\t" + endIndex);
			limit = endIndex - startIndex;
			System.out.println("Difference\t" + limit);

		}
		for (Integer i = startIndex; i <= endIndex; i++) {
			StringBuilder tempfill = new StringBuilder(startIp.substring(0, diff));
			tempfill.append(Integer.toHexString(i));
			ipList.add(tempfill.toString());
			System.out.println(tempfill);

		}
		System.out.println("Total IPS: " + ipList.size());
		return null;
	}

	public String ipGenerateHandler(String ip) {

		UtilClass util = new UtilClass();

		String singleVal;
		singleVal = util.callNextValue(ip);

		return singleVal;
	}

	/*
	 * Handler for IP generation by start and end index
	 */
	public void IPByRange() {
		Scanner scan = new Scanner(System.in);
		try {
			UtilClass2 obj = new UtilClass2();
			System.out.print("Start IP: ");

			String startIp = scan.next();
			if (!(obj.IPValidator(startIp))) {
				System.out.println("Invalid Ip");

			}

			System.out.print("End IP: ");
			String endIp = scan.next();
			if (!(obj.IPValidator(endIp)))
				System.out.println("Invalid Ip");

			obj.IPValidator(endIp);
			obj.ipGenerate(startIp, endIp);
		} finally {
			scan.close();
		}
	}

	/*
	 * Handler for IP generation by limit
	 */
	public int IpByLimit() {
		Scanner scan = new Scanner(System.in);
		UtilClass2 obj = new UtilClass2();
		try {
			System.out.println("Range Value should be less than 65535");
			int limit = scan.nextInt();
			if (limit >= 65536) {
				System.out.println("Range out of bound");
				return -1;
			}
			obj.ipGenerate(limit);
		} finally {
			scan.close();
		}
		return 0;
	}

	private int IPRangeService() {

		Scanner scan = new Scanner(System.in);
		try {
			UtilClass2 obj = new UtilClass2();
			List<String> listingIPs = new ArrayList<>();
			System.out.print("Start IP: ");
			String startIp = scan.next();
			startIp=obj.ipFixer(startIp);
			listingIPs.add(startIp);
			String trimmedIP = startIp.substring(startIp.length() - 19);
			String prefixIP = startIp.substring(0, startIp.length() - 19);
//			System.out.println("prefix \t:" + prefixIP);
//			System.out.println("sufix \t:" + trimmedIP);
			String lastIp;

			String singleVal;

			for (int i = 1; i < 5000; i++) {
				if (!(obj.IPValidator(startIp))) {
					System.out.println("Invalid Ip");
					return -1;
				}
				lastIp = obj.ipGenerateHandler(trimmedIP);
				singleVal = prefixIP + lastIp;
				listingIPs.add(singleVal);
				trimmedIP = lastIp;
			}
			System.out.println(listingIPs);
			// for (String i : listingIPs) {
			// System.out.println(i);
			// }

		} finally {
			scan.close();
		}
		return 0;
	}

	private String ipFixer(String startIp) {
		// Fixes the length of IP to full form
		IPV6GeneratorUtility uc=new IPV6GeneratorUtility();
		StringBuilder ip=new StringBuilder();
		String response;
		ip.append(startIp);
		response=uc.uncompress(ip);
		System.out.println("Uncompressed \t: "+response);
		return response;
	}
	

	/*
	 * Main Method triggering the program
	 */
	public static void main(String[] args) {
		Scanner scanning = new Scanner(System.in);
		try {
			int res = 0;
			UtilClass2 mainObj = new UtilClass2();
			System.out.println("IPv6 Generator");
			System.out.println("(1)IP start");
			System.out.print("(2)Limit\t");
			if (scanning.nextInt() == 1) {
				// mainObj.IPByRange();
				res = mainObj.IPRangeService();
				if (res == -1) {
					System.out.println("Error occured! Invalid IP");
				}
				else{
					System.out.println("IPV6 list Generated Successfully");
				}

			} else {
				res = mainObj.IpByLimit();
				if (res == -1) {
					System.out.println("Invalid Range");
				}

			}
		} finally {
			scanning.close();
		}
	}

}
