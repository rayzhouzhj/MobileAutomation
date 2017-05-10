package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.github.testclient.TestManager;
import com.github.testclient.ui.LogViewerFrame;
import com.github.testclient.ui.components.TestSuiteControlPane;
import com.github.testclient.util.AndroidDevice;

public class ShowLogAction  extends AbstractAction{

	private TestSuiteControlPane deviceControlPane;
	
	public ShowLogAction(TestSuiteControlPane deviceControlPane) {
		this.deviceControlPane = deviceControlPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		int[] rows = deviceControlPane.getTestCaseDataTable().getSelectedRows();
		int selectedRow = -1;
		
		if(rows.length == 0)
		{
			JOptionPane.showMessageDialog(null, "No testcase is selected!");
			return;
		}
		
		if(deviceControlPane.getTestCaseDataTable().getRowCount() == 0)
		{
			return;
		}
		
		selectedRow = deviceControlPane.getTestCaseDataTable().convertRowIndexToModel(rows[0]);
		
		TestManager tm = TestManager.getInstance(deviceControlPane.getTestSuiteName());
		
		LogViewerFrame logViewer = tm.getTestCaseByID(selectedRow).getLogViewer();
		if(logViewer != null)
		{
			logViewer.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Selected testcase is not Running!");
			return;
		}
	}

}
