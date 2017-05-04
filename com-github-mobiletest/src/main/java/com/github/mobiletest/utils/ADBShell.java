package com.github.mobiletest.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.github.mobiletest.test.TestContext;

public class ADBShell {

	public static boolean isScreenDisplayOff(String deviceID)
	{
		String[] output = getScreenDisplayStatus(deviceID);
		if(output.length == 2 && output[1].contains("false"))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	public static String[] getScreenDisplayStatus(String deviceID)
	{
		CommandPrompt cmd = new CommandPrompt();
		String output = "";
		try 
		{
			String os = System.getProperty("os.name");

			if(os.contains("Windows")) // if windows
			{
				output = cmd.runCommand("adb -s " + deviceID + " shell dumpsys power | find \"mHolding\"");
			}
			else 
			{// If Mac
				output = cmd.runCommand("adb -s " + deviceID + " shell dumpsys power | grep \"mHolding\"");
			}

			System.out.println(output);

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();

		}

		return output.split("\n");
	}

	public static void pressPowerButton(String deviceID)
	{
		CommandPrompt cmd = new CommandPrompt();
		try 
		{
			cmd.runCommand("adb -s " + deviceID + " shell input keyevent KEYCODE_POWER");
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();

		}
	}

	public static String[] getDisplaySize(String deviceID)
	{
		CommandPrompt cmd = new CommandPrompt();
		try 
		{
			String output = cmd.runCommand("adb -s " + deviceID + " shell wm size");
			System.out.println(output);
			output = output.replace("Physical size:", "").trim();
			return output.split("x");

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getCurrentActivity(String deviceID)
	{
		CommandPrompt cmd = new CommandPrompt();
		String output;
		try 
		{
			output = cmd.runCommand("adb -s " + deviceID + " shell dumpsys window windows");
			String[] lines = output.split("\n");
			for(String line : lines)
			{
				if(line.trim().startsWith("mCurrentFocus"))
				{
					System.out.println(line);
					if(line.contains("/"))
					{
						String activityName = line.split("/")[1];
						return activityName.substring(0, activityName.length() - 1);
					}
					else
					{
						return "";
					}
				}
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();

		}

		return "";
	}

	public static boolean runMonkey(String deviceID, List<String> packageList, long eventAmount)
	{
		CommandPrompt cmd = new CommandPrompt();
		String packages = "";
		for(String packageName : packageList)
		{
			packages = packages + " -p " + packageName + " ";
		}

		try {
			String command = "adb -s " + deviceID + " shell monkey --ignore-crashes --ignore-timeouts " + packages + "-v " + eventAmount 
					+ " >" + TestContext.getInstance().getVariable("LogFolder") + File.separator + "monkey.log";

			String output = cmd.runCommand(command);

			if(!output.toLowerCase().contains("error"))
			{
				return true;
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean setScreenTimeout(String deviceID, long timeoutInMinutes)
	{
		CommandPrompt cmd = new CommandPrompt();
		try {
			String output = cmd.runCommand("adb -s " + deviceID + " shell settings put system screen_off_timeout " + timeoutInMinutes * 60 * 1000);

			if(!output.toLowerCase().contains("error"))
			{
				return true;
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isDeviceConnected(String deviceID)
	{
		CommandPrompt cmd = new CommandPrompt();
		try {
			String output = cmd.runCommand("adb devices");
			System.out.println(output);

			if(output.contains(deviceID))
			{
				return true;
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean captureScreen(String deviceID, String fileName, String localPath)
	{
		CommandPrompt cmd = new CommandPrompt();
		try
		{
			// Capture Screen
			cmd.runCommand("adb -s " + deviceID + " shell screencap -p /sdcard/" + fileName);
			// Move it to local folder
			Path folderPath = Paths.get(localPath).toAbsolutePath();
			String output = cmd.runCommand("adb -s " + deviceID + " pull /sdcard/" + fileName + " \"" + folderPath.toString() + File.separator + fileName + "\"");
			System.out.println(output);
			if(output.contains("100%"))
			{
				return true;
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: error when capturing screen.");
		}

		return false;
	}

	public static void tap(String deviceID, int x, int y)
	{
		CommandPrompt cmd = new CommandPrompt();
		// Tap on screen to keep it active
		try {
			cmd.runCommand("adb -s " + deviceID + " shell input tap " + x + " " + y);
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean waitForActivityOnFocus(String deviceID, String activityName, int timeoutInSeconds)
	{
		CommandPrompt cmd = new CommandPrompt();
		boolean isConnected = false;
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();

		OUTTER_LOOP:
			while(!isConnected && startTime.until( endTime, ChronoUnit.SECONDS) < timeoutInSeconds)
			{
				String output;
				try {
					output = cmd.runCommand("adb -s " + deviceID + " shell dumpsys window windows");
					String[] lines = output.split("\n");
					for(String line : lines)
					{
						if(line.trim().startsWith("mCurrentFocus") && line.trim().endsWith(activityName + "}"))
						{
							System.out.println(line);
							isConnected = true;
							break OUTTER_LOOP;
						}
					}

					Thread.sleep(15000);

				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}

				endTime = LocalDateTime.now();
			}

		if(!isConnected)
		{
			System.out.println("ERROR: Fail to luanch activity [" + activityName + "].");
			return false;
		}
		else
		{
			System.out.println("INFO: Activity [" + activityName + "] is launched.");
			return true;
		}
	}

}
