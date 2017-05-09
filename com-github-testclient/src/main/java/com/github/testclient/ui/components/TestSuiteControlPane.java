package com.github.testclient.ui.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.github.testclient.context.Context;
import com.github.testclient.ui.SchedulerFrame;
import com.github.testclient.ui.TestManagerFrame;
import com.github.testclient.ui.components.listener.RunButtonActionListener;
import com.github.testclient.ui.components.listener.SaveAsTemplateMouseListener;
import com.github.testclient.ui.components.listener.StopButtonActionListener;
import com.github.testclient.ui.constant.ExecutionOption;
import com.github.testclient.ui.constant.TestCaseDataTableColumns;
import com.github.testclient.util.AndroidDevice;

public class TestSuiteControlPane extends JPanel{
	private static final long serialVersionUID = 1L;
	private String suiteName;
	private List<AndroidDevice> associated_devices;
	private List<AndroidDevice> all_devices;
	private SchedulerFrame scheduler;
	private String defaultTemplate;
	
	public TestSuiteControlPane(List<AndroidDevice> selectedDevices, List<AndroidDevice> devices, String template) {
		this.associated_devices = selectedDevices;
		this.all_devices = devices;
		this.defaultTemplate = template;
		
		initComponents();
	}
	
	/**
	 * Creates new form NewUI
	 */
	public TestSuiteControlPane() {
		all_devices = new ArrayList<>();
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

		dataChartSplitPane = new javax.swing.JSplitPane();
		dataPane = new javax.swing.JPanel();
		actionButtonPane = new javax.swing.JPanel();
		runButton = new javax.swing.JButton();
		runOption = new javax.swing.JComboBox();
		stopButton = new javax.swing.JButton();
		stopOption = new javax.swing.JComboBox();
		saveAsTemplateButton = new javax.swing.JButton();
		dataSourceDataTablePane = new javax.swing.JSplitPane();
		dataSourceTabbedPane = new javax.swing.JTabbedPane();
		fileTreeScrollPane = new javax.swing.JScrollPane();
		fileTree = new FileTree();
		templateScrollPane = new javax.swing.JScrollPane();
		dataTablePane = new javax.swing.JPanel();
		dataTableSplitPane = new javax.swing.JScrollPane();
		globalTblScrollPane = new javax.swing.JScrollPane();
		globalVarsTbl = new GlobalVarDataTable();
		testCaseTblScrollPane = new javax.swing.JScrollPane();
		testCasesTbl = new TestCaseDataTable(this);
		testCasesTbl.setComponentPopupMenu(new DataTablePopMenu(this));
		templateList = new TemplateList(globalVarsTbl, testCasesTbl);
		summaryChartPane = new SummaryChartPane(this, testCasesTbl);
		dataChartSplitPane.setRightComponent(summaryChartPane);
		dataChartSplitPane.setDividerLocation(700);
		dataChartSplitPane.setOneTouchExpandable(true);
		

		runButton.setText("RUN");
        runOption.setModel(new javax.swing.DefaultComboBoxModel(
        		new String[] {
        				ExecutionOption.SELECTED, 
        				ExecutionOption.PASSED, 
        				ExecutionOption.FAILED, 
        				ExecutionOption.ABORTED, 
        				ExecutionOption.NA, 
        				ExecutionOption.CANCELLED, 
        				ExecutionOption.TIMEOUT, 
        				ExecutionOption.NON_PASSED}));
        
        runButton.addActionListener(new RunButtonActionListener(this));
        if(this.associated_devices == null)
		{
        	runButton.setEnabled(false);
		}
        
        stopButton.setText("STOP");
        stopButton.addActionListener(new StopButtonActionListener(this));
        
        stopOption.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Current Script", "All" }));

        saveAsTemplateButton.setText("Save As Template");
        saveAsTemplateButton.addMouseListener(new SaveAsTemplateMouseListener(testCasesTbl, templateList));

		javax.swing.GroupLayout actionButtonPaneLayout = new javax.swing.GroupLayout(actionButtonPane);
		actionButtonPane.setLayout(actionButtonPaneLayout);
		actionButtonPaneLayout.setHorizontalGroup(
				actionButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionButtonPaneLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(runButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(runOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(stopButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(stopOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(46, 46, 46)
						.addComponent(saveAsTemplateButton)
						.addContainerGap())
				);
		actionButtonPaneLayout.setVerticalGroup(
				actionButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(actionButtonPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(actionButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(saveAsTemplateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(actionButtonPaneLayout.createSequentialGroup()
										.addGroup(actionButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(stopOption)
												.addComponent(runOption))
												.addGap(3, 3, 3)))
												.addGap(17, 17, 17))
				);

		dataSourceDataTablePane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		dataSourceDataTablePane.setDividerLocation(220);
		dataSourceDataTablePane.setOneTouchExpandable(true);

		fileTreeScrollPane.setViewportView(fileTree);

		dataSourceTabbedPane.addTab("Scripts", fileTreeScrollPane);

		templateScrollPane.setViewportView(templateList);

		dataSourceTabbedPane.addTab("Templates", templateScrollPane);

		dataSourceDataTablePane.setLeftComponent(dataSourceTabbedPane);

		testCaseTblScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Test Cases"));

		testCaseTblScrollPane.setViewportView(testCasesTbl);

		javax.swing.GroupLayout dataTablePaneLayout = new javax.swing.GroupLayout(dataTablePane);
		dataTablePane.setLayout(dataTablePaneLayout);
		dataTablePaneLayout.setHorizontalGroup(
				dataTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(testCaseTblScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
				);
		dataTablePaneLayout.setVerticalGroup(
				dataTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(testCaseTblScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
				);

		dataSourceDataTablePane.setRightComponent(dataTablePane);

		javax.swing.GroupLayout dataPaneLayout = new javax.swing.GroupLayout(dataPane);
		dataPane.setLayout(dataPaneLayout);
		dataPaneLayout.setHorizontalGroup(
				dataPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(actionButtonPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(dataSourceDataTablePane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
				);
		dataPaneLayout.setVerticalGroup(
				dataPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPaneLayout.createSequentialGroup()
						.addComponent(dataSourceDataTablePane)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(actionButtonPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				);

		dataChartSplitPane.setLeftComponent(dataPane);

		javax.swing.GroupLayout fullScreenLayout = new javax.swing.GroupLayout(this);
		this.setLayout(fullScreenLayout);
		fullScreenLayout.setHorizontalGroup(
				fullScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(fullScreenLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(dataChartSplitPane)
						.addContainerGap())
				);
		fullScreenLayout.setVerticalGroup(
				fullScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(fullScreenLayout.createSequentialGroup()
						.addGap(8, 8, 8)
						.addComponent(dataChartSplitPane, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);


	}// </editor-fold>                        

	public void associateDevice(AndroidDevice device)
	{
		this.associated_devices.add(device);
		
		// If for Parallel testing
		if(Context.getInstance().getAttribute("RUNNING_MODE").equals("PARALLEL"))
		{
			TestManagerFrame topFrame = (TestManagerFrame)SwingUtilities.getWindowAncestor(this);
			JTabbedPane pane = topFrame.getTabbedPane();
			int tabCount = pane.getTabCount();
			for(int i = 0; i < tabCount; i++)
			{
				if(pane.getTitleAt(i).equals(this.associated_devices.get(0).getDeviceID()))
				{
					JOptionPane.showMessageDialog(null, "Device [" + device.getDeviceID() + "] exists at tab " + i);
					return;
				}
			}

			int index = pane.getSelectedIndex();
			pane.setTitleAt(index, device.getDeviceID());
			pane.setToolTipTextAt(index, "Device: " + device.getDeviceName() + "-" + device.getDeviceID());

			this.endableRunButton();
			this.revalidate();
			this.repaint();

			((DataTableModel)this.getTestCaseDataTable().getModel())
			.updateColumnValue(TestCaseDataTableColumns.DEVICE.INDEX, device.getDeviceID());
		}
		
	}
	
	public String getDefaultTemplate()
	{
		return this.defaultTemplate;
	}
	
	public void endableRunButton()
	{
		this.runButton.setEnabled(true);
	}
	
	public javax.swing.JTable getTestCaseDataTable()
	{
		return this.testCasesTbl;
	}
	
	public javax.swing.JTable getGlobalDataTable()
	{
		return this.globalVarsTbl;
	}
	
	public List<AndroidDevice> getSelectedDevice()
	{
		return this.associated_devices;
	}
	
	public List<AndroidDevice> getAvailableDeviceList()
	{
		return this.all_devices;
	}
	
	public String getTestSuiteName()
	{
		return this.suiteName;
	}
	
	public void setTestSuiteName(String name)
	{
		this.suiteName = name;
	}
	
	public SchedulerFrame getScheduler()
	{
		if(this.scheduler == null)
		{
			this.scheduler = new SchedulerFrame(this);
			this.scheduler.setLocationRelativeTo(null);
		}
		
		return this.scheduler;
	}
	
	public JButton getRunButton()
	{
		return this.runButton;
	}
	
	public JComboBox getRunOption()
	{
		return this.runOption;
	}
	
	public JButton getStopButton()
	{
		return this.stopButton;
	}
	
	public JComboBox getStopOption()
	{
		return this.stopOption;
	}

	// Variables declaration - do not modify                     
	private javax.swing.JPanel actionButtonPane;
	private javax.swing.JPanel dataPane;
	private javax.swing.JSplitPane dataSourceDataTablePane;
	private javax.swing.JTabbedPane dataSourceTabbedPane;
	private javax.swing.JPanel dataTablePane;
	private javax.swing.JScrollPane dataTableSplitPane;
	private javax.swing.JTree fileTree;
	private javax.swing.JScrollPane fileTreeScrollPane;
	private javax.swing.JScrollPane globalTblScrollPane;
	private javax.swing.JTable globalVarsTbl;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JButton runButton;
	private javax.swing.JComboBox runOption;
	private javax.swing.JButton saveAsTemplateButton;
	private javax.swing.JButton stopButton;
	private javax.swing.JComboBox stopOption;
	private javax.swing.JList templateList;
	private javax.swing.JScrollPane templateScrollPane;
	private javax.swing.JScrollPane testCaseTblScrollPane;
	private javax.swing.JTable testCasesTbl;
	private javax.swing.JSplitPane dataChartSplitPane;
	private SummaryChartPane summaryChartPane;
	
	// End of variables declaration   
}