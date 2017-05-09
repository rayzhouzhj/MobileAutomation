package com.github.testclient.ui.components.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.github.testclient.context.Constants;
import com.github.testclient.models.TestCase;
import com.github.testclient.ui.components.DataTableModel;
import com.github.testclient.ui.components.FileTree;
import com.github.testclient.ui.components.TestCaseDataTable;

public class AddTestCaseAction  extends AbstractAction{

	TestCaseDataTable table;
	FileTree tree;
	
	public AddTestCaseAction(JTable testCasesTbl, JTree directory)
	{
		super("Add TestCases");
		this.table = (TestCaseDataTable)testCasesTbl;
		this.tree = (FileTree)directory;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String jTreeVarSelectedPath = "";
		TreePath[] paths = tree.getSelectionPaths();
		
		if(paths == null)
		{
			JOptionPane.showMessageDialog(null, "No script is selected.");
			return;
		}
		
		TestCase testCase;
		for(TreePath path : paths)
		{
			jTreeVarSelectedPath = Constants.CLASS_PATH + ".";
			Object[] fp = path.getPath();
			for (int i=0; i<fp.length; i++) {
				
				jTreeVarSelectedPath += fp[i];
				if (i+1 <fp.length ) {
					jTreeVarSelectedPath += ".";
				}
			}

			testCase = new TestCase(jTreeVarSelectedPath);
			((DataTableModel)this.table.getModel()).addRow(testCase);
		}
	}

}
