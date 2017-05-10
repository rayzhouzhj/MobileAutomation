package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.github.testclient.models.Devices;
import com.github.testclient.ui.ChooseDeviceFrame;
import com.github.testclient.ui.components.TestSuiteControlPane;
import com.github.testclient.util.AndroidDevice;

public class AssociateDeviceAction extends AbstractAction{

	private TestSuiteControlPane deviceControlPane;

	public AssociateDeviceAction(TestSuiteControlPane deviceControlPane)
	{
		super("Choose Device");
		this.deviceControlPane = deviceControlPane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new Thread(new Runnable() {
			public void run() {

				ChooseDeviceFrame chooseDevice = new ChooseDeviceFrame(Devices.getDevices(), false);
				chooseDevice.setLocationRelativeTo(null);
				chooseDevice.setVisible(true);

				while(chooseDevice.isVisible())
				{
					try 
					{
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				AndroidDevice device = chooseDevice.getselectedDevice();

				if(device != null)
				{
					deviceControlPane.associateDevice(device);
				}

				chooseDevice.dispose();

			}
		}).start();
	}

}
