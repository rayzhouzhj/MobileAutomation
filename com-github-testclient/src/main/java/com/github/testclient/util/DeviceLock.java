package com.github.testclient.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeviceLock {

	public static boolean createDeviceLock(AndroidDevice device)
	{
		try
		{
			// Lock device
			String folderName = "log" + File.separator + "Device-" + device.getDeviceName()  + "-" + device.getDeviceID();
			Path folderPath = Paths.get(folderName);
			if(!Files.exists(folderPath))
			{
				Files.createDirectories(folderPath);
			}

			Path lockFile = Paths.get(folderName + File.separator + "device.lck");

			if(!Files.exists(lockFile))
			{
				Files.createFile(lockFile);
			}
			else
			{
				return false;
			}

			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static boolean releaseDeviceLock(AndroidDevice device)
	{
		try
		{
			// release device
			String folderName = "log" + File.separator + "Device-" + device.getDeviceName()  + "-" + device.getDeviceID();
			Path folderPath = Paths.get(folderName);
			if(!Files.exists(folderPath))
			{
				System.out.println("WARN: Lock file was removed abnormally");
				return true;
			}

			Path lockFile = Paths.get(folderName + File.separator + "device.lck");

			if(!Files.exists(folderPath))
			{
				System.out.println("WARN: Lock file was removed abnormally");
				return true;
			}
			else
			{
				Files.delete(lockFile);
			}

			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
