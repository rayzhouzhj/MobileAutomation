package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.github.testclient.TestManager;
import com.github.testclient.ui.LogViewerFrame;
import com.github.testclient.ui.components.DeviceControlPane;
import com.github.testclient.util.AndroidDevice;

public class ShowLogAction  extends AbstractAction{

	private DeviceControlPane deviceControlPane;
	
	public ShowLogAction(DeviceControlPane deviceControlPane) {
		this.deviceControlPane = deviceControlPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(this.deviceControlPane.getSelectedDevice() == null)
		{
			JOptionPane.showMessageDialog(null, "No device is associated!");
			
			return;
		}
		
		AndroidDevice device = deviceControlPane.getSelectedDevice();
		LogViewerFrame logViewer = TestManager.getInstance(device).getLogViewer();
		logViewer.setTitle("LogViewer: " + device.getDeviceName() + "-" + device.getDeviceID());
		logViewer.setVisible(true);
	}

}
