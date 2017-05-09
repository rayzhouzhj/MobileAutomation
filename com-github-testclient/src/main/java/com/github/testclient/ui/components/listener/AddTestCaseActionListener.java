package com.github.testclient.ui.components.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.github.testclient.context.Configurations;
import com.github.testclient.models.TestCase;
import com.github.testclient.ui.components.DataTableModel;
import com.github.testclient.ui.components.FileTree;
import com.github.testclient.ui.components.TestCaseDataTable;

public class AddTestCaseActionListener implements ActionListener {

	TestCaseDataTable table;
	FileTree tree;

	public AddTestCaseActionListener(JTable testCasesTbl, JTree directory)
	{
		this.table = (TestCaseDataTable)testCasesTbl;
		this.tree = (FileTree)directory;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		String jTreeVarSelectedPath = "";
		TreePath[] paths = tree.getSelectionPaths();
		

		TestCase testCase;
		for(TreePath path : paths)
		{
			jTreeVarSelectedPath = Configurations.getInstance().getAttribute("DEFAULTPATH") + File.separator;
			Object[] fp = path.getPath();
			for (int i=0; i<fp.length; i++) {
				
				jTreeVarSelectedPath += fp[i];
				if (i+1 <fp.length ) {
					jTreeVarSelectedPath += File.separator;
				}
			}

			testCase = new TestCase(jTreeVarSelectedPath);
			((DataTableModel)this.table.getModel()).addRow(testCase);
		}
	}


}
