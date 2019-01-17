package main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.validator.routines.InetAddressValidator;

public class IPV6GeneratorUtility {
	InetAddressValidator validator = InetAddressValidator.getInstance();

	public static String uncompress(StringBuilder address) {

		// Store the location where you need add zeroes that were removed during
		// uncompression
		int tempCompressLocation = address.indexOf("::");

		// if address was compressed and zeroes were removed, remove that marker
		// i.e "::"
		if (tempCompressLocation != -1) {
			address.replace(tempCompressLocation, tempCompressLocation + 2, ":");
		}

		// extract rest of the components by splitting them using ":"
		String[] addressComponents = address.toString().split(":");

		for (int i = 0; i < addressComponents.length; i++) {
			StringBuilder uncompressedComponent = new StringBuilder("");
			for (int j = 0; j < 4 - addressComponents[i].length(); j++) {

				// add a padding of the ignored zeroes during compression if
				// required
				uncompressedComponent.append("0");

			}
			uncompressedComponent.append(addressComponents[i]);

			// replace the compressed component with the uncompressed one
			addressComponents[i] = uncompressedComponent.toString();
		}

		// Iterate over the uncompressed address components to add the ignored
		// "0000" components depending on position of "::"
		ArrayList<String> uncompressedAddressComponents = new ArrayList<String>();

		for (int i = 0; i < addressComponents.length; i++) {
			if (i == tempCompressLocation / 4) {
				for (int j = 0; j < 8 - addressComponents.length; j++) {
					uncompressedAddressComponents.add("0000");
				}
			}
			uncompressedAddressComponents.add(addressComponents[i]);

		}

		// iterate over the uncomopressed components to append and produce a
		// full address
		StringBuilder uncompressedAddress = new StringBuilder("");
		Iterator<String> it = uncompressedAddressComponents.iterator();
		while (it.hasNext()) {
			uncompressedAddress.append(it.next().toString());
			uncompressedAddress.append(":");
		}
		uncompressedAddress.replace(uncompressedAddress.length() - 1, uncompressedAddress.length(), "");
		return uncompressedAddress.toString();
	}

	public String superAdder(String ip) {

		String str = "";
		BigInteger added, one, result, t1;

		t1 = new BigInteger("ffffffffffffffffffffffffffffffff", 16);
		one = new BigInteger("1", 16);
		added = new BigInteger(ip, 16);
		result = added.add(one);
		str = result.toString(16);
		if (result.compareTo(t1) > 0)// (x.compareTo(y) <op> 0)
			return "Cannot create IPS, Will reach beyond range!";

		return str;

	}

	public String paternMaker(String unpatterenedIp) {
		int j = 0;
		StringBuilder stringBufferIP = new StringBuilder();
		stringBufferIP.append(unpatterenedIp);
		for (int i = 0; i < unpatterenedIp.length(); i++) {
			if (i % 4 == 0 && i != 0) {
				stringBufferIP.insert(i + j, ":");
				j++;
			}
		}
		return stringBufferIP.toString();

	}

	// Validate an IPv6 address
	public boolean IPValidator(String ip) {

		if (validator.isValidInet6Address(ip))
			return true;
		else
			return false;
	}

	public List<String> ipDriver(String startIp) {
		List<String> listingIPs = new ArrayList<>();
		IPV6GeneratorUtility uc = new IPV6GeneratorUtility();
		StringBuilder ip = new StringBuilder();
		Boolean ipValidIndicator = true;

		// Check ip valid
		if (!(uc.IPValidator(startIp))) {
			System.out.println("Invalid Ip");
			ipValidIndicator = false;
			return null;
		}
		if (startIp.equals("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff")) {
			System.out.println("IPs cannot be generated!");
		}

		if (ipValidIndicator) {

			// Full form
			ip.append(startIp);
			startIp = uncompress(ip);
			String nextIp = startIp;
			listingIPs.add(nextIp);

			// Loop
			for (int i = 1; i < 5000; i++) {

				// Trim
				nextIp = nextIp.replaceAll("[-+.^:,]", "");

				// Add
				nextIp = uc.superAdder(nextIp);
				if (nextIp.equals("Cannot create IPS, Will reach beyond range!")) {
					break;
				}
				// Pattern
				nextIp = paternMaker(nextIp);

				// Add to List
				listingIPs.add(nextIp);
			}
		}
		return listingIPs;

	}

	public static void main(String[] args) {
		List<String> ipList = new ArrayList<>();
		IPV6GeneratorUtility uc = new IPV6GeneratorUtility();
		Scanner sc = new Scanner(System.in);
		String startIp = sc.next();
		ipList = uc.ipDriver(startIp);
		sc.close();
		if (ipList != null) {
			for (Iterator<String> iterator = ipList.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.out.println(string);
			}
			System.out.println(ipList.size());
		}
	}

}
