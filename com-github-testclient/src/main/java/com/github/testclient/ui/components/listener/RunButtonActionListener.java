package com.github.testclient.ui.components.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.github.testclient.TestManager;
import com.github.testclient.models.TestCase;
import com.github.testclient.ui.components.DataTableModel;
import com.github.testclient.ui.components.DeviceControlPane;
import com.github.testclient.ui.components.GlobalVarDataTable;
import com.github.testclient.ui.components.TestCaseDataTable;
import com.github.testclient.ui.constant.TestCaseDataTableColumns;
import com.github.testclient.util.DeviceConfiguration;

public class RunButtonActionListener implements ActionListener {

	private Component button;
	private GlobalVarDataTable globalvarTable;
	private TestCaseDataTable testCaseDataTable;
	private JComboBox filter;
	private boolean isDeviceAvailable = false;
	private DeviceControlPane deviceControlPane;

	public RunButtonActionListener(DeviceControlPane deviceControlPane)
	{
		this.button = deviceControlPane.getRunButton();
		this.globalvarTable = (GlobalVarDataTable) deviceControlPane.getGlobalDataTable();
		this.testCaseDataTable = (TestCaseDataTable) deviceControlPane.getTestCaseDataTable();
		this.filter = deviceControlPane.getRunOption();
		this.deviceControlPane = deviceControlPane;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if(!button.isEnabled()) return;

		button.setEnabled(false);

		String defalutDevice = this.deviceControlPane.getSelectedDevice().getDeviceID();
		
		// Refresh Device ID for all test cases
		((DataTableModel)this.testCaseDataTable.getModel()).updateColumnValue(
				TestCaseDataTableColumns.DEVICE.INDEX, 
				defalutDevice);
		
		DeviceConfiguration config = new DeviceConfiguration();
		try {
			List<String> devices = config.getDivceList();
			if(!devices.contains(defalutDevice))
			{
				JOptionPane.showMessageDialog(null, "Device " + defalutDevice + " is disconnected, please reconnect device and try again!");
				button.setEnabled(true);
				
				return;
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		List<TestCase> list = this.testCaseDataTable.getFilteredTestCases(this.globalvarTable.getGlobalVars(), this.filter.getSelectedItem().toString());

		if(list.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "No Script is selected.");
			button.setEnabled(true);

			return;
		}
		
		TestManager tm = TestManager.getInstance(this.deviceControlPane.getSelectedDevice());
		tm.clearPendingQueue();
		tm.loadTestCase(list);

		// Start Test Manager
		new Thread(tm).start();

		// Monitor the status change of each test case
		Thread updateTMStatus = new Thread(new Runnable(){

			@Override
			public void run() {

				try 
				{
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				while(tm.isRunning() || !tm.isStatusChangeQueueEmpty())
				{
					while(!tm.isStatusChangeQueueEmpty())
					{
						try 
						{
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						final TestCase data = tm.getTestCaseFromStatusChangeQueue();

						java.awt.EventQueue.invokeLater(new Runnable() {
							public void run() {
								((DataTableModel) testCaseDataTable.getModel()).updateRow(data);
							}
						});
					}

					try 
					{
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}});
		updateTMStatus.start();

		// Enable the Run button once test execution is done
		Thread checkTMStatus = new Thread(new Runnable(){

			@Override
			public void run() {
				while(tm.isRunning())
				{
					try 
					{
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						button.setEnabled(true);
					}
				});
			}});
		checkTMStatus.start();

		System.out.println("Done on Click");
	}

}
