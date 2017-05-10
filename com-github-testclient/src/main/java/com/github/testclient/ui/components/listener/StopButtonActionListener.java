package com.github.testclient.ui.components.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.github.testclient.TestManager;
import com.github.testclient.models.TestCase;
import com.github.testclient.ui.components.GlobalVarDataTable;
import com.github.testclient.ui.components.TestCaseDataTable;
import com.github.testclient.ui.components.TestSuiteControlPane;

public class StopButtonActionListener implements ActionListener {

	private JComboBox action;
	private TestSuiteControlPane deviceControlPane;
	private GlobalVarDataTable globalvarTable;
	private TestCaseDataTable testCaseDataTable;

	public StopButtonActionListener(TestSuiteControlPane deviceControlPane) {
		this.action = deviceControlPane.getStopOption();
		this.deviceControlPane = deviceControlPane;
		this.globalvarTable = (GlobalVarDataTable) deviceControlPane.getGlobalDataTable();
		this.testCaseDataTable = (TestCaseDataTable) deviceControlPane.getTestCaseDataTable();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		List<TestCase> list = this.testCaseDataTable.getFilteredTestCases(this.globalvarTable.getGlobalVars(), this.action.getSelectedItem().toString());

		if(list.size() == 0 && this.action.getSelectedItem().toString().equalsIgnoreCase("Selected"))
		{
			JOptionPane.showMessageDialog(null, "No Script is selected.");

			return;
		}
		
		TestManager tm = TestManager.getInstance(this.deviceControlPane.getTestSuiteName());
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
				tm.stopTestExecution(list);	
			}).start();
		}
	}

}
