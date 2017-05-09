package com.github.testclient.ui.components.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import com.github.testclient.TestManager;
import com.github.testclient.ui.components.DeviceControlPane;

public class StopButtonActionListener implements ActionListener {

	private JComboBox action;
	private DeviceControlPane deviceControlPane;

	public StopButtonActionListener(DeviceControlPane deviceControlPane) {
		this.action = deviceControlPane.getStopOption();
		this.deviceControlPane = deviceControlPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TestManager tm = TestManager.getInstance(this.deviceControlPane.getSelectedDevice());
		if(tm.getScheduledTestCaseCount() == 0 || !tm.isRunning())
		{
			return;
		}
		
		if(action.getSelectedItem().toString().equalsIgnoreCase("All"))
		{
			new Thread(()->
			{
				tm.stopBatchExecution();	
			}).start();
		}
		else
		{
			new Thread(()->
			{
				tm.stopTestExecution();	
			}).start();
		}
	}

}
