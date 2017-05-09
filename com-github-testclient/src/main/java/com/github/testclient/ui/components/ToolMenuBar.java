package com.github.testclient.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import com.github.testclient.models.Devices;
import com.github.testclient.ui.ScheduleControlFrame;
import com.github.testclient.ui.TestManagerFrame;
import com.github.testclient.ui.components.action.AddDeviceAction;
import com.github.testclient.ui.components.action.ExitAction;
import com.github.testclient.ui.components.action.RefreshDeviceAction;
import com.github.testclient.ui.components.action.RunScriptsOnAllDeviceAction;
import com.github.testclient.ui.components.action.StopScriptsOnAllDeviceAction;
import com.github.testclient.util.AndroidDevice;

public class ToolMenuBar extends JMenuBar {

	private JMenu fileMenu = new JMenu();
	private JMenuItem closeAppMenuItem = new JMenuItem();

	private JMenu deviceMenu = new JMenu();
	private JMenuItem refreshDeviceMenuItem = new JMenuItem();
	private JMenu addDeviceSubMenu = new JMenu();

	private JMenu toolMenu = new JMenu();
	private JMenuItem schedulerMenuItem = new JMenuItem();

	private JMenu actionMenu = new JMenu();
	private JMenuItem runAllMenuItem = new JMenuItem();
	private JMenuItem stopAllMenuItem = new JMenuItem();

	private JMenu helpMenu = new JMenu();

	private JTabbedPane tabbedControlPane;
	private TestManagerFrame frame;

	public ToolMenuBar(TestManagerFrame frame)
	{
		super();
		this.frame = frame;
		this.tabbedControlPane = frame.getTabbedPane();

		initComponent();
	}

	private void initComponent()
	{
		//=============File Menu=====================
		fileMenu.setText("File");
		this.add(fileMenu);

		closeAppMenuItem.setAction(new ExitAction());
		fileMenu.add(closeAppMenuItem);

		//=============Device Menu=====================
		deviceMenu.setText("Device");
		this.add(deviceMenu);

		refreshDeviceMenuItem.setAction(new RefreshDeviceAction(addDeviceSubMenu, this.tabbedControlPane));
		deviceMenu.add(refreshDeviceMenuItem);

		addDeviceSubMenu.setText("Add Device");

		JMenuItem tempMenu = new JMenuItem();
		tempMenu.setText("ALL");
		tempMenu.setAction(new AddDeviceAction(tempMenu.getText(), null, tabbedControlPane));

		addDeviceSubMenu.add(tempMenu);
		addDeviceSubMenu.addSeparator();
		for(AndroidDevice device : Devices.getDevices())
		{
			tempMenu = new JMenuItem();
			tempMenu.setText(device.getDeviceName() + "-" + device.getDeviceID());
			tempMenu.setAction(new AddDeviceAction(tempMenu.getText(), device, tabbedControlPane));
			addDeviceSubMenu.add(tempMenu);
		}

		deviceMenu.add(addDeviceSubMenu);

		//=============Tools Menu=====================
		toolMenu.setText("Tools");
		schedulerMenuItem.setText("Scheduler");
		schedulerMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ScheduleControlFrame scheduler = ToolMenuBar.this.frame.getScheduler();
				scheduler.setVisible(true);
			}
		});
		toolMenu.add(schedulerMenuItem);
		this.add(toolMenu);

		//=============Help Menu=====================
		actionMenu.setText("Actions");
		runAllMenuItem.setAction(new RunScriptsOnAllDeviceAction("Run Scripts on All Devices", ToolMenuBar.this.frame.getTabbedPane()));
		stopAllMenuItem.setAction(new StopScriptsOnAllDeviceAction("Stop Scripts on All Devices", ToolMenuBar.this.frame.getTabbedPane()));
		actionMenu.add(runAllMenuItem);
		actionMenu.add(stopAllMenuItem);
		this.add(actionMenu);

		//=============Help Menu=====================
		helpMenu.setText("Help");
		this.add(helpMenu);

	}
}
