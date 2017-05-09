package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.github.testclient.models.Devices;
import com.github.testclient.ui.InitializationFrame;
import com.github.testclient.util.AndroidDevice;

public class RefreshDeviceAction extends AbstractAction{

	private JMenu addDeviceMenu;
	private JTabbedPane tabbedPane;
	
	public RefreshDeviceAction(JMenu addDeviceMenu, JTabbedPane pane)
	{
		super("Refresh Devices");
		this.addDeviceMenu = addDeviceMenu;
		this.tabbedPane = pane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new Thread(new Runnable() {
			public void run() {
				InitializationFrame initFram = new InitializationFrame("Refreshing");
				initFram.setLocationRelativeTo(null);
				initFram.setVisible(true);
				initFram.waitForInitialization();
				initFram.setVisible(false);

				Devices.setDevices(initFram.getAvailableDevices());
				initFram.dispose();
				
				RefreshDeviceAction.this.addDeviceMenu.removeAll();
				
		        JMenuItem tempMenu = new JMenuItem();
		        tempMenu.setText("ALL");
		        tempMenu.setAction(new AddDeviceAction(tempMenu.getText(), null, RefreshDeviceAction.this.tabbedPane));
		        
		        RefreshDeviceAction.this.addDeviceMenu.add(tempMenu);
		        RefreshDeviceAction.this.addDeviceMenu.addSeparator();
		        for(AndroidDevice device : Devices.getDevices())
		        {
		        	tempMenu = new JMenuItem();
		        	tempMenu.setText(device.getDeviceName() + "-" + device.getDeviceID());
		        	tempMenu.setAction(new AddDeviceAction(tempMenu.getText(), device, RefreshDeviceAction.this.tabbedPane));
		        	RefreshDeviceAction.this.addDeviceMenu.add(tempMenu);
		        } 
		        
		        JOptionPane.showMessageDialog(null, "Refresh devices completed!");
			}
		}).start();
	}

}
