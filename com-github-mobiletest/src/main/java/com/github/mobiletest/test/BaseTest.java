package com.github.mobiletest.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.github.mobiletest.devices.AndroidDevice;
import com.github.mobiletest.devices.AppiumManager;
import com.github.mobiletest.devices.DeviceConfiguration;
import com.github.mobiletest.driver.MyAndroidDriver;
import com.github.mobiletest.logs.FormatHTMLLayout;
import com.github.mobiletest.logs.MyLogger;
import com.github.mobiletest.logs.NewLogForEachRunFileAppender;
import com.github.mobiletest.utils.ADBShell;
import com.github.mobiletest.utils.ChooseDevice;
import com.github.mobiletest.utils.CommandPrompt;
import com.github.mobiletest.utils.LogSetting;

public class BaseTest {
	@Rule 
	public TestName name = null;
	public MyLogger logger = null;
	private String logFolder = null;
	private String logFile = null;

	public MyAndroidDriver<?> driver;
	public String deviceName = null;
	public String deviceID = null;
	public String port = null;
	public String platformVersion = null;
	public String browserName = "chrome";
	public AppiumManager appiumMan = new AppiumManager();
	public DeviceConfiguration deviceConf = null;
	private Thread activateScreenThread;
	private boolean stopActivateScreenThreadBool = false;

	@Rule
	public TestWatcherMan watchman = new TestWatcherMan();
	
	class TestWatcherMan extends TestWatcher
	{
		@Override
		protected void failed(Throwable e, Description description) {
			
			super.failed(e, description);
			
			TestContext.getInstance().setVariable("Status", "Failed");
			logger.error("End Test", "Test flow is completed with error.");
			// Update Log status
			LogSetting.updateLogStatus();
		}

		@Override
		protected void succeeded(Description description) {
			super.succeeded(description);
			
			if("RUNNING".equalsIgnoreCase(TestContext.getInstance().getVariable("Status")))
			{
				TestContext.getInstance().setVariable("Status", "Passed");
			}

			logger.info("End Test", "Test flow completed.");
			// Update Log status
			LogSetting.updateLogStatus();
		}
	}
	
	public String getDeviceID()
	{
		return this.deviceID;
	}
	
	public String getDeviceName()
	{
		return this.deviceName;
	}

	public String getLogFolder()
	{
		return this.logFolder;
	}

	public String getLogFile()
	{
		return this.logFile;
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("INFO: Setting up test environment...");
		initilize();
	}

	@After
	public void tearDown() throws Exception {

		System.out.println("INFO: Tearing down test environment...");

		try 
		{
			if(driver != null)
			{
				driver.quit();
			}

			// Leave the screen active
			ADBShell.setScreenTimeout(TestContext.getInstance().getVariable("DeviceID"), 15);

			if(deviceConf != null)
			{
				deviceConf.stopADB(this.deviceID);
			}

			appiumMan.stopAppium(deviceID);

		} catch (Exception e) {

			appiumMan.stopAppium(deviceID);
		}
		
		if("Failed".equalsIgnoreCase(TestContext.getInstance().getVariable("Status")))
		{
			Assert.fail();
		}
	}

	public BaseTest()
	{
		name = new TestName();
	}

	public void initilize()
	{
		InitTestData();
		// Set Screen Timeout
		ADBShell.setScreenTimeout(TestContext.getInstance().getVariable("DeviceID"), 30);
		setLogger();
		startAppium();
	}

	public void InitTestData()
	{
		TestContext.getInstance().setVariable("Status", "Running");

		System.out.println("INFO: Initilizing Test Data...");
		try
		{
			if(!"ON".equalsIgnoreCase(TestContext.getInstance().getVariable("CMD_Mode")))
			{
				DeviceConfiguration deviceConf = new DeviceConfiguration();
				List<String> devices = deviceConf.getDivceList();

				AndroidDevice device = null;

				if(devices.size() > 1)
				{
					ChooseDevice chooseDevice = new ChooseDevice();
					chooseDevice.setLocationRelativeTo(null);
					chooseDevice.setVisible(true);
					device = chooseDevice.waitForSelectedDevice();
				}
				else
				{
					device = deviceConf.getDeviceByID(devices.get(0));
				}

				TestContext.getInstance().setVariable("DeviceName", device.getDeviceName());
				TestContext.getInstance().setVariable("DeviceID", device.getDeviceID());
				TestContext.getInstance().setVariable("PlatformVersion", device.getPlatformVersion());
			}

			deviceID = TestContext.getInstance().getVariable("DeviceID");
			deviceName = TestContext.getInstance().getVariable("DeviceName");
			platformVersion = TestContext.getInstance().getVariable("PlatformVersion");

			if(deviceID == null || deviceName == null || platformVersion == null)
			{
				TestContext.getInstance().setVariable("Status", "FAILED");

				failAndExit("Initilize Test Data", "Cannot find Device with ID [" + deviceID + "] Name [" + deviceName + "] Version [" + platformVersion + "]", false);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void startAppium()
	{
		try	
		{
			port = appiumMan.startAppium(deviceID); 			// Start appium server	
		}
		catch(Exception e){
			TestContext.getInstance().setVariable("Status", "Failed");

			TestContext.getInstance().setVariable("Current_Step", "Initilize Connection");
			logger.error("Unable to connect to device", e);
		}
	}

	public void stopAppium()
	{
		try {
			appiumMan.stopAppium(deviceID);
		} catch (Exception e) {
			if(!"FAILED".equalsIgnoreCase(TestContext.getInstance().getVariable("Status")))
			{
				TestContext.getInstance().setVariable("Status", "Warning");
			}

			TestContext.getInstance().setVariable("Current_Step", "Close Connection");
			logger.error("Unable to close connection", e);
		}
	}

	public void setLogger()
	{
		logger = new MyLogger(name.getMethodName());

		Logger rootLogger = Logger.getRootLogger();

		Enumeration<?> appenders = rootLogger.getAllAppenders();
		FileAppender appender = null;
		while(appenders.hasMoreElements())
		{
			Appender currAppender = (Appender) appenders.nextElement();
			if(currAppender instanceof NewLogForEachRunFileAppender)
			{
				appender = (FileAppender) currAppender;
			}
		}
		if(appender != null)
		{
			rootLogger.removeAppender(appender);
		}

		appender = new NewLogForEachRunFileAppender();

		
		logFolder = "log" + File.separator + "Device-" + deviceName  + "-" + deviceID
				+ File.separator + DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(LocalDate.now())
				+ File.separator + this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1) 
				+ File.separator + name.getMethodName();

		appender.setName("FileLogger");
		appender.setFile(logFolder);
		appender.setLayout(new FormatHTMLLayout(this.getClass().getName() + "." + name.getMethodName()));
		appender.setAppend(false);
		//		appender.setThreshold(Priority.WARN);
		appender.activateOptions();

		rootLogger.addAppender(appender);

		logFolder = Paths.get(appender.getFile()).getParent().toString();
		logFile = appender.getFile();

		TestContext.getInstance().setVariable("LogFolder", logFolder);
		TestContext.getInstance().setVariable("LogFile", logFile);

		LogSetting.createLogNavigator();
	}

	public void info(String message)
	{
		logger.info(message);
	}

	public void info(String stepName, String message, boolean takeScreenShot)
	{
		if(takeScreenShot)
		{
			logger.info(stepName, message + " <a href='" + takeScreenShot() + "'>ScreenShot</a>");
		}
		else
		{
			logger.info(stepName, message);
		}
	}

	public void warn(String stepName, String message, boolean takeScreenShot)
	{
		if(takeScreenShot)
		{
			logger.warn(stepName, message + " <a href='" + takeScreenShot() + "'>ScreenShot</a>");
		}
		else
		{
			logger.warn(stepName, message);
		}
	}

	public void fail(String stepName, String message, boolean takeScreenShot)
	{
		if(takeScreenShot)
		{
			logger.error(stepName, message + " <a href='" + takeScreenShot() + "'>ScreenShot</a>");
		}
		else
		{
			logger.error(stepName, message);
		}
	}

	public void failAndExit(String stepName, String message, boolean takeScreenShot)
	{
		if(takeScreenShot)
		{
			logger.fatal(stepName, message + " <a href='" + takeScreenShot() + "'>ScreenShot</a>");
		}
		else
		{
			logger.fatal(stepName, message);
		}

		Assert.fail(message);
	}

	public boolean waitForDeviceReboot(int timeoutInSeconds)
	{
		info("Waitting for Device shutdown...");

		CommandPrompt cmd = new CommandPrompt();
		boolean isDisconnected = false;
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();

		OUTTER_LOOP:
			while(!isDisconnected && startTime.until( endTime, ChronoUnit.SECONDS) < timeoutInSeconds)
			{
				String output;
				try {
					Thread.sleep(5000);

					output = cmd.runCommand("adb -s " + deviceID + " shell dumpsys window windows");
					String[] lines = output.split("\n");
					for(String line : lines)
					{
						if(line.trim().startsWith("error") && line.trim().endsWith("not found"))
						{
							System.out.println(line);
							isDisconnected = true;
							break OUTTER_LOOP;
						}
					}

				} catch (InterruptedException | IOException e) {
					e.printStackTrace();
				}

				endTime = LocalDateTime.now();
			}

		return isDisconnected;
	}

	public void reboot()
	{
		System.out.println("INFO: " + "Reboot Device: " + deviceID);

		this.stopAppium();

		CommandPrompt cmd = new CommandPrompt();
		try {
			cmd.runCommand("adb -s " + deviceID + " reboot");
		} catch (InterruptedException | IOException e) {
			logger.fatal("Fail to reboot device: " + deviceID, e);
			Assert.fail("Fail to reboot device: " + deviceID);
		}

		if(!waitForDeviceReboot(30))
		{
			failAndExit("Reboot Device", "Fail to reboot device in 30 seconds.", false);
		}
		else
		{
			info("Reboot Device", "Reboot device successfully.", false);
		}
	}

	public void startUnLockScreenService()
	{
		stopActivateScreenThreadBool = false;

		activateScreenThread = new Thread(()-> 
		{
			while(!stopActivateScreenThreadBool)
			{
				MyAndroidDriver.activateScreen();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		activateScreenThread.start();
	}
	
	public void sleep(long millionSeconds)
	{
		try 
		{
			Thread.sleep(millionSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void stopUnLockScreenService()
	{
		stopActivateScreenThreadBool = true;

		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitForDeviceReboot(String launcherActivity, int waitForActivityTimeout)
	{
		this.startUnLockScreenService();

		info("Waitting for " + launcherActivity + " launches...");
		
		boolean status = ADBShell.waitForActivityOnFocus(this.getDeviceID(), launcherActivity, waitForActivityTimeout);

		if(!status)
		{
			failAndExit("Wait For " + launcherActivity, launcherActivity + " is not launched in " + waitForActivityTimeout + " seconds", false);
		}
		else
		{
			info("Wait For " + launcherActivity, launcherActivity + " is displayed successfully", false);
		}

		this.stopUnLockScreenService();

		// Set Screen Timeout
		ADBShell.setScreenTimeout(this.getDeviceID(), 30);

	}

	public void activateScreen()
	{
		try
		{
			// Check if the device is black out
			while(ADBShell.isScreenDisplayOff(this.getDeviceID()))
			{
				info("Screen is sleeping, try to activate screen...");

				// Press Power Button to activate screen
				ADBShell.pressPowerButton(this.getDeviceID());
				ADBShell.setScreenTimeout(this.getDeviceID(), 10);
				Thread.sleep(3000);
			}

			ADBShell.setScreenTimeout(this.getDeviceID(), 20);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String takeScreenShotThroughADB() 
	{
		// Set folder name to store screenshots.
		String destDir = TestContext.getInstance().getVariable("LogFolder") + File.separator + "screenshots";
		String destFile = null;

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// Create folder under project with name "screenshots" provided to destDir.
		new File(destDir).mkdirs();
		// Set file name using current date time.
		destFile = dateFormat.format(new Date()) + ".png";

		ADBShell.captureScreen(deviceID, destFile, destDir);
		
		return "screenshots" + File.separator + destFile;
	}
	
	public String takeScreenShot() {
		// Set folder name to store screenshots.
		String destDir = TestContext.getInstance().getVariable("LogFolder") + File.separator + "screenshots";
		String destFile = null;
		
		if(driver == null)
		{
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
			// Create folder under project with name "screenshots" provided to destDir.
			new File(destDir).mkdirs();
			// Set file name using current date time.
			destFile = dateFormat.format(new Date()) + ".png";

			ADBShell.captureScreen(deviceID, destFile, destDir);
		}
		else
		{

			// Capture screenshot.
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// Set date format to set It as screenshot file name.
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
			// Create folder under project with name "screenshots" provided to destDir.
			new File(destDir).mkdirs();
			// Set file name using current date time.
			destFile = dateFormat.format(new Date()) + ".png";

			try 
			{
				// Copy paste file at destination folder location
				FileUtils.copyFile(scrFile, new File(destDir + File.separator + destFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "screenshots" + File.separator + destFile;
	}
}
