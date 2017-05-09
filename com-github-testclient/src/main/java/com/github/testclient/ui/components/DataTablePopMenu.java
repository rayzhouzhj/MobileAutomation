package com.github.testclient.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.github.testclient.TestManager;
import com.github.testclient.ui.SchedulerFrame;
import com.github.testclient.ui.components.action.AssociateDeviceAction;
import com.github.testclient.ui.components.action.DuplicateDataTableRowAction;
import com.github.testclient.ui.components.action.ShowLogAction;

public class DataTablePopMenu extends JPopupMenu {

	private JMenuItem schedule = new JMenuItem("Schedule");
	private JMenuItem addColumn = new JMenuItem("Add Column");
	private JMenuItem deleteColumn = new JMenuItem("Delete Column");
	private JMenuItem hideColumn = new JMenuItem("Hide Column");
	private JMenuItem showColumns = new JMenuItem("Show Columns");
	private JMenuItem deleteRows = new JMenuItem("Delete Rows");
	private JMenuItem duplicateRows = new JMenuItem("Duplicate Rows");
	private JMenuItem addRow = new JMenuItem("Add Row");
	private JMenuItem associateDevice = new JMenuItem("Associate Device");
	private JMenuItem showLog = new JMenuItem("Show Log");
	
	
	private DataTable dataTable;
	private TestSuiteControlPane deviceControlPane;
	
	public DataTablePopMenu(TestSuiteControlPane deviceControlPane)
	{
		super();
		
		this.deviceControlPane = deviceControlPane;
		dataTable = (DataTable)deviceControlPane.getTestCaseDataTable();
		 
//		this.add(addColumn);
//		this.add(deleteColumn);
		this.add(schedule);
		this.add(hideColumn);
		this.add(showColumns);
		this.add(addRow);
		this.add(deleteRows);
		this.add(duplicateRows);
		this.add(associateDevice);
		this.add(showLog);
		
		schedule.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SchedulerFrame scheduler = deviceControlPane.getScheduler();
				scheduler.updateTitle();
				scheduler.setVisible(true);
			}
		});
		
		addRow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            	DataTableModel tableModel = (DataTableModel)dataTable.getModel();
            	tableModel.addEmptyRow();
            }
        });
		
		hideColumn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            	dataTable.hideSelectedColumn();
            }
        });
		
		showColumns.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            	dataTable.showHidedColumns();
            }
        });
		
		addColumn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            	DataTableModel tableModel = (DataTableModel)dataTable.getModel();
            	tableModel.AddColumn("New Column", "");
            	
            	TableColumn newCol = new TableColumn();
    		    newCol.setHeaderValue("New Column");
            }
        });
		
		deleteColumn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(TestManager.getInstance(DataTablePopMenu.this.deviceControlPane.getTestSuiteName()).isRunning())
            	{
            		JOptionPane.showMessageDialog(null, 
            				"Please stop scripts on current device before removing!", 
            				"Scripts are Running",
            		        JOptionPane.WARNING_MESSAGE);
            		
            		return;
            	}
            	
            	int column = dataTable.getSelectedColumn();
            	System.out.println("Selected Column: " + column);
            	
            	if(dataTable.getColumnCount() == 0) return;
            	
            	TableColumnModel columnModel = dataTable.getColumnModel();
            	columnModel.removeColumn(columnModel.getColumn(column));

            	dataTable.clearSelection();
            }
        });
		
		deleteRows.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(TestManager.getInstance(DataTablePopMenu.this.deviceControlPane.getTestSuiteName()).isRunning())
            	{
            		JOptionPane.showMessageDialog(null, 
            				"Please stop scripts on current device before removing!", 
            				"Scripts are Running",
            		        JOptionPane.WARNING_MESSAGE);
            		
            		return;
            	}
            	
            	int[] rows = dataTable.getSelectedRows();
            	int selectedRow = -1;
            	if(dataTable.getRowCount() == 0) return;
            	
            	DataTableModel tableModel = (DataTableModel)dataTable.getModel();

            	for(int i=0; i<rows.length; i++)
            	{
            		selectedRow = dataTable.convertRowIndexToModel(rows[i] - i);
            		System.out.println("Remove selected row: " + selectedRow);
                	tableModel.removeRow(selectedRow);
                }
                
            	dataTable.clearSelection();
            }
        });
		
		duplicateRows.addActionListener(new DuplicateDataTableRowAction(this.dataTable));
		
		associateDevice.addActionListener(new AssociateDeviceAction(deviceControlPane));
		
		showLog.addActionListener(new ShowLogAction(deviceControlPane));
	}
}
