package com.github.mobiletest.component.elements;

import java.io.File;
import java.io.IOException;
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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.github.mobiletest.driver.MyAndroidDriver;
import com.github.mobiletest.logs.MyLogger;
import com.github.mobiletest.test.TestContext;
import com.github.mobiletest.utils.ADBShell;


public class AndroidElement implements IAndroidElement {

	private org.openqa.selenium.WebElement element = null;
	private MyAndroidDriver<?> driver;
	private By by;
	private MyLogger log = new MyLogger("AndroidElement");

	public AndroidElement(MyAndroidDriver<?> driver, By by, org.openqa.selenium.WebElement element)
	{
		this.driver = driver;
		this.element = element;
		this.by = by;
	}

	public org.openqa.selenium.WebElement getWebElement()
	{
		return this.element;
	}

	public By getBy()
	{
		return this.by;
	}

	public boolean isChildElementExist(By by, int timeoutInSeconds)
	{
		boolean isFound = false;
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now();

		log.info("Wait for Element to be displayed: " + by.toString());

		while(!isFound && startTime.until( endTime, ChronoUnit.SECONDS) < timeoutInSeconds)
		{
			try
			{
				this.element.findElement(by);
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
	
	@Override
	public void click() {

		String step = "Click: " + element.getText() + " - " + by.toString();
		System.out.println("INFO: " + step);
		if(element != null)
		{
			log.info(step, "");
			element.click();
		}
	}

	@Override
	public void submit() {
		String step = "Submit: " + by.toString();

		if(element != null)
		{
			log.info(step, "");
			element.submit();
		}
	}

	@Override
	public void sendKeys(CharSequence... paramVarArgs) {
		StringBuilder chars = new StringBuilder();
		for(CharSequence c : paramVarArgs)
		{
			chars.append(c);
		}

		String step = "SendKeys: " + chars;

		if(element != null)
		{
			System.out.println("INFO: " + "SendKeys: " + chars.toString());
			log.info(step, "");
			element.sendKeys(paramVarArgs);
		}

	}

	@Override
	public void clear() {

		String step = "Clear: " + element.getText() + " - " + by.toString();
		System.out.println("INFO: " + step);
		if(element != null)
		{
			log.info(step, "");
			element.clear();
		}

	}

	@Override
	public String getTagName() {
		if(element != null)
		{
			return element.getTagName();
		}
		else
		{
			return null;
		}

	}

	@Override
	public String getAttribute(String paramString) {
		if(element != null)
		{
			return element.getAttribute(paramString);
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean isSelected() {
		if(element != null)
		{
			return element.isSelected();
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean isEnabled() {
		if(element != null)
		{
			return element.isEnabled();
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getText() {

		String text = "";
		if(element != null)
		{
			text = element.getText();
		}

		return text;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AndroidElement> findElements(By paramBy) {

		return this.findElements(paramBy, 15);
		
	}

	@SuppressWarnings("unchecked")
	public List<AndroidElement> findElements(By paramBy, int timeoutInSeconds) {

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
					List<?> tempList = this.element.findElements(paramBy);
					if(tempList != null)
					{
						tempList.forEach(element -> list.add(new AndroidElement(this.driver, paramBy, (WebElement)element)));
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
				int result = JOptionPane.showConfirmDialog(null, "Unable to find elements: " + paramBy.toString(), "Error", JOptionPane.YES_NO_OPTION);

				if(result == JOptionPane.NO_OPTION)
				{
					log.fatal("Find Elements", "Unable to find element: " + paramBy.toString() + " <a href='" + takeScreenShot() + "'>ScreenShot</a>", e);
				}
				else
				{
					log.error("Find Elements", "Unable to find element: " + paramBy.toString() + " <a href='" + takeScreenShot() + "'>ScreenShot</a>", e);
				}
			}
			else
			{
				log.fatal("Find Elements", "Unable to find element: " + paramBy.toString() + " <a href='" + takeScreenShot() + "'>ScreenShot</a>", e);
			}
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AndroidElement findElement(By paramBy) {
		
		return this.findElement(paramBy, 15);
	}

	@SuppressWarnings("unchecked")
	public AndroidElement findElement(By paramBy, int timeoutInSeconds) {
		WebElement newElement = null;
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
					newElement = this.element.findElement(paramBy);
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

				int result = JOptionPane.showConfirmDialog(null, "Unable to find element: " + paramBy.toString(), "Error", JOptionPane.YES_NO_OPTION);

				if(result == JOptionPane.NO_OPTION)
				{
					log.fatal("Find Element", "Unable to find element: " + paramBy.toString()  + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
				}
				else
				{
					log.error("Find Element", "Unable to find element: " + paramBy.toString() + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
				}
			}
			else
			{
				log.fatal("Find Element", "Unable to find element: " + paramBy.toString()  + " <a href='" + takeScreenShotThroughADB() + "'>ScreenShot</a>", e);
			}
		}

		return new AndroidElement(this.driver, paramBy, newElement);
	}

	public void longPress(int millionSeconds)
	{
		if(element != null)
		{
			this.driver.tap(1, this.element, millionSeconds);
		}
	}

	@Override
	public boolean isDisplayed() {
		if(element != null)
		{
			return element.isDisplayed();
		}
		else
		{
			return false;
		}
	}

	@Override
	public Point getLocation() {
		if(element != null)
		{
			return element.getLocation();
		}
		else
		{
			return null;
		}
	}

	@Override
	public Dimension getSize() {
		if(element != null)
		{
			return element.getSize();
		}
		else
		{
			return null;
		}
	}

	@Override
	public Rectangle getRect() {
		if(element != null)
		{
			return element.getRect();
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getCssValue(String paramString) {
		if(element != null)
		{
			return element.getCssValue(paramString);
		}
		else
		{
			return null;
		}
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> paramOutputType)
			throws WebDriverException {
		if(element != null)
		{
			return element.getScreenshotAs(paramOutputType);
		}
		else
		{
			return null;
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

}
