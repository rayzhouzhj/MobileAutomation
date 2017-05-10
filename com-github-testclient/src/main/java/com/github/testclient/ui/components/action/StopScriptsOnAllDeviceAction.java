package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

import com.github.testclient.ui.components.TestSuiteControlPane;

public class StopScriptsOnAllDeviceAction extends AbstractAction{

	private static final long serialVersionUID = -4843477089798303334L;
	private JTabbedPane tabbedPane;

	public StopScriptsOnAllDeviceAction(String title, JTabbedPane deviceControlPane)
	{
		super(title);
		this.tabbedPane = deviceControlPane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		int tabCount = this.tabbedPane.getTabCount();

		for(int index = 0; index < tabCount; index++)
		{
			JButton stopBtn = ((TestSuiteControlPane)tabbedPane.getComponentAt(index)).getStopButton();
			JComboBox stopOption = ((TestSuiteControlPane)tabbedPane.getComponentAt(index)).getStopOption();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run()
				{
					stopOption.setSelectedItem("All");
					stopBtn.doClick();
				}
			});
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
