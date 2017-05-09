package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.github.testclient.models.Devices;
import com.github.testclient.ui.components.ButtonTabComponent;
import com.github.testclient.ui.components.DeviceControlPane;
import com.github.testclient.util.AndroidDevice;

public class AddDeviceAction extends AbstractAction{

	private String title;
	private AndroidDevice device;
	private JTabbedPane deviceControlPane;

	public AddDeviceAction(String title, AndroidDevice device, JTabbedPane deviceControlPane)
	{
		super(title);
		this.title = title;
		this.device = device;
		this.deviceControlPane = deviceControlPane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int tabCount = this.deviceControlPane.getTabCount();

		if("ALL".equalsIgnoreCase(this.title.trim()))
		{
			boolean isDeviceFound = false;
			for(AndroidDevice tempDevice : Devices.getDevices())
			{
				isDeviceFound = false;
				for(int i = 0; i < tabCount; i++)
				{
					if(this.deviceControlPane.getTitleAt(i).equals(tempDevice.getDeviceID()))
					{
						isDeviceFound = true;
						break;
					}
				}
				
				if(!isDeviceFound)
				{
					this.deviceControlPane.addTab(tempDevice.getDeviceID(), new DeviceControlPane(tempDevice, Devices.getDevices(), null));
					this.deviceControlPane.setTabComponentAt(tabCount, new ButtonTabComponent(this.deviceControlPane));
					this.deviceControlPane.setToolTipTextAt(tabCount, "Device: " + tempDevice.getDeviceName() + "-" + tempDevice.getDeviceID());
					
					tabCount++;
				}
			}
		}
		else
		{
			for(int i = 0; i < tabCount; i++)
			{
				if(this.deviceControlPane.getTitleAt(i).equals(this.device.getDeviceID()))
				{
					JOptionPane.showMessageDialog(null, "Device [" + device.getDeviceID() + "] exists at tab " + i);
					return;
				}
			}

			this.deviceControlPane.addTab(device.getDeviceID(), new DeviceControlPane(this.device, Devices.getDevices(), null));
			this.deviceControlPane.setTabComponentAt(tabCount, new ButtonTabComponent(this.deviceControlPane));
			this.deviceControlPane.setToolTipTextAt(tabCount, "Device: " + this.device.getDeviceName() + "-" + this.device.getDeviceID());
		}
	}

}
