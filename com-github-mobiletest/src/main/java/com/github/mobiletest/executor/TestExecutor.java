package com.github.mobiletest.executor;

import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import com.github.mobiletest.devices.AndroidDevice;
import com.github.mobiletest.devices.DeviceConfiguration;
import com.github.mobiletest.test.TestContext;
import com.github.mobiletest.utils.ADBShell;
import com.github.mobiletest.utils.DeviceLock;

public class TestExecutor {

	public static void main(String... args) throws Exception {

		TestContext.getInstance().setVariable("CMD_Mode", "ON");

		System.out.println("Argument Length: " + args.length);
		for(String arg : args)
		{
			System.out.println("Argument: " + arg);
		}
		
		String className = args[0];
		String methodName = args[1];
		String deviceID = args[2];
		int iteration = 1;
		try
		{
			iteration = Integer.parseInt(args[3]);
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Argument[4]-Iteration [" + args[3] + "] is invalid, only number is accepted." );
			System.exit(0);
		}

		try
		{
			Class.forName(className);
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Argument[1]-Class Name [" + className + "] cannot be found." );
			System.out.println(e);
			System.exit(0);
		}

		String[] vars;
		// set variables
		for(int i = 4; i <= args.length - 1; i++)
		{
			if(!args[i].contains("="))
			{
				System.out.println("ERROR: Argument [" + i + "] is invalid, argument should be in key-value pair, e.g. variableName=value" );
				System.exit(0);
			}
			vars = args[i].split("=");
			TestContext.getInstance().setVariable(vars[0], vars[1]);
		}

		DeviceConfiguration deviceConf = new DeviceConfiguration();
		List<String> devices = deviceConf.getDivceList();

		if(devices.contains(deviceID))
		{
			AndroidDevice device = deviceConf.getDeviceByID(deviceID);

			TestContext.getInstance().setVariable("DeviceName", device.getDeviceName());
			TestContext.getInstance().setVariable("DeviceID", device.getDeviceID());
			TestContext.getInstance().setVariable("PlatformVersion", device.getPlatformVersion());
		}
		else
		{
			System.out.println("ERROR: Cannot find the expected device: " + deviceID);

			System.out.println("INFO: Exiting Test.");

			System.exit(0);
		}

		// Lock device
		if(DeviceLock.createDeviceLock())
		{
			System.out.println("INFO: Lock device " + TestContext.getInstance().getVariable("DeviceID") + " successfully.");
		}
		else
		{
			// Fail to lock device, retry once
			if(DeviceLock.createDeviceLock())
			{
				System.out.println("INFO: Lock device " + TestContext.getInstance().getVariable("DeviceID") + " successfully.");
			}
			else
			{
				System.out.println("ERROR: Device " + TestContext.getInstance().getVariable("DeviceID") + " is busy, please try again later.");
				System.exit(0);
			}
		}

		
		for(int count = 0; count < iteration; count ++)
		{
			if(!ADBShell.isDeviceConnected(TestContext.getInstance().getVariable("DeviceID")))
			{
				System.out.println("ERROR: Device [" + TestContext.getInstance().getVariable("DeviceID") + "] get disconnected.");
				break;
			}

			if("YES".equalsIgnoreCase(TestContext.getInstance().getVariable("QuitIteration")))
			{
				System.out.println("INFO: Quit Iteration.");
				break;
			}
			
			ADBShell.setScreenTimeout(TestContext.getInstance().getVariable("DeviceID"), 10);
			
			System.out.println("INFO: Starting testing TestClass [" + className + "] - Test Name [" + methodName + "], iteration " + (count + 1));
			try {
				Request request = Request.method(Class.forName(className), methodName);
				new JUnitCore().run(request);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("INFO: - Test Name [" + methodName + "], iteration " + (count + 1) + " completed.");
			
			// Wait for 5 seconds before starting next iteration
			Thread.sleep(5000);
		}

		DeviceLock.releaseDeviceLock();

		System.exit(0);
	}

}
