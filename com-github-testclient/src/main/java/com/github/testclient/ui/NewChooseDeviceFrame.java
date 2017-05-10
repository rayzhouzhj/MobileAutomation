package com.github.testclient.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import com.github.testclient.context.Context;
import com.github.testclient.util.AndroidDevice;

public class NewChooseDeviceFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewChooseDeviceFrame
     */
    public NewChooseDeviceFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        executionOption = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        executionModelPanel = new javax.swing.JPanel();
        paralletTest = new javax.swing.JRadioButton();
        distributeTest = new javax.swing.JRadioButton();
        testSuitePanel = new javax.swing.JPanel();
        testSuite = new javax.swing.JComboBox<>();
        chooseDevicesPanel = new javax.swing.JScrollPane();
        chooseDeviceList = new javax.swing.JList<>();
        optionButtonPanel = new javax.swing.JPanel();
        selectButton = new javax.swing.JButton();
        skipButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

		if(launchTestManagerFlag)
		{
			setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		}
		else
		{
			setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		}
		
		setTitle("Choose Test Suite and Devices");

		initDevicesList();
		initTestSuiteList();
		
        executionModelPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")), "Execution Model"));

        executionOption.add(paralletTest);
        paralletTest.setText("Parallel Test");
        paralletTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	parallelTestActionPerformed(evt);
            }
        });
        
        executionOption.add(distributeTest);
        distributeTest.setText("Distribute Test");
        distributeTest.setSelected(true);
        distributeTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distributeTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout executionModelPanelLayout = new javax.swing.GroupLayout(executionModelPanel);
        executionModelPanel.setLayout(executionModelPanelLayout);
        executionModelPanelLayout.setHorizontalGroup(
            executionModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(executionModelPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(distributeTest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(paralletTest)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        executionModelPanelLayout.setVerticalGroup(
            executionModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(executionModelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(executionModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(distributeTest)
                    .addComponent(paralletTest))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        testSuitePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("TestSuite"));

        javax.swing.GroupLayout testSuitePanelLayout = new javax.swing.GroupLayout(testSuitePanel);
        testSuitePanel.setLayout(testSuitePanelLayout);
        testSuitePanelLayout.setHorizontalGroup(
            testSuitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testSuitePanelLayout.createSequentialGroup()
                .addComponent(testSuite, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        testSuitePanelLayout.setVerticalGroup(
            testSuitePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testSuitePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testSuite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chooseDevicesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Choose Devices"));

        chooseDevicesPanel.setViewportView(chooseDeviceList);

		selectButton.setText("Select");
		selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				selectButtonMouseClicked(evt);
			}
		});
		
		skipButton.setText("Skip");
		skipButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				skipButtonMouseClicked(evt);
			}
		});
		
		// If enter from refresh device, disable skip
		if(!this.launchTestManagerFlag)
		{
			skipButton.setVisible(false);
//			selectTemplateLabel.setVisible(false);
//			templateList.setVisible(false);
		}
		
		quitButton.setText("Cancel");
		quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				quitButtonMouseClicked(evt);
			}
		});

        javax.swing.GroupLayout optionButtonPanelLayout = new javax.swing.GroupLayout(optionButtonPanel);
        optionButtonPanel.setLayout(optionButtonPanelLayout);
        optionButtonPanelLayout.setHorizontalGroup(
            optionButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionButtonPanelLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(selectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48)
                .addComponent(skipButton)
                .addGap(55, 55, 55)
                .addComponent(quitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(66, 66, 66))
        );
        optionButtonPanelLayout.setVerticalGroup(
            optionButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionButtonPanelLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(optionButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectButton)
                    .addComponent(skipButton)
                    .addComponent(quitButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chooseDevicesPanel)
                    .addComponent(optionButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(testSuitePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(executionModelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addComponent(chooseDevicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optionButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(executionModelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(testSuitePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(251, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void distributeTestActionPerformed(java.awt.event.ActionEvent evt) {                                               
        Context.getInstance().setAttribute("DistributeTest", "TRUE");
    }   
    
    private void parallelTestActionPerformed(java.awt.event.ActionEvent evt) {                                               
        Context.getInstance().setAttribute("DistributeTest", "FALSE");
    } 

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
            java.util.logging.Logger.getLogger(NewChooseDeviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewChooseDeviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewChooseDeviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewChooseDeviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewChooseDeviceFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JList<String> chooseDeviceList;
    private javax.swing.JScrollPane chooseDevicesPanel;
    private javax.swing.JRadioButton distributeTest;
    private javax.swing.JPanel executionModelPanel;
    private javax.swing.ButtonGroup executionOption;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel optionButtonPanel;
    private javax.swing.JRadioButton paralletTest;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton selectButton;
    private javax.swing.JButton skipButton;
    private javax.swing.JComboBox<String> testSuite;
    private javax.swing.JPanel testSuitePanel;
    // End of variables declaration  

	/**
	 * Creates new form ChooseDevice
	 */
	public NewChooseDeviceFrame(List<AndroidDevice> devices) {
		this.alldevices = devices;
		this.selectedDevices = new ArrayList<>();
		launchTestManagerFlag = true;
		Context.getInstance().setAttribute("DistributeTest", "TRUE");
		
		initComponents();
	}

	public NewChooseDeviceFrame(List<AndroidDevice> devices, boolean launchFlag) {
		this.alldevices = devices;
		this.selectedDevices = new ArrayList<>();
		this.launchTestManagerFlag = launchFlag;
		Context.getInstance().setAttribute("DistributeTest", "TRUE");
		
		initComponents();
	}
	
	private void quitButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if(launchTestManagerFlag)
		{
			System.exit(0);
		}
		else
		{
			this.dispose();
		}
	}  
	
	private void selectButtonMouseClicked(java.awt.event.MouseEvent evt) {  
		if(this.alldevices == null || this.alldevices.size() == 0) return;

		boolean isDeviceFound = false;

		List<String> deviceList = this.chooseDeviceList.getSelectedValuesList();
		
		for(AndroidDevice device : alldevices)
		{
			if(deviceList.contains(device.getDeviceName() + "/" + device.getDeviceID()))
			{
				this.selectedDevices.add(device);
				isDeviceFound = true;
			}
		}

		if(!isDeviceFound)
		{
			if(this.launchTestManagerFlag)
			{
				TestManagerFrame.launch(null, alldevices, null);
			}

			this.setVisible(false);
		}
		else
		{
			if(this.launchTestManagerFlag)
			{
				TestManagerFrame.launch(this.selectedDevices, this.alldevices, this.testSuite.getSelectedItem().toString());
			}
			this.setVisible(false);
		}
	}                                         

	private void skipButtonMouseClicked(java.awt.event.MouseEvent evt) {   
		if(this.launchTestManagerFlag)
		{
			TestManagerFrame.launch(null, this.alldevices, null);
		}
		
		this.setVisible(false);
	}

	public List<AndroidDevice> getselectedDevices()
	{
		return this.selectedDevices;
	}
	
	private boolean launchTestManagerFlag = false;
	private List<AndroidDevice> alldevices;
	private List<AndroidDevice> selectedDevices;


	private void initDevicesList() {
		try
		{
			DefaultListModel<String> listModel = new DefaultListModel<>();

			for(AndroidDevice item : alldevices)
			{
				listModel.addElement(item.getDeviceName() + "/" + item.getDeviceID());
				this.chooseDeviceList.setModel(listModel);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	private void initTestSuiteList() {
		try
		{
			// Empty template
			this.testSuite.addItem("");
			
			Files.list(Paths.get("./templates"))
					.map(f -> f.getFileName().toString())
					.filter(s -> s.endsWith("template.xml") && !s.endsWith("default.template.xml"))
					.forEach(e -> this.testSuite.addItem(e.toString()));
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void launchChooseDevice(List<AndroidDevice> devices) {
		NewChooseDeviceFrame chooseDevice = new NewChooseDeviceFrame(devices);
		chooseDevice.setLocationRelativeTo(null);
		chooseDevice.setVisible(true);
	}

}