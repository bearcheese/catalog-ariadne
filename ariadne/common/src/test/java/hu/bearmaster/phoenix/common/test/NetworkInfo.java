package hu.bearmaster.phoenix.common.test;

import java.io.*;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;
 
import static java.lang.management.ManagementFactory.*;
 
public final class NetworkInfo {
	private final static String getMacAddress() throws IOException {
		String os = System.getProperty("os.name");
		
		try {
			if(os.startsWith("Windows")) {
				return windowsParseMacAddress(windowsRunIpConfigCommand());
			} else if(os.startsWith("Linux")) {
				return linuxParseMacAddress(linuxRunIfConfigCommand());
			} else {
				throw new IOException("unknown operating system: " + os);
			}
		} catch(ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}
	
	
	/*
	 * Linux stuff
	 */
	private final static String linuxParseMacAddress(String ipConfigResponse) throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch(java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}
		
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		
		while(tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(localHost) >= 0;
			
			// see if line contains IP address
			if(containsLocalHost && lastMacAddress != null) {
				return lastMacAddress;
			}
			
			// see if line contains MAC address
			int macAddressPosition = line.indexOf("HWaddr");
			if(macAddressPosition <= 0) continue;
			
			String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
			if(linuxIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}
		
		ParseException ex = new ParseException
			("cannot read MAC address for " + localHost + " from [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}
	
	
	private final static boolean linuxIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if(macAddressCandidate.length() != 17) return false;
		return true;
	}
	
	
	private final static String linuxRunIfConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ifconfig");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		
		StringBuffer buffer= new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) break;
			buffer.append((char)c);
		}
		String outputText = buffer.toString();
		
		stdoutStream.close();
		
		return outputText;
	}
	
	
	
	/*
	 * Windows stuff
	 */
	private final static String windowsParseMacAddress(String ipConfigResponse) throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch(java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}
		
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		
		while(tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			
			// see if line contains IP address
			if(line.endsWith(localHost) && lastMacAddress != null) {
				return lastMacAddress;
			}
			
			// see if line contains MAC address
			int macAddressPosition = line.indexOf(":");
			if(macAddressPosition <= 0) continue;
			
			String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
			if(windowsIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}
		
		ParseException ex = new ParseException("cannot read MAC address from [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}
	
	
	private final static boolean windowsIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if(macAddressCandidate.length() != 17) return false;
		
		return true;
	}
	
	
	private final static String windowsRunIpConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		
		StringBuffer buffer= new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) break;
			buffer.append((char)c);
		}
		String outputText = buffer.toString();
		
		stdoutStream.close();
		
		return outputText;
	}
	
	private final static void loogFile(String Info, String Operating, String IP, String MAC, String javaVersion) throws IOException {
		File	f = new File ("NetworkInfoLoog.txt");
		
		try
		{
			PrintWriter	out = new PrintWriter (System.out, true);
			
			FileWriter	fw = new FileWriter (f, true);
			PrintWriter	fout = new PrintWriter (fw, true);
			
			
			fout.println ();
			fout.println (Info);
			fout.println (Operating);
			fout.println (IP);
			fout.println (MAC);
			fout.println (javaVersion);
			fout.println ();
			
			fout.close();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
		
	}
	
	private final static void terminalPrint(String Info, String Operating, String IP, String MAC, String javaVersion, String javaVendor, String architecture, String osVersion, String userName, String userHome, String javaVmSpecificationVersion, String javaVmSpecificationVendor)
	{
		System.out.println();
		System.out.println(Info);
		System.out.println(Operating);
		System.out.println(architecture);
		System.out.println(osVersion);
		System.out.println(IP);
		System.out.println(MAC);
		System.out.println(javaVersion);
		System.out.println(javaVendor);
		System.out.println(userName);
		System.out.println(userHome);
		System.out.println(javaVmSpecificationVersion);
		System.out.println(javaVmSpecificationVendor);
		
		System.out.println(System.getProperty("java.vm.version") + "	Java Virtual Machine implementation version");
			System.out.println(System.getProperty("java.vm.vendor") + "	 	Java Virtual Machine implementation vendor");
			System.out.println(System.getProperty("java.vm.name") + "	 	Java Virtual Machine implementation name");
			System.out.println(System.getProperty("java.specification.version") + "	 	Java Runtime Environment specification version");
			System.out.println(System.getProperty("java.specification.vendor") + "	 	Java Runtime Environment specification vendor");
			System.out.println(System.getProperty("java.specification.name") + "	 	Java Runtime Environment specification name");
			System.out.println(System.getProperty("java.class.version") + "	 	Java class format version number");
			System.out.println(System.getProperty("java.class.path") + "	 	Java class path");
			System.out.println(System.getProperty("java.library.path") + "	 	List of paths to search when loading libraries");
			System.out.println(System.getProperty("java.io.tmpdir") + "	 	Default temp file path");
			System.out.println(System.getProperty("java.compiler") + "	 	Name of JIT compiler to use");
			System.out.println(System.getProperty("java.ext.dirs") + "	 	Path of extension directory or directories");
		//System.out.println(" Returns the amount of memory in bytes that is committed for the Java virtual machine to use: " +  mockXAResource.getCommitted());	
		
		//MemoryUsage memUsage = memInfo.getUsage();
		
		System.out.println();
		System.out.println( "Runtime: " + getRuntimeMXBean().getUptime());
		System.out.println( "Memory: " + getMemoryMXBean().getHeapMemoryUsage() );
		System.out.println( "Memory Pool: " + getMemoryPoolMXBeans().toString());
		System.out.println( "Garbage Collector: " + getGarbageCollectorMXBeans().toString());
		System.out.println( "Available Processors: " + Runtime.getRuntime().availableProcessors());
		System.out.println( "Returns the amount of free memory in the Java Virtual Machine: " + Runtime.getRuntime().freeMemory());
		System.out.println( "Returns the maximum amount of memory that the Java virtual machine will attempt to use: " + Runtime.getRuntime().maxMemory() + " " + Runtime.getRuntime().totalMemory());
		System.out.println( "Returns kvot of the amount of memory that the Java virtual machine will attempt to use: " + ((Runtime.getRuntime().freeMemory())/(Runtime.getRuntime().totalMemory())));
		// return void System.out.println( "Runs the garbage collector: " + Runtime.getRuntime().gc());
	}
	
	/*
	 * Main
	 */
	public final static void main(String[] args) {
		
try {
			String Info = "Network infos";
			String Operating = "  Operating System: " + System.getProperty("os.name");
			String architecture = "  Operating system architecture: " + System.getProperty("os.arch");
			String osVersion = "  Operating system version: " + System.getProperty("os.version");
			String IP = "  IP/Localhost: " + InetAddress.getLocalHost().getHostAddress();
			String MAC = "  MAC Address: " + getMacAddress();
			String javaVersion = "  Java Version: " + System.getProperty("java.version");
			String javaVendor = "  Java Vendor: " + System.getProperty("java.vendor");
			String javaVendorUrl = "  Java vendor URL: " + System.getProperty("java.vendor.url");
			String javaHome = "  Java installation directory: " + System.getProperty("java.home");
			String javaVmSpecificationVersion = "  Java Virtual Machine specification version: " + System.getProperty("java.vm.specification.version");
			String javaVmSpecificationVendor = "  Java Virtual Machine specification vendor: " + System.getProperty("java.vm.specification.vendor");
			String javaVmSpecificationName = "  Java Virtual Machine specification name: " + System.getProperty("java.vm.specification.name");
			
			
			String userName = "  User Name: " + System.getProperty("user.name");
			String userHome = "  User's home directory: " + System.getProperty("user.home");
			
			
			
			// loog file
			//loogFile(Info, Operating, IP, MAC, javaVersion);
// out print
			terminalPrint(Info, Operating, IP, MAC, javaVersion, javaVendor, architecture, osVersion, userName, userHome, javaVmSpecificationVersion, javaVmSpecificationVendor);
			
		} catch(Throwable t) {
			t.printStackTrace();
		}
		
	}
}