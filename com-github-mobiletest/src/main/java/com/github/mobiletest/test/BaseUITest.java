package com.github.mobiletest.test;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.mobiletest.component.elements.AndroidElement;
import com.github.mobiletest.driver.MyAndroidDriver;
import com.github.mobiletest.utils.ADBShell;

import io.appium.java_client.remote.MobileCapabilityType;

public class BaseUITest extends BaseTest{

	@Before
	public void setUp() throws Exception {
		System.out.println("INFO: Setting up test environment...");
		initilize();

		info("Start Test", "Test flow started", false);
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
			//			info("Tear Down", "Fail to Tear down test environment", false);
		}

		if("Failed".equalsIgnoreCase(TestContext.getInstance().getVariable("Status")))
		{
			Assert.fail();
		}
	}

	public BaseUITest()
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

	@SuppressWarnings("rawtypes")
	public void createDriver(String appPkg, String appActivity){
		boolean isConnected = false;

		activateScreen();

		// Try to set screen timeout again
		ADBShell.setScreenTimeout(this.getDeviceID(), 15);

		try	{
			System.out.println("INFO: Trying to connect to Device Name [" + deviceName + "] with ID [" + deviceID + "]...");

			// create appium driver instance
			DesiredCapabilities capabilities = DesiredCapabilities.android();
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
			capabilities.setCapability(MobileCapabilityType.VERSION, platformVersion);
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
			capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
			// Set your application's appPackage if you are using any other app.
			capabilities.setCapability("appPackage", appPkg);
			// Set your application's appPackage if you are using any other app.
			capabilities.setCapability("appActivity", appActivity);
			// Set browserName desired capability. It's Android in our case here.
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

			this.driver = new MyAndroidDriver(new URL("http://127.0.0.1:"+port+"/wd/hub"), capabilities);
			this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

			isConnected = true;
		}
		catch(Exception e){
			TestContext.getInstance().setVariable("Current_Step", "Initilize Connection");
			logger.error("Unable to connect to device", e);
		}

		if(!isConnected)
		{
			TestContext.getInstance().setVariable("Status", "FAILED");

			failAndExit("Connect to Device", "Unable to connect to Device.", false);
		}
		else
		{
			info("Connect to Device", "Connect to Device successfully.", false);
		}

		// Try to set screen timeout again
		ADBShell.setScreenTimeout(this.getDeviceID(), 20);
	}

	@SuppressWarnings("rawtypes")
	public void createDriver(String appPkg, String appActivity, int reTryCount){
		int count = 0;
		boolean isConnected = false;

		activateScreen();

		// Try to set screen timeout again
		ADBShell.setScreenTimeout(this.getDeviceID(), 15);

		while(count < reTryCount)
		{
			try	{
				System.out.println("INFO: Trying to connect to Device Name [" + deviceName + "] with ID [" + deviceID + "]...");

				// create appium driver instance
				DesiredCapabilities capabilities = DesiredCapabilities.android();
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
				capabilities.setCapability(MobileCapabilityType.VERSION, platformVersion);
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
				capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
				capabilities.setCapability("appPackage", appPkg);
				capabilities.setCapability("appActivity", appActivity);
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Android");
				capabilities.setCapability(MobileCapabilityType.NO_RESET, true);

				this.driver = new MyAndroidDriver(new URL("http://127.0.0.1:"+port+"/wd/hub"), capabilities);
				this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

				isConnected = true;
				break;
			}
			catch(Exception e){
				if(count == reTryCount)
				{
					TestContext.getInstance().setVariable("Status", "Failed");

					logger.error("Unable to connect to device", e);
					failAndExit("Initilize Connection", "Unable to connect to device", true);
				}

				info("Initilize Connection", "Unable to creation connection, will try to connect to device again", false);

				//ReStart Appium
				stopAppium();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				startAppium();
			}
		}

		if(!isConnected)
		{
			failAndExit("Connect to Device", "Unable to connect to Device.", false);
		}
		else
		{
			TestContext.getInstance().setVariable("Status", "RUNNING");
			info("Connect to Device", "Connect to Device successfully.", false);
		}

		// Try to set screen timeout again
		ADBShell.setScreenTimeout(this.getDeviceID(), 20);
	}


	public void slideToUnlockScreen()
	{
		info("Swipe to unlock Screen");
		// Swipe To Unlock
		Dimension dimensions = driver.manage().window().getSize();
		Double screenWidthStart = dimensions.getWidth() * 0.5;
		int scrollStart = screenWidthStart.intValue();
		Double screenWidthEnd = dimensions.getWidth() * 0.8;
		int scrollEnd = screenWidthEnd.intValue();

		int scrollHeight = dimensions.getHeight() - 200;

		try 
		{
			Thread.sleep(1000);
			driver.swipe(scrollStart, scrollHeight, scrollEnd, scrollHeight, 1000);
			Thread.sleep(3000);

			// Wait for Home Screen launches
			List<AndroidElement> elements = driver.findElements(By.className("android.widget.ImageView"));
			while(elements.size() < 2)
			{
				Thread.sleep(1000);
				elements = driver.findElements(By.className("android.widget.ImageView"));
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
