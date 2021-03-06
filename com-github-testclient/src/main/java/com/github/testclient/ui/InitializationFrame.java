package com.github.testclient.ui;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.github.testclient.models.Devices;
import com.github.testclient.util.AndroidDevice;
import com.github.testclient.util.DeviceConfiguration;

/**
 *
 * @author Ray_Zhou
 */
public class InitializationFrame extends javax.swing.JFrame {

	private boolean isInitCompleted = false;
	private String displayText = "Initializing";
	/**
	 * Creates new form NewJFrame
	 */
	public InitializationFrame() {
		this.setTitle("TestManager");
		initComponents();
		initDeviceList();
	}
	
	public InitializationFrame(String displayText) {
		this.setTitle("TestManager");
		this.displayText = displayText;
		
		initComponents();
		initDeviceList();
	}

	public List<AndroidDevice> getAvailableDevices()
	{
		return this.devices;
	}
	
	public void launchChooseDeviceWindow()
	{
		if(this.devices.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "No Device Connected.", "", JOptionPane.INFORMATION_MESSAGE);
		}
		
		this.setVisible(false);
		
		Devices.setDevices(this.devices);
//		ChooseDeviceFrame.launchChooseDevice(this.devices);
		NewChooseDeviceFrame.launchChooseDevice(this.devices);
	}
	
	private void initDeviceList() 
	{
		new Thread(()->
		{
			DeviceConfiguration config = new DeviceConfiguration();
			try 
			{
				devices = config.getDivces();
				
				isInitCompleted = true;
			} 
			catch (Exception e) {
				e.printStackTrace();

				System.out.println("ERROR: Fail to read devices data.");
			}
		}).start();
	}

	public void waitForInitialization()
	{
		while(!isInitCompleted)
		{
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					if(initLabel.getText().endsWith("..."))
					{
						initLabel.setText(InitializationFrame.this.displayText);
					}
					else
					{
						initLabel.setText(initLabel.getText() + ".");
					}

					InitializationFrame.this.repaint();
				}
			});

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(InitializationFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {

		initLabel = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

		initLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
		initLabel.setText("Initializing...");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(64, 64, 64)
						.addComponent(initLabel)
						.addContainerGap(68, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(50, Short.MAX_VALUE)
						.addComponent(initLabel)
						.addGap(45, 45, 45))
				);

		pack();
	}// </editor-fold>                        

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(InitializationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(InitializationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(InitializationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(InitializationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
		//</editor-fold>

//		/* Create and display the form */
//		java.awt.EventQueue.invokeLater(new Runnable() {
//			public void run() {
				InitializationFrame initFram = new InitializationFrame();
				initFram.setLocationRelativeTo(null);
				initFram.setVisible(true);
				initFram.waitForInitialization();
				initFram.launchChooseDeviceWindow();
//			}
//		});
	}

	private List<AndroidDevice> devices;

	// Variables declaration - do not modify                     
	private javax.swing.JLabel initLabel;
	// End of variables declaration                   
}
