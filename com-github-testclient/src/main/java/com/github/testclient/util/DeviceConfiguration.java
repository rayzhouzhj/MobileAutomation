package com.github.testclient.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DeviceConfiguration - this class contains methods to start adb server, to get connected devices and their information.   
 */
public class DeviceConfiguration {

	CommandPrompt cmd = new CommandPrompt();
	Map<String, String> devices = new HashMap<String, String>();
	List<AndroidDevice> deviceList = new ArrayList<>();
	
	/**
	 * This method start adb server
	 */
	public void startADB() throws Exception{
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if(lines.length==1)
			System.out.println("adb service already started");
		else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if(lines[0].contains("internal or external command")){
			System.out.println("adb path not set in system varibale");
			System.exit(0);
		}
	}
	
	public static boolean checkConnection(int timeoutInSeconds) throws Exception	{
		CommandPrompt cmd = new CommandPrompt();
		
		String output;
		String[] lines;
		boolean isConnected = false;
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();
		
		while(!isConnected && startTime.until( endTime, ChronoUnit.SECONDS) < timeoutInSeconds)
		{
			output = cmd.runCommand("adb devices");
			lines = output.split("\n");
			if(lines.length>1){
				isConnected = true;
				Thread.sleep(15000);
				break;
			}
			else
			{
				Thread.sleep(5000);
				endTime = LocalDateTime.now();
			}
		}
		
		return isConnected;

	}
	
	/**
	 * This method stop adb server
	 */
	public void stopADB(String deviceID) throws Exception{
		cmd.runCommand("adb -s " + deviceID + " kill-server");
	}
	
	/**
	 * This method return connected devices
	 * @return list of connected devices information
	 */
	public List<AndroidDevice> getDivces() throws Exception	{
		
		deviceList.clear();
		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if(lines.length<=1){
//			stopADB();
			System.out.println("No Device Connected");
			
			return deviceList;
		}
		
		for(int i=1;i<lines.length;i++){
			lines[i]=lines[i].replaceAll("\\s+", "");
			
			if(lines[i].contains("device")){
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				String model = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").replaceAll("\\s+", "");
				String brand = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
				String osVersion = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");
				String deviceName = brand+" "+model;
				
				devices.put("deviceID"+i, deviceID);
				devices.put("deviceName"+i, deviceName);
				devices.put("osVersion"+i, osVersion);
				
				deviceList.add(new AndroidDevice(deviceName, deviceID, osVersion));
				
				System.out.println("Following device is connected");
				System.out.println(deviceID+" / "+deviceName+" / "+osVersion+"\n");
			}else if(lines[i].contains("unauthorized")){
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}else if(lines[i].contains("offline")){
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return deviceList;
	}
	
	public AndroidDevice getDeviceByID(String deviceID)
	{
		String model;
		String brand;
		String osVersion;
		try {
			model = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").replaceAll("\\s+", "");
			brand = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
			osVersion = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");
			
			String deviceName = brand+" "+model;
			
			System.out.println("Following device is connected");
			System.out.println(deviceID+" / "+deviceName+" / "+osVersion+"\n");
			
			return new AndroidDevice(deviceName, deviceID, osVersion);
			
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	/**
	 * This method return connected devices
	 * @return list of connected devices information
	 */
	public List<String> getDivceList() throws Exception	{
		
		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		System.out.println(output);
		
		String[] lines = output.split("\n");
		List<String> deviceList = new ArrayList<>();
		
		if(lines.length<=1){
			System.out.println("ERROR: No Device Connected");
		}
		
		for(int i=1;i<lines.length;i++){
			lines[i]=lines[i].replaceAll("\\s+", "");
			
			if(lines[i].contains("device")){
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				
				deviceList.add(deviceID);
				
			}else if(lines[i].contains("unauthorized")){
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}else if(lines[i].contains("offline")){
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return deviceList;
	}
	
	public static void main(String[] args) throws Exception {
		DeviceConfiguration gd = new DeviceConfiguration();
//		gd.startADB();	
		gd.getDivces();
//		gd.stopADB();
	}
}
