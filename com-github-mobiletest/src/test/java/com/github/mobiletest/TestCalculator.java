package com.github.mobiletest;

import org.junit.Test;
import org.openqa.selenium.By;

import com.github.mobiletest.test.BaseUITest;

public class TestCalculator extends BaseUITest{

	@Test
	public void test() throws Exception {
		this.createDriver("com.android.calculator2", "com.android.calculator2.Calculator");
		
		// Click on CLR button.
		driver.findElement(By.id("com.android.calculator2:id/del")).click();

		//OR you can use bellow given syntax to click on CLR/DEL button.
		//driver.findElements(By.className("android.view.View")).get(1).findElements(By.className("android.widget.Button")).get(0).click();

		// Click on number 2 button.
		driver.findElement(By.id("com.android.calculator2:id/digit2")).click();

		// Click on + button.
		driver.findElement(By.id("com.android.calculator2:id/plus")).click();

		// Click on number 5 button.
		driver.findElement(By.id("com.android.calculator2:id/digit5")).click();

		// Click on = button.
		driver.findElement(By.id("com.android.calculator2:id/equal")).click();

		// Get result from result text box of calc app.
		String result = driver.findElement(By.className("android.widget.EditText")).getText();
		info("Number sum result is : " + result);
	}
}
