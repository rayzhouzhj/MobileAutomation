package com.github.mobiletest.driver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.github.mobiletest.component.elements.AndroidElement;
import com.github.mobiletest.logs.MyLogger;
import com.github.mobiletest.test.TestContext;
import com.github.mobiletest.utils.ADBShell;
import com.github.mobiletest.utils.CommandPrompt;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

@SuppressWarnings("unchecked")
public class MyAndroidDriver<T extends WebElement> extends AndroidDriver<WebElement> {

	private MyLogger log = null;

	public MyAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, desiredCapabilities);

		log = new MyLogger("NewAndroidDriver");
	}

	@Override
	public void startActivity(String appPackage, String appActivity)
	{
		try
		{
			log.info("Start Activity", "Launch: Package: [" + appPackage + "], Activity: [" + appActivity + "]");
			super.startActivity(appPackage, appActivity);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.fatal("Start Activity", "Unable to start activity", e);
		}
	}

	public boolean isElementExist(By by, int timeoutInSeconds)
	{
		boolean isFound = false;
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();

		log.info("Wait for Element to be displayed: " + by.toString());

		while(!isFound && startTime.until( endTime, ChronoUnit.SECONDS) < timeoutInSeconds)
		{
			try
			{
				super.findElement(by);
				isFound = true;
			}
			catch(Exception e)
			{
				// DO Nothing
			}
			endTime = LocalDateTime.now();
		}

		return isFound;
	}

	/**
	 * Find element by specific By and timeout in 15 seconds by default.
	 * 
	 * @param by By indicator to find element
	 */
	@Override
	@SuppressWarnings("unchecked")
	public AndroidElement findElement(By by)
	{
		return this.findElement(by, 15);
	}

	/**
	 * Find Elements by specific By.
	 * @param by
	 * @param timeoutInSeconds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AndroidElement findElement(By by, int timeoutInSeconds)
	{
		WebElement element = null;
		boolean isElementFound = false;
		try
		{
			LocalDateTime startTime = LocalDateTime.now();
			LocalDateTime endTime = LocalDateTime.now();
			Exception tempException = null;

			while(startTime.until(endTime, ChronoUnit.SECONDS) < timeoutInSeconds)
			{
				try
				{
					element = super.findElement(by);
					isElementFound = true;

					break;
				}
				catch(Exception e)
				{
					tempException = e;
				}

				endTime = LocalDateTime.now();
			}

			if(!isElementFound)
			{
				throw tempException;
			}
		}
		catch(Exception e)
		{
			if("ON".equalsIgnoreCase(TestContext.getInstance().getVariable("DebugMode")))
			{
				int result = JOptionPane.showConfirmDialog(null, "Unable to find element: " + by.toString(), "Error", JOptionPane.YES_NO_OPTION);

				if(result == JOptionPane.NO_OPTION)
				{
					log.fatal("Find Element", "Unable to find element: " + by.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
				}
				else
				{
					log.error("Find Element", "Unable to find element: " + by.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
				}
			}
			else
			{
				log.fatal("Find Element", "Unable to find element: " + by.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
			}
		}

		return new AndroidElement(this, by, element);
	}

	/**
	 * Find elements by specific By and timeout in 15 seconds by default.
	 * 
	 * @param by By indicator to find element
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List findElements(By by)
	{
		return this.findElements(by, 15);
	}

	/**
	 * Find Elements by specific By.
	 * @param by
	 * @param timeoutInSeconds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AndroidElement> findElements(By by, int timeoutInSeconds)
	{
		List<AndroidElement> list = new ArrayList<>();
		boolean isElementFound = false;
		try
		{
			LocalDateTime startTime = LocalDateTime.now();
			LocalDateTime endTime = LocalDateTime.now();
			Exception tempException = null;

			while(startTime.until(endTime, ChronoUnit.SECONDS) < timeoutInSeconds)
			{
				try
				{
					List<?> tempList = super.findElements(by);
					if(tempList != null)
					{
						tempList.forEach(element -> list.add(new AndroidElement(this, by, (WebElement)element)));
					}
					isElementFound = true;

					break;
				}
				catch(Exception e)
				{
					tempException = e;
				}

				endTime = LocalDateTime.now();
			}

			if(!isElementFound)
			{
				throw tempException;
			}
		}
		catch(Exception e)
		{
			if("ON".equalsIgnoreCase(TestContext.getInstance().getVariable("DebugMode")))
			{
				log.error("Find Elements", "Unable to find element: " + by.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
				int result = JOptionPane.showConfirmDialog(null, "Unable to find element: " + by.toString(), "Error", JOptionPane.YES_NO_OPTION);

				if(result == JOptionPane.NO_OPTION)
				{
					log.fatal("Find Elements", "Unable to find element: " + by.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
				}
				else
				{
					log.error("Find Elements", "Unable to find element: " + by.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
				}
			}
			else
			{
				log.fatal("Find Elements", "Unable to find element: " + by.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
			}
		}

		return list;
	}

	/**
	 * @see io.appium.java_client.TouchShortcuts#swipe(int, int, int, int, int)
	 */
	@Override 
	public void swipe(int startx, int starty, int endx, int endy, int duration) 
	{
		try
		{
			log.info("Swipe " + "startx=" + startx + " starty=" + starty + " endx=" + endx + " endy=" + endy + " duration=" + duration, "");

			super.swipe(startx, starty, endx, endy, duration);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			// Re-try once after 2 seconds
			try
			{
				Thread.sleep(2000);
				log.info("Re-try Swipe");
				super.swipe(startx, starty, endx, endy, duration);
			}
			catch(Exception ex)
			{
				e.printStackTrace();
				if("ON".equalsIgnoreCase(TestContext.getInstance().getVariable("DebugMode")))
				{
					log.error("Swipe", "Unable to swipe on current screen.", e);
					int result = JOptionPane.showConfirmDialog(null, "Unable to swipe on current screen.", "Error", JOptionPane.YES_NO_OPTION);

					if(result == JOptionPane.NO_OPTION)
						throw e;
				}
				else
				{
					log.fatal("Swipe", "Unable to swipe on current screen. <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", ex);
				}
			}
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

		ADBShell.captureScreen(TestContext.getInstance().getVariable("DeviceID"), destFile, destDir);

		return "screenshots" + File.separator + destFile;
	}

	public String takeScreenShot() {
		// Set folder name to store screenshots.
		String destDir = TestContext.getInstance().getVariable("LogFolder") + File.separator + "screenshots";
		// Capture screenshot.
		File scrFile = ((TakesScreenshot) this).getScreenshotAs(OutputType.FILE);
		// Set date format to set It as screenshot file name.
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// Create folder under project with name "screenshots" provided to destDir.
		new File(destDir).mkdirs();
		// Set file name using current date time.
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			// Copy paste file at destination folder location
			FileUtils.copyFile(scrFile, new File(destDir + File.separator + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "screenshots" + File.separator + destFile;
	}

	public void pressHomeButton()
	{
		System.out.println("INFO: " + "Press Home Key");

		this.pressKeyCode(AndroidKeyCode.HOME);

		log.info("Press Home Key", "");
	}

	public void pressBackButton()
	{
		System.out.println("INFO: " + "Press Back Key");

		this.pressKeyCode(AndroidKeyCode.BACK);

		log.info("Press Back Key", "");
	}

	public static void activateScreen()
	{
		CommandPrompt cmd = new CommandPrompt();
		try 
		{
			cmd.runCommand("adb -s " + TestContext.getInstance().getVariable("DeviceID") + " shell input tap 10 10");

			Thread.sleep(20000);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}
