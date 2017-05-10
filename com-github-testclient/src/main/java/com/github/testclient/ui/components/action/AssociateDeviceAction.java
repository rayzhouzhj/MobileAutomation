package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import com.github.testclient.models.Devices;
import com.github.testclient.ui.NewChooseDeviceFrame;
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

				NewChooseDeviceFrame chooseDevice = new NewChooseDeviceFrame(deviceControlPane.getSelectedDevice(), Devices.getDevices(), false);
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

				List<AndroidDevice> devices = chooseDevice.getselectedDevices();

				if(devices != null && devices.size() > 0 )
				{
					deviceControlPane.associateDevices(devices);
				}

				chooseDevice.dispose();

			}
		}).start();
	}

}
