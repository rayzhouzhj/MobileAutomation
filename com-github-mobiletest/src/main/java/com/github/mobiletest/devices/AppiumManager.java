package com.github.mobiletest.devices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import com.github.mobiletest.test.TestContext;
import com.github.mobiletest.utils.CommandPrompt;
import com.github.mobiletest.utils.TaskManagerUtil;

/**
 * Appium Manager - this class contains method to start and stops appium server  
 */
public class AppiumManager {

	CommandPrompt cp = new CommandPrompt();
	AvailabelPorts ap = new AvailabelPorts();
	String processID = null;


	public boolean createAppiumLock()
	{
		try
		{
			Path lockFile = Paths.get("appium.lck");

			if(!Files.exists(lockFile))
			{
				Files.createFile(lockFile);
			}
			else
			{
				return false;
			}

			System.out.println("INFO: Created appium lock.");
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public boolean releaseAppiumLock()
	{
		try
		{
			Path lockFile = Paths.get("appium.lck");
			if(!Files.exists(lockFile))
			{
				System.out.println("WARN: Lock file was removed abnormally");
				return true;
			}
			else
			{
				System.out.println("INFO: Released appium lock.");
				Files.delete(lockFile);
			}

			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public void lockAppiumServer()
	{
		// Lock device
		if(this.createAppiumLock())
		{
			System.out.println("INFO: Lock Appium server successfully.");
		}
		else
		{
			LocalDateTime startTime = LocalDateTime.now();
			LocalDateTime endTime = LocalDateTime.now();

			while(startTime.until(endTime, ChronoUnit.SECONDS) < 90)
			{
				System.out.println("INFO: Appium server is locked, wait for 5 seconds and try again.");
				try 
				{
					Thread.sleep(5000);
					if(this.createAppiumLock())
					{
						System.out.println("INFO: Lock Appium server successfully.");
						break;
					}
				} 
				catch (InterruptedException e1)
				{
					// DO Nothing
				}
				endTime = LocalDateTime.now();
			}
		}
	}

	/**
	 * start appium with auto generated ports : appium port, chrome port, and bootstap port
	 */
	public String startAppium(String deviceID) throws Exception
	{
		// start appium server
		String port = ap.getPort();

		if("ON".equalsIgnoreCase(TestContext.getInstance().getVariable("CMD_Mode")))
		{
			lockAppiumServer();
		}

		List<String> existingProcessList,newProcessList;

		// For windows
		if(System.getProperty("os.name").contains("Windows"))
		{
			existingProcessList = TaskManagerUtil.findProcess("node.exe");
		}
		// For MAC
		else
		{
			existingProcessList = TaskManagerUtil.findProcess("appium --session-override");
		}

		System.out.println("INFO: Starting appium...");

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		String logFile = "\"" + TestContext.getInstance().getVariable("LogFolder") 
				+ File.separator + "appiumlog_" + dateFormat.format(new Date()) +".txt" + "\"";

		String command = "appium --session-override -a 127.0.0.1 -p "+port+" --udid " + deviceID + " --log " + logFile;
		String output = cp.runCommand(command);
		System.out.println(output);
		System.out.println();
		System.out.println("INFO: Appium Starts successfully.");

		// For windows
		if(System.getProperty("os.name").contains("Windows"))
		{
			newProcessList = TaskManagerUtil.findProcess("node.exe");
		}
		// For MAC
		else
		{
			newProcessList = TaskManagerUtil.findProcess("appium --session-override");
		}

		for(String process : existingProcessList)
		{
			newProcessList.remove(process);
		}

		if(newProcessList.size() > 0)
		{
			processID = newProcessList.get(0);
			TestContext.getInstance().setVariable("NODEJS_PROCESS_ID", processID);
			System.out.println("INFO: New node.exe process is running, PID: " + processID);
		}
		else
		{
			System.out.println("INFO: No new node.exe is created.");
		}

		if("ON".equalsIgnoreCase(TestContext.getInstance().getVariable("CMD_Mode")))
		{
			releaseAppiumLock();
		}

		return port;
	}


	public void stopAppium(String deviceID) throws InterruptedException, IOException
	{
		System.out.println("INFO: Stopping appium...");
		if(processID != null)
		{
			TaskManagerUtil.killProcess(processID);
			processID = null;
		}

		System.out.println("INFO: Appium is stopped");
	}

}
