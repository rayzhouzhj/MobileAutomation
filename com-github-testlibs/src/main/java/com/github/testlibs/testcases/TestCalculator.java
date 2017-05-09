package com.github.testlibs.testcases;

import org.junit.Test;
import org.openqa.selenium.By;

import com.github.mobiletest.test.BaseUITest;
import com.github.mobiletest.test.TestContext;

public class TestCalculator extends BaseUITest{

	@Test
	public void testMethod1() throws Exception {
		String number1 = TestContext.getInstance().getVariable("Number1");
		String number2 = TestContext.getInstance().getVariable("Number2");
		
		this.createDriver("com.android.calculator2", "com.android.calculator2.Calculator");
		
		// Click on CLR button.
		driver.findElement(By.id("com.android.calculator2:id/del")).click();

		// Click on number 2 button.
		driver.findElement(By.id("com.android.calculator2:id/digit" + number1)).click();

		// Click on + button.
		driver.findElement(By.id("com.android.calculator2:id/plus")).click();

		// Click on number 5 button.
		driver.findElement(By.id("com.android.calculator2:id/digit" + number2)).click();

		// Click on = button.
		driver.findElement(By.id("com.android.calculator2:id/equal")).click();

		// Get result from result text box of calc app.
		String result = driver.findElement(By.className("android.widget.EditText")).getText();
		info("Number sum result is : " + result);
	}
	
	@Test
	public void testMethod2() throws Exception {
		String number1 = TestContext.getInstance().getVariable("Number1");
		String number2 = TestContext.getInstance().getVariable("Number2");
		
		this.createDriver("com.android.calculator2", "com.android.calculator2.Calculator");
		
		// Click on CLR button.
		driver.findElement(By.id("com.android.calculator2:id/del")).click();

		// Click on number 2 button.
		driver.findElement(By.id("com.android.calculator2:id/digit" + number1)).click();

		// Click on + button.
		driver.findElement(By.id("com.android.calculator2:id/plus")).click();

		// Click on number 5 button.
		driver.findElement(By.id("com.android.calculator2:id/digit" + number2)).click();

		// Click on = button.
		driver.findElement(By.id("com.android.calculator2:id/equal")).click();

		// Get result from result text box of calc app.
		String result = driver.findElement(By.className("android.widget.EditText")).getText();
		info("Check Result", "Number sum result is : " + result, true);
	}
}
