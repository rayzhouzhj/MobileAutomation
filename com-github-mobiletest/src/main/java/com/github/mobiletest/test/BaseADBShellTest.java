package com.github.mobiletest.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.TestName;

import com.github.mobiletest.utils.ADBShell;

public class BaseADBShellTest  extends BaseTest{

	@Before
	public void setUp() throws Exception {
		System.out.println("INFO: Setting up test environment...");
		initilize();
		
		info("Start Testing");
	}

	@After
	public void tearDown() throws Exception {

		System.out.println("INFO: Tearing down test environment...");

		try 
		{
//			if("RUNNING".equalsIgnoreCase(TestContext.getInstance().getVariable("Status")))
//			{
//				TestContext.getInstance().setVariable("Status", "Passed");
//			}
//
//			// Update Log status
//			LogSetting.updateLogStatus();

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

		} catch (Exception e) {

//			info("Tear Down", "Fail to Tear down test environment", false);
		}

		if("Failed".equalsIgnoreCase(TestContext.getInstance().getVariable("Status")))
		{
			Assert.fail();
		}

	}

	public BaseADBShellTest()
	{
		name = new TestName();
	}

	public void initilize()
	{
		InitTestData();
		// Set Screen Timeout
		ADBShell.setScreenTimeout(TestContext.getInstance().getVariable("DeviceID"), 30);
		setLogger();
	}

}
