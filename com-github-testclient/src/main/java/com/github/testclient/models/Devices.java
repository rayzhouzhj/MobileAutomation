package com.github.testclient.models;

import java.util.List;

import com.github.testclient.util.AndroidDevice;

public class Devices {
	private static List<AndroidDevice> deviceList = null;
	
	public static void setDevices(List<AndroidDevice> devices)
	{
		deviceList = devices;
	}
	
	public static List<AndroidDevice> getDevices()
	{
		return deviceList;
	}
	
	public static AndroidDevice findDeviceByID(String deviceID)
	{
		if(deviceList != null)
		{
			for(AndroidDevice device : deviceList)
			{
				if(device.getDeviceID().equals(deviceID))
				{
					return device;
				}
			}
		}
	
		return null;
	}
}
