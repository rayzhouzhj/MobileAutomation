package com.github.testclient;

import com.github.testclient.ui.InitializationFrame;

public class Main {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		InitializationFrame initFram = new InitializationFrame();
		initFram.setLocationRelativeTo(null);
		initFram.setVisible(true);
		initFram.waitForInitialization();
		initFram.launchChooseDeviceWindow();
	}
}
